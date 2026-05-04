package com.library;

import java.sql.Connection;

import com.library.util.DBConnection;

public class TestConnection {

    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.connect();

            if (conn != null && !conn.isClosed()) {
                System.out.println("Connected Successfully!");
                conn.close();
            } else {
                System.out.println("Connection Failed");
            }

        } catch (Exception e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
    }
}