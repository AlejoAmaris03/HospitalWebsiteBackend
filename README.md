# 🏥 Hospital Management Backend API (Spring Boot)

This is a backend RESTful API for a hospital management system developed using **Spring Boot**. It provides endpoints to manage appointments, schedules, users (patients and doctors), services, and health packages. This project is designed to serve as the backend foundation for a full-stack medical or hospital web application.

> 💡 You can find the frontend here: [Hospital Website Frontend (Angular)](https://github.com/AlejoAmaris03/HospitalWebsiteFronted)

---

## 📌 Features

- 👤 User management (patients and doctors)
- 📅 Doctor scheduling
- 📆 Patient appointments
- 💉 Hospital services (e.g., lab tests, surgery, etc.)
- 📦 Health packages (e.g., wellness checkups, premium plans)
- 🗂️ Role-based access (admin, patient, doctor)
- 📄 RESTful API with JSON responses

---

## ⚙️ Tech Stack

- **Java 23+**
- **Spring Boot 3.4.4**
  - Spring Web
  - Spring Data JPA
  - Spring Security (JWT)
- **PostgreSQL**
- **Lombok**
- **Maven**

---

## 📁 Project Structure
![image](https://github.com/user-attachments/assets/6d7fb10f-0296-4e8d-a39e-6d1eba7c5f7b)

### Installation

1. **Clone the repo**
   ```bash
   git clone https://github.com/AlejoAmaris03/HospitalWebsiteBackend.git
   cd HospitalWebsiteBackend-main

2. **Configure DB in application.properties**
   ```bash
    spring.datasource.username=your_user
    spring.datasource.password=your_password
    jwt.key=your_generated_key
  To generate a secure JWT key, open a terminal (CMD o PowerShell) and run:
  ```bash
    openssl rand -base64 32
  ```
  Copy the generated key and replace *your_generated_key* with it.

4. Run the project

5. The app should be running at: http://localhost:8080

### Access with Default Admin Credentials
  > 📝 You can log in with the following default administrator account, which is automatically created when the application starts.

👤 Username:    admin  
🔑 Password: admin123
 
- This account has full access to manage users, appointments, schedules, services, and packages.
