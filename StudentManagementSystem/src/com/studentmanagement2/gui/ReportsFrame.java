/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 package com.studentmanagement2.gui;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.InputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.lang.reflect.Method;

public class ReportsFrame extends JFrame {
    
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> reportTypeComboBox;
    private JButton generateReportButton;
    private JButton exportButton;
    
    // Sample data - replace with database connection
    private List<Student> students = Arrays.asList(
        new Student(1, "John Doe", "john@example.com", "Computer Science"),
        new Student(2, "Jane Smith", "jane@example.com", "Mathematics"),
        new Student(3, "Bob Johnson", "bob@example.com", "Physics"),
        new Student(4, "Alice Brown", "alice@example.com", "Computer Science"),
        new Student(5, "Charlie Wilson", "charlie@example.com", "Mathematics")
    );
    
    public ReportsFrame() {
        initializeFrame();
        createComponents();
        addComponents();
        loadStudentData();
        addEventListeners();
        setVisible(true);
    }
    
    private void initializeFrame() {
        setTitle("Student Management System - Reports");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 245, 249));
    }
    
    private void createComponents() {
        // Table model
        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Name", "Email", "Course"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        // Table with styling
        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        studentTable.getTableHeader().setBackground(new Color(70, 130, 180));
        studentTable.getTableHeader().setForeground(Color.WHITE);
        studentTable.setRowHeight(30);
        studentTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        studentTable.setGridColor(new Color(220, 220, 220));
        
        // Report types
        String[] reportTypes = {
            "Generate student enrollment reports",
            "Course-wise student distribution",
            "Fee collection reports"
        };
        reportTypeComboBox = new JComboBox<>(reportTypes);
        reportTypeComboBox.setFont(new Font("SansSerif", Font.PLAIN, 16));
        reportTypeComboBox.setPreferredSize(new Dimension(350, 35));
        
        // Buttons
        generateReportButton = createStyledButton("Generate Report", new Color(70, 130, 180));
        exportButton = createStyledButton("Export Report", new Color(46, 139, 87));
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 1),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
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
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Student Reports");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Report controls panel
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        controlsPanel.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(15, 15, 15, 15),
            new TitledBorder("Report Options")
        ));
        controlsPanel.setBackground(Color.WHITE);
        
        controlsPanel.add(new JLabel("Select Report Type:"));
        controlsPanel.add(reportTypeComboBox);
        controlsPanel.add(generateReportButton);
        controlsPanel.add(exportButton);
        
        add(controlsPanel, BorderLayout.NORTH);
        
        // Table panel
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(0, 15, 10, 15),
            new TitledBorder("Student Data")
        ));
        scrollPane.setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);
        
        // Footer panel with Back button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        buttonPanel.setBackground(new Color(240, 245, 249));
        
        JButton backButton = new JButton("Back to Dashboard");
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            this.dispose();
            new DashboardFrame().setVisible(true);
        });
    }
    
    private void loadStudentData() {
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Add sample data - replace with database access
        for (Student student : students) {
            tableModel.addRow(new Object[]{
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getCourse()
            });
        }
    }
    
    private void addEventListeners() {
        generateReportButton.addActionListener(e -> generateReport());
        exportButton.addActionListener(e -> exportReport());
    }
    
    private void generateReport() {
        String reportType = (String) reportTypeComboBox.getSelectedItem();
        
        switch (reportType) {
            case "Generate student enrollment reports":
                generateEnrollmentReport();
                break;
            case "Course-wise student distribution":
                generateCourseDistributionReport();
                break;
            case "Fee collection reports":
                generateFeeCollectionReport();
                break;
        }
    }
    
    private void generateEnrollmentReport() {
        // This would come from database in real application
        StringBuilder report = new StringBuilder();
        report.append("STUDENT ENROLLMENT REPORT\n");
        report.append("=========================\n\n");
        report.append(String.format("%-5s %-20s %-30s %-20s\n", "ID", "Name", "Email", "Course"));
        report.append("----------------------------------------------------------------\n");
        
        for (Student student : students) {
            report.append(String.format("%-5d %-20s %-30s %-20s\n", 
                student.getId(), 
                student.getName(), 
                student.getEmail(), 
                student.getCourse()));
        }
        
        report.append("\nTotal Students: ").append(students.size());
        
        showReportDialog("Student Enrollment Report", report.toString());
    }
    
    private void generateCourseDistributionReport() {
        // Group students by course
        Map<String, Integer> courseCount = new HashMap<>();
        for (Student student : students) {
            courseCount.put(student.getCourse(), 
                courseCount.getOrDefault(student.getCourse(), 0) + 1);
        }
        
        StringBuilder report = new StringBuilder();
        report.append("COURSE-WISE STUDENT DISTRIBUTION\n");
        report.append("================================\n\n");
        report.append(String.format("%-20s %-10s\n", "Course", "Students"));
        report.append("----------------------------\n");
        
        for (Map.Entry<String, Integer> entry : courseCount.entrySet()) {
            report.append(String.format("%-20s %-10d\n", entry.getKey(), entry.getValue()));
        }
        
        report.append("\nTotal Courses: ").append(courseCount.size());
        report.append("\nTotal Students: ").append(students.size());
        
        showReportDialog("Course Distribution Report", report.toString());
    }
    
    private void generateFeeCollectionReport() {
        // Simulated fee data
        double totalFees = 0;
        Map<String, Double> courseFees = new HashMap<>();
        courseFees.put("Computer Science", 1500.0);
        courseFees.put("Mathematics", 1200.0);
        courseFees.put("Physics", 1300.0);
        
        StringBuilder report = new StringBuilder();
        report.append("FEE COLLECTION REPORT\n");
        report.append("=====================\n\n");
        report.append(String.format("%-5s %-20s %-20s %-10s\n", "ID", "Name", "Course", "Fee Paid"));
        report.append("----------------------------------------------------\n");
        
        Random rand = new Random();
        for (Student student : students) {
            double feePaid = courseFees.getOrDefault(student.getCourse(), 1000.0);
            feePaid = feePaid * (0.8 + (0.4 * rand.nextDouble())); // Randomize fee payment
            totalFees += feePaid;
            
            report.append(String.format("%-5d %-20s %-20s $%-10.2f\n", 
                student.getId(), 
                student.getName(), 
                student.getCourse(), 
                feePaid));
        }
        
        report.append("\nTotal Fees Collected: $").append(String.format("%.2f", totalFees));
        
        showReportDialog("Fee Collection Report", report.toString());
    }
    
    private void showReportDialog(String title, String content) {
        JTextArea textArea = new JTextArea(content);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(700, 500));
        
        JOptionPane.showMessageDialog(this, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    // ================== JasperReports Integration ==================
    private void exportReport() {
        String reportType = (String) reportTypeComboBox.getSelectedItem();
        String templatePath = getTemplatePath(reportType);
        
        if (templatePath == null) {
            JOptionPane.showMessageDialog(this, 
                "Report template not available", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ask for export format
        String[] options = {"PDF", "Excel"};
        int choice = JOptionPane.showOptionDialog(this,
            "Select Export Format",
            "Export Report",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        if (choice == -1) return; // User canceled
        
        String format = options[choice];
        
        try {
            // Load and compile report template
            InputStream templateStream = getClass().getResourceAsStream(templatePath);
            if (templateStream == null) {
                throw new JRException("Template not found: " + templatePath);
            }
            
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);
            
            // Get data source
            JRDataSource dataSource = getDataSource(reportType);
            
            // Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
            
            // Export to file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Export Report");
            
            // Set default file name
            String defaultName = reportType.replaceAll("\\s+", "_") + "." + format.toLowerCase();
            fileChooser.setSelectedFile(new File(defaultName));
            
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File outputFile = fileChooser.getSelectedFile();
                String filePath = outputFile.getAbsolutePath();
                
                // Add extension if missing
                if (!filePath.toLowerCase().endsWith("." + format.toLowerCase())) {
                    filePath += "." + format.toLowerCase();
                    outputFile = new File(filePath);
                }
                
                // Perform export
                if (format.equals("PDF")) {
                    JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
                } else { // Excel
                    try {
                        // Use reflection to avoid direct class references
                        Class<?> exporterClass = Class.forName("net.sf.jasperreports.engine.export.JRXlsxExporter");
                        Object exporter = exporterClass.getDeclaredConstructor().newInstance();
                        
                        // Set exporter input
                        Class<?> exporterInputClass = Class.forName("net.sf.jasperreports.export.ExporterInput");
                        Method setExporterInput = exporterClass.getMethod("setExporterInput", exporterInputClass);
                        Object simpleExporterInput = Class.forName("net.sf.jasperreports.export.SimpleExporterInput")
                            .getConstructor(JasperPrint.class)
                            .newInstance(jasperPrint);
                        setExporterInput.invoke(exporter, simpleExporterInput);
                        
                        // Set exporter output
                        Class<?> exporterOutputClass = Class.forName("net.sf.jasperreports.export.ExporterOutput");
                        Method setExporterOutput = exporterClass.getMethod("setExporterOutput", exporterOutputClass);
                        Object outputStreamExporterOutput = Class.forName("net.sf.jasperreports.export.SimpleOutputStreamExporterOutput")
                            .getConstructor(File.class)
                            .newInstance(outputFile);
                        setExporterOutput.invoke(exporter, outputStreamExporterOutput);
                        
                        // Set configuration
                        Class<?> reportConfigClass = Class.forName("net.sf.jasperreports.export.ReportExportConfiguration");
                        Method setConfiguration = exporterClass.getMethod("setConfiguration", reportConfigClass);
                        Object config = Class.forName("net.sf.jasperreports.export.SimpleXlsxReportConfiguration")
                            .getDeclaredConstructor()
                            .newInstance();
                        
                        // Configure settings
                        Class<?> configClass = config.getClass();
                        Method setOnePagePerSheet = configClass.getMethod("setOnePagePerSheet", boolean.class);
                        setOnePagePerSheet.invoke(config, true);
                        
                        Method setDetectCellType = configClass.getMethod("setDetectCellType", boolean.class);
                        setDetectCellType.invoke(config, true);
                        
                        Method setCollapseRowSpan = configClass.getMethod("setCollapseRowSpan", boolean.class);
                        setCollapseRowSpan.invoke(config, false);
                        
                        setConfiguration.invoke(exporter, config);
                        
                        // Export report
                        Method exportReport = exporterClass.getMethod("exportReport");
                        exportReport.invoke(exporter);
                    } catch (Exception ex) {
                        throw new JRException("Excel export failed. Check dependencies.", ex);
                    }
                }
                
                JOptionPane.showMessageDialog(this, 
                    "Report exported successfully to:\n" + filePath,
                    "Export Successful", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (JRException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error generating report: " + e.getMessage(), 
                "Export Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error during export: " + e.getMessage(), 
                "Export Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getTemplatePath(String reportType) {
        switch (reportType) {
            case "Generate student enrollment reports":
                return "/reports/student_enrollment.jrxml";
            case "Course-wise student distribution":
                return "/reports/course_distribution.jrxml";
            case "Fee collection reports":
                return "/reports/fee_collection.jrxml";
            default:
                return null;
        }
    }

    private JRDataSource getDataSource(String reportType) {
        switch (reportType) {
            case "Generate student enrollment reports":
                return new JRBeanCollectionDataSource(students);
            case "Course-wise student distribution":
                return new JRBeanCollectionDataSource(generateCourseDistributionData());
            case "Fee collection reports":
                return new JRBeanCollectionDataSource(generateFeeCollectionData());
            default:
                return new JREmptyDataSource();
        }
    }

    private List<CourseCount> generateCourseDistributionData() {
        Map<String, Integer> courseCount = new HashMap<>();
        for (Student student : students) {
            courseCount.put(student.getCourse(), 
                courseCount.getOrDefault(student.getCourse(), 0) + 1);
        }
        
        List<CourseCount> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : courseCount.entrySet()) {
            result.add(new CourseCount(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    private List<FeeRecord> generateFeeCollectionData() {
        List<FeeRecord> feeRecords = new ArrayList<>();
        // This should come from your database - using sample data
        Map<String, Double> courseFees = new HashMap<>();
        courseFees.put("Computer Science", 1500.0);
        courseFees.put("Mathematics", 1200.0);
        courseFees.put("Physics", 1300.0);
        
        Random rand = new Random();
        for (Student student : students) {
            double feePaid = courseFees.getOrDefault(student.getCourse(), 1000.0);
            feePaid = feePaid * (0.8 + (0.4 * rand.nextDouble())); // Randomize fee payment
            
            feeRecords.add(new FeeRecord(
                student.getId(),
                student.getName(),
                student.getCourse(),
                feePaid,
                new Date() // Use current date
            ));
        }
        return feeRecords;
    }
    
    // Helper classes for reports
    static class CourseCount {
        private String course;
        private int count;
        
        public CourseCount(String course, int count) {
            this.course = course;
            this.count = count;
        }
        
        public String getCourse() { return course; }
        public int getCount() { return count; }
    }
    
    static class FeeRecord {
        private int studentId;
        private String studentName;
        private String course;
        private double amount;
        private Date paymentDate;
        
        public FeeRecord(int studentId, String studentName, String course, double amount, Date paymentDate) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.course = course;
            this.amount = amount;
            this.paymentDate = paymentDate;
        }
        
        // Getters
        public int getStudentId() { return studentId; }
        public String getStudentName() { return studentName; }
        public String getCourse() { return course; }
        public double getAmount() { return amount; }
        public Date getPaymentDate() { return paymentDate; }
    }
    // ================== End of JasperReports Integration ==================
    
    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ReportsFrame();
        });
    }
    
    // Inner class for student data model
    static class Student {
        private int id;
        private String name;
        private String email;
        private String course;
        
        public Student(int id, String name, String email, String course) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.course = course;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getCourse() { return course; }
    }
}
