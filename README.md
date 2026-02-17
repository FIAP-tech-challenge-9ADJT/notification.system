# Notification System - API de Gestão Educacional

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Docker](https://img.shields.io/badge/Docker-✓-2496ED)
![Maven](https://img.shields.io/badge/Maven-4.0.0-red)
![Flyway](https://img.shields.io/badge/Flyway-✓-green)
![AWS](https://img.shields.io/badge/AWS-SQS-orange)

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Arquitetura](#arquitetura)
- [Endpoints da API](#endpoints-da-api)
- [Pré-requisitos](#pré-requisitos)
- [Configuração e Deploy](#configuração-e-deploy)
- [Monitoramento](#monitoramento)
- [Estrutura do Projeto](#estrutura-do-projeto)

---

## Visão Geral

Este projeto é uma **API REST para gestão educacional** desenvolvida em **Spring Boot 3.5.3** e **Java 21**. A aplicação gerencia usuários, aulas e feedbacks, com processamento assíncrono via **AWS SQS**.

### Funcionalidades Principais

- ✅ **Gestão de Aulas**: CRUD completo de aulas/lessons
- ✅ **Sistema de Feedbacks**: Avaliação de aulas com notas 0-10
- ✅ **Processamento Assíncrono**: Feedbacks enviados para fila SQS
- ✅ **Endpoint de Avaliação**: `POST /avaliacao` conforme especificação
- ✅ **Arquitetura Limpa**: Separação por camadas (Domain, Application, Infrastructure, Presentation)
- ✅ **Migrations Automáticas**: Flyway para versionamento do schema
- ✅ **Deploy Docker**: Containerização completa com Docker Compose
- ✅ **CI/CD**: GitHub Actions para deploy automatizado

---

## Arquitetura

```
┌─────────────────────────────────────────────────────────────────────┐
│                      notification.system                             │
│                    (Spring Boot REST API)                           │
└─────────────────────────────────────────────────────────────────────┘
                                │
        ┌───────────────────────┼───────────────────────┐
        │                       │                       │
        ▼                       ▼                       ▼
┌───────────────┐     ┌────────────────┐     ┌─────────────────┐
│  Users/       │     │   Lessons      │     │   Feedbacks     │
│  Roles        │     │                │     │                 │
└───────────────┘     └───────┬────────┘     └─────────────────┘
                               │
                               │ POST /avaliacao
                               │ POST /lessons/{id}/feedbacks
                               ▼
                      ┌────────────────┐
                      │  AWS SQS       │
                      │  Queue         │
                      └────────┬───────┘
                               ▼
                      ┌────────────────┐
                      │  Lambda        │
                      │  (persistance) │
                      └────────────────┘
```

### Camadas da Arquitetura

```
presentation/     → Controllers, DTOs, Mappers (REST API)
application/      → Use Cases, Application Services
domain/           → Entities, Value Objects, Repositories Interfaces
infrastructure/   → Database, Queue, Security implementations
```

---

## Endpoints da API

### Avaliação (Especificação Requerida)

#### `POST /avaliacao`

Envia uma avaliação geral para processamento assíncrono.

**Request Body:**
```json
{
  "descricao": "O curso precisa de mais exemplos práticos",
  "nota": 7
}
```

**Response (202 ACCEPTED):**
```json
{
  "mensagem": "Avaliação recebida com sucesso",
  "dataRecebimento": "2025-01-15T10:30:00Z"
}
```

### Gestão de Aulas

#### `POST /lessons`
Criar nova aula (Professor/Admin)

**Request:**
```json
{
  "name": "Introdução ao Serverless",
  "description": "Conceitos fundamentais de arquitetura serverless"
}
```

#### `GET /lessons/{id}`
Buscar aula por ID (Público)

#### `PUT /lessons/{id}`
Atualizar aula (Professor/Admin)

#### `DELETE /lessons/{id}`
Deletar aula (Professor/Admin)

#### `POST /lessons/{id}/feedbacks`
Adicionar feedback a uma aula (Estudante/Admin)

**Request:**
```json
{
  "description": "Aula excelente, muito didática!",
  "score": 9
}
```

#### `GET /lessons/{id}/feedbacks`
Listar feedbacks de uma aula (Público)

### Gestão de Usuários

- `POST /users` - Criar usuário
- `GET /users/{id}` - Buscar usuário
- `PUT /users/{id}` - Atualizar usuário
- `DELETE /users/{id}` - Deletar usuário
- `GET /users` - Listar usuários

### Autenticação

- `POST /auth/login` - Login e geração de token JWT
- `POST /auth/refresh` - Refresh token

---

## Configuração e Deploy

### Pré-requisitos

- **Docker** e **Docker Compose** (recomendado)
- **Java 21** (para desenvolvimento local)
- **Maven** (para build local)
- **Conta AWS** (para integração com SQS)

### Variáveis de Ambiente

Crie o arquivo `.env` na raiz:

```bash
cp .env.example .env
```

**Variáveis necessárias:**

```env
# MySQL Database
MYSQL_DATABASE=notification_system
MYSQL_USER=appuser
MYSQL_PASSWORD=your_secure_password_here
MYSQL_ROOT_PASSWORD=your_secure_root_password_here

# AWS SQS (opcional - fila será criada pelo Lambda)
AWS_REGION=us-east-1
AWS_SQS_FEEDBACK_QUEUE_URL=https://sqs.us-east-1.amazonaws.com/...
AWS_ACCESS_KEY_ID=AKIA...
AWS_SECRET_ACCESS_KEY=...
```

### Deploy com Docker

**1. Subir os containers:**
```bash
docker-compose up -d --build
```

Isso inicia:
- **MySQL** na porta 3306
- **API Spring Boot** na porta 8080
- **phpMyAdmin** na porta 8081

**2. Acessar a aplicação:**
- API: http://localhost:8080
- phpMyAdmin: http://localhost:8081
  - Servidor: `mysql`
  - Usuário: valor de `MYSQL_USER`
  - Senha: valor de `MYSQL_PASSWORD`

**3. Parar os containers:**
```bash
docker-compose down
```

### Deploy Local (sem Docker)

**1. Configure um MySQL local na porta 3306**

**2. Execute a aplicação:**
```bash
mvn spring-boot:run
```

### Deploy Automatizado (GitHub Actions)

O projeto possui CI/CD configurado para:

1. **Executar testes** automaticamente
2. **Gerar relatório de cobertura** (Jacoco)
3. **Fazer build da imagem Docker**
4. **Push para Amazon ECR**

**Secrets necessários:**
- `AWS_ACCESS_KEY_ID`
- `AWS_SECRET_ACCESS_KEY`

O workflow é executado automaticamente ao fazer push para `main` ou `master`.

---

## Monitoramento

### Endpoints de Health Check

```bash
# Health check geral
curl http://localhost:8080/actuator/health

# Métricas da aplicação
curl http://localhost:8080/actuator/metrics

# Informações do ambiente
curl http://localhost:8080/actuator/info
```

### Documentação da API

OpenAPI/Swagger disponível em:
```
http://localhost:8080/swagger-ui.html
```

### Logs

Os logs são exibidos no console e podem ser configurados em `src/main/resources/logback-spring.xml`.

---

## Estrutura do Projeto

```
notification.system/
├── .github/
│   └── workflows/
│       └── deploy.yml           # GitHub Actions CI/CD
├── src/main/
│   ├── java/tech/challenge/notification/system/
│   │   ├── domain/              # Entidades e Value Objects
│   │   │   ├── entities/        # User, Lesson, Feedback, Role
│   │   │   ├── exceptions/      # Exceções de domínio
│   │   │   ├── repositories/    # Interfaces de repositórios
│   │   │   └── valueobjects/    # Name, Email, Login, Password
│   │   ├── application/         # Casos de uso
│   │   │   ├── services/        # Application Services
│   │   │   └── usecases/        # Implementações de use cases
│   │   ├── infrastructure/      # Implementações concretas
│   │   │   ├── persistence/     # JPA repositories
│   │   │   ├── queue/           # SQS Queue Service
│   │   │   └── security/        # JWT, Security config
│   │   └── presentation/        # Camada de apresentação
│   │       ├── controllers/     # REST Controllers
│   │       ├── dtos/            # Data Transfer Objects
│   │       └── mappers/         # Entity-DTO mappers
│   └── resources/
│       ├── application.properties
│       ├── application-docker.properties
│       ├── db/migration/        # Flyway migrations
│       │   ├── V1__Create_users_table.sql
│       │   ├── V2__Create_roles_table.sql
│       │   ├── V3__Create_lessons_table.sql
│       │   ├── V4__Create_feedbacks_table.sql
│       │   └── V5__add_created_at_to_feedbacks.sql
│       └── logback-spring.xml   # Configuração de logs
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md
```

---

## Comandos Úteis

```bash
# Executar testes
mvn test

# Executar testes com cobertura
mvn test jacoco:report

# Buildar projeto
mvn clean package

# Buildar sem executar testes
mvn clean package -DskipTests

# Executar aplicação local
mvn spring-boot:run

# Ver migrations Flyway
mvn flyway:info

# Reparar migrations (se necessário)
mvn flyway:repair
```

---

## Banco de Dados

### Schema (Flyway Migrations)

#### users
```sql
id          BIGINT PRIMARY KEY
name        VARCHAR(255) NOT NULL
email       VARCHAR(255) UNIQUE NOT NULL
login       VARCHAR(50) UNIQUE NOT NULL
password    VARCHAR(255) NOT NULL
role_id     BIGINT FOREIGN KEY
```

#### roles
```sql
id    BIGINT PRIMARY KEY
name  VARCHAR(50) - (TEACHER, STUDENT, ADMIN)
```

#### lessons
```sql
id          BIGINT PRIMARY KEY
name        VARCHAR(255) NOT NULL
description TEXT
score       DECIMAL(3,2) - calculado dos feedbacks
```

#### feedbacks
```sql
id          BIGINT PRIMARY KEY
description VARCHAR(200) NOT NULL
score       INT NOT NULL (0-10)
lesson_id   BIGINT FOREIGN KEY
created_at  DATETIME NOT NULL
```

---

## Segurança

### Autenticação JWT

- **Login**: Gera token JWT válido por 24h
- **Roles**: TEACHER, STUDENT, ADMIN
- **Endpoints protegidos**: Requerem header `Authorization: Bearer <token>`

### Validações

- **Score**: 0 a 10 (Bean Validation)
- **Email**: formato válido e único
- **Descrição**: máximo 200 caracteres
- **Password**: mínimo 8 caracteres

---

## Integração AWS SQS

### FeedbackQueueService

Envia feedbacks para fila SQS de forma assíncrona:

```java
feedbackQueueService.sendFeedback(
    lessonId,      // ID da aula
    description,   // Descrição do feedback
    score          // Nota 0-10
);
```

**Payload enviado:**
```json
{
  "lessonId": 123,
  "description": "Conteúdo excelente!",
  "score": 9,
  "createdAt": "2025-01-15T10:30:00Z"
}
```

---

## API Documentation

### OpenAPI/Swagger

Acesse a documentação interativa em:
```
http://localhost:8080/swagger-ui/index.html
```

### Exemplos de Requisição

**Criar uma avaliação:**
```bash
curl -X POST http://localhost:8080/avaliacao \
  -H "Content-Type: application/json" \
  -d '{
    "descricao": "Curso muito bom, mas poderia ter mais exercícios",
    "nota": 7
  }'
```

**Criar uma aula (requer autenticação):**
```bash
curl -X POST http://localhost:8080/lessons \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN>" \
  -d '{
    "name": "Introdução ao Kubernetes",
    "description": "Conceitos básicos de orquestração de containers"
  }'
```

---

## Licença

MIT License - ver arquivo LICENSE para detalhes.
