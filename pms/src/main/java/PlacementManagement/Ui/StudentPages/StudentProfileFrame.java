package PlacementManagement.Ui.StudentPages;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Base64;

import PlacementManagement.database.StudentDAO;
import PlacementManagement.models.Student;
import com.toedter.calendar.JDateChooser;

public class StudentProfileFrame extends JFrame {

    private JFrame previous;
    private int studentId;

    // view-only labels
    private JLabel rollLabel, nameLabel, emailLabel, phoneLabel, cgpaLabel, dobLabel,
            genderLabel, deptLabel, resumeLabel, statusLabel;

    // Text fields (for edit mode)
    private JTextField nameField, emailField, phoneField, cgpaField, resumeField;
    private JTextField dobField;
    private JDateChooser dobChooser;
    private JComboBox<String> genderField;

    private JButton editButton, saveButton, backButton;
    private JButton uploadResumeButton, downloadResumeButton;

    private StudentDAO studentDAO = new StudentDAO();
    private Student current;

    private boolean editMode = false;

    public StudentProfileFrame(JFrame previous, int studentId) {
        this.previous = previous;
        this.studentId = studentId;

        setTitle("Student Profile");
        setSize(700, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        // Main container with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("My Profile", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel formPanel = new JPanel(new GridLayout(10, 2, 12, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        Font labelFont = new Font("Arial", Font.PLAIN, 15);
        Font valueFont = new Font("Arial", Font.PLAIN, 15);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        // View-only labels
        rollLabel = new JLabel();
        nameLabel = new JLabel();
        emailLabel = new JLabel();
        phoneLabel = new JLabel();
        cgpaLabel = new JLabel();
        dobLabel = new JLabel();
        genderLabel = new JLabel();
        deptLabel = new JLabel();
        resumeLabel = new JLabel();
        statusLabel = new JLabel();

        // Style view labels
        rollLabel.setFont(valueFont);
        nameLabel.setFont(valueFont);
        emailLabel.setFont(valueFont);
        phoneLabel.setFont(valueFont);
        cgpaLabel.setFont(valueFont);
        dobLabel.setFont(valueFont);
        genderLabel.setFont(valueFont);
        deptLabel.setFont(valueFont);
        resumeLabel.setFont(valueFont);
        statusLabel.setFont(valueFont);

        // Editable fields (hidden in view mode)
        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        cgpaField = new JTextField();
        dobField = new JTextField("YYYY-MM-DD");
        resumeField = new JTextField();

        // Style edit fields
        nameField.setFont(fieldFont);
        emailField.setFont(fieldFont);
        phoneField.setFont(fieldFont);
        cgpaField.setFont(fieldFont);
        dobField.setFont(fieldFont);
        resumeField.setFont(fieldFont);
        
        dobChooser = new JDateChooser();
        dobChooser.setDateFormatString("yyyy-MM-dd");
        dobChooser.setFont(fieldFont);
        dobChooser.setVisible(false);
        genderField = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        genderField.setFont(fieldFont);

        // Add rows with styled labels
        addRow(formPanel, "Roll No:", rollLabel, labelFont);
        addRow(formPanel, "Name:", nameLabel, nameField, labelFont);
        addRow(formPanel, "Email:", emailLabel, emailField, labelFont);
        addRow(formPanel, "Phone:", phoneLabel, phoneField, labelFont);
        addRow(formPanel, "CGPA:", cgpaLabel, cgpaField, labelFont);
        addRow(formPanel, "Date of Birth:", dobLabel, dobChooser, labelFont);

        addRow(formPanel, "Gender:", genderLabel, genderField, labelFont);
        addRow(formPanel, "Department:", deptLabel, labelFont);
        addRow(formPanel, "Status:", statusLabel, labelFont);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 15));

        editButton = new JButton("Edit Profile");
        saveButton = new JButton("Save Changes");
        backButton = new JButton("Back");

        uploadResumeButton = new JButton("Upload Resume");
        downloadResumeButton = new JButton("View Resume");

        // Style buttons
        styleButton(editButton, new Color(70, 130, 180), Color.WHITE, 14);
        styleButton(saveButton, new Color(60, 179, 113), Color.WHITE, 14);
        styleButton(backButton, Color.WHITE, new Color(70, 130, 180), 13);
        styleButton(uploadResumeButton, new Color(255, 140, 0), Color.WHITE, 13);
        styleButton(downloadResumeButton, new Color(100, 100, 100), Color.WHITE, 13);

        backButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));

        saveButton.setVisible(false);
        uploadResumeButton.setVisible(false);

        editButton.addActionListener(e -> enterEditMode());
        saveButton.addActionListener(e -> saveChanges());
        backButton.addActionListener(e -> {
            previous.setVisible(true);
            dispose();
        });

        uploadResumeButton.addActionListener(e -> uploadResume());
        downloadResumeButton.addActionListener(e -> viewResume());

        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(uploadResumeButton);
        buttonPanel.add(downloadResumeButton);
        buttonPanel.add(backButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        loadStudentData();
        setVisible(true);
    }

    private void styleButton(JButton button, Color bg, Color fg, int fontSize) {
        button.setFont(new Font("Arial", Font.PLAIN, fontSize));
        button.setPreferredSize(new Dimension(140, 36));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (bg.equals(Color.WHITE)) {
            button.setBorder(BorderFactory.createLineBorder(fg, 2));
        }
    }

    private void addRow(JPanel panel, String label, JComponent view, Font labelFont) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(labelFont);
        panel.add(lbl);
        panel.add(view);
    }

    private void addRow(JPanel panel, String label, JComponent view, JComponent editor, Font labelFont) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(labelFont);
        panel.add(lbl);
        JPanel p = new JPanel(new GridLayout(1, 2, 5, 0));
        p.add(view);
        p.add(editor);
        editor.setVisible(false);
        panel.add(p);
    }

    private void loadStudentData() {
        current = studentDAO.getStudentById(studentId);

        if (current == null) {
            JOptionPane.showMessageDialog(this, "Unable to load student data.");
            return;
        }

        rollLabel.setText(current.getRollNo());
        nameLabel.setText(current.getName());
        emailLabel.setText(current.getEmail());
        phoneLabel.setText(current.getPhone());
        cgpaLabel.setText(String.valueOf(current.getCgpa()));
        dobLabel.setText(current.getDob() != null ? current.getDob().toString() : "");
        genderLabel.setText(current.getGender());
        deptLabel.setText(current.getDepartment());
        resumeLabel.setText(current.getResumeUrl() == null || current.getResumeUrl().isEmpty()
                ? "No Resume Uploaded"
                : "Resume Available");
        statusLabel.setText(current.getStatus());
    }

    private void enterEditMode() {
        editMode = true;

        nameField.setText(current.getName());
        emailField.setText(current.getEmail());
        phoneField.setText(current.getPhone());
        cgpaField.setText(String.valueOf(current.getCgpa()));
        if (current.getDob() != null) {
    dobChooser.setDate(java.sql.Date.valueOf(current.getDob()));
}
        genderField.setSelectedItem(current.getGender());
        resumeField.setText(current.getResumeUrl());

        toggleFields(true);

        saveButton.setVisible(true);
        uploadResumeButton.setVisible(true);
        editButton.setVisible(false);
    }

    private void toggleFields(boolean edit) {
        nameLabel.setVisible(!edit);
        emailLabel.setVisible(!edit);
        phoneLabel.setVisible(!edit);
        cgpaLabel.setVisible(!edit);
        dobLabel.setVisible(!edit);
        genderLabel.setVisible(!edit);
        resumeLabel.setVisible(!edit);

        nameField.setVisible(edit);
        emailField.setVisible(edit);
        phoneField.setVisible(edit);
        cgpaField.setVisible(edit);
        dobLabel.setVisible(!edit);
dobChooser.setVisible(edit);

        genderField.setVisible(edit);
        resumeField.setVisible(edit);
    }

    private void saveChanges() {
    try {
        // =============== BASIC REQUIRED FIELDS CHECK ===============
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String cgpaText = cgpaField.getText().trim();
        

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || cgpaText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty.");
            return;
        }

        // =============== EMAIL VALIDATION ===============
        if (!PlacementManagement.Validations.Validation.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid Email Format!");
            return;
        }

        // =============== PHONE VALIDATION ===============
        if (!PlacementManagement.Validations.Validation.isValidPhone(phone)) {
            JOptionPane.showMessageDialog(this, 
                    "Invalid Phone!\nMust be 10 digits and not start with 0.");
            return;
        }

        // =============== CGPA VALIDATION ===============
        if (!PlacementManagement.Validations.Validation.isValidCgpa(cgpaText)) {
            JOptionPane.showMessageDialog(this, 
                    "Invalid CGPA!\nMust be between 0.0 and 10.0");
            return;
        }
        double cgpa = Double.parseDouble(cgpaText);

        // =============== DATE VALIDATION ===============
        LocalDate dob = new java.sql.Date(dobChooser.getDate().getTime()).toLocalDate();
        try {
            if (dobChooser.getDate() == null) {
    JOptionPane.showMessageDialog(this, "Please select a valid Date of Birth.");
    return;
}


// validate DOB not in future
if (dob.isAfter(LocalDate.now())) {
    JOptionPane.showMessageDialog(this, "Date of Birth cannot be in the future!");
    return;
}

            if (dob.isAfter(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, 
                        "Date of Birth cannot be in the future!");
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                    "Invalid Date Format! Use YYYY-MM-DD");
            return;
        }

        // =============== SAVE STUDENT ===============
        Student s = new Student();
        s.setStudentId(studentId);
        s.setRollNo(current.getRollNo());       // NOT editable
        s.setDepartment(current.getDepartment()); // NOT editable
        s.setStatus(current.getStatus());

        s.setName(name);
        s.setEmail(email);
        s.setPhone(phone);
        s.setCgpa(cgpa);
        s.setGender(genderField.getSelectedItem().toString());
        s.setDob(dob);

        // Resume (base64) is already stored in the field
        s.setResumeUrl(resumeField.getText().trim());

        boolean updated = studentDAO.updateStudent(s);

        if (updated) {
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");

            editMode = false;
            loadStudentData();
            toggleFields(false);

            saveButton.setVisible(false);
            uploadResumeButton.setVisible(false);
            editButton.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this, "Update failed.");
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
}


    private void uploadResume() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Resume (PDF)");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF Files", "pdf"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();

                byte[] bytes = Files.readAllBytes(file.toPath());
                String base64 = Base64.getEncoder().encodeToString(bytes);

                resumeField.setText(base64);

                JOptionPane.showMessageDialog(this, "Resume loaded! Click Save to update.");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error uploading resume: " + ex.getMessage());
            }
        }
    }

    private void downloadResume() {
        try {
            if (current.getResumeUrl() == null || current.getResumeUrl().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No resume available.");
                return;
            }

            byte[] bytes = Base64.getDecoder().decode(current.getResumeUrl());

            File file = new File("student_resume_" + studentId + ".pdf");
            Files.write(file.toPath(), bytes);

            JOptionPane.showMessageDialog(this,
                    "Resume downloaded as:\n" + file.getAbsolutePath());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error downloading: " + ex.getMessage());
        }
    }

    private void viewResume() {
        try {
            if (current.getResumeUrl() == null || current.getResumeUrl().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No resume available.");
                return;
            }

            byte[] bytes = Base64.getDecoder().decode(current.getResumeUrl());

            File temp = File.createTempFile("resume_preview_", ".pdf");
            Files.write(temp.toPath(), bytes);

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(temp);
            } else {
                JOptionPane.showMessageDialog(this, "Cannot open PDF on this system.");
            }

            temp.deleteOnExit();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error viewing resume: " + ex.getMessage());
        }
    }
}