# 🕉️ BhaktiFlow (Spiritual Sangam)
**A Scalable Full-Stack Devotional Media Platform** *Built with Spring Boot, Next.js, Apache Kafka, and Redis*

---

## 🚀 Project Overview
BhaktiFlow is a high-concurrency media streaming platform designed for the spiritual community. Unlike standard CRUD applications, this project implements **Event-Driven Architecture** and **In-Memory Caching** to ensure a low-latency user experience even during peak traffic (like festivals or live kathas).

---

## 🛠️ The Tech Stack
* **Frontend:** Next.js 14, React, Tailwind CSS, Lucide Icons.
* **Backend:** Java 17, Spring Boot, Spring Security (JWT), Maven.
* **Message Broker:** Apache Kafka (Asynchronous event logging).
* **Caching:** Redis (Real-time trending & session management).
* **Database:** MongoDB Atlas (NoSQL for flexible content metadata).



---

## 🏗️ System Architecture & Design Patterns
To meet industry standards (relevant to JPMC SEP), I implemented the following:

1. **Asynchronous Decoupling (Kafka):** Used Kafka to handle non-critical paths like "User Activity Logging." This prevents the API from "hanging" if the logging service is slow, ensuring a fast UI response.
2. **Distributed Caching (Redis):** Implemented a "Trending" algorithm that stores frequently accessed bhajan metadata in Redis, reducing the load on MongoDB.
3. **Monorepo Structure:** Organized both Frontend and Backend in a single repository for easier management and atomic commits.
4. **Security-First Approach:** Sanitized configuration using Environment Variables and placeholders to prevent credential leaks.



---

## 📂 Project Structure
```text
.
├── bhaktiflow-frontend/     # Next.js Frontend (React)
├── src/                     # Spring Boot Backend (Java)
│   ├── main/java/.../       # Controllers, Services, Repositories
│   └── main/resources/      # application.yaml (Config Templates)
├── pom.xml                  # Backend Dependencies
└── .gitignore               # Multi-layer exclusion (Node, Maven, Configs)
