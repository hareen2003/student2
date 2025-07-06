/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.studentmanagement.dao;

import com.studentmanagement.config.DatabaseConfig;
import com.studentmanagement.model.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    
    public void createTable() throws SQLException {
        // ✅ Improved: Added ENGINE specification and CHARSET
        String sql = "CREATE TABLE IF NOT EXISTS courses (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void addCourse(Course course) throws SQLException {
        // ✅ Fixed: Added validation for existing course
        if (courseExists(course.getName())) {
            throw new SQLException("Course '" + course.getName() + "' already exists");
        }
        
        String sql = "INSERT INTO courses (name) VALUES (?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, course.getName());
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating course failed, no rows affected.");
            }
            
            // Retrieve generated ID
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    course.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating course failed, no id obtained.");
                }
            }
        }
    }

    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses ORDER BY name ASC";  // ✅ Added sorting
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Course course = new Course(
                    rs.getInt("id"),
                    rs.getString("name")
                );
                courses.add(course);
            }
        }
        return courses;
    }

    public Course getCourseById(int id) throws SQLException {
        String sql = "SELECT * FROM courses WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                }
            }
        }
        return null;
    }

    public List<Course> searchCoursesByName(String name) throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses WHERE name LIKE ? ORDER BY name ASC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    courses.add(new Course(
                        rs.getInt("id"),
                        rs.getString("name")
                    ));
                }
            }
        }
        return courses;
    }

    public void updateCourse(Course course) throws SQLException {
        // ✅ Fixed: Added validation for existing course
        Course existing = getCourseById(course.getId());
        if (existing == null) {
            throw new SQLException("Course not found with id: " + course.getId());
        }
        
        // Check if new name conflicts with other courses
        if (!existing.getName().equals(course.getName()) && 
            courseExists(course.getName())) {
            throw new SQLException("Course '" + course.getName() + "' already exists");
        }
        
        String sql = "UPDATE courses SET name = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getName());
            pstmt.setInt(2, course.getId());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating course failed, no rows affected.");
            }
        }
    }

    public void deleteCourse(int id) throws SQLException {
        // ✅ Fixed: Check if course exists before deletion
        if (getCourseById(id) == null) {
            throw new SQLException("Course not found with id: " + id);
        }
        
        String sql = "DELETE FROM courses WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting course failed, no rows affected.");
            }
        }
    }

    public boolean courseExists(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM courses WHERE name = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
}
