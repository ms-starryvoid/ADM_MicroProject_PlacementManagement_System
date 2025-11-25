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

public class ViewAllStudentsFrame extends JFrame {

    private JFrame previous;
    private JTable table;

    public ViewAllStudentsFrame(JFrame previous) {
        this.previous = previous;

        setTitle("All Students");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        StudentDAO dao = new StudentDAO();
        List<Student> list = dao.getAllStudents();

        String[] cols = {"ID", "Roll No", "Name", "Email", "Phone", "CGPA", "Dept", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        for (Student s : list) {
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

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        JButton viewBtn = new JButton("View Profile");
        JButton editBtn = new JButton("Edit Student");
        JButton backBtn = new JButton("Back");

        viewBtn.addActionListener(e -> openSelected(false));
        editBtn.addActionListener(e -> openSelected(true));
        backBtn.addActionListener(e -> {
            previous.setVisible(true);
            dispose();
        });

        JPanel bottom = new JPanel();
        bottom.add(viewBtn);
        bottom.add(editBtn);
        bottom.add(backBtn);

        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void openSelected(boolean edit) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student.");
            return;
        }

        int studentId = (int) table.getValueAt(row, 0);

        new AdminStudentProfileFrame(this, studentId).setVisible(true);
        setVisible(false);
    }
}

