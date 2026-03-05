
# <p align="center">🚀 InterviewMate — The Future of Interview Prep</p>

<p align="center">
  <img src="https://img.shields.io/badge/Architecture-Microservices-blueviolet?style=for-the-badge&logo=micro-strategy" alt="Architecture" />
  <img src="https://img.shields.io/badge/Backend-Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot" alt="Backend" />
  <img src="https://img.shields.io/badge/Container-Docker-2496ED?style=for-the-badge&logo=docker" alt="Docker" />
  <img src="https://img.shields.io/badge/AI-Integrated-FF6F61?style=for-the-badge&logo=ai" alt="AI" />
</p>

---

## 🌌 Overview

**InterviewMate** is a cutting-edge, microservice-driven backend ecosystem engineered to revolutionize interview preparation. Leveraging **Spring Cloud**, **AI-assistants**, and **Scalable Containers**, it provides a seamless and high-performance environment for users to master their career goals.

---

## 🛠 Tech Stack — The Engine Room

| Category | Technology |
| :--- | :--- |
| **Core Framework** | ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white) ![Java 21](https://img.shields.io/badge/Java%2021-ED8B00?style=flat-square&logo=openjdk&logoColor=white) |
| **Microservices** | ![Eureka](https://img.shields.io/badge/Netflix%20Eureka-00A65A?style=flat-square&logo=netflix&logoColor=white) ![Spring Cloud Gateway](https://img.shields.io/badge/Cloud%20Gateway-6DB33F?style=flat-square&logo=spring-boot&logoColor=white) |
| **Databases** | ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white) ![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=flat-square&logo=mongodb&logoColor=white) |
| **Real-time / Caching** | ![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white) ![Kafka](https://img.shields.io/badge/Apache%20Kafka-231F20?style=flat-square&logo=apache-kafka&logoColor=white) |
| **Infrastructure** | ![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white) ![Nginx](https://img.shields.io/badge/Nginx-009639?style=flat-square&logo=nginx&logoColor=white) |
| **Evaluation** | ![Judge0](https://img.shields.io/badge/Judge0-333333?style=flat-square&logo=code-climate&logoColor=white) |

---

## 🏗 System Architecture

```mermaid
graph TD
    Client["🌐 Client Requests"] --> Gateway["🚪 API Gateway"]
    Gateway --> Eureka["🔭 Service Discovery (Eureka)"]
    
    subgraph "Core Microservices"
        Gateway --> UserService["👤 User Service"]
        Gateway --> InterviewService["🎤 Interview Service (AI)"]
        Gateway --> CodingService["💻 Coding Service"]
        Gateway --> ResumeService["📄 Resume Service (AI)"]
        Gateway --> NotificationService["🔔 Notification Service"]
    end
    
    subgraph "Infrastructure & Persistence"
        UserService --> MySQL[(MySQL)]
        CodingService --> MongoDB[(MongoDB)]
        CodingService --> JudgeWorker["⚙️ Judge Worker"]
        JudgeWorker --> Judge0["🚀 Judge0 API"]
        NotificationService --> Kafka["📩 Kafka"]
        ResumeService --> Redis[(Redis)]
    end

    classDef default fill:#1a1a1a,stroke:#333,stroke-width:2px,color:#fff;
    classDef highlight fill:#2d1b4e,stroke:#9b59b6,stroke-width:3px,color:#fff;
    class Client,Gateway,Eureka highlight;
```

---

## 🛰 Microservice Breakdown

### 👤 User Service
> *The Authentication Hub*
- **Role:** Handles registration, JWT-based security, and profile management.
- **Port:** `8081`

### 🎤 Interview Service (AI-Powered)
> *The AI Mock Interviewer*
- **Role:** Conducts simulated AI-driven interviews with real-time scoring.
- **Port:** `8084`

### 💻 Coding Service
> *The Practice Engine*
- **Role:** Recommends topics and tracks coding sessions via Judge0 integration.
- **Port:** `8083`

### 📄 Resume Service
> *The Career Architect*
- **Role:** AI-powered resume analyzer and professional builder.
- **Port:** `8086`

### 🔔 Notification Service
> *The Messenger*
- **Role:** Centralized system for email and real-time alerts via Kafka.
- **Port:** `8082`

---

## ⚡ Quick Start — Launch the Future

### 1. Requirements
- **JDK 21**
- **Docker Desktop**
- **Maven**

### 2. Initiation
```bash
# Clone the repository
git clone https://github.com/MrPal28/InterviewMate-V1-backend
cd InterviewMate-V1-backend

# Launch the entire ecosystem
docker compose -f docker-compose.master.yml up -d
```

### 3. Monitoring
- **Control Center (Eureka):** [http://localhost:8761](http://localhost:8761)
- **API Gateway (Edge):** [http://localhost:8080](http://localhost:8080)

---

## 🧩 How to Contribute

We are building the future together. 

1. **Fork** the repository
2. **Branch**: `git checkout -b feature/awesome-new-feature`
3. **Commit**: `git commit -m "Add something incredible"`
4. **Push**: `git push origin feature/awesome-new-feature`
5. **Request**: Open a Pull Request

---

<p align="center">
  <b>Built with ❤️ by InterviewMate Team.</b><br>
  <i>Mastering the art of interviewing, one service at a time.</i>
</p>
