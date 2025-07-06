/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.studentmanagement2.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DashboardFrame extends JFrame {
    private JButton studentRegBtn;
    private JButton studentMgmtBtn;
    private JButton courseMgmtBtn;
    private JButton reportsBtn;
    private JButton logoutBtn;

    public DashboardFrame() {
        initializeFrame();
        createComponents();
        addComponents();
        addEventListeners();
        setVisible(true);
    }
    
    private void initializeFrame() {
        setTitle("Student Management System - Dashboard");
        setSize(800, 600); // Larger size for better layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
    }
    
    private void createComponents() {
        // Create styled buttons
        studentRegBtn = createDashboardButton("Register Student", new Color(70, 130, 180));
        studentMgmtBtn = createDashboardButton("Manage Students", new Color(46, 139, 87));
        courseMgmtBtn = createDashboardButton("Manage Courses", new Color(128, 0, 128));
        reportsBtn = createDashboardButton("Generate Reports", new Color(218, 165, 32));
        logoutBtn = createDashboardButton("Logout", new Color(220, 20, 60));
    }
    
    private JButton createDashboardButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
            BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));
        button.setPreferredSize(new Dimension(250, 70));
        return button;
    }
    
    private void addComponents() {
        // Main panel with BorderLayout
        setLayout(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JLabel titleLabel = new JLabel("Student Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Dashboard buttons panel
        JPanel dashboardPanel = new JPanel(new GridBagLayout());
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        dashboardPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Row 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        dashboardPanel.add(studentRegBtn, gbc);
        
        gbc.gridx = 1;
        dashboardPanel.add(studentMgmtBtn, gbc);
        
        // Row 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        dashboardPanel.add(courseMgmtBtn, gbc);
        
        gbc.gridx = 1;
        dashboardPanel.add(reportsBtn, gbc);
        
        add(dashboardPanel, BorderLayout.CENTER);
        
        // Footer panel with logout button
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.add(logoutBtn);
        
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private void addEventListeners() {
        studentRegBtn.addActionListener(this::openStudentRegistration);
        studentMgmtBtn.addActionListener(this::openStudentManagement);
        courseMgmtBtn.addActionListener(this::openCourseManagement);
        reportsBtn.addActionListener(this::openReports);
        logoutBtn.addActionListener(this::logout);
    }
    
    private void openStudentRegistration(ActionEvent e) {
        this.dispose();
        new StudentRegistrationFrame().setVisible(true);
    }
    
    private void openStudentManagement(ActionEvent e) {
        this.dispose();
        new StudentManagementFrame().setVisible(true);
    }
    
    private void openCourseManagement(ActionEvent e) {
        this.dispose();
        new CourseManagementFrame().setVisible(true);
    }
    
    private void openReports(ActionEvent e) {
        this.dispose();
        new ReportsFrame().setVisible(true);
    }
    
    private void logout(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            new LoginFrame().setVisible(true);
        }
    }
}