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
How to Run
1. Compile:
javac -cp "lib/*" -d bin @sources.txt

2. Run:
java -cp "bin;lib/*" com.library.MainApp
Important Note
Every time the client wants to run the project:
1. Compile the code
2. Then run the application
This ensures all latest changes are included.
