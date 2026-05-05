package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.model.Borrowing;
import com.library.util.DBConnection;

public class BorrowingDAO {

    // CREATE
    public void addBorrowing(Borrowing b) {
        String sql = "INSERT INTO borrow_records (book_id, member_id, borrow_date, due_date, return_status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, b.getBookId());
            stmt.setInt(2, b.getMemberId());
            stmt.setString(3, b.getBorrowDate());
            stmt.setString(4, b.getDueDate());
            stmt.setString(5, b.getStatus());

            stmt.executeUpdate();
            System.out.println("Borrow record added successfully");

        } catch (Exception e) {
            System.out.println("Error adding borrow record");
            e.printStackTrace();
        }
    }

    // GET ALL
    public List<Borrowing> getAll() {
        List<Borrowing> list = new ArrayList<>();

        try (Connection conn = DBConnection.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM borrow_records")) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            System.out.println("Error retrieving records");
            e.printStackTrace();
        }

        return list;
    }

    // SEARCH
    public List<Borrowing> search(int bookId, int memberId) {
        List<Borrowing> list = new ArrayList<>();

        String sql = "SELECT * FROM borrow_records WHERE 1=1";

        if (bookId != -1) {
            sql += " AND book_id=" + bookId;
        }

        if (memberId != -1) {
            sql += " AND member_id=" + memberId;
        }

        try (Connection conn = DBConnection.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            System.out.println("Error searching records");
            e.printStackTrace();
        }

        return list;
    }

    // FILTER BY DATE
    public List<Borrowing> filterByDateRange(String startDate, String endDate) {
        List<Borrowing> list = new ArrayList<>();

        String sql = "SELECT * FROM borrow_records WHERE borrow_date BETWEEN ? AND ?";

        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, startDate);
            stmt.setString(2, endDate);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            System.out.println("Error filtering records");
            e.printStackTrace();
        }

        return list;
    }

    // RETURN BOOK
    public void returnBook(int bookId) {
        String sql = "UPDATE borrow_records SET return_status='Returned', return_date=date('now') WHERE book_id=? AND return_status='Borrowed'";

        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);
            stmt.executeUpdate();
            System.out.println("Book returned");

        } catch (Exception e) {
            System.out.println("Error returning book");
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteBorrowing(int bookId) {
        String sql = "DELETE FROM borrow_records WHERE book_id=?";

        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);
            stmt.executeUpdate();
            System.out.println("Record deleted");

        } catch (Exception e) {
            System.out.println("Error deleting record");
            e.printStackTrace();
        }
    }

    // OVERDUE UPDATE
    public void updateOverdueStatus() {
        String sql = "UPDATE borrow_records SET return_status='Overdue' WHERE return_status='Borrowed' AND date(due_date) < date('now')";

        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error updating overdue");
            e.printStackTrace();
        }
    }

    // OVERDUE FETCH
    public List<Borrowing> getOverdueRecords() {
        List<Borrowing> list = new ArrayList<>();

        try (Connection conn = DBConnection.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM borrow_records WHERE return_status='Overdue'")) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            System.out.println("Error fetching overdue");
            e.printStackTrace();
        }

        return list;
    }

    // SORT
    public List<Borrowing> getSortedByDate() {
        List<Borrowing> list = new ArrayList<>();

        try (Connection conn = DBConnection.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM borrow_records ORDER BY borrow_date ASC")) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            System.out.println("Error sorting");
            e.printStackTrace();
        }

        return list;
    }

    // HELPER
    private Borrowing mapRow(ResultSet rs) throws SQLException {
        return new Borrowing(
                rs.getInt("book_id"),
                rs.getInt("member_id"),
                rs.getString("borrow_date"),
                rs.getString("due_date"),
                rs.getString("return_status"));
    }
}