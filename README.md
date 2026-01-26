# Employota
# Employee Management System

A full-stack Employee Management System built with **Spring Boot (Java)** and **React (Vite)**.  
Provides admin & employee dashboards, CRUD operations, secure login, roles, and a clean UI for managing employees.

---

## 🛠 Tech Stack

### Backend
- **Spring Boot 3.5.10** – REST API, Security, JDBC/Hibernate
- **Spring Security** – Authentication, Sessions, Role-based access
- **JPA + Hibernate** – ORM for database operations
- **MySQL** – Database
- **BCrypt** – Password encoding

### Frontend
- **React 18 + Vite** – Modern, fast SPA
- **React Router DOM** – Client-side routing
- **Tailwind CSS / ShadCN UI** – Styling
- **Axios** – HTTP client for Spring Boot API
- **Lucide React** – Icons

### Tools
- VS Code  
- Maven for Spring Boot  
- npm for React
- Postman & Thunder Client – API testing

---

## 📌 Features

### 🔐 Authentication & Security
- Login with email & password  
- Session-based auth with Spring Security  
- Roles: `ROLE_ADMIN`, `ROLE_EMPLOYEE`  
- Role-based UI:  
  - Admin → Admin Dashboard  
  - Employee → Employee Dashboard

### 👤 Admin Dashboard
- View all employees in a table  
- Create, edit, and delete employees  
- Role assignment (Admin/Employee)  
- Fields: First Name, Last Name, Email, Phone, Department, Salary

### 👤 Employee Dashboard
- View personal details:
  - Name, Email, Role  
  - Department, Salary  
  - Session Status (Active/Inactive)

### 📡 REST API (all under `/api`)

#### Auth & User
- `POST /api/login` – Login  
- `POST /api/logout` – Logout  

#### Admin: Employees
- `GET /api/admin/employees` – List all employees  
- `POST /api/admin/employees` – Create employee  
- `PATCH /api/admin/employees/{id}` – Update employee  
- `DELETE /api/admin/employees/{id}` – Delete employee

#### Public: Register
- `POST /api/register` – Register new employee (for self-signup)

---
---

## 🔧 Getting Started

### Prerequisites
- Java 17
- Maven
- Node.js 18+ / npm
- MySQL (or use H2 in-memory database)

---

### 1. Setup Backend

```bash
cd backend

# Optional: Edit application.properties for DB
# spring.datasource.url=jdbc:mysql://localhost:3306/yourdb
# spring.datasource.username=your_user
# spring.datasource.password=your_password
```
# Build and run
```bash
./mvnw spring-boot:run 
```
```bash
cd frontend
```
# Install dependencies
```bash
npm install
```
# Start React dev server
```bash
npm run dev
```
