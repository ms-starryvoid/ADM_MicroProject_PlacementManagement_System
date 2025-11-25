package PlacementManagement.Ui.StudentPages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import PlacementManagement.database.JobDAO;
import PlacementManagement.database.ApplicationDAO;
import PlacementManagement.models.Job;

public class EligibleJobsFrame extends JFrame {

    private JFrame previous;
    private int studentId;
    private double studentCgpa;

    private JTextField companyField, roleField, locationField;
    private DefaultTableModel model;

    public EligibleJobsFrame(JFrame previous, int studentId, double studentCgpa) {
        this.previous = previous;
        this.studentId = studentId;
        this.studentCgpa = studentCgpa;

        setTitle("Eligible Jobs");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ============================
        // SEARCH PANEL (TOP)
        // ============================
        JPanel searchPanel = new JPanel(new GridLayout(1, 7, 6, 6));

        searchPanel.add(new JLabel("Company:"));
        companyField = new JTextField();
        searchPanel.add(companyField);

        searchPanel.add(new JLabel("Role:"));
        roleField = new JTextField();
        searchPanel.add(roleField);

        searchPanel.add(new JLabel("Location:"));
        locationField = new JTextField();
        searchPanel.add(locationField);

        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> loadFilteredJobs());
        searchPanel.add(searchBtn);

        add(searchPanel, BorderLayout.NORTH);

        // ============================
        // TABLE MODEL
        // ============================
        String[] cols = {
                "Job ID", "Company", "Role", "Location", "CGPA Required",
                "Salary", "Last Date"
        };

        model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        add(scroll, BorderLayout.CENTER);

        // Load initial data
        loadFilteredJobs();

        // ============================
        // APPLY BUTTON
        // ============================
        JButton applyBtn = new JButton("Apply");

        applyBtn.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a job to apply!");
                return;
            }

            int jobId = (int) table.getValueAt(row, 0);

            ApplicationDAO appDAO = new ApplicationDAO();
            boolean success = appDAO.apply(studentId, jobId);

            if (success) {
                JOptionPane.showMessageDialog(this, "Application submitted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "You already applied for this job");
            }
        });

        // ============================
        // BACK BUTTON
        // ============================
        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> {
            previous.setVisible(true);
            dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(applyBtn);
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // =====================================
    // LOAD FILTERED JOBS
    // =====================================
    private void loadFilteredJobs() {
        String company = companyField.getText().trim();
        String role = roleField.getText().trim();
        String location = locationField.getText().trim();

        JobDAO jobDAO = new JobDAO();
        List<Job> jobs = jobDAO.searchEligibleJobs(studentCgpa, company, role, location);

        model.setRowCount(0);

        for (Job j : jobs) {
            model.addRow(new Object[]{
                    j.getJobId(),
                    j.getCompanyName(),
                    j.getJobRole(),
                    j.getJobLocation(),
                    j.getEligibilityCgpa(),
                    j.getSalary(),
                    j.getLastDate()
            });
        }
    }
}
