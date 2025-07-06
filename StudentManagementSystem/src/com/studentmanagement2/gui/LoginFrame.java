/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.studentmanagement2.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    
    public LoginFrame() {
        initializeFrame();
        createComponents();
        addComponents();
        addEventListeners();
        setupFocusManagement();
    }
    
    private void initializeFrame() {
        setTitle("Student Management System - Login");
        setSize(800, 500); // Adjusted frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void createComponents() {
        // Create larger text fields
        usernameField = new JTextField(40);
        passwordField = new JPasswordField(40);
        
        // Set larger dimensions to match screenshot
        usernameField.setPreferredSize(new Dimension(600, 35)); // Wider fields
        passwordField.setPreferredSize(new Dimension(600, 35)); // Wider fields
        
        // Buttons
        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");
        loginButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.setPreferredSize(new Dimension(120, 40));
    }
    
    private void setupFocusManagement() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                usernameField.requestFocusInWindow();
            }
        });
    }
    
    private void addComponents() {
        setLayout(new BorderLayout());
        add(createTitlePanel(), BorderLayout.NORTH);
        add(createLoginPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JLabel titleLabel = new JLabel("Student Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        titlePanel.add(titleLabel);
        return titlePanel;
    }
    
    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 20, 50));
        loginPanel.setBackground(Color.WHITE);
        
        // Username panel
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        usernamePanel.setBackground(Color.WHITE);
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        
        // Password panel
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        passwordPanel.setBackground(Color.WHITE);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 18));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        
        loginPanel.add(usernamePanel);
        loginPanel.add(Box.createVerticalStrut(15));
        loginPanel.add(passwordPanel);
        
        return loginPanel;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        // Button styling
        styleButton(loginButton, new Color(70, 130, 180), Color.WHITE);
        styleButton(cancelButton, new Color(220, 220, 220), Color.BLACK);
        
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        buttonPanel.add(cancelButton);
        
        return buttonPanel;
    }
    
    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 1),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
    }
    
    private void addEventListeners() {
        loginButton.addActionListener(e -> handleLogin());
        cancelButton.addActionListener(e -> handleCancel());
        passwordField.addActionListener(e -> handleLogin());
        
        // Improved tab navigation
        usernameField.addActionListener(e -> passwordField.requestFocusInWindow());
    }
    
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter both username and password!", 
                "Login Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Authentication logic
        if ("admin".equals(username) && "admin".equals(password)) {
            JOptionPane.showMessageDialog(this, 
                "Login successful! Welcome " + username, 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();  // Close the login window
            
            // Open dashboard in Event Dispatch Thread
            SwingUtilities.invokeLater(() -> {
                try {
                    // Create and show dashboard
                    DashboardFrame dashboard = new DashboardFrame();
                    dashboard.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, 
                        "Error opening dashboard: " + e.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, 
                "Invalid username or password!", 
                "Login Failed", 
                JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            usernameField.requestFocus();
        }
    }
    
    private void handleCancel() {
        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this,
            "Are you sure you want to exit?",
            "Confirm Exit",
            JOptionPane.YES_NO_OPTION)) {
            System.exit(0);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}