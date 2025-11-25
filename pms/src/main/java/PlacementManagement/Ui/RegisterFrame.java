/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PlacementManagement.Ui;

/**
 *
 * @author HP
 */

import PlacementManagement.Validations.Validation;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Base64;

import PlacementManagement.database.StudentDAO;
import PlacementManagement.models.Student;

public class RegisterFrame extends JFrame {

    private JTextField rollField, nameField, emailField, phoneField, cgpaField, deptField;
    private JPasswordField passwordField;
    private JComboBox<String> genderBox;
    private JDateChooser dobChooser;
    private JTextField resumePathField;

    private StudentDAO studentDAO = new StudentDAO();

    private String resumeBase64 = "";

    public RegisterFrame() {

        setTitle("Student Registration");
        setSize(600, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Student Registration", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(11, 2, 8, 6));
        form.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        rollField = new JTextField();
        nameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        phoneField = new JTextField();
        cgpaField = new JTextField();
        deptField = new JTextField();

        genderBox = new JComboBox<>(new String[] {"Male", "Female", "Other"});
        genderBox.setFont(new Font("Arial", Font.PLAIN, 14));

        dobChooser = new JDateChooser();
        dobChooser.setDateFormatString("yyyy-MM-dd");
        dobChooser.setFont(new Font("Arial", Font.PLAIN, 14));

        resumePathField = new JTextField();
        resumePathField.setEditable(false);
        resumePathField.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JButton uploadResumeBtn = new JButton("Upload Resume");
        uploadResumeBtn.setFont(new Font("Arial", Font.PLAIN, 13));
        uploadResumeBtn.setBackground(new Color(240, 240, 240));
        uploadResumeBtn.setFocusPainted(false);
        uploadResumeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        uploadResumeBtn.addActionListener(e -> chooseResume());

        // Style text fields
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        rollField.setFont(fieldFont);
        nameField.setFont(fieldFont);
        emailField.setFont(fieldFont);
        passwordField.setFont(fieldFont);
        phoneField.setFont(fieldFont);
        cgpaField.setFont(fieldFont);
        deptField.setFont(fieldFont);

        Font labelFont = new Font("Arial", Font.PLAIN, 14);

        // add all fields
        JLabel rollLabel = new JLabel("Roll Number:");
        rollLabel.setFont(labelFont);
        form.add(rollLabel);
        form.add(rollField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        form.add(nameLabel);
        form.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        form.add(emailLabel);
        form.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        form.add(passwordLabel);
        form.add(passwordField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(labelFont);
        form.add(phoneLabel);
        form.add(phoneField);

        JLabel cgpaLabel = new JLabel("CGPA:");
        cgpaLabel.setFont(labelFont);
        form.add(cgpaLabel);
        form.add(cgpaField);

        JLabel deptLabel = new JLabel("Department:");
        deptLabel.setFont(labelFont);
        form.add(deptLabel);
        form.add(deptField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(labelFont);
        form.add(genderLabel);
        form.add(genderBox);

        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setFont(labelFont);
        form.add(dobLabel);
        form.add(dobChooser);

        JLabel resumeLabel = new JLabel("Resume:");
        resumeLabel.setFont(labelFont);
        form.add(resumeLabel);
        form.add(resumePathField);

        form.add(new JLabel(""));
        form.add(uploadResumeBtn);

        add(form, BorderLayout.CENTER);

        // buttons
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottom.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        
        JButton registerBtn = new JButton("Create Account");
        registerBtn.setFont(new Font("Arial", Font.BOLD, 15));
        registerBtn.setPreferredSize(new Dimension(150, 38));
        registerBtn.setBackground(new Color(70, 130, 180));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton backBtn = new JButton("Back to Login");
        backBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        backBtn.setPreferredSize(new Dimension(140, 38));
        backBtn.setBackground(Color.WHITE);
        backBtn.setForeground(new Color(70, 130, 180));
        backBtn.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        registerBtn.addActionListener(e -> register());
        backBtn.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });

        bottom.add(registerBtn);
        bottom.add(backBtn);

        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void chooseResume() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF files", "pdf"));

        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            resumePathField.setText(file.getName());

            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                resumeBase64 = Base64.getEncoder().encodeToString(bytes);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to load resume: " + ex.getMessage());
            }
        }
    }

    private void register() {
    try {
        String roll = rollField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();
        String phone = phoneField.getText().trim();
        String dept = deptField.getText().trim();
        String cgpaText = cgpaField.getText().trim();

        // ===============================
        // REQUIRED FIELDS CHECK
        // ===============================
        if (roll.isEmpty() || name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all required fields.");
            return;
        }

        // ===============================
        // VALIDATION: EMAIL
        // ===============================
        if (!Validation.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, Validation.onInvalidEmail());
            return;
        }

        // ===============================
        // VALIDATION: PHONE
        // ===============================
        if (!Validation.isValidPhone(phone)) {
            JOptionPane.showMessageDialog(this, 
                    "Invalid phone number!\nPhone must be 10 digits and not start with 0.");
            return;
        }

        // ===============================
        // VALIDATION: CGPA
        // ===============================
        if (!Validation.isValidCgpa(cgpaText)) {
            JOptionPane.showMessageDialog(this, 
                    "Invalid CGPA! Must be a number between 0.0 and 10.0");
            return;
        }
        double cgpa = Double.parseDouble(cgpaText);

        // ===============================
        // VALIDATION: DEPARTMENT
        // ===============================
        if (!Validation.isValidDepartment(dept)) {
            JOptionPane.showMessageDialog(this, 
                    "Invalid Department!\nAllowed: CSE, IT, ECE, EEE, MECH, CIVIL, AI, AIML");
            return;
        }

        // ===============================
        // VALIDATION: DOB (NOT IN FUTURE)
        // ===============================
        LocalDate dob = null;
        if (dobChooser.getDate() != null) {
            dob = new java.sql.Date(dobChooser.getDate().getTime()).toLocalDate();
            if (dob.isAfter(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "Date of Birth cannot be in the future!");
                return;
            }
        }

        // ===============================
        // CHECK DUPLICATES
        // ===============================
        if (studentDAO.rollExists(roll)) {
            JOptionPane.showMessageDialog(this, "Roll Number already registered.");
            return;
        }

        if (studentDAO.emailExists(email)) {
            JOptionPane.showMessageDialog(this, "Email already registered.");
            return;
        }

        // ===============================
        // CREATE OBJECT AND INSERT
        // ===============================
        Student s = new Student();
        s.setRollNo(roll);
        s.setName(name);
        s.setEmail(email);
        s.setPassword(pass);
        s.setPhone(phone);
        s.setDepartment(dept);
        s.setCgpa(cgpa);
        s.setGender((String) genderBox.getSelectedItem());
        s.setDob(dob);
        s.setResumeUrl(resumeBase64);
        s.setStatus("active");

        boolean saved = studentDAO.insertStudent(s);

        if (saved) {
            JOptionPane.showMessageDialog(this, "Registration Successful!");
            new Login().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration Failed.");
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
}

}