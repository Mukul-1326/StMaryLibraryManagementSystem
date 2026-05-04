package com.library.ui.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("St Mary's Library Management System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // CENTER WINDOW
        setLocationRelativeTo(null);

        // TABS
        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Books", new BookPanel());
        tabs.add("Members", new MemberPanel());
        tabs.add("Borrowing", new BorrowingPanel());

        add(tabs);

        setVisible(true);
    }

    // THREAD-SAFE GUI LAUNCH (ADVANCED)
    public static void launch() {
        SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }
}