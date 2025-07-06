/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// File: ChildFrame.java
import javax.swing.*;
import java.awt.*;

public abstract class ChildFrame extends JFrame {
    protected final JFrame dashboardFrame;
    
    public ChildFrame(JFrame dashboardFrame, String title) {
        super(title);
        this.dashboardFrame = dashboardFrame;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }
    
    protected void addDashboardButton() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.add(new DashboardButton(this, dashboardFrame));
        add(buttonPanel, BorderLayout.SOUTH);
    }
}