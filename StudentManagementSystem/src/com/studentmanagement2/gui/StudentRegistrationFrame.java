/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.studentmanagement2.gui;

import com.studentmanagement.dao.StudentDAO;
import com.studentmanagement.dao.CourseDAO;
import com.studentmanagement.model.student;
import com.studentmanagement.model.Course;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class StudentRegistrationFrame extends JFrame {
    private JTextField nameField, emailField;
    private JComboBox<String> courseCombo;
    private JButton backButton;

    public StudentRegistrationFrame() {
        setTitle("Register Student");
        setSize(500, 400);  // Increased size
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create header with title
        JLabel headerLabel = new JLabel("Register New Student", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Form panel with improved layout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Name field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        nameField = new JTextField(25);
        formPanel.add(nameField, gbc);
        
        // Email field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        emailField = new JTextField(25);
        formPanel.add(emailField, gbc);
        
        // Course selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Course:"), gbc);
        gbc.gridx = 1;
        courseCombo = new JComboBox<>();
        courseCombo.setPreferredSize(new Dimension(200, 30));
        loadCourses();
        formPanel.add(courseCombo, gbc);
        
        // Add button to refresh courses
        gbc.gridx = 2;
        JButton refreshBtn = new JButton("⟳");
        refreshBtn.setToolTipText("Refresh courses");
        refreshBtn.addActionListener(e -> refreshCourses());
        formPanel.add(refreshBtn, gbc);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Button panel at bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        
        // Back to Dashboard button
        backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> {
            dispose();  // Close this window
             new DashboardFrame ().setVisible(true);        });
        buttonPanel.add(backButton);
        
        // Register button with improved styling
        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(50, 150, 200));  // Blue color
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 12));
        registerBtn.setPreferredSize(new Dimension(120, 35));
        registerBtn.addActionListener(e -> registerStudent());
        buttonPanel.add(registerBtn);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
    }

    private void refreshCourses() {
        courseCombo.removeAllItems();
        loadCourses();
    }

    private void loadCourses() {
        try {
            CourseDAO courseDAO = new CourseDAO();
            List<Course> courses = courseDAO.getAllCourses();
            for (Course c : courses) {
                courseCombo.addItem(c.getName());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading courses: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registerStudent() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String course = (String) courseCombo.getSelectedItem();
        
        // Validation
        if (name.isEmpty() || email.isEmpty() || course == null) {
            JOptionPane.showMessageDialog(this, "All fields are required.", 
                                         "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.", 
                                         "Invalid Email", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            StudentDAO studentDAO = new StudentDAO();
            studentDAO.addStudent(new student(0, name, email, course));
            JOptionPane.showMessageDialog(this, "Student registered successfully!");
            clearForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error registering student: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean isValidEmail(String email) {
        // Simple email validation regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
    
    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        courseCombo.setSelectedIndex(0);
        nameField.requestFocus();
    }
}