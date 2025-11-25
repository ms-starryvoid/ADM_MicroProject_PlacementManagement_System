/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PlacementManagement.controllers;

/**
 *
 * @author HP
 */

import PlacementManagement.database.*;
import PlacementManagement.models.*;



public class LoginController {

    private AdminDAO adminDAO = new AdminDAO();
    private StudentDAO studentDAO = new StudentDAO();

    public Object login(String email, String password) {

        // Check admin
        Admin admin = adminDAO.login(email, password);
        if (admin != null) {
            return admin; // Admin logged in
        }

//        // Check student
        Student student = studentDAO.login(email, password);
      if (student != null) {
          return student; // Student logged in
       }

        // Nothing found
        return null;
    }
}
