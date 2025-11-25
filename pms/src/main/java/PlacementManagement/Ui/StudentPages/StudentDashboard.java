package PlacementManagement.Ui.StudentPages;

import javax.swing.*;
import java.awt.*;
import PlacementManagement.database.*;
import PlacementManagement.models.*;

public class StudentDashboard extends JFrame {

    private JFrame previous;
    private int studentId;
    private Student student;

    public StudentDashboard(JFrame previous, int studentId) {
        this.previous = previous;
        this.studentId = studentId;

        setTitle("Student Dashboard");
        setSize(420, 330);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        // ============================
        // LOAD STUDENT DATA
        // ============================
        StudentDAO dao = new StudentDAO();
        student = dao.getStudentById(studentId);

        // ============================
        // HEADER
        // ============================
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(240, 240, 245));

        JLabel welcomeLabel = new JLabel("Welcome, " + student.getName());
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerPanel.add(welcomeLabel);

        add(headerPanel, BorderLayout.NORTH);

        // ============================
        // CENTER – MAIN BUTTONS
        // ============================
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 12, 12));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));

        JButton btnEligibleJobs = createMenuButton("View Eligible Jobs");
        JButton btnPlacementStatus = createMenuButton("View Placement Status");
        JButton btnProfile = createMenuButton("My Profile");

        // Open student profile
        btnProfile.addActionListener(e -> {
            new StudentProfileFrame(this, studentId).setVisible(true);
            setVisible(false);
        });

        // Open eligible jobs list
        btnEligibleJobs.addActionListener(e -> {
            new EligibleJobsFrame(this, studentId, student.getCgpa()).setVisible(true);
            setVisible(false);
        });

        // Open placement status
        btnPlacementStatus.addActionListener(e -> {
            new PlacementStatusFrame(this, studentId).setVisible(true);
            setVisible(false);
        });

        centerPanel.add(btnEligibleJobs);
        centerPanel.add(btnPlacementStatus);
        centerPanel.add(btnProfile);

        add(centerPanel, BorderLayout.CENTER);

        // ============================
        // FOOTER – BACK BUTTON
        // ============================
        JPanel footerPanel = new JPanel();
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        JButton backButton = new JButton("Back to Login");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        backButton.addActionListener(e -> {
            previous.setVisible(true);
            dispose();
        });

        footerPanel.add(backButton);

        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ===== Shared Button Style =====
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(225, 225, 240));
        btn.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 180)));
        btn.setPreferredSize(new Dimension(250, 40));
        btn.setOpaque(true);
        return btn;
    }
}
