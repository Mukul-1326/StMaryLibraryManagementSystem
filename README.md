St Mary Library Management System
Overview
This project is a Java-based Library Management System developed for St Mary’s University. It manages books, members, and borrowing transactions using both console and GUI interfaces. SQLite is used for persistent database storage.
Features
Book Management
- Add, view, update, and delete books
- Search books by title, author, or category
- Track availability status
Member Management
- Register members
- View, update, delete members
- Search members
Borrowing Management
- Borrow and return books
- Track borrowing records
- Delete incorrect records
- Automatic overdue detection
Technologies Used
- Java (JDK)
- SQLite
- JDBC
- Java Swing
- Multithreading
How to Run (No need to compile the file)
1. Run:
java -cp ".;bin;lib/sqlite-jdbc-3.45.1.0.jar;lib/slf4j-api-2.0.9.jar;lib/slf4j-simple-2.0.9.jar" com.library.MainApp

Press 1 for Console Mode
Press 2 for Gui Mode
