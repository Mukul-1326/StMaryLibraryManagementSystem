package com.library.ui.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.library.model.Borrowing;
import com.library.service.BookService;
import com.library.service.BorrowingService;
import com.library.util.ThreadUtil;
import com.library.util.ValidationUtil;

public class BorrowingPanel extends JPanel {

    private BorrowingService service = new BorrowingService();
    private BookService bookService = new BookService();

    private JTable table;
    private DefaultTableModel model;

    private JTextField bookIdField, memberIdField, borrowDateField, dueDateField;

    public BorrowingPanel() {
        setLayout(new BorderLayout());

        // TABLE
        model = new DefaultTableModel(new String[] {
                "Book ID", "Member ID", "Borrow Date", "Due Date", "Status"
        }, 0);

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // FORM
        JPanel form = new JPanel(new GridLayout(4, 2));

        bookIdField = new JTextField();
        memberIdField = new JTextField();
        borrowDateField = new JTextField();
        dueDateField = new JTextField();

        form.add(new JLabel("Book ID:"));
        form.add(bookIdField);

        form.add(new JLabel("Member ID:"));
        form.add(memberIdField);

        form.add(new JLabel("Borrow Date (YYYY-MM-DD):"));
        form.add(borrowDateField);

        form.add(new JLabel("Due Date (YYYY-MM-DD):"));
        form.add(dueDateField);

        add(form, BorderLayout.NORTH);

        // BUTTONS
        JPanel buttons = new JPanel();

        JButton borrowBtn = new JButton("Borrow");
        JButton returnBtn = new JButton("Return");
        JButton deleteBtn = new JButton("Delete");
        JButton refreshBtn = new JButton("Refresh");

        buttons.add(borrowBtn);
        buttons.add(returnBtn);
        buttons.add(deleteBtn);
        buttons.add(refreshBtn);

        add(buttons, BorderLayout.SOUTH);

        // ACTIONS
        borrowBtn.addActionListener(e -> borrowBook());
        returnBtn.addActionListener(e -> returnBook());
        deleteBtn.addActionListener(e -> deleteRecord());
        refreshBtn.addActionListener(e -> loadRecordsAsync());

        // LOAD DATA
        loadRecordsAsync();
    }

    private void borrowBook() {
        String bookId = bookIdField.getText();
        String memberId = memberIdField.getText();
        String borrowDate = borrowDateField.getText();
        String dueDate = dueDateField.getText();

        if (!ValidationUtil.isNumeric(bookId) || !ValidationUtil.isNumeric(memberId)) {
            JOptionPane.showMessageDialog(this, "Invalid IDs");
            return;
        }

        if (!ValidationUtil.isValidDate(borrowDate) || !ValidationUtil.isValidDate(dueDate)) {
            JOptionPane.showMessageDialog(this, "Invalid date format");
            return;
        }

        Borrowing b = new Borrowing(
                Integer.parseInt(bookId),
                Integer.parseInt(memberId),
                borrowDate,
                dueDate,
                "Borrowed");

        service.addBorrowing(b);
        bookService.markAsBorrowed(Integer.parseInt(bookId));

        JOptionPane.showMessageDialog(this, "Book borrowed successfully");

        loadRecordsAsync();
    }

    private void returnBook() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a record");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Confirm return?", "Confirm",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION)
            return;

        int bookId = (int) model.getValueAt(row, 0);

        service.returnBook(bookId);
        bookService.markAsAvailable(bookId);

        JOptionPane.showMessageDialog(this, "Book returned successfully");

        loadRecordsAsync();
    }

    private void deleteRecord() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a record");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Delete this record?", "Confirm",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION)
            return;

        int bookId = (int) model.getValueAt(row, 0);

        service.deleteBorrowing(bookId);

        JOptionPane.showMessageDialog(this, "Record deleted");

        loadRecordsAsync();
    }

    // MULTI-THREADING USED HERE
    private void loadRecordsAsync() {
        ThreadUtil.runAsync(() -> {
            List<Borrowing> list = service.getAllBorrowings();

            javax.swing.SwingUtilities.invokeLater(() -> {
                model.setRowCount(0);

                for (Borrowing b : list) {
                    model.addRow(new Object[] {
                            b.getBookId(),
                            b.getMemberId(),
                            b.getBorrowDate(),
                            b.getDueDate(),
                            b.getStatus()
                    });
                }
            });
        });
    }
}