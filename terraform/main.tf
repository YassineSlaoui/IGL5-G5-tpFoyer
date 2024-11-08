provider "aws" {
  region = var.aws_region
}

# Create a new VPC
resource "aws_vpc" "eks_vpc" {
  cidr_block           = "10.0.0.0/16"
  enable_dns_support   = true
  enable_dns_hostnames = true
  tags = {
    Name = "${var.cluster_name}-vpc"
  }
}

# Create a public subnet
resource "aws_subnet" "public_subnet" {
  vpc_id                  = aws_vpc.eks_vpc.id
  cidr_block              = "10.0.1.0/24"
  availability_zone       = "${var.aws_region}a"
  map_public_ip_on_launch = true
  tags = {
    Name = "${var.cluster_name}-public-subnet"
  }
}

# Create an internet gateway for the public subnet
resource "aws_internet_gateway" "eks_igw" {
  vpc_id = aws_vpc.eks_vpc.id
  tags = {
    Name = "${var.cluster_name}-igw"
  }
}

# Create a public route table and associate it with the public subnet
resource "aws_route_table" "public_rt" {
  vpc_id = aws_vpc.eks_vpc.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.eks_igw.id
  }
  tags = {
    Name = "${var.cluster_name}-public-rt"
  }
}

resource "aws_route_table_association" "public_rt_assoc" {
  subnet_id      = aws_subnet.public_subnet.id
  route_table_id = aws_route_table.public_rt.id
}

# Create a private subnet
resource "aws_subnet" "private_subnet" {
  vpc_id            = aws_vpc.eks_vpc.id
  cidr_block        = "10.0.2.0/24"
  availability_zone = "${var.aws_region}b"
  tags = {
    Name = "${var.cluster_name}-private-subnet"
  }
}

# Create a NAT Gateway in the public subnet
resource "aws_eip" "nat" {
  depends_on = [aws_internet_gateway.eks_igw]
}

resource "aws_nat_gateway" "nat_gw" {
  allocation_id = aws_eip.nat.id
  subnet_id     = aws_subnet.public_subnet.id
  tags = {
    Name = "${var.cluster_name}-nat-gateway"
  }
}

# Create a private route table for the private subnet
resource "aws_route_table" "private_rt" {
  vpc_id = aws_vpc.eks_vpc.id
  route {
    cidr_block     = "0.0.0.0/0"
    nat_gateway_id = aws_nat_gateway.nat_gw.id
  }
  tags = {
    Name = "${var.cluster_name}-private-rt"
  }
}

# Associate the private route table with the private subnet
resource "aws_route_table_association" "private_rt_assoc" {
  subnet_id      = aws_subnet.private_subnet.id
  route_table_id = aws_route_table.private_rt.id
}

# Reference the LabRole ARN directly
data "aws_iam_role" "lab_role" {
  name = "LabRole"
}

# Create an EKS cluster using LabRole
resource "aws_eks_cluster" "my_cluster" {
  name     = var.cluster_name
  role_arn = data.aws_iam_role.lab_role.arn
  version  = "1.30"

  vpc_config {
    subnet_ids = [aws_subnet.public_subnet.id, aws_subnet.private_subnet.id]
  }
}

# Existing public node group (for the application)
resource "aws_eks_node_group" "app_node_group" {
  cluster_name    = aws_eks_cluster.my_cluster.name
  node_group_name = "${var.cluster_name}-app-ng"
  node_role_arn   = data.aws_iam_role.lab_role.arn
  subnet_ids = [aws_subnet.public_subnet.id]  # Public subnet

  scaling_config {
    desired_size = 2
    max_size     = 4
    min_size     = 1
  }

  depends_on = [aws_eks_cluster.my_cluster]
}

# New private node group (for the database)
resource "aws_eks_node_group" "db_node_group" {
  cluster_name    = aws_eks_cluster.my_cluster.name
  node_group_name = "${var.cluster_name}-db-ng"
  node_role_arn   = data.aws_iam_role.lab_role.arn
  subnet_ids = [aws_subnet.private_subnet.id]  # Private subnet

  scaling_config {
    desired_size = 1
    max_size     = 2
    min_size     = 1
  }

  depends_on = [aws_eks_cluster.my_cluster]
}
