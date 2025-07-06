/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// File: DashboardButton.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DashboardButton extends JButton {
    public DashboardButton(JFrame currentFrame, JFrame dashboardFrame) {
        super("Return to Dashboard");
        setBackground(new Color(0, 120, 215)); // Blue background
        setForeground(Color.WHITE);
        setFont(new Font("SansSerif", Font.BOLD, 14));
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Add return-to-dashboard functionality
        addActionListener((ActionEvent e) -> {
            currentFrame.dispose();        // Close current child frame
            dashboardFrame.setVisible(true); // Show dashboard
        });
    }
}