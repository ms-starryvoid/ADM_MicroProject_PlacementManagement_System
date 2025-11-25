package PlacementManagement.Ui.AdminPages;

import javax.swing.*;
import java.awt.*;

public class ManageStudentsFrame extends JFrame {

    private JFrame previous;

    public ManageStudentsFrame(JFrame previous) {
        this.previous = previous;

        setTitle("Manage Students");
        setSize(420, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        // ===== HEADER =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(240, 240, 245));

        JLabel title = new JLabel("Manage Students");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerPanel.add(title);

        add(headerPanel, BorderLayout.NORTH);

        // ===== CENTER BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 12, 12));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));

        JButton viewAllBtn = createMenuButton("View All Students");
        JButton searchBtn = createMenuButton("View / Search Student");
        JButton backBtn = createMenuButton("Back");

        viewAllBtn.addActionListener(e -> {
            new ViewAllStudentsFrame(this).setVisible(true);
            setVisible(false);
        });

        searchBtn.addActionListener(e -> {
            new SearchStudentFrame(this).setVisible(true);
            setVisible(false);
        });

        backBtn.addActionListener(e -> {
            previous.setVisible(true);
            dispose();
        });

        buttonPanel.add(viewAllBtn);
        buttonPanel.add(searchBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // ===== Reusable Button Design =====
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(250, 40));
        btn.setBackground(new Color(225, 225, 240));
        btn.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 180)));
        btn.setOpaque(true);
        return btn;
    }
}
