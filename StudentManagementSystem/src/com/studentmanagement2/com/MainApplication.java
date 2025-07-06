/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.studentmanagement2.com;

// Import statements
import com.studentmanagement2.gui.LoginFrame;
import com.studentmanagement.dao.StudentDAO;
import com.studentmanagement.dao.CourseDAO;

public class MainApplication {
    public static void main(String[] args) {
        // First, ensure database tables exist
        try {
            System.out.println("Creating database tables...");
            
            StudentDAO studentDAO = new StudentDAO();
            studentDAO.createTable();
            System.out.println("Students table created successfully");

            CourseDAO courseDAO = new CourseDAO();
            courseDAO.createTable();
            System.out.println("Courses table created successfully");
            
        } catch (Exception e) {
            System.err.println("Error creating tables: " + e.getMessage());
            e.printStackTrace();
            return; // Stop if tables can't be created
        }

        // Now launch the GUI
        System.out.println("Launching GUI...");
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("Creating LoginFrame...");
                LoginFrame loginFrame = new LoginFrame();
                
                // ✅ CRITICAL: Make the frame visible
                loginFrame.setVisible(true);
                
                System.out.println("LoginFrame is now visible");
            } catch (Exception e) {
                System.err.println("Error creating GUI: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
