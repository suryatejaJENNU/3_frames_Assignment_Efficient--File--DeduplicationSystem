### 📁 Efficient File Deduplication System  

### Assignment for 3Frames  
👤 **Submitted by:** Suryateja Jennu  
🆔 **Student ID:** B192653  
📂 **GitHub Repository:** [3Frames Assignment - File Deduplication](https://github.com/suryatejaJENNU/3_frames_Assignment_Efficient--File--DeduplicationSystem)  

---

### 📌 Problem Statement  

Develop a system that scans a folder and removes duplicate files based on **content** (not just name).  

### ✅ Features:  
- 🔍 **Uses SHA-256 hashing** or checksums to detect duplicates  
- 🚀 **Optimized for large file scanning**  
- 🔄 **Allows safe deletion or backup of duplicates**  
- ☁️ **Supports local and cloud storage (Google Drive, S3, etc.)**  

---

### 📂 Setup MySQL Database  

1️⃣ **Start MySQL Server**  
2️⃣ **Open MySQL Command Line or MySQL Workbench**  
3️⃣ **Run the following SQL command:**  

```sql
CREATE DATABASE file_deduplication; ''''

### Update DatabaseManager.java with your MySQL credentials:

private static final String DB_URL = "jdbc:mysql://localhost:3306/file_deduplication";
private static final String DB_USER = "your_mysql_user";
private static final String DB_PASSWORD = "your_mysql_password";

### Running the Application
🔹 Without Maven (Direct Java Compilation)

javac -d out -cp src $(find src -name "*.java")
java -cp out deduplication.Main


🔹 Using Maven (Recommended)
1️⃣ Ensure MySQL dependency is added in pom.xml


<dependencies>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
</dependencies>


2️⃣ Run the project

mvn compile exec:java -Dexec.mainClass="deduplication.Main"


📜 Usage Instructions
1️⃣ Run the application
2️⃣ Enter folder path when prompted
3️⃣ Choose an action:

(D) Delete duplicates
(B) Backup duplicates
4️⃣ The program will process files and display results.

📁 Enter folder path to scan: /Users/John/Documents
Do you want to (D)elete or (B)ackup duplicates? (D/B): D

🔍 Scanning directory: /Users/Suryateja/Documents
🗑️ Removed duplicate: report.pdf
🗑️ Removed duplicate: invoice_2023.xlsx
✅ Deduplication Completed
🗑️ Duplicates removed: 2
💾 Total space saved: 4.3 MB




📜 License
This project is part of the 3Frames hiring assignment.

🚀 Happy Coding!
