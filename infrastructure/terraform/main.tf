terraform {
  required_version = ">= 1.5.0"
  
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = var.aws_region
}

# VPC Configuration
resource "aws_vpc" "levelup_vpc" {
  cidr_block           = "10.0.0.0/16"
  enable_dns_hostnames = true
  enable_dns_support   = true

  tags = {
    Name = "levelup-vpc"
    Project = "LevelUp-Microservices"
  }
}

# Public Subnets
resource "aws_subnet" "public_subnet_1" {
  vpc_id                  = aws_vpc.levelup_vpc.id
  cidr_block              = "10.0.1.0/24"
  availability_zone       = "${var.aws_region}a"
  map_public_ip_on_launch = true

  tags = {
    Name = "levelup-public-subnet-1"
  }
}

resource "aws_subnet" "public_subnet_2" {
  vpc_id                  = aws_vpc.levelup_vpc.id
  cidr_block              = "10.0.2.0/24"
  availability_zone       = "${var.aws_region}b"
  map_public_ip_on_launch = true

  tags = {
    Name = "levelup-public-subnet-2"
  }
}

# Private Subnets
resource "aws_subnet" "private_subnet_1" {
  vpc_id            = aws_vpc.levelup_vpc.id
  cidr_block        = "10.0.10.0/24"
  availability_zone = "${var.aws_region}a"

  tags = {
    Name = "levelup-private-subnet-1"
  }
}

resource "aws_subnet" "private_subnet_2" {
  vpc_id            = aws_vpc.levelup_vpc.id
  cidr_block        = "10.0.11.0/24"
  availability_zone = "${var.aws_region}b"

  tags = {
    Name = "levelup-private-subnet-2"
  }
}

# Internet Gateway
resource "aws_internet_gateway" "levelup_igw" {
  vpc_id = aws_vpc.levelup_vpc.id

  tags = {
    Name = "levelup-igw"
  }
}

# Route Table for Public Subnets
resource "aws_route_table" "public_rt" {
  vpc_id = aws_vpc.levelup_vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.levelup_igw.id
  }

  tags = {
    Name = "levelup-public-rt"
  }
}

# Associate Route Table with Public Subnets
resource "aws_route_table_association" "public_1" {
  subnet_id      = aws_subnet.public_subnet_1.id
  route_table_id = aws_route_table.public_rt.id
}

resource "aws_route_table_association" "public_2" {
  subnet_id      = aws_subnet.public_subnet_2.id
  route_table_id = aws_route_table.public_rt.id
}

# Security Group for RDS
resource "aws_security_group" "rds_sg" {
  name        = "levelup-rds-sg"
  description = "Security group for RDS instances"
  vpc_id      = aws_vpc.levelup_vpc.id

  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["10.0.0.0/16"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "levelup-rds-sg"
  }
}

# DB Subnet Group
resource "aws_db_subnet_group" "levelup_db_subnet" {
  name       = "levelup-db-subnet"
  subnet_ids = [aws_subnet.private_subnet_1.id, aws_subnet.private_subnet_2.id]

  tags = {
    Name = "levelup-db-subnet-group"
  }
}

# RDS Instance for Gestion Usuarios
resource "aws_db_instance" "usuarios_db" {
  identifier             = "levelup-usuarios-db"
  engine                 = "mysql"
  engine_version         = "8.0.35"
  instance_class         = var.db_instance_class
  allocated_storage      = 20
  storage_type           = "gp3"
  db_name                = "usuarios_db"
  username               = var.db_username
  password               = var.db_password
  db_subnet_group_name   = aws_db_subnet_group.levelup_db_subnet.name
  vpc_security_group_ids = [aws_security_group.rds_sg.id]
  skip_final_snapshot    = true
  publicly_accessible    = false

  tags = {
    Name = "levelup-usuarios-db"
  }
}

# RDS Instance for Core Negocio
resource "aws_db_instance" "core_db" {
  identifier             = "levelup-core-db"
  engine                 = "mysql"
  engine_version         = "8.0.35"
  instance_class         = var.db_instance_class
  allocated_storage      = 20
  storage_type           = "gp3"
  db_name                = "core_db"
  username               = var.db_username
  password               = var.db_password
  db_subnet_group_name   = aws_db_subnet_group.levelup_db_subnet.name
  vpc_security_group_ids = [aws_security_group.rds_sg.id]
  skip_final_snapshot    = true
  publicly_accessible    = false

  tags = {
    Name = "levelup-core-db"
  }
}

# RDS Instance for Carrito
resource "aws_db_instance" "carrito_db" {
  identifier             = "levelup-carrito-db"
  engine                 = "mysql"
  engine_version         = "8.0.35"
  instance_class         = var.db_instance_class
  allocated_storage      = 20
  storage_type           = "gp3"
  db_name                = "carrito_db"
  username               = var.db_username
  password               = var.db_password
  db_subnet_group_name   = aws_db_subnet_group.levelup_db_subnet.name
  vpc_security_group_ids = [aws_security_group.rds_sg.id]
  skip_final_snapshot    = true
  publicly_accessible    = false

  tags = {
    Name = "levelup-carrito-db"
  }
}

# SQS Queue for Orders/Payments
resource "aws_sqs_queue" "payment_queue" {
  name                       = "levelup-payment-queue"
  delay_seconds              = 0
  max_message_size           = 262144
  message_retention_seconds  = 345600 # 4 days
  receive_wait_time_seconds  = 10
  visibility_timeout_seconds = 300

  tags = {
    Name = "levelup-payment-queue"
  }
}

# SQS Dead Letter Queue
resource "aws_sqs_queue" "payment_dlq" {
  name = "levelup-payment-dlq"

  tags = {
    Name = "levelup-payment-dlq"
  }
}

# IAM Role for Lambda
resource "aws_iam_role" "lambda_execution_role" {
  name = "levelup-lambda-execution-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "lambda.amazonaws.com"
        }
      }
    ]
  })

  tags = {
    Name = "levelup-lambda-role"
  }
}

# IAM Policy for Lambda to access SQS
resource "aws_iam_policy" "lambda_sqs_policy" {
  name = "levelup-lambda-sqs-policy"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "sqs:ReceiveMessage",
          "sqs:DeleteMessage",
          "sqs:GetQueueAttributes"
        ]
        Resource = aws_sqs_queue.payment_queue.arn
      },
      {
        Effect = "Allow"
        Action = [
          "logs:CreateLogGroup",
          "logs:CreateLogStream",
          "logs:PutLogEvents"
        ]
        Resource = "arn:aws:logs:*:*:*"
      }
    ]
  })
}

# Attach policy to role
resource "aws_iam_role_policy_attachment" "lambda_sqs_attach" {
  role       = aws_iam_role.lambda_execution_role.name
  policy_arn = aws_iam_policy.lambda_sqs_policy.arn
}

# S3 Bucket for assets
resource "aws_s3_bucket" "levelup_assets" {
  bucket = "levelup-ecommerce-assets-${var.environment}"

  tags = {
    Name = "levelup-assets"
  }
}

# S3 Bucket versioning
resource "aws_s3_bucket_versioning" "levelup_assets_versioning" {
  bucket = aws_s3_bucket.levelup_assets.id

  versioning_configuration {
    status = "Enabled"
  }
}

# CloudWatch Log Group
resource "aws_cloudwatch_log_group" "levelup_logs" {
  name              = "/aws/levelup-microservices"
  retention_in_days = 7

  tags = {
    Name = "levelup-logs"
  }
}
