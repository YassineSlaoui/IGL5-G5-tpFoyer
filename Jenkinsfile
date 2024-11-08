pipeline {
    environment {
        registry = "dfcn0/tpFoyer-springboot"
        registryCredential = 'dockerhub_credentials'
        sonarCredential = 'sonarqube_credentials'
        awsCredentialsId = 'aws_credentials'

        clusterName = 'tpFoyerK8sCluster'
        region = 'us-east-1'
    }

    agent any

    stages {
        stage('Maven Clean') {
            steps {
                echo 'Cleaning the project...'
                sh 'mvn clean'
            }
        }

        stage('Artifact Construction') {
            steps {
                echo 'Constructing artifact...'
                sh 'mvn package -Dmaven.test.skip=true -P test-coverage'
            }
        }

        stage('Unit Tests') {
            steps {
                echo 'Running Unit Tests...'
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube Analysis...'
                withCredentials([usernamePassword(credentialsId: sonarCredential, usernameVariable: 'SONAR_USER', passwordVariable: 'SONAR_PASS')]) {
                    sh '''
                    mvn sonar:sonar \
                        -Dsonar.host.url=http://sonarqube:9000 \
                        -Dsonar.login=$SONAR_USER \
                        -Dsonar.password=$SONAR_PASS
                    '''
                }
            }
        }

        stage('Publish to Nexus') {
            steps {
                echo 'Publishing artifact to Nexus...'
                sh 'mvn deploy'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building Docker image...'
                    dockerImage = docker.build("${registry.toLowerCase()}:latest")
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    echo "Pushing Docker image..."
                    docker.withRegistry('https://registry.hub.docker.com', registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }

        stage('Test AWS Credentials') {
            steps {
                withCredentials([file(credentialsId: awsCredentialsId, variable: 'AWS_CREDENTIALS_FILE')]) {
                    script {
                        def awsCredentials = readFile(AWS_CREDENTIALS_FILE).trim().split("\n")
                        env.AWS_ACCESS_KEY_ID = awsCredentials.find { it.startsWith("aws_access_key_id") }.split("=")[1].trim()
                        env.AWS_SECRET_ACCESS_KEY = awsCredentials.find { it.startsWith("aws_secret_access_key") }.split("=")[1].trim()
                        env.AWS_SESSION_TOKEN = awsCredentials.find { it.startsWith("aws_session_token") }?.split("=")[1]?.trim()

                        sh 'aws configure set aws_access_key_id ${AWS_ACCESS_KEY_ID}'
                        sh 'aws configure set aws_secret_access_key ${AWS_SECRET_ACCESS_KEY}'
                        sh 'aws configure set aws_session_token ${AWS_SESSION_TOKEN}'

                        echo "AWS Access Key ID: ${env.AWS_ACCESS_KEY_ID}"

                        echo "AWS Credentials File Loaded"

                        sh 'aws sts get-caller-identity'
                    }
                }
            }
        }

        stage('Retrieve AWS Resources') {
            steps {
                withCredentials([file(credentialsId: awsCredentialsId, variable: 'AWS_CREDENTIALS_FILE')]) {
                    script {
                        def awsCredentials = readFile(AWS_CREDENTIALS_FILE).trim().split("\n")
                        env.AWS_ACCESS_KEY_ID = awsCredentials.find { it.startsWith("aws_access_key_id") }.split("=")[1].trim()
                        env.AWS_SECRET_ACCESS_KEY = awsCredentials.find { it.startsWith("aws_secret_access_key") }.split("=")[1].trim()
                        env.AWS_SESSION_TOKEN = awsCredentials.find { it.startsWith("aws_session_token") }?.split("=")[1]?.trim()

                        echo "AWS Access Key ID: ${env.AWS_ACCESS_KEY_ID}"
                        echo "AWS Credentials File Loaded"
                    }
                }
            }
        }

        stage('Terraform Setup') {
            steps {
                script {
                    sh 'terraform -chdir=terraform init'

                    sh 'terraform -chdir=terraform validate'

                    sh '''
                    terraform -chdir=terraform plan \
                        -var aws_region=${region} \
                        -var cluster_name=${clusterName}
                    '''

                    sh '''
                    terraform -chdir=terraform apply -auto-approve \
                        -var aws_region=${region} \
                        -var cluster_name=${clusterName}
                    '''
                }
            }
        }

        stage('Deploy to AWS Kubernetes (EKS)') {
            steps {
                script {
                    dir('kubernetes') {
                        // Update kubeconfig to interact with the EKS cluster
                        sh """
                        aws eks update-kubeconfig --region ${region} --name ${clusterName}
                        kubectl apply -f mysql-secrets.yaml
                        kubectl apply -f mysql-configMap.yaml
                        """

                        sh """
                        export cluster_name=${clusterName}
                        envsubst < db-deployment.yaml > rendered-db-deployment.yaml
                        kubectl apply -f rendered-db-deployment.yaml
                        """

                        // Substitute the cluster name in app-deployment.yaml using envsubst
                        sh """
                        export cluster_name=${clusterName}
                        envsubst < app-deployment.yaml > rendered-app-deployment.yaml
                        kubectl apply -f rendered-app-deployment.yaml
                        """
                    }
                }
            }
        }

        stage('Install Node Exporter') {
            steps {
                script {
                    def nodeExporterInstalled = sh(script: "helm list -q -n monitoring | grep -w prometheus-node-exporter", returnStatus: true) == 0
                    if (!nodeExporterInstalled) {
                        sh 'helm repo add prometheus-community https://prometheus-community.github.io/helm-charts'
                        sh 'helm repo update'
                        sh 'helm install prometheus-node-exporter prometheus-community/prometheus-node-exporter -n monitoring --create-namespace'
                        sh 'nohup kubectl port-forward --address 0.0.0.0 svc/prometheus-node-exporter 9100:9100 -n monitoring &'
                    } else {
                        echo 'Node Exporter is already installed in the monitoring namespace. Skipping installation.'
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}