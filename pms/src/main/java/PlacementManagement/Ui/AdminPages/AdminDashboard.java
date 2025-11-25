package PlacementManagement.Ui.AdminPages;

import PlacementManagement.Ui.Login;
import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {

        setTitle("Admin Dashboard");
        setSize(420, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        // ===== HEADER =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(240, 240, 245));
        JLabel headerLabel = new JLabel("Admin Dashboard");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(headerLabel);

        // ===== CENTER BUTTONS =====
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 12, 12));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));

        JButton btnManageStudents = createMenuButton("Manage Students");
        JButton btnManageJobs = createMenuButton("Manage Job Postings");
        JButton btnViewApplications = createMenuButton("View Applications");
        JButton btnReports = createMenuButton("Reports");

        btnManageStudents.addActionListener(e -> {
            new ManageStudentsFrame(this).setVisible(true);
            this.setVisible(false);
        });

        btnManageJobs.addActionListener(e -> {
            new ManageJobsFrame(this).setVisible(true);
            this.setVisible(false);
        });

        btnViewApplications.addActionListener(e -> {
            new ApplicationListFrame(this).setVisible(true);
            this.setVisible(false);
        });

        btnReports.addActionListener(e -> {
            new ReportsFrame(this).setVisible(true);
            this.setVisible(false);
        });

        buttonPanel.add(btnManageStudents);
        buttonPanel.add(btnManageJobs);
        buttonPanel.add(btnViewApplications);
        buttonPanel.add(btnReports);

        // ===== FOOTER =====
        JPanel footerPanel = new JPanel();
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        JButton backButton = new JButton("Back to Login");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        backButton.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });

        footerPanel.add(backButton);

        // ===== ATTACH PANELS =====
        add(headerPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER); 
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ===== Reusable button styling =====
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
