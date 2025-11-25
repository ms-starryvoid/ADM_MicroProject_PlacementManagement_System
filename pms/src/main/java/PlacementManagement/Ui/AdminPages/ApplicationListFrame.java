package PlacementManagement.Ui.AdminPages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import PlacementManagement.database.ApplicationDAO;
import PlacementManagement.database.CompanyDAO;
import PlacementManagement.database.StudentDAO;
import PlacementManagement.models.Application;
import PlacementManagement.models.Company;

public class ApplicationListFrame extends JFrame {

    private JTextField studentIdField, studentNameField, jobRoleField;
    private JComboBox<String> companyBox, deptBox, statusBox;
    private JTable table;
    private ApplicationDAO applicationDAO = new ApplicationDAO();
    private CompanyDAO companyDAO = new CompanyDAO();
    private StudentDAO studentDAO = new StudentDAO();

    private final String[] STATUS_DISPLAY = {"", "Applied", "Shortlisted", "Selected", "Rejected"};
    // map display -> db (lowercase)
    private String displayToDb(String disp) {
        if (disp == null || disp.isEmpty()) return "";
        return disp.toLowerCase();
    }

    public ApplicationListFrame(JFrame previous) {
        setTitle("Manage Applications");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Top: filters
        JPanel filterPanel = new JPanel(new GridLayout(2, 6, 8, 8));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Search / Filter"));

        studentIdField = new JTextField();
        studentNameField = new JTextField();
        jobRoleField = new JTextField();

        companyBox = new JComboBox<>();
        deptBox = new JComboBox<>();
        statusBox = new JComboBox<>();

        // populate company dropdown (first blank)
        companyBox.addItem("");
        for (Company c : companyDAO.getAllCompanies()) {
            companyBox.addItem(c.getCompanyName());
        }

        // populate dept dropdown from StudentDAO (distinct departments)
        deptBox.addItem("");
        for (String d : studentDAO.getAllDepartments()) {
            deptBox.addItem(d);
        }

        // status dropdown (display friendly)
        statusBox.addItem("");
        for (int i = 1; i < STATUS_DISPLAY.length; i++) statusBox.addItem(STATUS_DISPLAY[i]);

        filterPanel.add(new JLabel("Student ID:"));
        filterPanel.add(studentIdField);

        filterPanel.add(new JLabel("Student Name:"));
        filterPanel.add(studentNameField);

        filterPanel.add(new JLabel("Company:"));
        filterPanel.add(companyBox);

        filterPanel.add(new JLabel("Job Role:"));
        filterPanel.add(jobRoleField);

        filterPanel.add(new JLabel("Department:"));
        filterPanel.add(deptBox);

        filterPanel.add(new JLabel("Status:"));
        filterPanel.add(statusBox);

        JButton searchBtn = new JButton("Search");
        JButton clearBtn  = new JButton("Clear");
        JPanel pBtns = new JPanel();
        pBtns.add(searchBtn);
        pBtns.add(clearBtn);

        add(filterPanel, BorderLayout.NORTH);
        add(pBtns, BorderLayout.EAST);

        // center: table
        table = new JTable();
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // bottom: actions
        JPanel bottom = new JPanel();
        JButton editStatusBtn = new JButton("Update Status");
        JButton viewResumeBtn = new JButton("View Student Resume");
        JButton backBtn = new JButton("Back");

        bottom.add(editStatusBtn);
        bottom.add(viewResumeBtn);
        bottom.add(backBtn);

        add(bottom, BorderLayout.SOUTH);

        // actions
        searchBtn.addActionListener(e -> loadResults());
        clearBtn.addActionListener(e -> {
            studentIdField.setText("");
            studentNameField.setText("");
            jobRoleField.setText("");
            companyBox.setSelectedIndex(0);
            deptBox.setSelectedIndex(0);
            statusBox.setSelectedIndex(0);
            loadResults();
        });

        editStatusBtn.addActionListener(e -> updateSelectedStatus());
        viewResumeBtn.addActionListener(e -> viewSelectedStudentResume());
        backBtn.addActionListener(e -> {
            previous.setVisible(true);
            dispose();
        });

        // load initial results (all)
        loadResults();

        setVisible(true);
    }

    private void loadResults() {
        Integer sid = null;
        String sidText = studentIdField.getText().trim();
        if (!sidText.isEmpty()) {
            try { sid = Integer.parseInt(sidText); } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Invalid Student ID"); return; }
        }
        String sname = studentNameField.getText().trim();
        String jrole = jobRoleField.getText().trim();
        String company = (String) companyBox.getSelectedItem();
        String dept = (String) deptBox.getSelectedItem();
        String statusDisplay = (String) statusBox.getSelectedItem();
        String statusDb = (statusDisplay == null || statusDisplay.isEmpty()) ? "" : displayToDb(statusDisplay);

        List<Application> rows = applicationDAO.searchApplications(
                sid,
                sname,
                company,
                dept,
                jrole,
                statusDb.isEmpty() ? null : statusDb
        );

        String[] cols = {"Application ID", "Student ID", "Student Name", "Department", "Company", "Job Role", "Status", "Apply Date"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Application a : rows) {
            model.addRow(new Object[]{
                    a.getApplicationId(),
                    a.getStudentId(),
                    a.getStudentName(),
                    a.getDepartment(),
                    a.getCompanyName(),
                    a.getJobRole(),
                    capitalize(a.getStatus()),
                    a.getApplyDate() != null ? a.getApplyDate().format(df) : ""
            });
        }

        table.setModel(model);
    }

    // Capitalize first letter for display ("shortlisted" -> "Shortlisted")
    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return "";
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }

    // Update status of selected application using a dialog with dropdown
    private void updateSelectedStatus() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select an application first."); return; }

        int appId = (int) table.getValueAt(row, 0);
        String currentDisplay = (String) table.getValueAt(row, 6);
        String currentDb = currentDisplay == null ? "" : currentDisplay.toLowerCase();

        String[] options = {"applied","shortlisted","selected","rejected"}; // DB enum values
        // create display labels
        String[] display = new String[options.length];
        for (int i=0;i<options.length;i++) display[i] = capitalize(options[i]);

        String chosen = (String) JOptionPane.showInputDialog(this, "Update Status", "Status",
                JOptionPane.QUESTION_MESSAGE, null, display, capitalize(currentDb));

        if (chosen == null) return; // cancelled

        String chosenDb = chosen.toLowerCase();

        boolean ok = applicationDAO.updateApplicationStatus(appId, chosenDb);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Status updated.");
            loadResults();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update status.");
        }
    }

    // View resume of selected student (opens temp PDF)
    private void viewSelectedStudentResume() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select an application first."); return; }

        int studentId = (int) table.getValueAt(row, 1);
        try {
            String base64 = studentDAO.getResume(studentId); // implement in StudentDAO
            if (base64 == null || base64.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No resume uploaded for this student.");
                return;
            }
            byte[] bytes = Base64.getDecoder().decode(base64);
            File temp = File.createTempFile("resume_preview_" + studentId + "_", ".pdf");
            Files.write(temp.toPath(), bytes);
            Desktop.getDesktop().open(temp);
            temp.deleteOnExit();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error opening resume: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
