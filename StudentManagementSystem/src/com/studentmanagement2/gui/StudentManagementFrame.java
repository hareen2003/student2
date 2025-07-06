/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.studentmanagement2.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentManagementFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;
    private String currentSearchTerm = null;

    public StudentManagementFrame() {
        setTitle("Manage Students");
        setSize(800, 500); // Increased size for better UI
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");
        JButton resetBtn = new JButton("Reset");

        searchPanel.add(new JLabel("Search by ID/Name/Course:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(resetBtn);

        // Table setup
        model = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Course"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // For demo: add sample data (comment out the next line if you want to load from DB)
        addSampleData();

        // If you want to load from database, comment out addSampleData() above and uncomment below:
        // loadStudents(null);

        // Action buttons
        JButton editBtn = new JButton("Edit");
        editBtn.addActionListener(e -> editStudent());

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.addActionListener(e -> deleteStudent());

        JButton backBtn = new JButton("Back to Dashboard");
        backBtn.addActionListener(e -> {
            this.dispose();
            new DashboardFrame().setVisible(true);
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(backBtn);

        // Search functionality (works only with DB, not sample data)
        searchBtn.addActionListener(e -> {
            currentSearchTerm = searchField.getText().trim();
            // loadStudents(currentSearchTerm); // Uncomment for DB search
        });

        resetBtn.addActionListener(e -> {
            searchField.setText("");
            currentSearchTerm = null;
            // loadStudents(null); // Uncomment for DB reset
        });

        // Add components to frame
        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // === SAMPLE DATA FOR DEMO PURPOSES ===
    private void addSampleData() {
        model.setRowCount(0); // Clear existing data
        model.addRow(new Object[]{1, "John Doe", "john@example.com", "Computer Science"});
        model.addRow(new Object[]{2, "Jane Smith", "jane@example.com", "Mathematics"});
        model.addRow(new Object[]{3, "Alice Brown", "alice@example.com", "Physics"});
        model.addRow(new Object[]{4, "Bob Johnson", "bob@example.com", "Business"});
        model.addRow(new Object[]{5, "Charlie Wilson", "charlie@example.com", "English"});
    }

    // === DATABASE METHODS (OPTIONAL, FOR REAL DATA) ===
    /*
    private void loadStudents(String searchTerm) {
        try {
            model.setRowCount(0); // Clear existing data
            StudentDAO dao = new StudentDAO();
            List<Student> students;

            if (searchTerm == null || searchTerm.isEmpty()) {
                students = dao.getAllStudents();
            } else {
                students = dao.searchStudents(searchTerm);
            }

            for (Student s : students) {
                model.addRow(new Object[]{s.getId(), s.getName(), s.getEmail(), s.getCourse()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    */

    private void editStudent() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to edit",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        // new StudentEditFrame(id, this); // Uncomment if you have StudentEditFrame implemented
        JOptionPane.showMessageDialog(this, "Edit feature not implemented in sample mode.");
    }

    public void refresh() {
        // loadStudents(currentSearchTerm); // Uncomment for DB
        addSampleData(); // For demo/sample mode
    }

    private void deleteStudent() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        String name = (String) model.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete student: " + name + " (ID: " + id + ")?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            // For demo/sample mode
            model.removeRow(row);

            // For DB mode, uncomment below:
            /*
            try {
                StudentDAO dao = new StudentDAO();
                dao.deleteStudent(id);
                loadStudents(currentSearchTerm); // Refresh with current search
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error deleting student: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
            */
        }
    }
}
