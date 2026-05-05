package com.library.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {

    private static final String URL = "jdbc:sqlite:database/library.db";

    public static Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");

            // Ensure database folder exists
            File folder = new File("database");
            if (!folder.exists()) {
                folder.mkdir();
            }

            Connection conn = DriverManager.getConnection(URL);

            // Enable foreign keys
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON");
            }

            return conn;

        } catch (Exception e) {
            System.out.println("Database connection failed");
            e.printStackTrace();
            throw new RuntimeException("Cannot connect to database");
        }
    }

    public static void initializeDatabase() {
        try (Connection conn = connect();
                Statement stmt = conn.createStatement()) {

            // BOOK TABLE (UPDATED NAME)
            stmt.execute("CREATE TABLE IF NOT EXISTS books (" +
                    "book_id INTEGER PRIMARY KEY," +
                    "title TEXT NOT NULL," +
                    "author TEXT NOT NULL," +
                    "category TEXT NOT NULL," +
                    "availability_status TEXT NOT NULL)");

            // MEMBER TABLE (UPDATED NAME + COLUMN)
            stmt.execute("CREATE TABLE IF NOT EXISTS members (" +
                    "member_id INTEGER PRIMARY KEY," +
                    "member_name TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "membership_type TEXT NOT NULL)");

            // BORROWING TABLE (UPDATED NAME + COLUMN)
            stmt.execute("CREATE TABLE IF NOT EXISTS borrow_records (" +
                    "record_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "book_id INTEGER NOT NULL," +
                    "member_id INTEGER NOT NULL," +
                    "borrow_date TEXT NOT NULL," +
                    "due_date TEXT NOT NULL," +
                    "return_date TEXT," +
                    "return_status TEXT NOT NULL," +
                    "FOREIGN KEY(book_id) REFERENCES books(book_id)," +
                    "FOREIGN KEY(member_id) REFERENCES members(member_id))");

            // PERFORMANCE OPTIMIZATION (UNCHANGED)
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_book_title ON books(title)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_member_name ON members(member_name)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_borrow_date ON borrow_records(borrow_date)");

            System.out.println("Database initialized successfully");

        } catch (Exception e) {
            System.out.println("Database initialization failed");
            e.printStackTrace();
        }
    }
}