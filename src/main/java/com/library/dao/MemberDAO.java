package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.model.Member;
import com.library.util.DBConnection;

public class MemberDAO {

    // CREATE
    public void addMember(Member m) {
        String sql = "INSERT INTO Member VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, m.getMemberId());
            stmt.setString(2, m.getName());
            stmt.setString(3, m.getEmail());
            stmt.setString(4, m.getMembershipType());

            stmt.executeUpdate();
            System.out.println("Member added successfully");

        } catch (Exception e) {
            System.out.println("Error adding member");
            e.printStackTrace();
        }
    }

    // READ ALL
    public List<Member> getAllMembers() {
        List<Member> list = new ArrayList<>();

        try (Connection conn = DBConnection.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Member")) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            System.out.println("Error fetching members");
            e.printStackTrace();
        }

        return list;
    }

    // UPDATE
    public void updateMember(Member m) {
        String sql = "UPDATE Member SET name=?, email=?, membership_type=? WHERE member_id=?";

        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getName());
            stmt.setString(2, m.getEmail());
            stmt.setString(3, m.getMembershipType());
            stmt.setInt(4, m.getMemberId());

            stmt.executeUpdate();
            System.out.println("Member updated successfully");

        } catch (Exception e) {
            System.out.println("Error updating member");
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteMember(int id) {
        String sql = "DELETE FROM Member WHERE member_id=?";

        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Member deleted successfully");

        } catch (Exception e) {
            System.out.println("Error deleting member");
            e.printStackTrace();
        }
    }

    // BASIC SEARCH
    public List<Member> search(String keyword) {
        List<Member> list = new ArrayList<>();

        String sql = "SELECT * FROM Member WHERE name LIKE ? OR email LIKE ?";

        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            System.out.println("Error searching members");
            e.printStackTrace();
        }

        return list;
    }

    // ADVANCED SEARCH (MULTI-FIELD)
    public List<Member> advancedSearch(String name, String email, String type) {
        List<Member> list = new ArrayList<>();

        String sql = "SELECT * FROM Member WHERE name LIKE ? AND email LIKE ? AND membership_type LIKE ?";

        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            stmt.setString(2, "%" + email + "%");
            stmt.setString(3, "%" + type + "%");

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

    // FILTER BY TYPE
    public List<Member> filterByType(String type) {
        List<Member> list = new ArrayList<>();

        String sql = "SELECT * FROM Member WHERE membership_type=?";

        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            System.out.println("Error filtering members");
            e.printStackTrace();
        }

        return list;
    }

    // SORT MEMBERS
    public List<Member> getSortedMembers() {
        List<Member> list = new ArrayList<>();

        String sql = "SELECT * FROM Member ORDER BY name ASC";

        try (Connection conn = DBConnection.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            System.out.println("Error sorting members");
            e.printStackTrace();
        }

        return list;
    }

    // HELPER METHOD
    private Member mapRow(ResultSet rs) throws SQLException {
        return new Member(
                rs.getInt("member_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("membership_type"));
    }
}