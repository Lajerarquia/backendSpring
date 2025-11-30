#!/bin/bash

echo "======================================"
echo "LevelUp Microservices - Docker Swarm"
echo "======================================"

GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

# Initialize Swarm
echo -e "${YELLOW}Initializing Docker Swarm...${NC}"
docker swarm init 2>/dev/null || echo "Swarm already initialized"

# Create secrets
echo -e "\n${YELLOW}Creating Docker Secrets...${NC}"
echo "root" | docker secret create db_username - 2>/dev/null || echo "db_username already exists"
echo "rootpassword" | docker secret create db_password - 2>/dev/null || echo "db_password already exists"
echo "https://sqs.us-east-1.amazonaws.com/123456789/levelup-payment-queue" | docker secret create sqs_queue_url - 2>/dev/null || echo "sqs_queue_url already exists"

# Create network
echo -e "\n${YELLOW}Creating overlay network...${NC}"
docker network create --driver overlay --attachable levelup-network 2>/dev/null || echo "Network already exists"

# Deploy stack
echo -e "\n${YELLOW}Deploying LevelUp stack...${NC}"
docker stack deploy -c docker-compose.yml levelup

echo -e "\n${GREEN}Deployment complete!${NC}"
echo "Services:"
docker service ls

echo -e "\nAccess: http://localhost"
