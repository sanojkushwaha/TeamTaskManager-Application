========================================================
 TEAM TASK MANAGER - Full Stack Web Application
 Built with: Java 17 + Spring Boot 3.2 + MySQL + JWT
========================================================

LIVE URL: https://your-app.railway.app   (update after deployment)
GITHUB:   https://github.com/yourusername/team-task-manager

------------------------------------------------------------
PROJECT OVERVIEW
------------------------------------------------------------
A full-stack Team Task Manager with:
  - JWT Authentication (Signup/Login)
  - Role-Based Access Control (ADMIN / MEMBER)
  - Project & Team Management
  - Task Creation, Assignment & Status Tracking
  - Dashboard with Stats, Progress & Overdue Tasks
  - Simple HTML/JS Frontend + REST API

------------------------------------------------------------
TECH STACK
------------------------------------------------------------
  Backend  : Java 17, Spring Boot 3.2.3
  Security : Spring Security + JWT (jjwt 0.11.5)
  Database : MySQL 8 + Spring Data JPA (Hibernate)
  Frontend : HTML5 + Vanilla JavaScript + CSS3
  Build    : Maven

------------------------------------------------------------
PROJECT STRUCTURE
------------------------------------------------------------
src/main/java/com/taskmanager/
  ├── TeamTaskManagerApplication.java   (Entry point)
  ├── config/
  │   ├── SecurityConfig.java           (JWT + CORS + Auth)
  │   └── GlobalExceptionHandler.java   (Error handling)
  ├── security/
  │   ├── JwtUtil.java                  (Token generate/validate)
  │   ├── JwtAuthenticationFilter.java  (Request interceptor)
  │   └── UserDetailsServiceImpl.java   (Load user from DB)
  ├── enums/
  │   ├── Role.java          (ADMIN, MEMBER)
  │   ├── TaskStatus.java    (TODO, IN_PROGRESS, DONE)
  │   └── Priority.java      (LOW, MEDIUM, HIGH)
  ├── model/
  │   ├── User.java          (Users table + UserDetails)
  │   ├── Project.java       (Projects table)
  │   ├── Task.java          (Tasks table)
  │   └── ProjectMember.java (Project members join table)
  ├── repository/            (Spring Data JPA repos)
  ├── dto/                   (Request/Response objects)
  ├── service/               (Business logic)
  └── controller/            (REST API endpoints)

src/main/resources/
  ├── application.properties
  └── static/
      ├── index.html         (Login / Signup)
      ├── dashboard.html     (Stats + Overdue + Progress)
      ├── projects.html      (Project CRUD + Members)
      └── tasks.html         (Task CRUD + Quick Status)

------------------------------------------------------------
REST API ENDPOINTS
------------------------------------------------------------

AUTH:
  POST   /api/auth/signup         Register (ADMIN/MEMBER)
  POST   /api/auth/login          Login, returns JWT

PROJECTS (Authenticated):
  GET    /api/projects            List my projects
  POST   /api/projects            Create project [ADMIN]
  GET    /api/projects/{id}       Get project details
  PUT    /api/projects/{id}       Update project [ADMIN]
  DELETE /api/projects/{id}       Delete project [ADMIN]
  POST   /api/projects/{id}/members/{userId}   Add member [ADMIN]
  DELETE /api/projects/{id}/members/{userId}   Remove member [ADMIN]
  GET    /api/projects/users/all  List all users

TASKS (Authenticated):
  POST   /api/tasks               Create task
  GET    /api/tasks/project/{id}  Tasks by project
  GET    /api/tasks/my            My assigned tasks
  GET    /api/tasks/all           All tasks [ADMIN]
  GET    /api/tasks/{id}          Get task
  PUT    /api/tasks/{id}          Update task
  PATCH  /api/tasks/{id}/status   Quick status update
  DELETE /api/tasks/{id}          Delete task

DASHBOARD:
  GET    /api/dashboard           Stats, overdue, progress

------------------------------------------------------------
SETUP & RUN LOCALLY
------------------------------------------------------------

PREREQUISITES:
  - Java 17+
  - Maven 3.8+
  - MySQL 8 running on localhost:3306
  - IntelliJ IDEA (recommended)

STEPS:

1. Clone or extract the project:
   git clone https://github.com/yourusername/team-task-manager
   cd team-task-manager

2. Create MySQL database:
   mysql -u root -p
   CREATE DATABASE task_manager_db;
   EXIT;

3. Update DB credentials in:
   src/main/resources/application.properties
   → Set spring.datasource.username and spring.datasource.password

4. Run the application:
   mvn spring-boot:run
   OR open in IntelliJ → Run TeamTaskManagerApplication

5. Access the app:
   Frontend:  http://localhost:8080/
   API Base:  http://localhost:8080/api

NOTE: Tables are auto-created by Hibernate on first run.

------------------------------------------------------------
DEPLOYMENT ON RAILWAY
------------------------------------------------------------

1. Push code to GitHub

2. Go to https://railway.app → New Project → Deploy from GitHub

3. Add MySQL Plugin:
   Dashboard → + New → Database → MySQL
   Copy the DATABASE_URL

4. Set Environment Variables in Railway:
   SPRING_DATASOURCE_URL=jdbc:mysql://...
   SPRING_DATASOURCE_USERNAME=root
   SPRING_DATASOURCE_PASSWORD=your_password
   APP_JWT_SECRET=TaskManagerSuperSecretKey2024

5. Railway auto-detects Maven and deploys.
   Get the public URL from Railway dashboard.

------------------------------------------------------------
ROLE-BASED ACCESS CONTROL
------------------------------------------------------------

ADMIN can:
  ✅ Create / Edit / Delete projects
  ✅ Add / Remove members from projects
  ✅ Create / Edit / Delete any task
  ✅ View all projects, tasks, and users
  ✅ See full dashboard stats

MEMBER can:
  ✅ View projects they are added to
  ✅ Create tasks in their projects
  ✅ Update tasks assigned to them
  ✅ Update task status
  ✅ View their own dashboard stats

------------------------------------------------------------
SAMPLE API USAGE (Postman)
------------------------------------------------------------

1. Signup as Admin:
   POST /api/auth/signup
   {
     "name": "John Admin",
     "email": "admin@test.com",
     "password": "admin123",
     "role": "ADMIN"
   }

2. Login:
   POST /api/auth/login
   { "email": "admin@test.com", "password": "admin123" }
   → Copy the "token" from response

3. Use token in all requests:
   Header: Authorization: Bearer <your_token>

4. Create Project:
   POST /api/projects
   { "name": "My Project", "description": "Test project" }

5. Create Task:
   POST /api/tasks
   {
     "title": "Fix login bug",
     "description": "User reports login fails",
     "projectId": 1,
     "priority": "HIGH",
     "dueDate": "2024-12-31",
     "assignedToUserId": 2
   }

6. Update Task Status:
   PATCH /api/tasks/1/status
   { "status": "IN_PROGRESS" }

------------------------------------------------------------
AUTHOR
------------------------------------------------------------
Name  : [Your Name]
Email : [Your Email]
Date  : 2024
========================================================
