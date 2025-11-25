package PlacementManagement.Ui.StudentPages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import PlacementManagement.database.ApplicationDAO;
import PlacementManagement.models.Application;

public class PlacementStatusFrame extends JFrame {

    private JFrame previous;
    private int studentId;

    private JTextField companyField, roleField;
    private JComboBox<String> statusBox;
    private DefaultTableModel model;

    public PlacementStatusFrame(JFrame previous, int studentId) {
        this.previous = previous;
        this.studentId = studentId;

        setTitle("Placement Application Status");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // =============================
        // SEARCH PANEL (TOP)
        // =============================
        JPanel searchPanel = new JPanel(new GridLayout(1, 4, 6, 6));

        companyField = new JTextField();
        roleField = new JTextField();
        statusBox = new JComboBox<>(new String[]{"", "applied", "shortlisted", "rejected", "selected"});

        JButton searchBtn = new JButton("Search");

        searchBtn.addActionListener(e -> loadFilteredData());

        searchPanel.add(new JLabel("Company:"));
        searchPanel.add(companyField);

        searchPanel.add(new JLabel("Role:"));
        searchPanel.add(roleField);
        
        searchPanel.add(new JLabel("Status"));
        searchPanel.add(statusBox);
        searchPanel.add(searchBtn);

        add(searchPanel, BorderLayout.NORTH);

        // =============================
        // TABLE MODEL
        // =============================
        String[] cols = {
                "Application ID", "Company", "Role", "Location",
                "Salary", "Status", "Applied On", "Last Date"
        };

        model = new DefaultTableModel(cols, 0);

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        add(scroll, BorderLayout.CENTER);

        // load table initially
        loadFilteredData();

        // =============================
        // BACK BUTTON
        // =============================
        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> {
            previous.setVisible(true);
            dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadFilteredData() {
        String company = companyField.getText().trim();
        String role = roleField.getText().trim();
        String status = statusBox.getSelectedItem().toString().trim();

        ApplicationDAO appDAO = new ApplicationDAO();
        List<Application> apps = appDAO.searchStudentApplications(studentId, company, role, status);

        model.setRowCount(0);

        for (Application a : apps) {
            model.addRow(new Object[]{
                    a.getApplicationId(),
                    a.getCompanyName(),
                    a.getJobRole(),
                    a.getJobLocation(),
                    a.getSalary(),
                    a.getStatus(),
                    a.getApplyDate(),
                    a.getLastDate()
            });
        }
    }
}
