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
javac -cp ".;lib/sqlite-jdbc-3.45.1.0.jar;lib/slf4j-api-2.0.9.jar;lib/slf4j-simple-2.0.9.jar" -d bin src\main\java\com\library\MainApp.java src\main\java\com\library\dao\*.java src\main\java\com\library\service\*.java src\main\java\com\library\model\*.java src\main\java\com\library\util\*.java src\main\java\com\library\ui\console\*.java src\main\java\com\library\ui\gui\*.java

2. Run:
java -cp ".;bin;lib/sqlite-jdbc-3.45.1.0.jar;lib/slf4j-api-2.0.9.jar;lib/slf4j-simple-2.0.9.jar" com.library.MainApp
Important Note
Every time the client wants to run the project:
1. Compile the code
2. Then run the application
This ensures all latest changes are included.
