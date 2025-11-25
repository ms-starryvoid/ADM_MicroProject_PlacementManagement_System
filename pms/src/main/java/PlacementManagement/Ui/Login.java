package PlacementManagement.Ui;
import javax.swing.*;
import java.awt.*;
import PlacementManagement.Ui.AdminPages.AdminDashboard;
import PlacementManagement.Ui.StudentPages.StudentDashboard;
import PlacementManagement.Validations.Validation;
import PlacementManagement.controllers.LoginController;
import PlacementManagement.models.Admin;
import PlacementManagement.models.Student;

public class Login extends JFrame {
    private JTextField emailField;
    private JPasswordField passField;
    private LoginController controller;
    
    public Login() {
        controller = new LoginController();
        setTitle("Placement System - Login");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        
        // ----- MAIN CONTAINER -----
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // ----- LOGO & TITLE PANEL -----
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        
        // Logo placeholder
        ImageIcon icon = new ImageIcon("F:\\MTECH_COURSEWork\\ADM\\ADM_Microproject\\pms\\src\\main\\resources\\images\\placementlogo.png");
        Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(resizedIcon, SwingConstants.CENTER);
        logoLabel.setFont(new Font("Arial", Font.PLAIN, 64));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Title
        JLabel title = new JLabel("Placement Management", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        
        // Subtitle
        JLabel subtitle = new JLabel("Login", SwingConstants.CENTER);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(new Color(100, 100, 100));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(logoLabel);
        headerPanel.add(title);
        headerPanel.add(subtitle);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // ----- CENTER FORM PANEL -----
        JPanel formContainer = new JPanel();
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Email field
        JLabel emailLbl = new JLabel("Email:");
        emailLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setPreferredSize(new Dimension(350, 40));
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Password field
        JLabel passLbl = new JLabel("Password:");
        passLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        passLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        passLbl.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        passField = new JPasswordField();
        passField.setFont(new Font("Arial", Font.PLAIN, 14));
        passField.setPreferredSize(new Dimension(350, 40));
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        formContainer.add(emailLbl);
        formContainer.add(Box.createRigidArea(new Dimension(0, 8)));
        formContainer.add(emailField);
        formContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        formContainer.add(passLbl);
        formContainer.add(Box.createRigidArea(new Dimension(0, 8)));
        formContainer.add(passField);
        
        mainPanel.add(formContainer, BorderLayout.CENTER);
        
        // ----- BOTTOM PANEL -----
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Arial", Font.BOLD, 16));
        loginBtn.setPreferredSize(new Dimension(350, 45));
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setBackground(new Color(70, 130, 180));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.addActionListener(e -> handleLogin());
        
        JLabel regText = new JLabel("Don't have an account?", SwingConstants.CENTER);
        regText.setFont(new Font("Arial", Font.PLAIN, 13));
        regText.setAlignmentX(Component.CENTER_ALIGNMENT);
        regText.setBorder(BorderFactory.createEmptyBorder(20, 0, 8, 0));
        
        JButton registerBtn = new JButton("Register");
        registerBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        registerBtn.setPreferredSize(new Dimension(350, 40));
        registerBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.setBackground(Color.WHITE);
        registerBtn.setForeground(new Color(70, 130, 180));
        registerBtn.setFocusPainted(false);
        registerBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.addActionListener(e -> {
            new RegisterFrame().setVisible(true);
            dispose();
        });
        
        bottomPanel.add(loginBtn);
        bottomPanel.add(regText);
        bottomPanel.add(registerBtn);
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private void handleLogin() {
    String email = emailField.getText().trim();
    String pass  = new String(passField.getPassword()).trim();

    // ----- EMPTY FIELD CHECK -----
    if (email.isEmpty() || pass.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in both fields.");
        return;
    }

    // ----- EMAIL FORMAT CHECK -----
    if (!Validation.isValidEmail(email)) {
        JOptionPane.showMessageDialog(this, Validation.onInvalidEmail());
        return;
    }

    // ----- AUTH CHECK -----
    Object result = controller.login(email, pass);

    if (result == null) {
        JOptionPane.showMessageDialog(this, "Invalid login credentials.");
        return;
    }

    if (result instanceof Admin) {
        new AdminDashboard().setVisible(true);
    }
    else if (result instanceof Student) {
        Student s = (Student) result;
        new StudentDashboard(this, s.getStudentId()).setVisible(true);
    }

    dispose();
}

}