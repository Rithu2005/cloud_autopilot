# ECR Repositories for all microservices
resource "aws_ecr_repository" "repos" {
  for_each = toset([
    "api-gateway", 
    "auth-service", 
    "monitoring-service", 
    "incident-service", 
    "scaling-service", 
    "notification-service", 
    "discovery-service", 
    "frontend"
  ])
  name                 = "${var.project_name}-${each.key}"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }

  tags = { Name = "${var.project_name}-repo-${each.key}" }
}
