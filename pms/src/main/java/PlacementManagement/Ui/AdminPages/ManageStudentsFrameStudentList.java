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
import java.util.List;
import PlacementManagement.database.StudentDAO;
import PlacementManagement.models.Student;

public class ManageStudentsFrameStudentList extends JFrame {
private JFrame previous;

    
    public ManageStudentsFrameStudentList(JFrame previous) {
        this.previous = previous;
        setTitle("Manage Students");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        StudentDAO dao = new StudentDAO();
        List<Student> list = dao.getAllStudents();

        // TABLE MODEL
        String[] cols = {"ID", "Name", "Register No", "Department", "CGPA", "Contact"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        for (Student s : list) {
            model.addRow(new Object[]{
                    s.getStudentId(),
                    s.getName(),
                    s.getRollNo(),
                    s.getDepartment(),
                    s.getCgpa(),
                    s.getPhone()
            });
        }

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        // BACK BUTTON
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
           previous.setVisible(true);
    dispose();
        });

        JPanel bottom = new JPanel();
        bottom.add(backButton);

        add(scroll);
        add(bottom, "South");

        setVisible(true);
    }
}

