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

import com.library.model.Book;
import com.library.service.BookService;
import com.library.util.ThreadUtil;
import com.library.util.ValidationUtil;

public class BookPanel extends JPanel {

    private BookService service = new BookService();

    private JTable table;
    private DefaultTableModel model;

    private JTextField idField, titleField, authorField, categoryField;

    public BookPanel() {
        setLayout(new BorderLayout());

        // TABLE
        model = new DefaultTableModel(new String[] {
                "ID", "Title", "Author", "Category", "Status"
        }, 0);

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // FORM
        JPanel form = new JPanel(new GridLayout(4, 2));

        idField = new JTextField();
        titleField = new JTextField();
        authorField = new JTextField();
        categoryField = new JTextField();

        form.add(new JLabel("Book ID:"));
        form.add(idField);

        form.add(new JLabel("Title:"));
        form.add(titleField);

        form.add(new JLabel("Author:"));
        form.add(authorField);

        form.add(new JLabel("Category:"));
        form.add(categoryField);

        add(form, BorderLayout.NORTH);

        // BUTTONS
        JPanel buttons = new JPanel();

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton refreshBtn = new JButton("Refresh");

        buttons.add(addBtn);
        buttons.add(updateBtn);
        buttons.add(deleteBtn);
        buttons.add(refreshBtn);

        add(buttons, BorderLayout.SOUTH);

        // ACTIONS
        addBtn.addActionListener(e -> addBook());
        updateBtn.addActionListener(e -> updateBook());
        deleteBtn.addActionListener(e -> deleteBook());
        refreshBtn.addActionListener(e -> loadBooksAsync());

        // LOAD DATA
        loadBooksAsync();
    }

    private void addBook() {
        String id = idField.getText();
        String title = titleField.getText();
        String author = authorField.getText();
        String category = categoryField.getText();

        if (!ValidationUtil.isNumeric(id)) {
            JOptionPane.showMessageDialog(this, "Invalid ID");
            return;
        }

        if (title.isEmpty() || author.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty");
            return;
        }

        Book b = new Book(
                Integer.parseInt(id),
                title,
                author,
                category,
                "Available");

        service.addBook(b);
        JOptionPane.showMessageDialog(this, "Book added successfully");

        loadBooksAsync();
    }

    private void updateBook() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to update");
            return;
        }

        String id = idField.getText();
        String title = titleField.getText();
        String author = authorField.getText();
        String category = categoryField.getText();

        if (!ValidationUtil.isNumeric(id)) {
            JOptionPane.showMessageDialog(this, "Invalid ID");
            return;
        }

        Book b = new Book(
                Integer.parseInt(id),
                title,
                author,
                category,
                "Available");

        service.updateBook(b);
        JOptionPane.showMessageDialog(this, "Book updated");

        loadBooksAsync();
    }

    private void deleteBook() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete?",
                "Confirm",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION)
            return;

        int id = (int) model.getValueAt(row, 0);

        service.deleteBook(id);
        JOptionPane.showMessageDialog(this, "Book deleted");

        loadBooksAsync();
    }

    // MULTI-THREADING (ADVANCED)
    private void loadBooksAsync() {
        ThreadUtil.runAsync(() -> {
            List<Book> list = service.getAllBooks();

            javax.swing.SwingUtilities.invokeLater(() -> {
                model.setRowCount(0);

                for (Book b : list) {
                    model.addRow(new Object[] {
                            b.getBookId(),
                            b.getTitle(),
                            b.getAuthor(),
                            b.getCategory(),
                            b.getAvailabilityStatus()
                    });
                }
            });
        });
    }
}