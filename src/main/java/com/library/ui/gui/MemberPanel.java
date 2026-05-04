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

import com.library.model.Member;
import com.library.service.MemberService;
import com.library.util.ThreadUtil;
import com.library.util.ValidationUtil;

public class MemberPanel extends JPanel {

    private MemberService service = new MemberService();

    private JTable table;
    private DefaultTableModel model;

    private JTextField idField, nameField, emailField, typeField;

    public MemberPanel() {
        setLayout(new BorderLayout());

        // TABLE
        model = new DefaultTableModel(new String[] {
                "ID", "Name", "Email", "Type"
        }, 0);

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // FORM
        JPanel form = new JPanel(new GridLayout(4, 2));

        idField = new JTextField();
        nameField = new JTextField();
        emailField = new JTextField();
        typeField = new JTextField();

        form.add(new JLabel("Member ID:"));
        form.add(idField);

        form.add(new JLabel("Name:"));
        form.add(nameField);

        form.add(new JLabel("Email:"));
        form.add(emailField);

        form.add(new JLabel("Membership Type:"));
        form.add(typeField);

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
        addBtn.addActionListener(e -> addMember());
        updateBtn.addActionListener(e -> updateMember());
        deleteBtn.addActionListener(e -> deleteMember());
        refreshBtn.addActionListener(e -> loadMembersAsync());

        // INITIAL LOAD
        loadMembersAsync();
    }

    private void addMember() {
        String id = idField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String type = typeField.getText();

        if (!ValidationUtil.isNumeric(id)) {
            JOptionPane.showMessageDialog(this, "Invalid ID");
            return;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid Email");
            return;
        }

        if (name.isEmpty() || type.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty");
            return;
        }

        Member m = new Member(
                Integer.parseInt(id),
                name,
                email,
                type);

        service.addMember(m);
        JOptionPane.showMessageDialog(this, "Member added successfully");

        loadMembersAsync();
    }

    private void updateMember() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to update");
            return;
        }

        String id = idField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String type = typeField.getText();

        if (!ValidationUtil.isNumeric(id)) {
            JOptionPane.showMessageDialog(this, "Invalid ID");
            return;
        }

        Member m = new Member(
                Integer.parseInt(id),
                name,
                email,
                type);

        service.updateMember(m);
        JOptionPane.showMessageDialog(this, "Member updated");

        loadMembersAsync();
    }

    private void deleteMember() {
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

        service.deleteMember(id);
        JOptionPane.showMessageDialog(this, "Member deleted");

        loadMembersAsync();
    }

    // MULTI-THREADING (ADVANCED)
    private void loadMembersAsync() {
        ThreadUtil.runAsync(() -> {
            List<Member> list = service.getAllMembers();

            javax.swing.SwingUtilities.invokeLater(() -> {
                model.setRowCount(0);

                for (Member m : list) {
                    model.addRow(new Object[] {
                            m.getMemberId(),
                            m.getName(),
                            m.getEmail(),
                            m.getMembershipType()
                    });
                }
            });
        });
    }
}