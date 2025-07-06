/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.studentmanagement.model;

public class Course {
    private int id;
    private String name;

    public Course() {
        // Default constructor required for ORM frameworks
    }

    public Course(int id, String name) {
        setId(id);
        setName(name);
    }

    // Getters and setters with validation
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        if(id < 0) {
            throw new IllegalArgumentException("Course ID cannot be negative");
        }
        this.id = id; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public void setName(String name) {
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty");
        }
        this.name = name.trim(); 
    }
    
    // Add toString() for better debugging
    @Override
    public String toString() {
        return "Course [id=" + id + ", name=" + name + "]";
    }
}