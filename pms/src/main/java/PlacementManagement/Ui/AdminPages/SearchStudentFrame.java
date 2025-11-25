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

import PlacementManagement.database.StudentDAO;
import PlacementManagement.models.Student;

public class SearchStudentFrame extends JFrame {

    private JFrame previous;
    private boolean editMode; // if true → open editor; else → view only

    private JTextField idField, nameField, deptField;
    private JComboBox<String> statusBox;
    private JTable resultTable;
    private JButton editButton;

    private StudentDAO dao = new StudentDAO();

    public SearchStudentFrame(JFrame previous) {
        this(previous, false);
    }

    public SearchStudentFrame(JFrame previous, boolean editMode) {
        this.previous = previous;
        this.editMode = editMode;

        setTitle("Search Student");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ============================
        // SEARCH PANEL
        // ============================
        JPanel searchPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Filters"));

        idField = new JTextField();
        nameField = new JTextField();
        deptField = new JTextField();
        statusBox = new JComboBox<>(new String[]{
                "", "Active", "Inactive", "Shortlisted", "Selected", "Rejected", "Placed"
        });

        searchPanel.add(new JLabel("Student ID:"));
        searchPanel.add(idField);

        searchPanel.add(new JLabel("Name:"));
        searchPanel.add(nameField);

        searchPanel.add(new JLabel("Department:"));
        searchPanel.add(deptField);

        searchPanel.add(new JLabel("Status:"));
        searchPanel.add(statusBox);

        // ============================
        // BUTTONS PANEL
        // ============================
        JPanel buttonPanel = new JPanel();
        JButton searchButton = new JButton("Search");
        JButton backButton = new JButton("Back");
        editButton = new JButton(editMode ? "Edit Selected" : "View Profile");
        editButton.setEnabled(false);

        buttonPanel.add(searchButton);
        buttonPanel.add(editButton);
        buttonPanel.add(backButton);

        // ============================
        // RESULT TABLE
        // ============================
        resultTable = new JTable();
        JScrollPane scroll = new JScrollPane(resultTable);

        // ============================
        // ADD TO FRAME
        // ============================
        add(searchPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // ============================
        // ACTIONS
        // ============================

        // SEARCH ACTION
        searchButton.addActionListener(e -> performSearch());

        // EDIT / VIEW ACTION
        editButton.addActionListener(e -> openSelected());

        // BACK ACTION
        backButton.addActionListener(e -> {
            previous.setVisible(true);
            dispose();
        });

        // TABLE SELECT LISTENER
        resultTable.getSelectionModel().addListSelectionListener(ev -> {
            if (!ev.getValueIsAdjusting()) {
                editButton.setEnabled(resultTable.getSelectedRow() != -1);
            }
        });

        setVisible(true);
    }

    // ============================
    // PERFORM SEARCH
    // ============================
    private void performSearch() {
        String idText = idField.getText().trim();
        String nameText = nameField.getText().trim();
        String deptText = deptField.getText().trim();
        String statusText = statusBox.getSelectedItem().toString();

        Integer id = null;
        if (!idText.isEmpty()) {
            try {
                id = Integer.parseInt(idText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid ID format.");
                return;
            }
        }

        // Query DB using filters
        List<Student> results = dao.searchStudents(id, nameText, deptText, statusText);

        String[] cols = {"Student ID", "Roll No", "Name", "Email", "Phone", "CGPA", "Department", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        for (Student s : results) {
            model.addRow(new Object[]{
                    s.getStudentId(),
                    s.getRollNo(),
                    s.getName(),
                    s.getEmail(),
                    s.getPhone(),
                    s.getCgpa(),
                    s.getDepartment(),
                    s.getStatus()
            });
        }

        resultTable.setModel(model);

        editButton.setEnabled(results.size() > 0);
    }

    // ============================
    // OPEN SELECTED STUDENT
    // ============================
    private void openSelected() {
        int row = resultTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a student first.");
            return;
        }

        int studentId = (int) resultTable.getValueAt(row, 0);

        new AdminStudentProfileFrame(this, studentId).setVisible(true);
        setVisible(false);
    }
}
