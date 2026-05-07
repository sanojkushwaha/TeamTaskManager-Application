# 🚀 Team Task Manager

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen?style=for-the-badge&logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![JWT](https://img.shields.io/badge/JWT-Auth-purple?style=for-the-badge&logo=jsonwebtokens)
![Railway](https://img.shields.io/badge/Deployed-Railway-black?style=for-the-badge&logo=railway)

---

# TEAM TASK MANAGER - Full Stack Web Application

### Built with:
`Java 17 + Spring Boot 3.2 + MySQL + JWT`

A modern full-stack task management application designed to help teams manage projects, assign tasks, and track progress efficiently.

Built with simplicity in mind, the platform allows admins to organize projects and manage team members, while users can focus on completing assigned tasks and monitoring project progress in real time.

The application features secure JWT authentication, role-based access control, project management, task tracking, dashboard analytics, and a responsive user interface.

---

# 🌐 Live URL

### 🔗 Live Application
https://teamtaskmanager-application-production.up.railway.app

### 🔗 GitHub Repository
https://github.com/sanojkushwaha/TeamTaskManager-Application

---

# 📌 Project Overview

A full-stack Team Task Manager with:

- JWT Authentication (Signup/Login)
- Role-Based Access Control (ADMIN / MEMBER)
- Project & Team Management
- Task Creation, Assignment & Status Tracking
- Dashboard with Stats, Progress & Overdue Tasks
- Simple HTML/JS Frontend + REST API

---

# 🛠 Tech Stack

| Category | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.2.3 |
| Security | Spring Security + JWT (jjwt 0.11.5) |
| Database | MySQL 8 + Spring Data JPA (Hibernate) |
| Frontend | HTML5 + Vanilla JavaScript + CSS3 |
| Build | Maven |
| Hosting | Railway |

---

# 📂 Project Structure

```bash
team-task-manager/
│
├── src/main/java/com/taskmanager/
│   │
│   ├── TeamTaskManagerApplication.java
│   │
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   └── GlobalExceptionHandler.java
│   │
│   ├── security/
│   │   ├── JwtUtil.java
│   │   ├── JwtAuthenticationFilter.java
│   │   └── UserDetailsServiceImpl.java
│   │
│   ├── enums/
│   │   ├── Role.java
│   │   ├── TaskStatus.java
│   │   └── Priority.java
│   │
│   ├── model/
│   │   ├── User.java
│   │   ├── Project.java
│   │   ├── Task.java
│   │   └── ProjectMember.java
│   │
│   ├── dto/
│   │   ├── SignupRequest.java
│   │   ├── LoginRequest.java
│   │   ├── AuthResponse.java
│   │   ├── ProjectRequest.java
│   │   ├── ProjectResponse.java
│   │   ├── TaskRequest.java
│   │   ├── TaskResponse.java
│   │   └── DashboardStats.java
│   │
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── ProjectRepository.java
│   │   ├── TaskRepository.java
│   │   └── ProjectMemberRepository.java
│   │
│   ├── service/
│   │   ├── AuthService.java
│   │   ├── ProjectService.java
│   │   ├── TaskService.java
│   │   └── DashboardService.java
│   │
│   └── controller/
│       ├── AuthController.java
│       ├── ProjectController.java
│       ├── TaskController.java
│       └── DashboardController.java
│
├── src/main/resources/
│   ├── application.properties
│   ├── application-prod.properties
│   └── static/
│       ├── index.html
│       ├── dashboard.html
│       ├── projects.html
│       └── tasks.html
│
├── nixpacks.toml
├── railway.json
├── Procfile
└── pom.xml
```

---

# 🌐 REST API Endpoints

## 🔐 AUTH APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/signup` | Register User |
| POST | `/api/auth/login` | Login & Get JWT |

---

## 📁 PROJECT APIs

| Method | Endpoint |
|---|---|
| GET | `/api/projects` |
| POST | `/api/projects` |
| GET | `/api/projects/{id}` |
| PUT | `/api/projects/{id}` |
| DELETE | `/api/projects/{id}` |

---

## ✅ TASK APIs

| Method | Endpoint |
|---|---|
| POST | `/api/tasks` |
| GET | `/api/tasks/project/{id}` |
| GET | `/api/tasks/my` |
| PUT | `/api/tasks/{id}` |
| PATCH | `/api/tasks/{id}/status` |
| DELETE | `/api/tasks/{id}` |

---

# ⚙️ Setup & Run Locally

## 1️⃣ Clone Repository

```bash
git clone https://github.com/sanojkushwaha/TeamTaskManager-Application
cd team-task-manager
```

---

## 2️⃣ Create Database

```sql
CREATE DATABASE task_manager_db;
```

---

## 3️⃣ Configure Database

Update:

```properties
src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/task_manager_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

---

## 4️⃣ Run Application

```bash
mvn spring-boot:run
```

Application runs at:

```bash
http://localhost:8080
```

---

# 🚀 Deployment on Railway

1. Push code to GitHub

2. Go to Railway Dashboard

3. Create New Project

4. Deploy from GitHub Repository

5. Add MySQL Plugin

6. Configure Environment Variables

7. Deploy Application

---

# 🔒 Role-Based Access Control

## ADMIN
- Create / Edit / Delete projects
- Add / Remove members
- Create / Edit / Delete tasks
- View all projects and tasks
- Access full dashboard

## MEMBER
- View assigned projects
- Create tasks
- Update assigned tasks
- Update task status
- View personal dashboard stats

---

# 📬 Sample API Usage

## Signup

```http
POST /api/auth/signup
```

```json
{
  "name": "John Admin",
  "email": "admin@test.com",
  "password": "admin123",
  "role": "ADMIN"
}
```

---

## Login

```http
POST /api/auth/login
```

```json
{
  "email": "admin@test.com",
  "password": "admin123"
}
```

---

# 👨‍💻 Author

## Sanoj Kushwaha

📧 Email:  
kushawahasanoj123@gmail.com

🔗 GitHub:  
https://github.com/sanojkushwaha/TeamTaskManager-Application

---

# 📄 License

This project was developed for learning and assessment purposes.
