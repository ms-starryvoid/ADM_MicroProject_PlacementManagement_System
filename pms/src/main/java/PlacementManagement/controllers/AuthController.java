/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package PlacementManagement.controllers;

import PlacementManagement.models.Admin;
import PlacementManagement.models.Student;
import PlacementManagement.database.AdminDAO;
import PlacementManagement.database.StudentDAO;
import java.net.URL;
import java.util.ResourceBundle;


/**
 *
 * @author HP
 */



public class AuthController {

    private AdminDAO adminDAO = new AdminDAO();
    private StudentDAO studentDAO = new StudentDAO();

    // ADMIN LOGIN
    public Admin loginAdmin(String email, String password) {
        return adminDAO.login(email, password);
    }

    // STUDENT LOGIN
    public Student loginStudent(String email, String password) {
        return studentDAO.login(email, password);
    }
}

//public class LoginContoller implements Initializable {
//    
//    @FXML
//    private Label label;
//    
//    @FXML
//    private void handleButtonAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        label.setText("Hello World!");
//    }
//    
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // TODO
//    }    
//    
//}
