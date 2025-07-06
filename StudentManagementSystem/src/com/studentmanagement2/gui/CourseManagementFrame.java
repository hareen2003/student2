/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.studentmanagement2.gui;

import com.studentmanagement.dao.CourseDAO;
import com.studentmanagement.model.Course;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class CourseManagementFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField nameField;
    private JTextField searchField;
    private JButton backButton;

    public CourseManagementFrame() {
        initializeFrame();
        createComponents();
        addComponents();
        addEventListeners();
        loadCourses();
        setVisible(true);
    }
    
    private void initializeFrame() {
        setTitle("Course Management");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 245, 249));
    }
    
    private void createComponents() {
        // Table model
        model = new DefaultTableModel(new String[]{"id", "Course name"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        // Table with styling
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setGridColor(new Color(220, 220, 220));
        
        // Form fields
        nameField = new JTextField();
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Back to Dashboard button
        backButton = createStyledButton("Back to Dashboard", new Color(60, 120, 170));
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }
            
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private void addComponents() {
        setLayout(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Course Management");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        // REMOVE this line (don't add backButton to header):
        // headerPanel.add(backButton, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(15, 15, 10, 15),
            new TitledBorder("Search Courses")
        ));
        searchPanel.setBackground(Color.WHITE);
        
        searchPanel.add(new JLabel("Search by Name:"), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        
        JButton searchButton = createStyledButton("Search", new Color(218, 165, 32));
        searchButton.addActionListener(e -> searchCourses());
        searchPanel.add(searchButton, BorderLayout.EAST);
        
        add(searchPanel, BorderLayout.NORTH);
        
        // Table panel
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(0, 15, 10, 15),
            new TitledBorder("Course List")
        ));
        scrollPane.setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(0, 15, 15, 15),
            new TitledBorder("Add New Course")
        ));
        formPanel.setBackground(Color.WHITE);
        
        formPanel.add(new JLabel("Course Name:"));
        formPanel.add(nameField);
        
        JButton addButton = createStyledButton("Add Course", new Color(46, 139, 87));
        addButton.addActionListener(e -> addCourse());
        formPanel.add(addButton);
        
        add(formPanel, BorderLayout.SOUTH);

        // Action buttons panel (including Back to Dashboard)
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        actionPanel.setBorder(new EmptyBorder(0, 15, 15, 15));
        actionPanel.setBackground(Color.WHITE);

        JButton editButton = createStyledButton("Edit Selected", new Color(70, 130, 180));
        editButton.addActionListener(e -> editCourse());
        actionPanel.add(editButton);

        JButton deleteButton = createStyledButton("Delete Selected", new Color(220, 20, 60));
        deleteButton.addActionListener(e -> deleteCourse());
        actionPanel.add(deleteButton);

        JButton refreshButton = createStyledButton("Refresh", new Color(46, 139, 87));
        refreshButton.addActionListener(e -> loadCourses());
        actionPanel.add(refreshButton);

        // ADD the backButton here:
        actionPanel.add(backButton);

        add(actionPanel, BorderLayout.PAGE_END);
    }
    
    private void addEventListeners() {
        // Back button action
        backButton.addActionListener(e -> returnToDashboard());
        
        // Add Enter key functionality
        nameField.addActionListener(e -> addCourse());
        searchField.addActionListener(e -> searchCourses());
        
        // Double-click to edit
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editCourse();
                }
            }
        });
    }
    
    private void loadCourses() {
        try {
            model.setRowCount(0); // Clear existing data
            CourseDAO dao = new CourseDAO();
            List<Course> courses = dao.getAllCourses();
            
            if (courses.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No courses found in the database.", 
                    "Information", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            for (Course c : courses) {
                model.addRow(new Object[]{c.getId(), c.getName()});
            }
            
            searchField.setText(""); // Clear search field
        } catch (SQLException e) {
            showError("Error loading courses: " + e.getMessage());
        }
    }
    
    private void searchCourses() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadCourses();
            return;
        }
        
        try {
            model.setRowCount(0);
            CourseDAO dao = new CourseDAO();
            List<Course> courses = dao.searchCoursesByName(searchTerm);
            
            if (courses.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No courses found matching: " + searchTerm, 
                    "Search Results", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            for (Course c : courses) {
                model.addRow(new Object[]{c.getId(), c.getName()});
            }
        } catch (SQLException e) {
            showError("Error searching courses: " + e.getMessage());
        }
    }
    
    private void addCourse() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            showError("Course name is required.");
            nameField.requestFocus();
            return;
        }
        
        try {
            CourseDAO dao = new CourseDAO();
            dao.addCourse(new Course(0, name));
            
            JOptionPane.showMessageDialog(this, 
                "Course added successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            nameField.setText("");
            loadCourses();
        } catch (SQLException e) {
            showError("Error adding course: " + e.getMessage());
        }
    }
    
    private void editCourse() {
        int row = table.getSelectedRow();
        if (row == -1) {
            showError("Please select a course to edit.");
            return;
        }
        
        int id = (int) model.getValueAt(row, 0);
        String currentName = (String) model.getValueAt(row, 1);
        
        String newName = JOptionPane.showInputDialog(
            this, 
            "Edit course name:", 
            currentName
        );
        
        if (newName != null && !newName.trim().isEmpty()) {
            try {
                CourseDAO dao = new CourseDAO();
                dao.updateCourse(new Course(id, newName.trim()));
                
                JOptionPane.showMessageDialog(this, 
                    "Course updated successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                loadCourses();
            } catch (SQLException e) {
                showError("Error updating course: " + e.getMessage());
            }
        }
    }
    
    private void deleteCourse() {
        int row = table.getSelectedRow();
        if (row == -1) {
            showError("Please select a course to delete.");
            return;
        }
        
        int id = (int) model.getValueAt(row, 0);
        String name = (String) model.getValueAt(row, 1);
        
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Are you sure you want to delete the course:\n\"" + name + "\"?", 
            "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                CourseDAO dao = new CourseDAO();
                dao.deleteCourse(id);
                
                JOptionPane.showMessageDialog(this, 
                    "Course deleted successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                loadCourses();
            } catch (SQLException e) {
                showError("Error deleting course: " + e.getMessage());
            }
        }
    }
    
    private void returnToDashboard() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Return to Dashboard? Any unsaved changes will be lost.",
            "Confirm Navigation",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            new DashboardFrame().setVisible(true);
        }
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(
            this, 
            message, 
            "Error", 
            JOptionPane.ERROR_MESSAGE
        );
    }
}
