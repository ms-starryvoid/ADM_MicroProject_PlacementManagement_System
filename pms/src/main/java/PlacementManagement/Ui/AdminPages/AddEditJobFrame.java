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
import java.time.LocalDate;
import java.util.List;
import com.toedter.calendar.JDateChooser;

import PlacementManagement.database.JobDAO;
import PlacementManagement.database.CompanyDAO;
import PlacementManagement.models.Job;
import PlacementManagement.models.Company;

public class AddEditJobFrame extends JFrame {

    private JDateChooser lastDateChooser;
    private JFrame previous;
    private Integer jobId;  // null for ADD mode

    private JComboBox<String> companyBox;
    private JTextField roleField, salaryField, cgpaField, locationField;
    private JTextArea descriptionArea;

    private JobDAO jobDAO = new JobDAO();
    private CompanyDAO companyDAO = new CompanyDAO();

    public AddEditJobFrame(JFrame previous, Integer jobId) {
        this.previous = previous;
        this.jobId = jobId;

        setTitle(jobId == null ? "Add New Job" : "Edit Job");
        setSize(600, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        // ================================
        // FORM PANEL
        // ================================
        JPanel form = new JPanel(new GridLayout(8, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        companyBox = new JComboBox<>();
        loadCompanies();

        roleField = new JTextField();
        salaryField = new JTextField();
        cgpaField = new JTextField();
        locationField = new JTextField();

        descriptionArea = new JTextArea(4, 20);
        JScrollPane descScroll = new JScrollPane(descriptionArea);

        form.add(new JLabel("Company:"));
        form.add(companyBox);

        form.add(new JLabel("Job Role:"));
        form.add(roleField);

        form.add(new JLabel("Job Location:"));
        form.add(locationField);

        form.add(new JLabel("Salary:"));
        form.add(salaryField);

        form.add(new JLabel("Eligibility CGPA:"));
        form.add(cgpaField);

        form.add(new JLabel("Last Date:"));
//        form.add(lastDateField);
        lastDateChooser = new JDateChooser();
        lastDateChooser.setDateFormatString("yyyy-MM-dd");
        form.add(lastDateChooser);

        form.add(new JLabel("Description:"));
        form.add(descScroll);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton(jobId == null ? "Add Job" : "Save Changes");
        JButton backButton = new JButton("Back");

        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        add(form, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Fill fields if edit mode
        if (jobId != null) {
            loadJobDetails(jobId);
        }

        backButton.addActionListener(e -> {
            previous.setVisible(true);
            dispose();
        });

        saveButton.addActionListener(e -> saveJob());

        setVisible(true);
    }

    // ================================
    // LOAD COMPANY LIST FOR DROPDOWN
    // ================================
    private void loadCompanies() {
        List<Company> companies = companyDAO.getAllCompanies();
        for (Company c : companies) {
            companyBox.addItem(c.getCompanyName());
        }
    }

    // ================================
    // LOAD JOB DETAILS FOR EDIT MODE
    // ================================
    private void loadJobDetails(int jobId) {
        Job job = jobDAO.getJobById(jobId);

        if (job == null) {
            JOptionPane.showMessageDialog(this, "Job not found.");
            previous.setVisible(true);
            dispose();
            return;
        }

        // Set company name
        companyBox.setSelectedItem(job.getCompanyName());
        roleField.setText(job.getJobRole());
        salaryField.setText(job.getSalary());
        cgpaField.setText(String.valueOf(job.getEligibilityCgpa()));
        locationField.setText(job.getJobLocation());
        descriptionArea.setText(job.getDescription());

        if (job.getLastDate() != null) {
            lastDateChooser.setDate(java.sql.Date.valueOf(job.getLastDate()));
        }

    }

    // ================================
    // SAVE JOB (ADD or UPDATE)
    // ================================
    private void saveJob() {
        try {
            String companyName = companyBox.getSelectedItem().toString();
            int companyId = companyDAO.getCompanyIdByName(companyName);

            Job job = new Job();

            job.setCompanyId(companyId);
            job.setJobRole(roleField.getText().trim());
            job.setJobLocation(locationField.getText().trim());
            job.setSalary(salaryField.getText().trim());
            // ========================
            // CGPA VALIDATION
            // ========================
            String cgpaText = cgpaField.getText().trim();

            if (!PlacementManagement.Validations.Validation.isValidCgpa(cgpaText)) {
                JOptionPane.showMessageDialog(this,
                        "Invalid CGPA!\nMust be a number between 0.0 and 10.0");
                return;
            }

            double cgpa = Double.parseDouble(cgpaText);
            job.setEligibilityCgpa(cgpa);

            job.setDescription(descriptionArea.getText().trim());

            if (lastDateChooser.getDate() != null) {
                java.util.Date utilDate = lastDateChooser.getDate();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                job.setLastDate(sqlDate.toLocalDate());
            }

            boolean success;

            if (jobId == null) {
                success = jobDAO.addJob(job);
            } else {
                job.setJobId(jobId);
                success = jobDAO.updateJob(job);
            }

            if (success) {
                JOptionPane.showMessageDialog(this, "Job saved successfully.");
                previous.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error saving job.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }
}
