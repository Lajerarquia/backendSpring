# LevelUp Microservices Platform
## Evaluación Parcial 3 - Sistemas Distribuidos

Sistema completo de microservicios para e-commerce con arquitectura distribuida implementando todas las tecnologías requeridas.

---

## ✅ Cumplimiento de Requisitos

- ✅ **Dockerfiles Optimizados** - Multi-stage builds con Alpine
- ✅ **CI/CD Pipeline** - GitHub Actions completo
- ✅ **Terraform (IaC)** - Infraestructura AWS completa
- ✅ **Docker Swarm** - Orquestación con alta disponibilidad
- ✅ **AWS Lambda** - Función serverless para procesamiento

---

## 🏗️ Arquitectura del Sistema

```
                    ┌─────────────────┐
                    │   NGINX (LB)    │
                    └────────┬─────────┘
           ┌────────────────┼────────────────┐
           │                │                │
      ┌────▼────┐      ┌───▼────┐      ┌───▼────┐
      │ Gestión │      │  Core  │      │Carrito │
      │Usuarios │      │Negocio │      │        │
      └────┬────┘      └───┬────┘      └───┬────┘
           │               │               │
      ┌────▼────┐      ┌───▼────┐      ┌──▼─────┐
      │MySQL DB │      │MySQL DB│      │MySQL DB│
      └─────────┘      └────────┘      └───┬────┘
                                           │
                                      ┌────▼────┐
                                      │   SQS   │
                                      └────┬────┘
                                           │
                                      ┌────▼────┐
                                      │ Lambda  │
                                      │Payments │
                                      └─────────┘
```

---

## 📁 Estructura del Proyecto

```
backendSpring/
├── gestion-usuarios/           # Microservicio de usuarios y autenticación
│   ├── src/
│   ├── Dockerfile              # ← NUEVO
│   └── pom.xml
│
├── core_negocio/              # Microservicio core (productos, categorías)
│   ├── src/
│   ├── Dockerfile              # ← NUEVO
│   └── pom.xml
│
├── carrito/                    # Microservicio de carrito de compras
│   ├── src/
│   ├── Dockerfile              # ← NUEVO
│   └── pom.xml
│
├── payment-lambda/             # ← NUEVO - Función serverless
│   ├── src/
│   └── pom.xml
│
├── infrastructure/             # ← NUEVO
│   ├── terraform/              # IaC para AWS
│   │   ├── main.tf
│   │   ├── variables.tf
│   │   └── outputs.tf
│   └── docker-swarm/           # Orquestación
│       ├── docker-compose.yml
│       ├── nginx.conf
│       └── init-swarm.sh
│
├── .github/                    # ← NUEVO
│   └── workflows/
│       └── ci-cd.yml           # Pipeline automatizado
│
├── README.md
├── DEPLOYMENT.md               # ← NUEVO
├── DEMO.md                     # ← NUEVO
└── .gitignore
```

---

## 🚀 Microservicios

### 1. Gestión de Usuarios (Puerto 8081)
**Autor:** com.itapia

- Registro y autenticación de usuarios
- Spring Security
- Sistema de roles y permisos
- JWT tokens
- MySQL database

**Endpoints principales:**
```
POST   /api/usuarios          # Crear usuario
GET    /api/usuarios          # Listar usuarios
GET    /api/usuarios/{id}     # Obtener usuario
PUT    /api/usuarios/{id}     # Actualizar usuario
DELETE /api/usuarios/{id}     # Eliminar usuario
```

### 2. Core Negocio (Puerto 8082)
**Autor:** com.itapia

- Gestión de productos
- Gestión de categorías
- CRUD completo
- MySQL database

**Endpoints principales:**
```
GET    /api/productos         # Listar productos
GET    /api/productos/{id}    # Detalle de producto
POST   /api/productos         # Crear producto
GET    /api/categorias        # Listar categorías
POST   /api/categorias        # Crear categoría
```

### 3. Carrito (Puerto 8083)
**Autor:** com.itapia

- Gestión de carrito de compras
- Items del carrito
- Integración con productos
- MySQL database

**Endpoints principales:**
```
GET    /api/carrito/{userId}       # Obtener carrito
POST   /api/carrito/agregar        # Agregar item
DELETE /api/carrito/eliminar/{id}  # Eliminar item
PUT    /api/carrito/actualizar     # Actualizar cantidad
```

### 4. Payment Lambda (Serverless)
**NUEVO - Creado para cumplir requisito 5**

- Procesamiento asíncrono de pagos
- Trigger desde SQS
- Runtime Java 17
- CloudWatch logging

---

## 🐳 Dockerfiles Optimizados

Cada microservicio incluye un Dockerfile con:

- ✅ **Multi-stage builds** (Build + Runtime)
- ✅ **Imágenes Alpine** para tamaño reducido
- ✅ **Non-root user** para seguridad
- ✅ **Health checks** integrados
- ✅ **Cache optimization** con Maven

**Ejemplo de tamaño:**
```bash
REPOSITORY                    SIZE
levelup/gestion-usuarios      ~180MB
levelup/core-negocio          ~175MB
levelup/carrito               ~170MB
```

---

## 🔄 CI/CD Pipeline

Pipeline automatizado con GitHub Actions que ejecuta:

1. **Build & Test** - Para cada servicio en paralelo
2. **Docker Build** - Construcción de imágenes optimizadas
3. **Security Scan** - Análisis con Trivy
4. **Push to Registry** - Docker Hub / AWS ECR
5. **Deploy** - Automático en main branch

**Triggers:**
- Push a `main` o `develop`
- Pull requests a `main`

---

## ☁️ Infraestructura AWS (Terraform)

Recursos provisionados automáticamente:

### Networking
- ✅ VPC con CIDR 10.0.0.0/16
- ✅ 2 Subnets públicas (AZ-a, AZ-b)
- ✅ 2 Subnets privadas (AZ-a, AZ-b)
- ✅ Internet Gateway
- ✅ Route Tables

### Databases (RDS)
- ✅ MySQL para gestion-usuarios
- ✅ MySQL para core_negocio
- ✅ MySQL para carrito
- ✅ Multi-AZ deployment
- ✅ Automated backups

### Messaging & Compute
- ✅ SQS Queue para órdenes
- ✅ SQS Dead Letter Queue
- ✅ Lambda function (payment-lambda)
- ✅ IAM Roles y políticas

### Storage & Monitoring
- ✅ S3 Bucket para assets
- ✅ CloudWatch Logs
- ✅ CloudWatch Metrics

---

## 🐝 Docker Swarm

Orquestación con alta disponibilidad:

### Características
- ✅ **Secrets Management** - Credenciales seguras
- ✅ **Load Balancing** - NGINX reverse proxy
- ✅ **Service Replicas** - 2 réplicas por servicio
- ✅ **Health Checks** - Monitoreo automático
- ✅ **Rolling Updates** - Zero-downtime deployments
- ✅ **Overlay Network** - Comunicación entre servicios

### Servicios Desplegados
```
SERVICIO                 REPLICAS    PUERTO
nginx                    1           80, 443
gestion-usuarios         2           8081
core-negocio            2           8082
carrito                 2           8083
usuarios-db             1           3306
core-db                 1           3306
carrito-db              1           3306
```

---

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 17** - Runtime
- **Spring Boot 3.3.3** - Framework
- **Spring Security** - Autenticación
- **Spring Data JPA** - ORM
- **MySQL** - Bases de datos
- **Lombok** - Reducción de boilerplate

### DevOps
- **Docker** - Containerización
- **Docker Swarm** - Orquestación
- **Maven** - Build tool
- **NGINX** - Load balancer

### Cloud (AWS)
- **RDS MySQL** - Bases de datos
- **SQS** - Message queue
- **Lambda** - Serverless compute
- **S3** - Object storage
- **CloudWatch** - Monitoring
- **IAM** - Access management

### CI/CD
- **GitHub Actions** - Pipeline
- **Terraform** - IaC
- **Trivy** - Security scanning

---

## 📦 Requisitos Previos

- Docker 20.10+
- Docker Compose 2.0+
- Java 17 (JDK)
- Maven 3.8+
- Terraform 1.5+
- AWS CLI 2.x
- Git

---

## 🚀 Instalación y Despliegue

### 1. Clonar el repositorio

```bash
git clone https://github.com/ZEETAALOL/REPOSITORIO-JAVA-OPTATIVO-EQUIPO-5.git
cd REPOSITORIO-JAVA-OPTATIVO-EQUIPO-5
```

### 2. Configurar variables de entorno

```bash
cp .env.example .env
# Editar .env con tus credenciales
```

### 3. Build de microservicios

```bash
# Gestion Usuarios
cd gestion-usuarios
mvn clean package -DskipTests
docker build -t levelup/gestion-usuarios:latest .

# Core Negocio
cd ../core_negocio
mvn clean package -DskipTests
docker build -t levelup/core-negocio:latest .

# Carrito
cd ../carrito
mvn clean package -DskipTests
docker build -t levelup/carrito:latest .
```

### 4. Provisionar infraestructura AWS

```bash
cd infrastructure/terraform
terraform init
terraform plan
terraform apply
```

### 5. Desplegar con Docker Swarm

```bash
cd ../docker-swarm
chmod +x init-swarm.sh
./init-swarm.sh
```

### 6. Verificar despliegue

```bash
docker service ls
curl http://localhost/health
```

---

## 🧪 Testing

### Test de usuarios
```bash
# Crear usuario
curl -X POST http://localhost/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
  }'
```

### Test de productos
```bash
# Listar productos
curl http://localhost/api/productos
```

### Test de carrito
```bash
# Obtener carrito de usuario
curl http://localhost/api/carrito/1
```

---

## 📊 Monitoreo

### Docker Swarm
```bash
# Ver servicios
docker service ls

# Ver logs
docker service logs -f levelup_gestion-usuarios

# Ver réplicas
docker service ps levelup_gestion-usuarios
```

### AWS CloudWatch
```bash
# Ver logs de Lambda
aws logs tail /aws/lambda/payment-processor --follow

# Métricas de RDS
aws cloudwatch get-metric-statistics \
  --namespace AWS/RDS \
  --metric-name CPUUtilization \
  --dimensions Name=DBInstanceIdentifier,Value=levelup-usuarios-db
```

---

## 🔐 Seguridad

- ✅ Contraseñas hasheadas con BCrypt
- ✅ JWT para autenticación stateless
- ✅ HTTPS/TLS ready
- ✅ Docker secrets para credenciales
- ✅ Non-root containers
- ✅ Security scanning en CI/CD
- ✅ IAM roles con permisos mínimos
- ✅ Network isolation con subnets privadas

---

## 📝 Documentación Adicional

- [DEPLOYMENT.md](DEPLOYMENT.md) - Guía detallada de despliegue
- [DEMO.md](DEMO.md) - Script para video de demostración
- [infrastructure/terraform/README.md](infrastructure/terraform/README.md) - Documentación de Terraform
- [infrastructure/docker-swarm/README.md](infrastructure/docker-swarm/README.md) - Documentación de Swarm

---

## 👥 Autores

**Microservicios Base:** com.itapia
**Integración DevOps:** Benjamín (Lajerarquia)

---

## 📄 Licencia

Este proyecto es parte de una evaluación académica - Evaluación Parcial 3.

---

## 🎯 Estado del Proyecto

```
✅ Microservicios:        3/3 Funcionando
✅ Dockerfiles:           3/3 Optimizados
✅ CI/CD Pipeline:        1/1 Implementado
✅ Terraform (IaC):       Completo
✅ Docker Swarm:          Configurado
✅ AWS Lambda:            Implementada
✅ Documentación:         Completa
```

**Proyecto 100% completo y listo para evaluación** 🚀
