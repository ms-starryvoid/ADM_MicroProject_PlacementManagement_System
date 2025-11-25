/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PlacementManagement.Ui.AdminPages;

/**
 *
 * @author HP
 */

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Base64;
import java.awt.Desktop;

import PlacementManagement.database.StudentDAO;
import PlacementManagement.models.Student;

public class AdminStudentProfileFrame extends JFrame {

    // If you want to show a small logo at the top, set LOGO_PATH to the local file path.
    // Developer note: file uploaded earlier available at: sandbox:/mnt/data/935a5ce5-3a0c-4da9-af12-9ef863ca897c.png
    private static final String LOGO_PATH = "sandbox:/mnt/data/935a5ce5-3a0c-4da9-af12-9ef863ca897c.png";

    private JFrame previous;
    private int originalStudentId; // remember original id so updates can change the PK if needed

    // Fields and labels
    private JTextField studentIdField; // admin can edit
    private JTextField rollNoField;    // admin can edit
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField cgpaField;
    private JTextField dobField; // format YYYY-MM-DD
    private JComboBox<String> genderBox;
    private JTextField deptField;
    private JComboBox<String> statusBox;
    private JLabel resumeStatusLabel;

    private JButton saveButton;
    private JButton backButton;
    private JButton uploadResumeButton;
    private JButton viewResumeButton;
    private JButton deleteResumeButton;

    private StudentDAO studentDAO = new StudentDAO();
    private Student current;

    public AdminStudentProfileFrame(JFrame previous, int studentId) {
        this.previous = previous;
        this.originalStudentId = studentId;

        setTitle("Admin - Student Profile (Edit)");
        setSize(750, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header (title + optional logo)
        JPanel header = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Student Profile (Admin)", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        header.add(title, BorderLayout.CENTER);

        // optional logo (if file exists)
        try {
            File logoFile = new File("/mnt/data/935a5ce5-3a0c-4da9-af12-9ef863ca897c.png");
            if (logoFile.exists()) {
                ImageIcon icon = new ImageIcon(new ImageIcon(logoFile.getAbsolutePath()).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
                JLabel logo = new JLabel(icon);
                header.add(logo, BorderLayout.WEST);
            }
        } catch (Exception ignored) {}

        add(header, BorderLayout.NORTH);

        // Form panel (labels + inputs)
        JPanel form = new JPanel(new GridLayout(11, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        studentIdField = new JTextField();
        rollNoField = new JTextField();
        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        cgpaField = new JTextField();
        dobField = new JTextField("YYYY-MM-DD");
        genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other", ""});
        deptField = new JTextField();

        // Status dropdown requested: Active, Inactive, Shortlisted, Selected, Rejected, Placed
        statusBox = new JComboBox<>(new String[]{"Active", "Inactive", "Shortlisted", "Selected", "Rejected", "Placed"});

        resumeStatusLabel = new JLabel("No resume");

        form.add(new JLabel("Student ID:"));
        form.add(studentIdField);

        form.add(new JLabel("Roll No:"));
        form.add(rollNoField);

        form.add(new JLabel("Name:"));
        form.add(nameField);

        form.add(new JLabel("Email:"));
        form.add(emailField);

        form.add(new JLabel("Phone:"));
        form.add(phoneField);

        form.add(new JLabel("CGPA:"));
        form.add(cgpaField);

        form.add(new JLabel("DOB (YYYY-MM-DD):"));
        form.add(dobField);

        form.add(new JLabel("Gender:"));
        form.add(genderBox);

        form.add(new JLabel("Department:"));
        form.add(deptField);

        form.add(new JLabel("Status:"));
        form.add(statusBox);

        form.add(new JLabel("Resume:"));
        JPanel resumePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        resumePanel.add(resumeStatusLabel);
        form.add(resumePanel);

        add(form, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        saveButton = new JButton("Save Changes");
        uploadResumeButton = new JButton("Upload Resume (PDF)");
        viewResumeButton = new JButton("View Resume");
        deleteResumeButton = new JButton("Delete Resume");
        backButton = new JButton("Back");

        buttons.add(saveButton);
        buttons.add(viewResumeButton);
        buttons.add(deleteResumeButton);
        buttons.add(backButton);

        add(buttons, BorderLayout.SOUTH);

        // Wire actions
        backButton.addActionListener(e -> {
            previous.setVisible(true);
            dispose();
        });

        saveButton.addActionListener(e -> saveStudent());

        uploadResumeButton.addActionListener(e -> adminUploadResume());

        viewResumeButton.addActionListener(e -> adminViewResume());

        deleteResumeButton.addActionListener(e -> adminDeleteResume());

        // Load data
        loadStudentData();

        setVisible(true);
    }

    private void loadStudentData() {
        current = studentDAO.getStudentById(originalStudentId);
        if (current == null) {
            JOptionPane.showMessageDialog(this, "Student not found (id=" + originalStudentId + ")");
            previous.setVisible(true);
            dispose();
            return;
        }

        // populate fields
        studentIdField.setText(String.valueOf(current.getStudentId()));
        rollNoField.setText(current.getRollNo());
        nameField.setText(current.getName());
        emailField.setText(current.getEmail());
        phoneField.setText(current.getPhone());
        cgpaField.setText(String.valueOf(current.getCgpa()));
        dobField.setText(current.getDob() != null ? current.getDob().toString() : "");
        genderBox.setSelectedItem(current.getGender() == null ? "" : current.getGender());
        deptField.setText(current.getDepartment());
        statusBox.setSelectedItem(current.getStatus() == null ? "Active" : current.getStatus());

        if (current.getResumeUrl() == null || current.getResumeUrl().isEmpty()) {
            resumeStatusLabel.setText("No resume uploaded");
        } else {
            resumeStatusLabel.setText("Resume available");
        }
    }

    private void saveStudent() {
        try {
            // Build Student object from fields
            Student s = new Student();

            // Admin allowed to change student id and roll number (note: if you update primary key,
            // your DAO should perform update carefully - we provide originalStudentId)
            int newId = Integer.parseInt(studentIdField.getText().trim());
            s.setStudentId(newId);

            s.setRollNo(rollNoField.getText().trim());
            s.setName(nameField.getText().trim());
            s.setEmail(emailField.getText().trim());
            s.setPhone(phoneField.getText().trim());
            s.setCgpa(Double.parseDouble(cgpaField.getText().trim()));
            s.setDob(dobField.getText().trim().isEmpty() ? null : LocalDate.parse(dobField.getText().trim()));
            s.setGender((String) genderBox.getSelectedItem());
            s.setDepartment(deptField.getText().trim());
            s.setStatus((String) statusBox.getSelectedItem());

            // If resume field in DB updated via upload method; current.getResumeUrl() holds latest value
            s.setResumeUrl(current.getResumeUrl());

            // Call DAO: updateStudentAdmin(originalId, newStudentObject)
            boolean ok = studentDAO.updateStudent(s);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Student updated successfully.");
                // if id changed, update original id so future operations act on new id
                originalStudentId = s.getStudentId();
                loadStudentData();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed. Check logs.");
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Student ID and CGPA must be numeric.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Admin uploads resume (stores Base64 into current and mark for save)
    private void adminUploadResume() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Resume (PDF)");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF Files", "pdf"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                if (!file.getName().toLowerCase().endsWith(".pdf")) {
                    JOptionPane.showMessageDialog(this, "Please choose a PDF file.");
                    return;
                }

                byte[] bytes = Files.readAllBytes(file.toPath());
                String base64 = Base64.getEncoder().encodeToString(bytes);

                // store temporarily in current object (will be saved when admin clicks Save)
                current.setResumeUrl(base64);
                resumeStatusLabel.setText("Resume loaded (click Save)");

                JOptionPane.showMessageDialog(this, "Resume loaded. Click 'Save Changes' to persist.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error uploading resume: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    // Admin views current resume (temp or DB) by creating a temp PDF and opening system viewer
    private void adminViewResume() {
        try {
            String base64 = current.getResumeUrl();
            if (base64 == null || base64.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No resume uploaded for this student.");
                return;
            }

            byte[] bytes = Base64.getDecoder().decode(base64);
            File temp = File.createTempFile("student_resume_" + originalStudentId + "_", ".pdf");
            Files.write(temp.toPath(), bytes);

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(temp);
            } else {
                JOptionPane.showMessageDialog(this, "Cannot open file on this platform. File saved at: " + temp.getAbsolutePath());
            }

            // optional: delete on exit
            temp.deleteOnExit();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error viewing resume: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Admin deletes resume from DB (immediately)
    private void adminDeleteResume() {
        int confirm = JOptionPane.showConfirmDialog(this, "Delete resume for this student?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        boolean ok = studentDAO.deleteResume(originalStudentId);
        if (ok) {
            current.setResumeUrl(null);
            resumeStatusLabel.setText("No resume uploaded");
            JOptionPane.showMessageDialog(this, "Resume deleted.");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete resume.");
        }
    }
}

