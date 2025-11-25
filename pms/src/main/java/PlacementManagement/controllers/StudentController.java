/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package PlacementManagement.controllers;

import PlacementManagement.models.Student;
import PlacementManagement.database.StudentDAO;
import java.net.URL;
import java.util.ResourceBundle;



/**
 *
 * @author HP
 */

public class StudentController {

    private StudentDAO studentDAO = new StudentDAO();

    // Fetch student by ID
    public Student getStudentById(int id) {
        return studentDAO.getStudentById(id);
    }

    // Update profile
    public boolean updateProfile(Student s) {
        return studentDAO.updateStudent(s);
    }

    // Fetch all students (admin use)
    public java.util.List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }
}
