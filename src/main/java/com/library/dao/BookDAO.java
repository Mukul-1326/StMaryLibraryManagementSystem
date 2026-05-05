package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.model.Book;
import com.library.util.DBConnection;

public class BookDAO {

    // CREATE
    public void addBook(Book book) {
        String sql = "INSERT INTO books VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, book.getBookId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getCategory());
            stmt.setString(5, book.getAvailabilityStatus());

            stmt.executeUpdate();
            System.out.println("Book added successfully");

        } catch (Exception e) {
            System.out.println("Error adding book");
            e.printStackTrace();
        }
    }

    // READ ALL
    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();

        try (Connection conn = DBConnection.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            System.out.println("Error fetching books");
            e.printStackTrace();
        }

        return list;
    }

    // UPDATE
    public void updateBook(Book book) {
        String sql = "UPDATE books SET title=?, author=?, category=? WHERE book_id=?";

        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getCategory());
            stmt.setInt(4, book.getBookId());

            stmt.executeUpdate();
            System.out.println("Book updated successfully");

        } catch (Exception e) {
            System.out.println("Error updating book");
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteBook(int id) {
        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE book_id=?")) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Book deleted successfully");

        } catch (Exception e) {
            System.out.println("Error deleting book");
            e.printStackTrace();
        }
    }

    // BASIC SEARCH
    public List<Book> search(String keyword) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?";

        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            System.out.println("Error searching books");
            e.printStackTrace();
        }

        return list;
    }

    // ADVANCED SEARCH (MULTI-FILTER)
    public List<Book> advancedSearch(String title, String author, String category) {
        List<Book> list = new ArrayList<>();

        String sql = "SELECT * FROM books WHERE title LIKE ? AND author LIKE ? AND category LIKE ?";

        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + title + "%");
            stmt.setString(2, "%" + author + "%");
            stmt.setString(3, "%" + category + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            System.out.println("Error in advanced search");
            e.printStackTrace();
        }

        return list;
    }

    // FILTER BY CATEGORY
    public List<Book> filterByCategory(String category) {
        List<Book> list = new ArrayList<>();

        String sql = "SELECT * FROM books WHERE category=?";

        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            System.out.println("Error filtering books");
            e.printStackTrace();
        }

        return list;
    }

    // SORT BOOKS
    public List<Book> getSortedBooks() {
        List<Book> list = new ArrayList<>();

        String sql = "SELECT * FROM books ORDER BY title ASC";

        try (Connection conn = DBConnection.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            System.out.println("Error sorting books");
            e.printStackTrace();
        }

        return list;
    }

    // MARK BORROWED
    public void markAsBorrowed(int bookId) {
        updateStatus(bookId, "Borrowed");
    }

    // MARK AVAILABLE
    public void markAsAvailable(int bookId) {
        updateStatus(bookId, "Available");
    }

    private void updateStatus(int bookId, String status) {
        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE books SET availability_status=? WHERE book_id=?")) {

            stmt.setString(1, status);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error updating book status");
            e.printStackTrace();
        }
    }

    // HELPER
    private Book mapRow(ResultSet rs) throws SQLException {
        return new Book(
                rs.getInt("book_id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("category"),
                rs.getString("availability_status"));
    }
}