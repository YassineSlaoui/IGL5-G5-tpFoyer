output "vpc_id" {
  value = aws_vpc.my_vpc.id
}

output "cluster_name" {
  value = aws_eks_cluster.my_cluster.name
}

output "node_group_id" {
  value = aws_eks_node_group.my_node_group.id
}