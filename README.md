# 📝 Task Manager

Task Manager is a RESTful Java Spring Boot application that allows users to manage tasks efficiently. It supports features like creating, updating, filtering, and retrieving tasks with support for pagination, due dates, and tags. It also provides endpoints to fetch expired or completed tasks.

---

## 🚀 Tech Stack

* Java 17
* Spring Boot 3.x
* Maven
* Hibernate + JPA
* H2 / MySQL (or any RDBMS)

---

## 📂 Project Structure

```
com.project.taskmanager
│
├── controller         # REST Controllers
├── dto                # Data Transfer Objects
├── exception          # Custom exceptions
├── model              # Entity classes
├── repository         # Spring Data JPA Repositories
├── service            # Business Logic Layer
├── constant           # Application-wide constants & enums
└── TaskManagerApplication.java
```

---

## 🔧 Endpoints

### ➕ Create Task

```
POST /tasks
```

**Body:** `TaskRequest`

### 🔁 Update Task

```
PUT /tasks?id={id}
```

**Body:** `TaskRequest`

### 📄 Get All Tasks (Paginated)

```
GET /tasks?page=0&size=5
```

### 🔍 Get Task By ID

```
GET /tasks/{id}
```

### 📅 Upcoming Tasks

```
GET /tasks/upcoming?days=3
```

### ⏰ Due Today Tasks

```
GET /tasks/dueToday
```

### ❌ Delete Task

```
DELETE /tasks/{id}
```

### 🧮 Get Tasks By Category

```
GET /category
```

### 🏷️ Filter By Tags

```
GET /tasks/tags?tags=tag1,tag2
```

### 🧪 Filter By Fields

```
POST /tasks/filtering
```

**Body:** Partial `TaskRequest`

### ❗ Expired Tasks

```
GET /expiredTasks
```

### ✅ Completed Tasks

```
GET /completedTasks
```

---

## 🎯 Task Metadata

Each task supports the following **priorities** and **statuses**:

### Priority

| Priority | Description                |
| -------- | -------------------------- |
| URGENT   | Needs immediate action     |
| HIGH     | Important but not critical |
| MEDIUM   | Moderate importance        |
| LOW      | Can be done later          |

### Status

| Status       | Description                   |
| ------------ | ----------------------------- |
| TODO         | Task planned, not started     |
| IN\_PROGRESS | Currently being worked on     |
| INCOMPLETE   | Work started but not finished |
| DONE         | Task completed                |

---

## ✅ Validation & Error Handling

* Uses `jakarta.validation` annotations to validate input.
* Throws `ResourceNotFoundException` with custom messages.

---

## 🛠️ Setup & Run


# 1️⃣ Clone the repository
```
git clone https://github.com/SoumyaShinde/taskManager.git
cd taskManager
```

# 2️⃣ Build & Run with Maven
```
./mvnw spring-boot:run
```

# 3️⃣ Run with Docker

Build Docker image
```
docker build -t taskmanager:latest .
```

Run Docker container
```
docker run -p 8080:8080 taskmanager:latest
```

Access the app at: http://localhost:8080

---

## 📌 Notes

* Can switch between H2 and other RDBMS by updating `application.properties`.

---

## 👤 Author

* Soumya Shinde

Feel free to contribute or raise issues! 🚀
