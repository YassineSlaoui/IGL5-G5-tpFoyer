output "vpc_id" {
  value = aws_vpc.my_vpc.id
}

output "cluster_name" {
  value = aws_eks_cluster.my_cluster.name
}
