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
import java.sql.SQLException;
import java.util.List;

public class StudentEditFrame extends JFrame {
    private JTextField nameField, emailField;
    private JComboBox<String> courseCombo;
    private int studentId;
    private StudentManagementFrame parent;

    public StudentEditFrame(int studentId, StudentManagementFrame parent) {
        this.studentId = studentId;
        this.parent = parent;
        setTitle("Edit Student");
        setSize(350, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Course:"));
        courseCombo = new JComboBox<>();
        loadCourses();
        panel.add(courseCombo);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> saveStudent());
        panel.add(new JLabel());
        panel.add(saveBtn);

        add(panel);
        loadStudent();
        setVisible(true);
    }

    private void loadCourses() {
        try {
            CourseDAO courseDAO = new CourseDAO();
            List<Course> courses = courseDAO.getAllCourses();
            for (Course c : courses) {
                courseCombo.addItem(c.getName());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading courses.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadStudent() {
        try {
            StudentDAO dao = new StudentDAO();
            student s = dao.getStudentById(studentId);
            if (s != null) {
                nameField.setText(s.getName());
                emailField.setText(s.getEmail());
                courseCombo.setSelectedItem(s.getCourse());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading student.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveStudent() {
        String name = nameField.getText();
        String email = emailField.getText();
        String course = (String) courseCombo.getSelectedItem();
        if (name.isEmpty() || email.isEmpty() || course == null) {
            JOptionPane.showMessageDialog(this, "All fields required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            StudentDAO dao = new StudentDAO();
            dao.updateStudent(new student(studentId, name, email, course));
            JOptionPane.showMessageDialog(this, "Student updated!");
            parent.refresh();
            dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating student.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
