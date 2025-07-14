# taskManager

TaskManager is a powerful, full-stack Task Management Web Application built using Spring Boot (Java) for the backend and React for the frontend (coming soon). It provides users with efficient ways to manage tasks, set deadlines, assign priorities, track progress, and group tasks by categories and tags. The application is designed with modern best practices, including JWT Authentication for secure user sessions and integration with PostgreSQL for persistent storage.

Features Implemented
Task Management:

CRUD Operations: Create, read, update, and delete tasks with the necessary details, including titles, descriptions, due dates, categories, and more.

Task Priority & Status: Assign priorities (e.g., high, medium, low) and update task statuses (e.g., pending, completed).

Pagination: Fetch tasks in paginated form for better performance on large datasets.

Filtering and Sorting:

Task Filtering: Filter tasks based on conditions like title, status, priority, category, and tags.

Task Sorting: Sort tasks by various criteria such as priority, due date, etc.

Upcoming Tasks: View tasks that are due in the coming days (e.g., today, in 3 days).

Expired Tasks: Display tasks that have missed their due date and remain incomplete.

Completed Tasks: Retrieve and display completed tasks.

Category and Tag Grouping: Group tasks by categories or tags to help users stay organized.

JWT Authentication: Secure access to the app with JWT (JSON Web Tokens) for user login and session management.

API Documentation with Swagger:

Access the complete API documentation for your application directly at /swagger-ui.

This makes it easy to interact with and test the API.

Tech Stack
Backend:

Java 17 (or later)

Spring Boot (for RESTful APIs)

Spring Security with JWT for authentication

PostgreSQL for database management

Frontend:

React.js (coming soon)

Development Tools:

Maven (for dependency management and builds)

Spring Tool Suite / IntelliJ IDEA for backend development
