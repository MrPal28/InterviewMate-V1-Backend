
# InterviewMate - Backend

**InterviewMate** is a microservice-based backend system designed for a modern interview preparation platform.
It provides authentication, notifications, practice management, mock interviews, resume tools, job boards, and API gateway functionalities, 
all orchestrated via Docker for easy deployment. 
The backend is built using **Spring Boot**, **Eureka**, and **Docker**, ensuring scalability and maintainability.

---

## Table of Contents
- [Project Overview](#project-overview)
- [Repository Structure](#repository-structure)
- [Technologies Used](#technologies-used)
- [Setup & Installation](#setup--installation)
- [Running the Services](#running-the-services)
- [Microservices Details](#microservices-details)
- [Docker & Docker-Compose](#docker--docker-compose)
- [Contributing](#contributing)
- [License](#license)

---

## Project Overview
The backend of InterviewMate provides core functionalities including:

- **User Service:** Handles user registration, login, profile management, and roles.
- **Notification Service:** Sends email and system notifications to users.
- **API Gateway:** Routes requests to respective microservices and provides centralized access.
- **Eureka Server:** Service discovery for all microservices.
- **Coding Service:** Manages user practice sessions, topics, and recommendations.
- **Interview Service:** Simulates interviews with AI-driven questions and scoring.
- **Resume  Service:** Helps users build professional resumes & Evaluates resumes and gives improvement suggestions.
- **Judge Worker:** Middleware to evaluate the code
The system uses **Docker** and **Docker Compose** to orchestrate all services, including dependencies like MySQL, MongoDB, Redis, and Kafka.

---

## Repository Structure
```

InterviewMate-backend/
│
├─ .vscode/                     # VSCode workspace settings
├─ api-gateway/                 # API Gateway microservice
│  └─ api-gateway/
├─ eureka-server/               # Eureka service discovery
│  └─ eureka-server/
├─ notification-service/        # Notification microservice
│  └─ notification-service/
├─ user-service/                # User microservice
│  └─ user-service/
├─ docker-compose.infra.yml
├─ docker-compose.master.yml           # Docker Compose setup for all services
├─ coding-service/coding-service            # Practice recommendation service
├─ interview-service/interview-service      # Mock Interview microservice
├─ resume-service/      # Resume Builder& Analyzer microservice

````

---

## Technologies Used
- **Backend:** Java 21, Spring Boot  
- **Service Discovery:** Eureka  
- **Gateway:** Spring Cloud Gateway  
- **Database:** MySQL (Dockerized)  , MongoDB
- **Caching:** Redis (Dockerized)  
- **Messaging:** Kafka (Dockerized)  
- **Containerization:** Docker, Docker Compose  

---

## Setup & Installation

### Prerequisites
- Java 21 JDK
- Maven
- Docker & Docker Compose
- Git
- MongoDB atlas Connection String

### Steps
1. **Clone the repository**
```bash
mkdir InterviewMate
git clone https://github.com/MrPal28/InterviewMate-V1-backend
cd InterviewMate
git checkout backend
````
2. **Configure environment variables** 

---

3. **Build & Run microservices**

```bash
docker compose -f docker-compose.master.yml up -d
```

This command will start:

* API Gateway
* Eureka Server
* User Service
* Notification Service
* Coding Service
* Interview Service (Not done yet)
* Resume Service
* MySQL (if configured)
* Redis (if configured)
* Kafka
* MongoDB

Check logs with:

```bash
docker-compose logs -f
```

### Accessing Services (All are Private By Default except gateway & the ports are configurable )

* **Eureka Dashboard:** `http://localhost:8761/`
* **API Gateway:** `http://localhost:8080/`
* **User Service API:** `http://localhost:8081/`
* **Notification Service API:** `http://localhost:8082/`
* **Coding Service API:** `http://localhost:8083/`
* **Interview API:** `http://localhost:8084/`
* **Resume API:** `http://localhost:8086/`


---

## Microservices Details

### 1. User Service

* Handles user registration, authentication, and role-based access.
* Built with Spring Boot and MySQL.

### 2. Notification Service

* Handles email notifications and other user alerts.
* Integrates with JavaMail or other notification protocols.

### 3. API Gateway

* Routes incoming requests to respective microservices.
* Implements load balancing and centralized security.

### 4. Eureka Server

* Provides service discovery for all microservices.
* Ensures dynamic scaling and service registration.

### 5. Coding Service

* Handles recommendation of practice topics and session tracking.
* Uses AI/ML models for topic recommendations.

### 6. Mock Interview Service

* Conducts simulated interviews for users.
* Provides AI-generated questions and scoring.

### 7. Resume Service

* Helps users create professional resumes.
* Supports templates and formatting options.
* Evaluates resumes and suggests improvements.
* Provides scoring and skill-matching suggestions.

---

## Docker & Docker-Compose

* Each microservice has its own `Dockerfile`.
* The `docker-compose.master.yml` orchestrates all services.
* Supports easy deployment in local or cloud environments.

**Example Commands:**

```bash
docker-compose up --build       # Build and start all services
docker-compose down             # Stop and remove all containers
docker ps                       # Check running containers
```

---

## Contributing

We welcome contributions!

1. Fork the repository.
2. Create a new branch: `git checkout -b feature/YourFeature`
3. Make your changes and commit: `git commit -m "Add your message"`
4. Push to the branch: `git push origin feature/YourFeature`
5. Open a Pull Request.

---


---

**InterviewMate Backend** provides a scalable foundation for building a full-featured interview preparation platform using modern microservices architecture, with new services continuously being added for a complete learning ecosystem.

```
