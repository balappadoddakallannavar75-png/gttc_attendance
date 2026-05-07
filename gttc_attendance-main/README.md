# 🏫 GTTC Attendance Management System

A professional, enterprise-grade Spring Boot Attendance Tracking Application featuring SaaS-style UI elements, instant reactive filtering, backend MySQL data management, and Automated Gmail Eligibility Assessment notifications.

## 🛠️ Project Architecture
- **Backend Stack:** Java 17 + Spring Boot (3.x)
- **Database:** Relational MySQL DB (Schema target: `gttc_attendance`)
- **Frontend Stack:** HTML5, CSS3 Variables, ES6 Javascript (No heavy SPA frameworks needed -> Zero load time performance)
- **Features Extensively Packed:** 
   - Instant Reactive Search/Filtering by Global Course
   - Live Math Rendering Performance Dashboards
   - Batch Execute Mailing Macros
   - Responsive Glassmorphism Styling

## 🚀 Setup & Execution 

### 1. Database Configuration (MySQL)
Make sure you have a local MySQL instance running natively on `localhost:3306`.
Create a blank database safely named: `gttc_attendance` by logging into MySQL and running:
```sql
CREATE DATABASE gttc_attendance;
```
The Java Spring server natively binds utilizing Hibernate to automatically compile and deploy all table schemas (Students/Users/Attendance Logs) into that database on its first run!

### 2. Configure Email Alert Protocol
The Java Application uses Google SMTP for firing Email Alerts. You must configure an **App Password** (16 characters) inside Google to use it (Using your normal password will not work securely).

**How to secure a 16-Digit App Password:**
1. Log in to your primary Admin Gmail address.
2. Go to your **Google Account Settings** -> **Security**.
3. Under the "How you sign in to Google" block, ensure **2-Step Verification** is Active.
4. In the Search Bar globally searching your account settings, search for **App Passwords**.
5. Create a new linked App profile (name it something cool like "GTTC Mail Server") and click Generate. Google will immediately yield a 16-character scrambled string.
6. Open your project at `src/main/resources/application.properties` and safely copy the email + 16-character payload:
```properties
spring.mail.username=YOUR_ADMIN_GMAIL_HERE@gmail.com
spring.mail.password=YOUR_16_CHAR_NEW_PASSWORD_HERE
```

### 3. Build and Run the Project
Navigate to the root directory dynamically on your box via PowerShell:
```powershell
.\mvnw spring-boot:run
```

The Java application builds dependencies securely on port `8081`. 

### 4. Exploring the Production Client
The custom SPA front-end executes locally via File Protocol rendering on edge! Simply execute the `index.html` file using any Chromium or Webkit browser to dive seamlessly into the fully authorized GTTC Portal dashboard!

