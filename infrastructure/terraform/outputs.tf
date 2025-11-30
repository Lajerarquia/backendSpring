output "vpc_id" {
  description = "VPC ID"
  value       = aws_vpc.levelup_vpc.id
}

output "usuarios_db_endpoint" {
  description = "Usuarios database endpoint"
  value       = aws_db_instance.usuarios_db.endpoint
  sensitive   = true
}

output "core_db_endpoint" {
  description = "Core database endpoint"
  value       = aws_db_instance.core_db.endpoint
  sensitive   = true
}

output "carrito_db_endpoint" {
  description = "Carrito database endpoint"
  value       = aws_db_instance.carrito_db.endpoint
  sensitive   = true
}

output "sqs_queue_url" {
  description = "SQS Queue URL"
  value       = aws_sqs_queue.payment_queue.url
}

output "sqs_queue_arn" {
  description = "SQS Queue ARN"
  value       = aws_sqs_queue.payment_queue.arn
}

output "lambda_role_arn" {
  description = "Lambda execution role ARN"
  value       = aws_iam_role.lambda_execution_role.arn
}

output "s3_bucket_name" {
  description = "S3 bucket name"
  value       = aws_s3_bucket.levelup_assets.bucket
}
