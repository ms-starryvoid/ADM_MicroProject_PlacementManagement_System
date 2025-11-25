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

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import PlacementManagement.database.JobDAO;
import PlacementManagement.models.Job;

public class ManageJobsFrame extends JFrame {

    private JTextField idField, companyField, roleField, locationField, cgpaField;
    private JTable table;
    private JFrame previous;
    private JobDAO dao = new JobDAO();

    public ManageJobsFrame(JFrame previous) {
        this.previous = previous;

        setTitle("Manage Job Postings");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===============================
        // SEARCH PANEL
        // ===============================
        JPanel searchPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Jobs"));

        idField = new JTextField();
        companyField = new JTextField();
        roleField = new JTextField();
        locationField = new JTextField();
        cgpaField = new JTextField();

        searchPanel.add(new JLabel("Job ID:"));
        searchPanel.add(idField);

        searchPanel.add(new JLabel("Company:"));
        searchPanel.add(companyField);

        searchPanel.add(new JLabel("Role:"));
        searchPanel.add(roleField);

        searchPanel.add(new JLabel("Location:"));
        searchPanel.add(locationField);

        searchPanel.add(new JLabel("CGPA <= "));
        searchPanel.add(cgpaField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> loadJobs());
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // ===============================
        // TABLE
        // ===============================
        table = new JTable();
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // ===============================
        // BUTTON PANEL
        // ===============================
        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Add Job");
        JButton editButton = new JButton("Edit Job");
        JButton deleteButton = new JButton("Delete Job");
        JButton backButton = new JButton("Back");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        addButton.addActionListener(e -> {
            new AddEditJobFrame(this, null).setVisible(true);
            setVisible(false);
        });

        editButton.addActionListener(e -> editSelected());
        deleteButton.addActionListener(e -> deleteSelected());

        backButton.addActionListener(e -> {
            previous.setVisible(true);
            dispose();
        });

        // Load all jobs initially
        loadJobs();

        setVisible(true);
    }

    // ===============================
    // LOAD JOBS WITH FILTERS
    // ===============================
    private void loadJobs() {
        String id = idField.getText().trim();
        String company = companyField.getText().trim();
        String role = roleField.getText().trim();
        String location = locationField.getText().trim();
        String cgpa = cgpaField.getText().trim();

        List<Job> jobs = dao.searchJobs(id, company, role, location, cgpa);

        String[] cols = {"Job ID", "Company", "Role", "CGPA", "Salary", "Location", "Last Date"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        for (Job j : jobs) {
            model.addRow(new Object[]{
                    j.getJobId(),
                    j.getCompanyName(),
                    j.getJobRole(),
                    j.getEligibilityCgpa(),
                    j.getSalary(),
                    j.getJobLocation(),
                    j.getLastDate()
            });
        }

        table.setModel(model);
    }

    // ===============================
    // EDIT SELECTED
    // ===============================
    private void editSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a job to edit.");
            return;
        }

        int jobId = (int) table.getValueAt(row, 0);
        new AddEditJobFrame(this, jobId).setVisible(true);
        setVisible(false);
    }

    // ===============================
    // DELETE SELECTED
    // ===============================
    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a job to delete.");
            return;
        }

        int jobId = (int) table.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Delete this job?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        boolean success = dao.deleteJob(jobId);

        if (success) {
            JOptionPane.showMessageDialog(this, "Job deleted.");
            loadJobs();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete job.");
        }
    }
}
