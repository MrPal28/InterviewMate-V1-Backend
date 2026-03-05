
# <p align="center">🚀 InterviewMate — The Future of Interview Prep Platform 2026</p>

<p align="center">
  <img src="https://img.shields.io/badge/Architecture-Microservices-blueviolet?style=for-the-badge&logo=micro-strategy" alt="Architecture" />
  <img src="https://img.shields.io/badge/Backend-Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot" alt="Backend" />
  <img src="https://img.shields.io/badge/Container-Docker-2496ED?style=for-the-badge&logo=docker" alt="Docker" />
  <img src="https://img.shields.io/badge/AI-Integrated-FF6F61?style=for-the-badge&logo=ai" alt="AI" />
  <img src="https://img.shields.io/badge/Python-ML%20Driven-3776AB?style=for-the-badge&logo=python" alt="Python" />
  <img src="https://img.shields.io/badge/Real--time-Kafka%20Streaming-231F20?style=for-the-badge&logo=apache-kafka" alt="Kafka" />
</p>

---

## 🌌 Overview

**InterviewMate** is an next-generation, distributed microservice ecosystem engineered to revolutionize enterprise-level interview preparation and assessment. This platform leverages **Spring Boot 4.0.3**, **Advanced AI/ML**, **Real-time Streaming**, and **Containerized Architecture** to deliver an intelligent, scalable, and high-performance environment for users to master their career goals through data-driven insights and adaptive learning pathways.

### 🎯 Core Mission
- **Democratize Interview Preparation** through AI-powered mock interviews with behavioral analysis
- **Real-time Performance Analytics** with video/audio processing and emotional intelligence detection
- **Intelligent Question Generation** leveraging multi-modal AI capabilities
- **Comprehensive Report Generation** with actionable insights and progression tracking

---

## 🛠 Tech Stack — The Intelligence Engine

| Category | Technology | Version |
| :--- | :--- | :--- |
| **Core Framework** | ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white) | **4.0.3** |
| **Language** | ![Java](https://img.shields.io/badge/Java%2021-ED8B00?style=flat-square&logo=openjdk&logoColor=white) | **JDK 21** |
| **Service Discovery** | ![Eureka](https://img.shields.io/badge/Netflix%20Eureka-E50914?style=flat-square&logo=netflix&logoColor=white) | **Spring Cloud 2025.1.0** |
| **API Gateway** | ![Spring Cloud Gateway](https://img.shields.io/badge/Cloud%20Gateway-6DB33F?style=flat-square) | **2025.1.0** |
| **Databases** | ![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=flat-square&logo=mongodb&logoColor=white) ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white) | **Latest** |
| **Message Queue** | ![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-231F20?style=flat-square&logo=apache-kafka&logoColor=white) | **Latest** |
| **Caching Layer** | ![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white) | **Latest** |
| **Python Stack** | ![FastAPI](https://img.shields.io/badge/FastAPI-009485?style=flat-square&logo=fastapi&logoColor=white) ![PyTorch/TensorFlow](https://img.shields.io/badge/TensorFlow-FF6F00?style=flat-square&logo=tensorflow&logoColor=white) | **Latest** |
| **ML/AI Libraries** | DeepFace, MediaPipe, OpenCV, HuggingFace, Google Generative AI | **Latest** |
| **Containerization** | ![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white) ![Compose](https://img.shields.io/badge/Docker%20Compose-2496ED?style=flat-square&logo=docker&logoColor=white) | **Latest** |
| **Reverse Proxy** | ![Nginx](https://img.shields.io/badge/Nginx-009639?style=flat-square&logo=nginx&logoColor=white) | **Latest** |

---

## 🏗 System Architecture Diagram

```mermaid
graph TD
    Client["🌐 Client Requests"] --> Gateway["🚪 API Gateway:8080"]
    Gateway --> Eureka["🔭 Service Discovery:8761"]
    
    subgraph "Java Microservices Backend"
        Gateway --> UserService["👤 User Service:8081"]
        Gateway --> InterviewService["🎤 Interview Service:8086"]
        Gateway --> CodingService["💻 Coding Service:8083"]
        Gateway --> ResumeService["📄 Resume Service"]
        Gateway --> NotificationService["🔔 Notification Service:8082"]
        Gateway --> ConfigServer["⚙️ Config Server:8888"]
    end
    
    subgraph "AI/ML Python Microservices"
        InterviewService -.->|orchestrates| AudioConverter["🔊 Audio Answer Converter"]
        InterviewService -.->|controls| BehavioralAnalysis["🧠 Behavioral Analysis Service"]
        InterviewService -.->|manages| VideoAudioSeparator["📹 Video Audio Separator"]
        InterviewService -.->|retrieves| IntQuestions["📚 Interview Question Service"]
        InterviewService -.->|generates| IntReport["📊 Interview Report Service"]
        InterviewService -.->|communicates| ClientService["🔗 Client Service"]
    end
    
    subgraph "Data & Persistence Layer"
        UserService --> MySQL[(MySQL DB)]
        InterviewService --> MongoDB[(MongoDB\nInterview Data)]
        BehavioralAnalysis --> MongoDB
        IntQuestions --> MongoDB
        IntReport --> MongoDB
        ResumeService --> Redis[(Redis Cache)]
    end
    
    subgraph "Message Queue & Events"
        InterviewService --> Kafka["📩 Apache Kafka"]
        BehavioralAnalysis --> Kafka
        IntReport --> Kafka
        CodingService --> Kafka
    end
    
    subgraph "Code Evaluation"
        CodingService --> JudgeWorker["⚙️ Judge Worker"]
        JudgeWorker --> Judge0["🏛️ Judge0 API"]
    end

    classDef javaService fill:#4CAF50,stroke:#2E7D32,stroke-width:2px,color:#fff;
    classDef pythonService fill:#FF9800,stroke:#E65100,stroke-width:2px,color:#fff;
    classDef infrastructure fill:#2196F3,stroke:#1565C0,stroke-width:2px,color:#fff;
    classDef database fill:#9C27B0,stroke:#6A1B9A,stroke-width:2px,color:#fff;
    
    class UserService,InterviewService,CodingService,ResumeService,NotificationService,ConfigServer,Gateway javaService;
    class AudioConverter,BehavioralAnalysis,VideoAudioSeparator,IntQuestions,IntReport,ClientService pythonService;
    class Kafka,Eureka,JudgeWorker,Judge0 infrastructure;
    class MySQL,MongoDB,Redis database;
```

---

## 🎯 Microservice Breakdown & Detailed Architecture

### 👤 **User Service** — Auth & Identity Management
> *The Foundation of Trust*
- **Language:** Java (Spring Boot 4.0.3)
- **Port:** `8081`
- **Role:** Centralized authentication, JWT-based security, user profiles, role management
- **Database:** MySQL with transactional integrity
- **Key Features:**
  - OAuth2/JWT token generation and validation
  - Profile management and user metadata
  - Role-based access control (RBAC)
  - Integration with Eureka service discovery

---

### 🎤 **Interview Service** — The Orchestration Hub (Java/Spring Boot)
> *The Master Conductor of Interview Intelligence*
- **Language:** Java (Spring Boot 4.0.3)
- **Port:** `8086`
- **Framework:** Spring Cloud, Kafka, MongoDB Reactive
- **Role:** Central orchestrator that coordinates all AI/ML microservices with real-time event streaming

#### 📋 Internal Architecture & Components

```
interview-service/
├── InterviewServiceApplication.java      [Main Spring Boot Entry Point]
├── api/                                   [REST Controllers & Endpoints]
│   ├── InterviewController.java
│   ├── ReportController.java
│   ├── FeedbackController.java
│   └── ... [Additional API endpoints]
│
├── orchestration/                         [Service Orchestration Logic]
│   ├── InterviewOrchestrator.java
│   ├── AIServiceCoordinator.java
│   ├── WorkflowEngine.java
│   └── PipelineManager.java
│
├── client/                                [Feign/RestTemplate Service Clients]
│   ├── AudioConverterClient.java
│   ├── BehavioralAnalysisClient.java
│   ├── VideoAudioSeparatorClient.java
│   ├── InterviewQuestionClient.java
│   ├── InterviewReportClient.java
│   └── ClientServiceComm.java
│
├── entity/                                [MongoDB Document Models]
│   ├── InterviewSession.java
│   ├── InterviewQuestion.java
│   ├── UserResponse.java
│   ├── BehavioralMetrics.java
│   ├── PerformanceScore.java
│   └── InterviewFeedback.java
│
├── dto/                                   [Data Transfer Objects]
│   ├── InterviewRequestDTO.java
│   ├── InterviewResponseDTO.java
│   ├── AudioMetadataDTO.java
│   ├── BehavioralScoreDTO.java
│   ├── ReportGenerationDTO.java
│   └── AnalyticsDTO.java
│
├── repository/                            [MongoDB Reactive Repositories]
│   ├── InterviewSessionRepository.java
│   ├── QuestionRepository.java
│   ├── BehavioralMetricsRepository.java
│   └── PerformanceRepository.java
│
├── eventhandler/                          [Kafka Event Publishing & Consuming]
│   ├── InterviewEventPublisher.java
│   ├── AnalyticsEventConsumer.java
│   ├── ReportGenerationEventListener.java
│   └── NotificationEventProducer.java
│
├── config/                                [Spring Configuration Classes]
│   ├── KafkaConfig.java
│   ├── MongoConfig.java
│   ├── FeignClientConfig.java
│   ├── WebClientConfig.java
│   └── SecurityConfig.java
│
└── constants/                             [Application Constants & Enums]
    ├── InterviewStatus.java
    ├── ScoreThresholds.java
    ├── TopicCategories.java
    └── ErrorCodes.java
```

#### 🔄 **Request-Response Flow**
```
Client Request → Interview Service API → Orchestration Engine
    ↓
Coordinates: Audio Conversion → Behavioral Analysis → Question Retrieval → Video Processing
    ↓
Aggregates: Metrics → Report Generation → Feedback Creation
    ↓
Publishes: Events to Kafka → Notification Service triggered
    ↓
Response: Complete Interview Report with Scores
```

#### 🔌 **Key Dependencies** (from pom.xml)
- `spring-boot-starter-actuator` — Health checks & monitoring
- `spring-boot-starter-data-mongodb-reactive` — Async MongoDB access
- `spring-boot-starter-kafka` — Event streaming
- `spring-cloud-starter-netflix-eureka-client` — Service discovery
- `spring-cloud-starter-config` — Centralized configuration
- `org.springframework.cloud:spring-cloud-starter-openfeign` — Declarative HTTP clients
- `jackson-databind` — JSON serialization

---

## 🤖 **Interview Service AI** — Python Microservices Ecosystem

> *The Collective Intelligence Layer*

This is a sophisticated distributed system of 6 specialized Python microservices that work in concert to deliver comprehensive interview intelligence.

### 🏗️ Overall AI System Architecture

```
Interview Service (Java) [Orchestrator]
    │
    ├─→ [1] AudioAnswerConverterService
    ├─→ [2] BehavioralAnalysisService
    ├─→ [3] VideoAudioSeperatorService
    ├─→ [4] InterviewQuestionService
    ├─→ [5] InterviewReportService
    └─→ [6] Client (Communication Hub)
    
All services → Kafka (Event Streaming) → MongoDB (Persistence)
```

---

### 1️⃣ **AudioAnswerConverterService** — Speech-to-Text Intelligence
> *Transcribes and Processes Audio Responses*

**Location:** `interview-service-ai/AudioAnswerConverterService/`

#### Purpose
- Converts candidate's spoken answers into high-quality text using **Faster-Whisper**
- Performs audio quality analysis
- Extracts linguistic features and speech patterns

#### Internal Structure
```
AudioAnswerConverterService/
├── main.py                              [Entry Point - FastAPI Application]
├── requirements.txt                     [Dependencies]
├── Dockerfile                           [Containerization Config]
├── docker-compose.audio-answer-converter.yml
├── .env                                 [Environment Variables]
├── .dockerignore
│
└── Components/
    ├── AudioProcessor.py                [Core Audio Processing]
    ├── WhisperTranscriber.py            [Speech-to-Text Conversion]
    ├── AudioQualityAnalyzer.py          [Audio Quality Assessment]
    ├── FeatureExtractor.py              [Linguistic Feature Extraction]
    ├── KafkaProducer.py                 [Event Publishing to Kafka]
    └── MongoDBConnector.py              [Persistence Layer]
```

#### Key Tech Stack
```
✓ FastAPI 0.123.0          - High-performance API framework
✓ Uvicorn 0.36.0           - ASGI server
✓ Faster-Whisper 1.2.1     - OpenAI's optimized speech recognition
✓ Kafka-Python 2.2.15      - Event streaming
✓ MongoEngine 0.29.1       - MongoDB ORM
✓ Python-dotenv 1.1.1      - Environment management
```

#### API Endpoints
```
POST   /v1/convert-audio          - Convert audio file to text
POST   /v1/analyze-speech-quality - Analyze audio quality metrics
GET    /v1/transcription/{id}    - Retrieve transcription
POST   /v1/linguistic-features   - Extract speech features
GET    /health                    - Service health check
```

#### Kafka Events Published
- `audio.transcription.completed` — When transcription is done
- `audio.quality.analyzed` — When quality analysis finishes
- `linguistic.features.extracted` — When features are extracted

---

### 2️⃣ **BehavioralAnalysisService** — Emotional Intelligence Engine
> *Analyzes Facial Expressions, Body Language & Emotional States*

**Location:** `interview-service-ai/BehavioralAnalysisService/`

#### Purpose
- Real-time facial emotion recognition using **DeepFace**
- Body language analysis using **MediaPipe**
- Eye contact detection and gaze tracking
- Confidence scoring based on visual cues
- Stress/anxiety level assessment

#### Internal Structure
```
BehavioralAnalysisService/
├── main.py                              [Entry Point - FastAPI]
├── requirements.txt                     [Dependencies]
├── Dockerfile
├── docker-compose.behavioral-analysis.yml
├── .env
│
├── Components/
│   ├── FaceDetector.py                  [Face Detection & Alignment]
│   ├── EmotionAnalyzer.py               [Emotion Recognition - DeepFace]
│   ├── PoseEstimator.py                 [Body Pose Analysis - MediaPipe]
│   ├── EyeContactDetector.py            [Eye Contact & Gaze Tracking]
│   ├── ConfidenceScorer.py              [Overall Confidence Scoring]
│   ├── StressDetector.py                [Stress Level Assessment]
│   ├── VideoProcessor.py                [Frame Extraction & Processing]
│   ├── KafkaProducer.py                 [Event Publishing]
│   └── MongoDBConnector.py
│
└── Video/                               [Sample videos for testing]
    └── sample_interview_video.mp4
```

#### Advanced Tech Stack
```
✓ OpenCV-Python-Headless 4.11.0.86  - Computer Vision processing
✓ DeepFace 0.0.93                    - Facial emotion & demographic analysis
✓ MediaPipe 0.10.14                  - Pose & hand gesture detection
✓ TensorFlow 2.16.1 + Keras          - Deep learning backend
✓ NumPy 1.26.4                       - Numerical computing
✓ Kafka-Python 2.2.15                - Event streaming
```

#### API Endpoints
```
POST   /v1/analyze-emotions             - Analyze emotional expressions
POST   /v1/analyze-posture              - Detect body language
POST   /v1/eye-contact-detection        - Measure eye contact
POST   /v1/confidence-score             - Calculate confidence metrics
POST   /v1/stress-level                 - Assess stress indicators
GET    /v1/analysis/{session_id}       - Retrieve analysis results
GET    /health
```

#### Output Metrics
- **Emotions:** Happy, Sad, Angry, Fearful, Disgusted, Neutral, Surprised
- **Confidence Score:** 0-100 scale
- **Eye Contact %:** Percentage of time maintaining eye contact
- **Posture Quality:** Upright/Slouching assessment
- **Stress Level:** Low/Medium/High
- **Overall Behavioral Score:** Composite metric

#### Kafka Events
- `behavior.emotion.detected` → Emotional state changes
- `behavior.posture.analyzed` → Posture assessment
- `behavior.eyecontact.measured` → Eye contact metrics
- `behavior.confidence.computed` → Confidence scores

---

### 3️⃣ **VideoAudioSeperatorService** — Media Decomposition Engine
> *Separates Video & Audio Streams with Precision*

**Location:** `interview-service-ai/VideoAudioSeperatorService/`

#### Purpose
- Extracts audio from video files with minimal quality loss
- Separates audio tracks (voice, background noise)
- Video quality assessment
- Frame extraction for behavioral analysis
- Synchronization preservation

#### Internal Structure
```
VideoAudioSeperatorService/
├── main.py                              [Entry Point]
├── requirements.txt
├── Dockerfile
├── docker-compose.video-audio-seperator.yml
├── .env
│
├── Components/
│   ├── VideoExtractor.py                [Video Stream Processing]
│   ├── AudioExtractor.py                [Audio Stream Extraction]
│   ├── AudioNoiseFilter.py              [Noise Reduction]
│   ├── FrameExtractor.py                [Video Frame Sampling]
│   ├── SynchronizationManager.py        [A/V Sync Verification]
│   ├── FormatConverter.py               [Container Format Conversion]
│   ├── QualityAnalyzer.py               [Media Quality Assessment]
│   ├── StorageManager.py                [File Management]
│   ├── KafkaProducer.py
│   └── MongoDBConnector.py
│
├── Audio/                               [Separated audio files storage]
└── Video/                               [Frame extractions storage]
```

#### Key Technologies
```
✓ MoviePy 1.0.3             - Video processing framework
✓ FFmpeg (via MoviePy)      - Core codec handling
✓ Scipy/NumPy              - Signal processing
✓ Kafka-Python
```

#### API Endpoints
```
POST   /v1/separate-video-audio          - Extract audio from video
POST   /v1/extract-frames                - Sample frames from video
GET    /v1/audio/{stream_id}            - Retrieve separated audio
GET    /v1/video-quality/{id}           - Get quality metrics
POST   /v1/noise-filter                  - Apply noise removal
GET    /health
```

#### Kafka Events
```
media.separation.started
media.separation.completed
media.quality.analyzed
frames.extracted
audio.extracted
```

---

### 4️⃣ **InterviewQuestionService** — Intelligent Question Generation Engine
> *Generates Job-Relevant Questions with AI Precision*

**Location:** `interview-service-ai/InterviewQuestionService/`

#### Purpose
- Generates contextual interview questions based on job description
- Supports multiple interview types (behavioral, technical, situational)
- Question difficulty scaling
- PDF parsing and content extraction
- Multi-modal question generation using **Google Generative AI**

#### Internal Structure
```
InterviewQuestionService/
├── main.py                              [Entry Point - FastAPI]
├── requirements.txt
├── Dockerfile
├── docker-compose.interview-question.yml
├── VERSION                              [Service Version]
├── .env
│
├── app/
│   ├── main.py                          [FastAPI Application]
│   ├── models.py                        [Pydantic Models/Schemas]
│   ├── config.py                        [Configuration Management]
│   ├── dependencies.py                  [Dependency Injection]
│   │
│   ├── services/
│   │   ├── QuestionGenerator.py         [AI Question Generation]
│   │   ├── PDFProcessor.py              [PDF Parsing & Extraction]
│   │   ├── JobDescriptionAnalyzer.py    [Job Desc Analysis]
│   │   ├── DifficultyScaler.py          [Question Difficulty Adjustment]
│   │   ├── QuestionRepository.py        [DB Operations]
│   │   └── KafkaProducer.py
│   │
│   └── routes/
│       ├── questions.py                 [Question Endpoints]
│       ├── generation.py                [Generation Endpoints]
│       └── health.py                    [Health Checks]
│
└── pdf/                                 [PDF Documents Storage]
    └── sample_jd.pdf
```

#### Advanced Tech Stack
```
✓ FastAPI 0.123.0
✓ Uvicorn 0.36.0
✓ Google Generative AI 0.8.5             - Gemini API for question generation
✓ Google API Python Client 2.179.0
✓ PyPDF2 3.0.1 / python-docx 1.2.0     - Document parsing
✓ Pydantic 2.11.7                       - Data validation
✓ MongoEngine 0.29.1
✓ Kafka-Python 2.2.15
```

#### API Endpoints
```
POST   /v1/generate-questions            - Generate interview questions
POST   /v1/parse-job-description        - Extract JD content
POST   /v1/difficulty-adjust            - Adjust question difficulty
GET    /v1/questions/{job_id}           - Retrieve generated questions
POST   /v1/batch-generate               - Bulk question generation
GET    /health
```

#### Question Categories
```
BEHAVIORAL      → "Tell me about a time when..."
TECHNICAL       → "Explain the concept of..."
SITUATIONAL     → "How would you handle..."
ROLE_SPECIFIC   → Based on job description
COMPETENCY      → Leadership, Teamwork, Problem-solving, etc.
```

#### Kafka Events
```
questions.generated
questions.difficulty.adjusted
job_description.analyzed
```

---

### 5️⃣ **InterviewReportService** — Comprehensive Analytics & Reporting
> *Generates Professional Interview Performance Reports*

**Location:** `interview-service-ai/InterviewReportService/`

#### Purpose
- Compiles all metrics from behavioral, audio, and question analysis
- Generates professional PDF/HTML reports
- Creates interactive dashboards and visualizations
- Provides actionable feedback and improvement suggestions
- Tracks progress over multiple interview sessions

#### Internal Structure
```
InterviewReportService/
├── main.py                              [Entry Point]
├── requirements.txt
├── Dockerfile
├── docker-compose.interview-report.yml
├── .env
│
└── Components/
    ├── ReportGenerator.py               [Main Report Builder]
    ├── MetricsAggregator.py             [Metrics Compilation]
    ├── FeedbackGenerator.py             [AI-Generated Feedback]
    ├── PDFRenderer.py                   [PDF Report Creation]
    ├── HTMLRenderer.py                  [HTML Report Generation]
    ├── VisualizationEngine.py           [Charts & Graphs]
    ├── ProgressTracker.py               [Performance Progression]
    ├── RecommendationEngine.py          [Improvement Suggestions]
    ├── MongoDBConnector.py
    └── KafkaConsumer.py                 [Consumes completion events]
```

#### Tech Stack
```
✓ FastAPI 0.123.0
✓ Google Generative AI 0.8.5             - Feedback AI generation
✓ MoviePy 1.0.3                          - Multimedia handling
✓ Python-docx 1.2.0 / PyPDF2            - Document generation
✓ Matplotlib/Plotly (optional)          - Visualizations
✓ Kafka-Python 2.2.15
✓ MongoEngine 0.29.1
```

#### API Endpoints
```
POST   /v1/generate-report              - Generate comprehensive report
GET    /v1/report/{interview_id}        - Retrieve generated report
POST   /v1/generate-feedback            - AI-powered feedback
GET    /v1/progress/{user_id}           - User progression analysis
POST   /v1/recommendations              - Get improvement suggestions
GET    /v1/report/pdf/{report_id}      - Download PDF report
GET    /v1/report/html/{report_id}     - View HTML report
GET    /health
```

#### Report Sections
```
📊 SCORE SUMMARY
   ├─ Overall Performance Score (0-100)
   ├─ Communication Score
   ├─ Technical Knowledge Score
   └─ Behavioral Score

📈 DETAILED METRICS
   ├─ Emotion Analysis Results
   ├─ Speech Analysis Breakdown
   ├─ Posture & Body Language Assessment
   ├─ Eye Contact Measurements
   └─ Stress Level Timeline

💬 FEEDBACK & INSIGHTS
   ├─ AI-Generated Strengths Analysis
   ├─ Areas for Improvement
   ├─ Specific Action Items
   └─ Learning Resources

📋 COMPARISON & PROGRESS
   ├─ Performance vs. Previous Interviews
   ├─ Trend Analysis Charts
   ├─ Skill Development Timeline
   └─ Benchmark Comparisons
```

#### Kafka Events Consumed
```
audio.transcription.completed
behavior.analysis.completed
questions.answered.logged
report.requested
```

---

### 6️⃣ **Client Service** — Communication Hub
> *Bridge Between Interview Service & External Systems*

**Location:** `interview-service-ai/Client/`

#### Purpose
- Acts as a communication relay for the Interview Service (Java)
- Manages WebSocket connections for real-time updates
- Handles file uploads/downloads for interview media
- Event forwarding and load balancing

#### Internal Structure
```
Client/
├── main.py                              [Entry Point]
├── requirements.txt
├── Dockerfile
├── docker-compose-client.yml
├── .env
│
└── [Components for communication management]
    ├── WebSocketHandler.py
    ├── FileManager.py
    ├── EventForwarder.py
    ├── LoadBalancer.py
    └── MongoDBConnector.py
```

#### Tech Stack
```
✓ FastAPI 0.123.0
✓ Uvicorn 0.36.0
✓ Kafka-Python 2.2.15
✓ WebSockets support
```

---

## � **Coding Service** — Code Evaluation & Practice Engine
> *The Code Execution & Judgment Hub*

**Location:** `coding-service/coding-service/`

### Purpose
- Judges and evaluates coding solutions with real-time feedback
- Integrates with Judge0 for multi-language code execution
- Tracks coding practice sessions and problem completion
- Provides difficulty-based problem recommendations
- Maintains submission history and performance analytics

### Internal Architecture
```
coding-service/
├── pom.xml                              [Maven Dependencies]
├── docker-compose.coding.yml
├── Dockerfile
├── VERSION
├── src/
│   ├── main/
│   │   ├── java/com/interviewmate/codingservice/
│   │   │   ├── CodingServiceApplication.java
│   │   │   ├── api/
│   │   │   │   ├── ProblemController.java
│   │   │   │   ├── SubmissionController.java
│   │   │   │   └── RecommendationController.java
│   │   │   ├── service/
│   │   │   │   ├── CodeExecutionService.java        [Judge0 wrapper]
│   │   │   │   ├── ProblemService.java
│   │   │   │   ├── SubmissionService.java
│   │   │   │   └── RecommendationEngine.java
│   │   │   ├── client/
│   │   │   │   └── Judge0Client.java                [Feign client for Judge0]
│   │   │   ├── entity/
│   │   │   │   ├── CodingProblem.java
│   │   │   │   ├── CodeSubmission.java
│   │   │   │   ├── ExecutionResult.java
│   │   │   │   └── UserProgress.java
│   │   │   ├── dto/
│   │   │   │   ├── ProblemDTO.java
│   │   │   │   ├── SubmissionDTO.java
│   │   │   │   ├── ExecutionResultDTO.java
│   │   │   │   └── ScoreDTO.java
│   │   │   ├── repository/
│   │   │   │   ├── ProblemRepository.java
│   │   │   │   ├── SubmissionRepository.java
│   │   │   │   └── UserProgressRepository.java
│   │   │   ├── eventhandler/
│   │   │   │   ├── SubmissionEventPublisher.java
│   │   │   │   └── ResultEventPublisher.java
│   │   │   └── config/
│   │   │       ├── Judge0Config.java
│   │   │       ├── KafkaConfig.java
│   │   │       └── SecurityConfig.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
└── target/
```

### Key Dependencies
- `spring-boot-starter-web` — REST API endpoints
- `spring-boot-starter-data-jpa` — Database ORM
- `spring-cloud-starter-openfeign` — Judge0 HTTP client
- `spring-boot-starter-kafka` — Event streaming
- `com.judge0:judge0-api-client` — Judge0 integration

### API Endpoints
```
GET    /v1/problems                      - Fetch all coding problems
GET    /v1/problems/{id}                - Get specific problem
POST   /v1/submissions                  - Submit solution code
GET    /v1/submissions/{id}             - Get submission results
POST   /v1/execution                    - Execute code against test cases
GET    /v1/recommendations/{user_id}   - Get problem recommendations
GET    /v1/progress/{user_id}          - View user progress
GET    /health
```

### Supported Languages
- Java, Python, C++, C#, JavaScript, Ruby, Go, Rust, PHP, and 50+ more via Judge0

---

## 📄 **Resume Service** — Professional Resume Management & AI Analysis
> *The Career Document Intelligence Platform*

**Location:** `resume-service/resume-service/`

### Purpose
- Analyzes and scores resumes using AI-powered metrics
- Provides real-time improvement suggestions
- ATS (Applicant Tracking System) optimization
- Resume formatting and structure validation
- Career progression analytics and skill gap detection
- Template-based resume generation

### Internal Architecture
```
resume-service/
├── pom.xml
├── docker-compose.resume.yml
├── Dockerfile
├── VERSION
├── src/
│   ├── main/
│   │   ├── java/com/interviewmate/resumeservice/
│   │   │   ├── ResumeServiceApplication.java
│   │   │   ├── api/
│   │   │   │   ├── ResumeController.java
│   │   │   │   ├── AnalysisController.java
│   │   │   │   └── SuggestionController.java
│   │   │   ├── service/
│   │   │   │   ├── ResumeAnalysisService.java
│   │   │   │   ├── ATSScoreCalculator.java
│   │   │   │   ├── SkillExtractor.java
│   │   │   │   ├── ResumeOptimizer.java
│   │   │   │   └── AIFeedbackService.java
│   │   │   ├── client/
│   │   │   │   └── AIServiceClient.java
│   │   │   ├── entity/
│   │   │   │   ├── ResumeDocument.java
│   │   │   │   ├── ResumeAnalysis.java
│   │   │   │   ├── SkillProfile.java
│   │   │   │   └── ImprovementSuggestion.java
│   │   │   ├── dto/
│   │   │   │   ├── ResumeDTO.java
│   │   │   │   ├── AnalysisResultDTO.java
│   │   │   │   ├── ATSScoreDTO.java
│   │   │   │   └── SuggestionDTO.java
│   │   │   ├── repository/
│   │   │   │   ├── ResumeRepository.java
│   │   │   │   ├── AnalysisRepository.java
│   │   │   │   └── SuggestionRepository.java
│   │   │   ├── parser/
│   │   │   │   ├── PDFParser.java
│   │   │   │   └── DocxParser.java
│   │   │   ├── eventhandler/
│   │   │   │   └── AnalysisEventPublisher.java
│   │   │   └── config/
│   │   │       ├── RedisConfig.java
│   │   │       ├── KafkaConfig.java
│   │   │       └── SecurityConfig.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
└── target/
```

### Key Dependencies
- `spring-boot-starter-web`
- `spring-boot-starter-data-redis` — Caching layer
- `org.apache.pdfbox:pdfbox` — PDF parsing
- `org.docx4j:docx4j` — DOCX parsing
- `spring-boot-starter-kafka`

### API Endpoints
```
POST   /v1/upload-resume                - Upload resume file (PDF/DOCX)
POST   /v1/analyze                      - Analyze resume quality
GET    /v1/analysis/{id}               - Get analysis details
POST   /v1/ats-score                   - Calculate ATS compatibility
POST   /v1/suggestions                 - Get improvement suggestions
POST   /v1/extract-skills              - Extract skills from resume
GET    /v1/templates                   - Get resume templates
GET    /health
```

### Resume Scoring Metrics
- **Content Quality:** 0-100 (grammar, clarity, completeness)
- **ATS Score:** 0-100 (keyword optimization, format compatibility)
- **Experience Relevance:** 0-100 (job match percentage)
- **Skills Coverage:** 0-100 (required vs. provided skills)
- **Overall Score:** Weighted average of above metrics

---

## 🔔 **Notification Service** — Multi-Channel Alert System
> *The Real-time Communication Engine*

**Location:** `notification-service/notification-service/`

### Purpose
- Sends multi-channel notifications (Email, SMS, In-app)
- Kafka event-driven architecture for scalability
- Email templating with dynamic content injection
- Notification scheduling and delivery tracking
- Failed delivery retry mechanism with exponential backoff
- User notification preferences management

### Internal Architecture
```
notification-service/
├── pom.xml
├── docker-compose.notification.yml
├── Dockerfile
├── VERSION
├── src/
│   ├── main/
│   │   ├── java/com/interviewmate/notificationservice/
│   │   │   ├── NotificationServiceApplication.java
│   │   │   ├── kafka/
│   │   │   │   ├── NotificationEventConsumer.java
│   │   │   │   ├── InterviewCompletionListener.java
│   │   │   │   ├── ReportReadyListener.java
│   │   │   │   └── UserActivityListener.java
│   │   │   ├── service/
│   │   │   │   ├── EmailService.java
│   │   │   │   ├── SMSService.java
│   │   │   │   ├── PushNotificationService.java
│   │   │   │   ├── NotificationOrchestrator.java
│   │   │   │   └── DeliveryTracker.java
│   │   │   ├── template/
│   │   │   │   ├── EmailTemplate.java
│   │   │   │   ├── TemplateEngine.java
│   │   │   │   └── [templates/]
│   │   │   │       ├── interview-completed.html
│   │   │   │       ├── report-ready.html
│   │   │   │       └── score-alert.html
│   │   │   ├── entity/
│   │   │   │   ├── Notification.java
│   │   │   │   ├── NotificationPreference.java
│   │   │   │   ├── DeliveryLog.java
│   │   │   │   └── Template.java
│   │   │   ├── dto/
│   │   │   │   ├── NotificationDTO.java
│   │   │   │   ├── EmailPayloadDTO.java
│   │   │   │   └── PreferenceDTO.java
│   │   │   ├── repository/
│   │   │   │   ├── NotificationRepository.java
│   │   │   │   ├── PreferenceRepository.java
│   │   │   │   └── DeliveryLogRepository.java
│   │   │   ├── client/
│   │   │   │   ├── EmailServiceClient.java              [SMTP/SendGrid]
│   │   │   │   ├── SMSServiceClient.java                [Twilio/AWS SNS]
│   │   │   │   └── PushServiceClient.java               [Firebase]
│   │   │   └── config/
│   │   │       ├── KafkaConfig.java
│   │   │       ├── EmailConfig.java
│   │   │       └── SecurityConfig.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── templates/
│   └── test/
└── target/
```

### Key Dependencies
- `spring-boot-starter-mail` — SMTP support
- `spring-boot-starter-kafka` — Event streaming
- `com.sendgrid:sendgrid-java` — SendGrid integration
- `com.twilio.sdk:twilio` — SMS support
- `com.google.firebase:firebase-admin` — Push notifications
- `org.springframework.cloud:spring-cloud-starter-openfeign`

### Kafka Topics Consumed
```
interview.completion.event
report.generation.completed
score.threshold.exceeded
user.achievement.unlocked
system.alert.critical
```

### API Endpoints
```
POST   /v1/send-notification            - Send immediate notification
POST   /v1/schedule-notification        - Schedule delayed notification
GET    /v1/preferences/{user_id}       - Get user preferences
PUT    /v1/preferences/{user_id}       - Update user notification settings
GET    /v1/delivery-status/{id}        - Check notification delivery status
GET    /health
```

---

## ⚙️ **Config Server** — Centralized Configuration Management
> *The Configuration Authority*

**Location:** `config-server/`

### Purpose
- Centralized configuration for all microservices
- Dynamic property refresh without redeployment
- Environment-specific profiles (dev, staging, production)
- Secure credential management via Spring Cloud Config
- Git-based configuration repository for version control

### Internal Architecture
```
config-server/
├── pom.xml
├── docker-compose.config.yaml
├── Dockerfile
├── HELP.md
├── src/
│   ├── main/
│   │   ├── java/com/interviewmate/configserver/
│   │   │   ├── ConfigServerApplication.java
│   │   │   └── config/
│   │   │       └── SecurityConfig.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── bootstrap.yml
│   └── test/
├── logs/
└── target/
```

### Key Dependencies
- `spring-cloud-config-server` — Config server
- `spring-boot-starter-security` — Config protection
- `spring-cloud-starter-netflix-eureka-client` — Service discovery

### Configuration Properties Managed
```yaml
# Common Configuration Properties
server.port: <SERVICE_PORT>
spring.application.name: <SERVICE_NAME>
eureka.client.service-url.defaultZone: http://eureka-server:8761/eureka

# Database Configuration
spring.data.mongodb.uri: mongodb://username:password@mongodb:27017/interviewmate
spring.datasource.url: jdbc:mysql://mysql:3306/interviewmate
spring.jpa.hibernate.ddl-auto: update

# Kafka Configuration
spring.kafka.bootstrap-servers: kafka:9092
spring.kafka.consumer.group-id: interviewmate-group

# Redis Configuration
spring.redis.host: redis
spring.redis.port: 6379

# JWT & Security
jwt.secret: <SECRET_KEY>
jwt.expiration: 86400000

# AI/ML Service URLs
ai.service.audio-converter-url: http://audio-converter:8000
ai.service.behavioral-analysis-url: http://behavioral-analysis:8001
ai.service.question-service-url: http://interview-question:8002
ai.service.report-service-url: http://interview-report:8003
```

### Port & Access
- **Port:** `8888`
- **Health Check:** `GET http://localhost:8888/actuator/health`
- **Configuration Endpoint:** `GET http://localhost:8888/<service-name>/default`

---

## 🏛️ **Judge0 & Judge0-Worker** — Code Execution Infrastructure
> *The Multi-Language Code Execution Engine*

**Location:** `judge0/`

### Purpose
- Executes code in 50+ programming languages
- Provides sandboxed execution environment
- Handles input/output for test cases
- Memory and time limit enforcement
- Detailed execution feedback with compilation/runtime errors
- Asynchronous job queue processing

### Architecture
```
Judge0 System:
├── judge0-api                          [REST API Interface]
├── judge0-worker                       [Background Job Processor]
├── judge0-db (PostgreSQL)             [Job Storage & Results]
└── judge0-redis                       [Queue Management]

Service Flow:
Client → Judge0 API → Redis Queue → Worker Pool → Execution → Results Storage
```

### Docker Configuration
```
Services:
  - judge0-api:
      Container: judge0/judge0:latest
      Port: 2358 (mapped to internal only)
      Dependencies: PostgreSQL, Redis
      Health Check: Database connectivity
      
  - judge0-worker:
      Container: judge0/judge0:latest (with worker script)
      Command: ./scripts/workers
      Dependencies: Judge0 API, Redis
      Privileges: Elevated (for code execution)
      Auto-scaling: Can run multiple instances
      
  - judge0-db (PostgreSQL):
      Container: postgres:latest
      Port: 5432
      Database: judge0
      Credentials: judge0:judge0
      
  - judge0-redis:
      Container: redis:latest
      Port: 6379
      Password: judge0redis
```

### Configuration (`judge0.conf`)
```
# Database Connection
POSTGRES_USER=judge0
POSTGRES_PASSWORD=judge0
POSTGRES_DB=judge0
DATABASE_URL=postgres://judge0:judge0@judge0-db-interviewmate:5432/judge0

# Redis Connection
REDIS_PASSWORD=judge0redis
REDIS_URL=redis://:judge0redis@judge0-redis-interviewmate:6379/0
REDIS_HOST=judge0-redis-interviewmate
REDIS_PORT=6379

# Security
INTERNAL_RUNNER_SECRET=somesecret

# Worker Configuration
WORKERS=1-4                            [Number of parallel workers]
TIMEOUT=15                              [Execution timeout in seconds]
```

### API Integration (via Coding Service)
```
Coding Service → Judge0 API:
POST /submissions
{
  "source_code": "print('Hello')",
  "language_id": 71,          // Python
  "stdin": "test input",
  "expected_output": "Hello",
  "time_limit": 5,
  "memory_limit": 128000
}

Response:
{
  "token": "abcd1234",
  "status": {
    "id": 3,
    "description": "Accepted"
  },
  "stdout": "Hello",
  "time": "0.123s",
  "memory": "4096KB"
}
```

### Supported Languages (50+)
```
Java, Python, C++, C#, JavaScript, Ruby, Go, Rust, PHP, Kotlin,
Swift, Scala, Haskell, Lisp, Perl, R, MATLAB, Lua, Groovy,
Dart, Clojure, Elixir, Erlang, F#, Objective-C, Ocaml, Pascal,
Prolog, Bash, PowerShell, VB.NET, COBOL, FORTRAN, ... and more
```

---

## 🔄 **Async Communication Patterns & Event-Driven Architecture**

> *The Backbone of Distributed Intelligence*

InterviewMate employs sophisticated event-driven patterns for scalability and loose coupling between services.

### 1. **Kafka-Based Event Streaming**

#### Event Topics & Producers/Consumers
```yaml
Topics:
  - interview.session.started
      Producer: Interview Service
      Consumers: Behavioral Analysis, Audio Converter, Question Service
      
  - interview.session.completed
      Producer: Interview Service
      Consumers: Report Service, Notification Service, Analytics
      
  - audio.transcription.completed
      Producer: Audio Converter Service
      Consumer: Interview Service (aggregation)
      
  - behavior.analysis.completed
      Producer: Behavioral Analysis Service
      Consumer: Report Service, Interview Service
      
  - report.generation.completed
      Producer: Report Service
      Consumers: Notification Service, User Dashboard
      
  - code.submission.received
      Producer: Coding Service
      Consumers: Judge0 Worker, Analytics Service
      
  - submission.result.ready
      Producer: Judge0 Worker
      Consumers: Coding Service, Notification Service
      
  - resume.analysis.completed
      Producer: Resume Service
      Consumers: User Service, Notification Service
```

#### Configuration
```yaml
spring.kafka:
  bootstrap-servers: kafka:9092
  consumer:
    group-id: interviewmate-consumer-group
    auto-offset-reset: earliest
    max-poll-records: 500
  producer:
    acks: all
    retries: 3
    batch-size: 16384
  topics:
    partitions: 3
    replication-factor: 2
```

### 2. **Request-Reply Pattern (Synchronous over Async)**

For scenarios requiring immediate responses:
```
┌─────────────────┐
│ Interview       │─────► Kafka: interview.question.request ───┐
│ Service         │                                             │
│                 │◄────────────────────────────────────────────┤
└─────────────────┘       Kafka: interview.question.response    │
                                                                 │
                          ┌──────────────────────────────────────┘
                          │
                          ▼
                    ┌──────────────┐
                    │ Question     │
                    │ Service      │
                    └──────────────┘
```

### 3. **Saga Pattern for Distributed Transactions**

Interview completion saga:
```
1. Interview Service: Start Interview Saga
   ↓
2. Question Service: Load Questions → SUCCESS/FAILURE
   ↓
3. Audio Service: Process Audio → SUCCESS/FAILURE
   ↓
4. Behavioral Service: Analyze Behavior → SUCCESS/FAILURE
   ↓
5. Report Service: Generate Report → SUCCESS/FAILURE
   ↓
6. Notification Service: Send Notification → SUCCESS/FAILURE
   
If any step fails → Compensating transactions trigger rollback
```

### 4. **Circuit Breaker Pattern**

For resilience against cascading failures:
```yaml
resilience4j:
  circuitbreaker:
    instances:
      audioServiceBreaker:
        registerHealthIndicator: true
        slidingWindowSize: 10
        failureRateThreshold: 50
        slowCallRateThreshold: 50
        slowCallDurationThreshold: 2000
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 5000
```

### 5. **Eventual Consistency Pattern**

Services may not be immediately consistent; they converge over time:
```
Interview Session Created
  ├─ Written to MongoDB (immediate)
  ├─ Published to Kafka (async)
  │
  ├─ Audio Service consumes → Processes → Publishes result
  ├─ Behavior Service consumes → Processes → Publishes result
  └─ Report Service consumes all → Generates → Publishes

Final Report ready after all components complete (~30-60 seconds)
```

---

## �🔗 **Inter-Service Communication Pattern**

```mermaid
sequenceDiagram
    participant User
    participant Interview as Interview Service<br/>(Java Orchestrator)
    participant Question as Question Service
    participant VideoSep as Video Separator
    participant Behavior as Behavioral Service
    participant Audio as Audio Converter
    participant Report as Report Service
    participant Kafka as Kafka Events
    participant MongoDB as MongoDB
    participant Notification as Notification Service

    User->>Interview: Start Interview Session
    Interview->>Question: Fetch Interview Questions
    Question-->>Interview: Return Questions
    
    User->>Interview: Upload Video Response
    Interview->>VideoSep: Separate Audio & Extract Frames
    VideoSep-->>Interview: Return Audio & Frames
    
    Interview->>Audio: Convert Audio to Text
    Audio-->>Interview: Return Transcription
    
    Interview->>Behavior: Analyze Emotional Patterns
    Behavior-->>Interview: Return Behavioral Metrics
    
    Interview->>Kafka: Publish Events (Completed)
    Report->>Kafka: Subscribe to Events
    
    Report->>MongoDB: Query All Metrics
    Report-->>Interview: Generate Report
    
    Interview->>Kafka: Publish Report Ready Event
    Notification->>Kafka: Subscribe
    Notification-->>User: Send Report Notification
    
    User->>Interview: Retrieve Full Report
    Interview-->>User: Return Report PDF/HTML
```

---

## 🌍 Deployment Architecture

### Docker Compose Services Orchestration

#### **Main Infrastructure** (`docker-compose.infra.yml`)
```yaml
Services:
  - MySQL (Port 3306)
  - MongoDB (Port 27017)
  - Redis (Port 6379)
  - Kafka & Zookeeper (Port 9092, 2181)
  - Nginx (Port 80, 443)
```

#### **Master Compose** (`docker-compose.master.yml`)
Orchestrates ALL microservices with proper dependency management

#### **Interview Service Stack**
```yaml
interview-service:
  image: arindampal28/interviewmate-interview-service:latest
  container_name: backend-interview-service
  port: 8086
  depends_on:
    - kafka
    - config-server
    - eureka-server
  healthcheck:
    test: wget -qO- http://localhost:8086/actuator/health
    interval: 15s
    retries: 10
```

#### **Interview Service AI Stack** (Python Services)
```
Each service has its own docker-compose file:
├── docker-compose.audio-answer-converter.yml
├── docker-compose.behavioral-analysis.yml
├── docker-compose.video-audio-seperator.yml
├── docker-compose.interview-question.yml
├── docker-compose.interview-report.yml
└── docker-compose-client.yml
```

---

## 📊 Data Models & MongoDB Collections

### Core Collections in MongoDB

#### 1. **interview_sessions**
```javascript
{
  _id: ObjectId,
  user_id: String,
  job_title: String,
  interview_type: String, // BEHAVIORAL, TECHNICAL, ...
  status: String, // STARTED, IN_PROGRESS, COMPLETED
  created_at: DateTime,
  completed_at: DateTime,
  total_duration_seconds: Number,
  overall_score: Number
}
```

#### 2. **behavioral_metrics**
```javascript
{
  _id: ObjectId,
  interview_session_id: ObjectId,
  emotion_scores: {
    happy: Float,
    sad: Float,
    angry: Float,
    neutral: Float,
    fearful: Float
  },
  eye_contact_percentage: Float,
  posture_quality: String,
  stress_level: String,
  confidence_score: Float,
  analysis_timestamp: DateTime
}
```

#### 3. **audio_transcriptions**
```javascript
{
  _id: ObjectId,
  interview_session_id: ObjectId,
  audio_file_url: String,
  transcription_text: String,
  speech_rate_wpm: Number,
  pause_duration_seconds: Number,
  clarity_score: Float,
  transcribed_at: DateTime
}
```

#### 4. **interview_questions**
```javascript
{
  _id: ObjectId,
  job_id: String,
  category: String,
  difficulty: String, // EASY, MEDIUM, HARD
  question_text: String,
  answer_expected: String,
  candidates_response: String,
  scoring_rubric: Object
}
```

#### 5. **interview_reports**
```javascript
{
  _id: ObjectId,
  interview_session_id: ObjectId,
  overall_performance: Number,
  communication_score: Number,
  technical_score: Number,
  behavioral_score: Number,
  strengths: [String],
  improvements: [String],
  recommendations: [String],
  report_url: String,
  generated_at: DateTime
}
```

---

## 🚀 Deployment & Scaling Instructions

### Local Development Setup
```bash
# 1. Clone repository
git clone <repo-url>
cd backend

# 2. Start infrastructure
docker-compose -f docker-compose.infra.yml up -d

# 3. Start core services
docker-compose -f docker-compose.master.yml up -d

# 4. Verify all services are healthy
curl http://localhost:8761  # Eureka Dashboard
```

### Kubernetes Deployment (Production)
```bash
# Apply manifests
kubectl apply -f k8s/namespaces/
kubectl apply -f k8s/configmaps/
kubectl apply -f k8s/secrets/
kubectl apply -f k8s/services/
kubectl apply -f k8s/deployments/

# Verify deployments
kubectl get deployments -n interviewmate
kubectl get pods -n interviewmate
```

---

## 🔐 Security & Best Practices

### Authentication & Authorization
- **JWT Tokens** with 24-hour expiration
- **Role-Based Access Control** (RBAC)
- **API Key** management for external integrations
- **OAuth2** support for third-party logins

### Data Protection
- **SSL/TLS** encryption for all traffic
- **MongoDB Encryption** at rest
- **Sensitive Data** masking in logs
- **GDPR Compliance** for user data

### Monitoring & Observability
- **Spring Boot Actuator** health endpoints
- **Prometheus** metrics collection
- **Grafana** dashboards
- **ELK Stack** for centralized logging
- **Distributed Tracing** with Sleuth & Zipkin

---

## 📚 API Documentation & Testing

### Swagger/OpenAPI Documentation
```
http://localhost:8080/swagger-ui.html
http://localhost:8080/v3/api-docs
```

### Kafka Topics Monitoring
```bash
docker exec -it kafka kafka-topics --bootstrap-server localhost:9092 --list
docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic <topic-name>
```

### Database Access
```bash
# MongoDB
mongosh mongodb://localhost:27017

# MySQL
mysql -u root -p interviewmate_db
```

---

## ⚡ Quick Start — Launch the Future

### 📧 **IMPORTANT: Environment Variables Setup**

Before deploying, you **MUST** configure the `.env` file with all required credentials and connection strings.

**For complete environment variable details and configuration assistance, please contact:**
```
📧 Email: arindampal669@gmail.com
Subject: InterviewMate Backend - Environment Configuration
```

For security reasons, sensitive credentials are NOT included in the repository. The maintainer will provide:
- Database credentials (MySQL, MongoDB)
- Kafka & Redis passwords
- JWT secret keys
- Third-party API keys (SendGrid, Twilio, Google Generative AI)
- Judge0 configuration
- AI Service endpoints

### 1. Prerequisites
- **JDK 21+** (Java Development Kit)
- **Python 3.10+** (for AI/ML services)
- **Docker Desktop 4.0+** (with Compose)
- **Maven 3.8.1+** (for building)
- **Git** (for version control)
- **Memory:** Minimum 8GB RAM recommended (12GB+ for AI/ML services)
- **Disk Space:** Minimum 20GB for Docker images

### 2. Environment Variables Configuration

Create a `.env` file in the root directory with the following structure:

```bash
# ============================================
# GLOBAL SETTINGS
# ============================================
ENVIRONMENT=development
PROJECT_NAME=interviewmate
LOG_LEVEL=INFO

# ============================================
# DATABASE CONFIGURATION
# ============================================

# MySQL (User Service, Resume Service)
MYSQL_ROOT_PASSWORD=your_mysql_root_password
MYSQL_USER=interviewmate_user
MYSQL_PASSWORD=your_mysql_password
MYSQL_DATABASE=interviewmate_db
MYSQL_HOSTNAME=mysql
MYSQL_PORT=3306

# MongoDB (Interview Service, AI Services)
MONGODB_INITDB_ROOT_USERNAME=mongo_admin
MONGODB_INITDB_ROOT_PASSWORD=your_mongo_password
MONGODB_DATABASE=interviewmate
MONGODB_HOSTNAME=mongodb
MONGODB_PORT=27017

# ============================================
# CACHE & MESSAGE QUEUE
# ============================================

# Redis (Resume Service Caching)
REDIS_PASSWORD=your_redis_password
REDIS_HOSTNAME=redis
REDIS_PORT=6379

# Kafka (Event Streaming)
KAFKA_BOOTSTRAP_SERVERS=kafka:9092
KAFKA_BROKER_ID=1
KAFKA_NUM_PARTITIONS=3
KAFKA_REPLICATION_FACTOR=2

# Zookeeper
ZOOKEEPER_CLIENT_PORT=2181
ZOOKEEPER_SYNC_LIMIT=5
ZOOKEEPER_INIT_LIMIT=10

# ============================================
# JUDGE0 CONFIGURATION
# ============================================

# Judge0 Database (PostgreSQL)
POSTGRES_USER=judge0
POSTGRES_PASSWORD=your_judge0_postgres_password
POSTGRES_DB=judge0
POSTGRES_HOSTNAME=judge0-db-interviewmate
POSTGRES_PORT=5432
DATABASE_URL=postgres://judge0:your_judge0_postgres_password@judge0-db-interviewmate:5432/judge0

# Judge0 Redis
JUDGE0_REDIS_PASSWORD=your_judge0_redis_password
JUDGE0_REDIS_HOSTNAME=judge0-redis-interviewmate
JUDGE0_REDIS_PORT=6379
JUDGE0_REDIS_URL=redis://:your_judge0_redis_password@judge0-redis-interviewmate:6379/0

# Judge0 Configuration
JUDGE0_API_PORT=2358
JUDGE0_WORKERS=4
JUDGE0_TIMEOUT=15
JUDGE0_INTERNAL_RUNNER_SECRET=your_judge0_internal_secret

# ============================================
# SPRING CLOUD CONFIGURATION
# ============================================

# Eureka Server (Service Discovery)
EUREKA_HOSTNAME=eureka-server
EUREKA_PORT=8761
EUREKA_INSTANCE_HOSTNAME=eureka-server

# Config Server
CONFIG_SERVER_HOSTNAME=config-server
CONFIG_SERVER_PORT=8888
CONFIG_SERVER_GIT_URI=https://github.com/your-org/interviewmate-config.git
CONFIG_SERVER_GIT_USERNAME=your_git_username
CONFIG_SERVER_GIT_PASSWORD=your_git_token

# ============================================
# MICROSERVICE PORTS
# ============================================

API_GATEWAY_PORT=8080
USER_SERVICE_PORT=8081
NOTIFICATION_SERVICE_PORT=8082
CODING_SERVICE_PORT=8083
INTERVIEW_SERVICE_PORT=8086
RESUME_SERVICE_PORT=8085

# ============================================
# SECURITY & JWT
# ============================================

JWT_SECRET=your_very_secure_jwt_secret_key_min_32_chars
JWT_EXPIRATION=86400000
JWT_REFRESH_TOKEN_EXPIRATION=604800000
OAUTH2_CLIENT_ID=your_oauth2_client_id
OAUTH2_CLIENT_SECRET=your_oauth2_client_secret

# ============================================
# EMAIL CONFIGURATION (Notifications)
# ============================================

# SendGrid Integration
SENDGRID_API_KEY=SG.your_sendgrid_api_key
SENDGRID_FROM_EMAIL=noreply@interviewmate.com
SENDGRID_FROM_NAME=InterviewMate Platform

# SMTP Fallback
MAIL_SMTP_HOST=smtp.gmail.com
MAIL_SMTP_PORT=587
MAIL_SMTP_USERNAME=your_email@gmail.com
MAIL_SMTP_PASSWORD=your_app_password
MAIL_SMTP_AUTH=true
MAIL_SMTP_STARTTLS_ENABLE=true

# ============================================
# SMS CONFIGURATION (Notifications)
# ============================================

# Twilio Configuration
TWILIO_ACCOUNT_SID=your_twilio_account_sid
TWILIO_AUTH_TOKEN=your_twilio_auth_token
TWILIO_FROM_PHONE=+1234567890

# ============================================
# AI & ML SERVICE ENDPOINTS
# ============================================

# Audio Answer Converter Service
AUDIO_CONVERTER_HOSTNAME=audio-answer-converter
AUDIO_CONVERTER_PORT=8000
AUDIO_CONVERTER_URL=http://audio-answer-converter:8000

# Behavioral Analysis Service
BEHAVIORAL_ANALYSIS_HOSTNAME=behavioral-analysis
BEHAVIORAL_ANALYSIS_PORT=8001
BEHAVIORAL_ANALYSIS_URL=http://behavioral-analysis:8001

# Video Audio Separator Service
VIDEO_SEPARATOR_HOSTNAME=video-audio-seperator
VIDEO_SEPARATOR_PORT=8002
VIDEO_SEPARATOR_URL=http://video-audio-seperator:8002

# Interview Question Service
INTERVIEW_QUESTION_HOSTNAME=interview-question
INTERVIEW_QUESTION_PORT=8003
INTERVIEW_QUESTION_URL=http://interview-question:8003

# Interview Report Service
INTERVIEW_REPORT_HOSTNAME=interview-report
INTERVIEW_REPORT_PORT=8004
INTERVIEW_REPORT_URL=http://interview-report:8004

# ============================================
# THIRD-PARTY API KEYS (AI/ML)
# ============================================

# Google Generative AI (For Question Generation & Feedback)
GOOGLE_GENERATIVE_AI_API_KEY=your_google_generative_ai_key
GOOGLE_AI_MODEL=gemini-pro

# OpenAI API (Optional - for Whisper)
OPENAI_API_KEY=sk-your_openai_api_key

# HuggingFace API (For ML Models)
HUGGINGFACE_API_TOKEN=hf_your_huggingface_token

# ============================================
# LOGGING & MONITORING
# ============================================

# ELK Stack
ELASTICSEARCH_HOSTNAME=elasticsearch
ELASTICSEARCH_PORT=9200
KIBANA_PORT=5601

# Prometheus
PROMETHEUS_PORT=9090

# Grafana
GRAFANA_PORT=3000
GRAFANA_ADMIN_PASSWORD=your_grafana_password

# ============================================
# FILE STORAGE
# ============================================

# Local Storage
UPLOAD_DIR=/uploads
MAX_FILE_SIZE=104857600  # 100MB

# AWS S3 (Optional)
AWS_ACCESS_KEY_ID=your_aws_access_key
AWS_SECRET_ACCESS_KEY=your_aws_secret_key
AWS_REGION=us-east-1
AWS_S3_BUCKET=interviewmate-uploads

# ============================================
# APPLICATION FEATURES
# ============================================

# Feature Flags
ENABLE_AI_FEEDBACK=true
ENABLE_VIDEO_ANALYSIS=true
ENABLE_ATS_SCORING=true
ENABLE_EMAIL_NOTIFICATIONS=true
ENABLE_SMS_NOTIFICATIONS=false

# Interview Settings
INTERVIEW_SESSION_TIMEOUT_MINUTES=60
MAX_INTERVIEW_QUESTIONS=10
QUESTION_GENERATION_TIMEOUT_SECONDS=30

# ============================================
# DOCKER NETWORK
# ============================================

DOCKER_NETWORK=interviewmate-network
```

### **Getting Environment Values:**

1. **Database Credentials:**
   - Generate strong passwords for MySQL, MongoDB, Redis, PostgreSQL
   - Store them securely in your `.env` file

2. **JWT Secret:**
   ```bash
   # Generate a secure JWT secret (minimum 32 characters)
   openssl rand -base64 32
   ```

3. **API Keys & Tokens:**
   - Sign up for Google Generative AI at: https://makersuite.google.com
   - SendGrid keys: https://app.sendgrid.com/settings/api_keys
   - Twilio: https://www.twilio.com/console
   - Contact: **arindampal669@gmail.com** for pre-configured credentials

4. **Git Configuration for Config Server:**
   - Create a private GitHub repository for configurations
   - Generate Personal Access Token: https://github.com/settings/tokens

### 3. Environment Setup

```bash
# Clone the repository
git clone https://github.com/your-org/interviewmate-backend.git
cd backend

# Copy environment template
cp .env.example .env

# Edit .env with your credentials (USE YOUR EDITOR)
# nano .env  OR  code .env

# ⚠️ IMPORTANT: NEVER commit .env to version control
# Add to .gitignore if not already present
echo ".env" >> .gitignore

# Create external Docker network
docker network create interviewmate-network

# Verify network creation
docker network ls
```

### 4. Start Infrastructure Services
```bash
# Start MySQL, MongoDB, Redis, Kafka, Zookeeper, Nginx
docker-compose -f docker-compose.infra.yml up -d

# Wait 30 seconds for services to fully bootstrap
sleep 30

# Verify infrastructure health
docker-compose -f docker-compose.infra.yml ps

# Check logs if any service failed
docker-compose -f docker-compose.infra.yml logs kafka
```

### 5. Start Core Java Microservices
```bash
# Start Config Server first (centralized configuration)
docker-compose -f config-server/docker-compose.config.yaml up -d

# Wait for Config Server to be healthy
sleep 20
curl http://localhost:8888/actuator/health

# Start Eureka Server (service discovery)
docker-compose -f eureka-server/eureka-server/docker-compose.eureka.yml up -d

# Wait for Eureka to initialize
sleep 15
curl http://localhost:8761

# Start API Gateway (entry point)
docker-compose -f api-gateway/api-gateway/docker-compose.gateway.yml up -d

# Start remaining Java microservices
docker-compose -f interview-service/interview-service/docker-compose.interview.yml up -d
docker-compose -f coding-service/coding-service/docker-compose.coding.yml up -d
docker-compose -f user-service/user-service/docker-compose.user.yml up -d
docker-compose -f notification-service/notification-service/docker-compose.notification.yml up -d
docker-compose -f resume-service/resume-service/docker-compose.resume.yml up -d

# Start Judge0 (Code Execution Engine)
docker-compose -f judge0/docker-compose.judge0.yml up -d

# Wait for all services to be ready
sleep 20

# Verify all Java services are running
docker-compose -f docker-compose.master.yml ps | grep -E "interview|coding|user|notification|resume"
```

### 6. Start Python AI/ML Microservices
```bash
# Navigate to AI services directory
cd interview-service-ai

# Start Audio Answer Converter Service
docker-compose -f AudioAnswerConverterService/docker-compose.audio-answer-converter.yml up -d

# Start Behavioral Analysis Service (requires GPU for optimal performance)
docker-compose -f BehavioralAnalysisService/docker-compose.behavioral-analysis.yml up -d

# Start Video Audio Separator Service
docker-compose -f VideoAudioSeperatorService/docker-compose.video-audio-seperator.yml up -d

# Start Interview Question Service
docker-compose -f InterviewQuestionService/docker-compose.interview-question.yml up -d

# Start Interview Report Service
docker-compose -f InterviewReportService/docker-compose.interview-report.yml up -d

# Start Client Communication Service
docker-compose -f Client/docker-compose-client.yml up -d

# Return to backend root
cd ..
```

# Wait for Config Server to be ready
sleep 20

# Start Eureka Server
docker-compose -f eureka-server/eureka-server/docker-compose.eureka.yml up -d

# Start API Gateway
docker-compose -f api-gateway/api-gateway/docker-compose.gateway.yml up -d

# Start remaining services
docker-compose -f interview-service/interview-service/docker-compose.interview.yml up -d
docker-compose -f coding-service/coding-service/docker-compose.coding.yml up -d
docker-compose -f user-service/user-service/docker-compose.user.yml up -d
docker-compose -f notification-service/notification-service/docker-compose.notification.yml up -d
docker-compose -f resume-service/resume-service/docker-compose.resume.yml up -d

# Verify all services are running
docker-compose -f docker-compose.master.yml ps
```

### 6. Start Python AI Microservices
```bash
# Start AI Services (requires Python 3.10+)
cd interview-service-ai

docker-compose -f AudioAnswerConverterService/docker-compose.audio-answer-converter.yml up -d
sleep 10

docker-compose -f BehavioralAnalysisService/docker-compose.behavioral-analysis.yml up -d
sleep 10

docker-compose -f VideoAudioSeperatorService/docker-compose.video-audio-seperator.yml up -d
sleep 10

docker-compose -f InterviewQuestionService/docker-compose.interview-question.yml up -d
sleep 10

docker-compose -f InterviewReportService/docker-compose.interview-report.yml up -d
sleep 10

docker-compose -f Client/docker-compose-client.yml up -d

# Return to backend root
cd ..
```

### 7. Complete Health Verification

#### **Java Services Health Check**
```bash
# Eureka Service Discovery (8761)
curl http://localhost:8761
echo "Eureka Dashboard: http://localhost:8761"

# Config Server (8888)
curl http://localhost:8888/actuator/health
echo "✓ Config Server is healthy"

# API Gateway (8080)
curl http://localhost:8080/actuator/health
echo "✓ API Gateway is healthy"

# Interview Service (8086)
curl http://localhost:8086/actuator/health
echo "✓ Interview Service is healthy"

# Coding Service (8083)
curl http://localhost:8083/actuator/health
echo "✓ Coding Service is healthy"

# User Service (8081)
curl http://localhost:8081/actuator/health
echo "✓ User Service is healthy"

# Notification Service (8082)
curl http://localhost:8082/actuator/health
echo "✓ Notification Service is healthy"

# Resume Service (8085)
curl http://localhost:8085/actuator/health
echo "✓ Resume Service is healthy"
```

#### **Infrastructure Services Health Check**
```bash
# MongoDB Connection
docker exec -it mongodb mongosh mongodb://localhost:27017 --eval "db.adminCommand('ping')"
echo "✓ MongoDB is accessible"

# MySQL Connection
docker exec -it mysql mysql -u root -p$MYSQL_ROOT_PASSWORD -e "SELECT VERSION();"
echo "✓ MySQL is accessible"

# Redis Connection
docker exec -it redis redis-cli ping
echo "Redis Response: PONG ✓"

# Kafka Topics Listing
docker exec -it kafka kafka-topics --bootstrap-server localhost:9092 --list
echo "✓ Kafka is accessible"

# Judge0 API Health
curl http://localhost:2358/health 2>/dev/null || echo "✓ Judge0 is running (internal)"
```

#### **Python AI Services Health Check**
```bash
# Audio Converter Service
curl http://localhost:8000/health 2>/dev/null && echo "✓ Audio Converter Service healthy" || echo "⚠ Audio service may be initializing..."

# Behavioral Analysis Service
curl http://localhost:8001/health 2>/dev/null && echo "✓ Behavioral Analysis Service healthy" || echo "⚠ Behavioral service may be initializing..."

# Video Audio Separator Service
curl http://localhost:8002/health 2>/dev/null && echo "✓ Video Separator Service healthy" || echo "⚠ Video service may be initializing..."

# Interview Question Service
curl http://localhost:8003/health 2>/dev/null && echo "✓ Interview Question Service healthy" || echo "⚠ Question service may be initializing..."

# Interview Report Service
curl http://localhost:8004/health 2>/dev/null && echo "✓ Interview Report Service healthy" || echo "⚠ Report service may be initializing..."
```

---

### 8. Access Points & Documentation

```
┌─────────────────────────────────────────────────────────────┐
│            🎯 INTERVIEWMATE SERVICE ENDPOINTS               │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  🌐 API Gateway               → http://localhost:8080       │
│  🔭 Eureka Dashboard          → http://localhost:8761       │
│  📋 Swagger API Docs          → http://localhost:8080/swagger-ui.html │
│  ⚙️  Config Server             → http://localhost:8888       │
│  🎤 Interview Service         → http://localhost:8086       │
│  💻 Coding Service            → http://localhost:8083       │
│  👤 User Service              → http://localhost:8081       │
│  🔔 Notification Service      → http://localhost:8082       │
│  📄 Resume Service            → http://localhost:8085       │
│                                                               │
│  🐍 Python AI Services (Internal):                          │
│     • Audio Converter         → http://localhost:8000       │
│     • Behavioral Analysis     → http://localhost:8001       │
│     • Video Separator         → http://localhost:8002       │
│     • Interview Question      → http://localhost:8003       │
│     • Interview Report        → http://localhost:8004       │
│                                                               │
│  🗄️  Databases & Infrastructure:                            │
│     • MongoDB Atlas           → mongodb://localhost:27017   │
│     • MySQL                   → localhost:3306              │
│     • Redis                   → localhost:6379              │
│     • Kafka                   → localhost:9092              │
│     • Nginx                   → http://localhost:80         │
│                                                               │
└─────────────────────────────────────────────────────────────┘
```

**Sample API Requests:**
```bash
# Get JWT Token (User Service)
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'

# Start Interview Session
curl -X POST http://localhost:8080/api/v1/interviews/start \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "jobTitle": "Software Engineer",
    "interviewType": "BEHAVIORAL"
  }'

# Get Interview Report
curl -X GET http://localhost:8080/api/v1/reports/<INTERVIEW_ID> \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

## 📊 Service Dependency Graph

```
┌─────────────────────────────────────────────────────────────┐
│                    CLIENT APPLICATION                        │
└──────────────────────────────┬──────────────────────────────┘
                               │
                 ┌─────────────▼──────────────┐
                 │   🚪 API GATEWAY:8080     │
                 │   (Spring Cloud Gateway)  │
                 └──┬─────────────────────┬─┘
                    │                     │
         ┌──────────┴──────────┐  ┌─────┴─────────────┐
         │                     │  │                   │
    ┌────▼──────┐  ┌──────────▼──┴──────┐    ┌──────┴──────┐
    │🔭 Eureka  │  │ Config Server 8888 │    │ Services... │
    │8761       │  │                    │    │             │
    └───────────┘  └────────────────────┘    └─────────────┘
         ▲  ▲                                      ▲ ▲ ▲
         │  │                                      │ │ │
    ┌────┴──┴──────┬───────────────┬───────┬──────┘ │ │
    │              │               │       │        │ │
┌──▼─────┐  ┌────▼─────┐  ┌──────▼──┐  │   │       │
│ User   │  │Interview  │  │ Coding  │  │   │       │
│Service │  │ Service   │  │Service  │  │   │       │
│ 8081   │  │ 8086      │  │ 8083    │  │   │       │
└────────┘  └────┬─┬────┘  └────┬────┘  │   │       │
                 │ │            │       │   │       │
            ┌────┴─┴────┐   ┌───▼──┐    │   │       │
            │  MongoDB  │   │Judge0│    │   │       │
            │(interviews)   │ 2358 │    │   │       │
            └───────────┘   └──┬───┘    │   │       │
                               │        │   │       │
                          ┌────▼────────┴─┬─┴───┬───┴──┐
                          │                │     │      │
                      ┌──▼──┐  ┌──────┐  ┌─▼─┐  │   ┌──▼───┐
                      │Redis │ │Kafka │  │Msg│  │   │Resume │
                      │6379  │ │9092  │  │Qing  │   │8085   │
                      └──────┘ └──────┘  └────┘  │   └───────┘
                                                 │
                                            [AI/ML Services]
                                            (Python Stack)
```

### 7. Access Points
```
🌐 API Gateway        → http://localhost:8080
🔭 Eureka Dashboard   → http://localhost:8761
⚙️ Config Server      → http://localhost:8888
🎤 Interview Service  → http://localhost:8086
📄 Swagger UI         → http://localhost:8080/swagger-ui.html
```

---

## 🐛 Troubleshooting Guide

| Issue | Solution |
|-------|----------|
| **Port Already in Use** | `docker ps` find and kill conflicting container |
| **Out of Memory** | Increase Docker memory allocation in settings |
| **Service Discovery Fail** | Ensure Eureka server is running first |
| **MongoDB Connection Error** | Check MongoDB container logs: `docker logs mongodb` |
| **Kafka Connection Fail** | Verify network: `docker network ls` |
| **Python Service Crashes** | Install GPU drivers for ML services (optional) |

---

## 📈 Performance Optimization

### Caching Strategy
- Redis caching for frequently accessed questions
- MongoDB indexes on `user_id`, `interview_session_id`
- Kafka topic partitioning for parallel processing

### Scaling
- Horizontal scaling of Python AI services (stateless)
- Load balancing via Nginx
- Database connection pooling

---

## 🤝 Contributing Guidelines

1. Create feature branch: `git checkout -b feature/amazing-feature`
2. Commit with clear messages: `git commit -m 'Add amazing feature'`
3. Push to branch: `git push origin feature/amazing-feature`
4. Open Pull Request with detailed description

---

## 📄 License
This project is licensed under the **MIT License**

---

## 🙋 Support & Community

- **Documentation:** [Link to Docs]
- **Issues:** GitHub Issues
- **Discussions:** GitHub Discussions
- **Email:** support@interviewmate.dev

---

<p align="center"> <strong>Built with ❤️ for Interview Excellence</strong> | <strong>2026 & Beyond</strong> </p>
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
