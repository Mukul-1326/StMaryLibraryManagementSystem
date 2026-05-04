package com.library;

import java.util.Scanner;

import com.library.ui.console.ConsoleMenu;
import com.library.ui.gui.MainFrame;
import com.library.util.DBConnection;

public class MainApp {

    public static void main(String[] args) {

        // Initialize database
        DBConnection.initializeDatabase();

        Scanner sc = new Scanner(System.in);

        System.out.println("Select Mode:");
        System.out.println("1. Console Mode");
        System.out.println("2. GUI Mode");

        int choice = sc.nextInt();

        if (choice == 1) {
            ConsoleMenu menu = new ConsoleMenu();
            menu.start();
        } else if (choice == 2) {
            MainFrame.launch(); // GUI
        } else {
            System.out.println("Invalid choice");
        }

        sc.close();
    }
}