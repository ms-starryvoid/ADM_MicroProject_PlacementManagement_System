/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package PlacementManagement.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import PlacementManagement.database.ApplicationDAO;
import PlacementManagement.models.Application;

/**
 *
 * @author HP
 */

import java.util.List;

public class ApplicationController {

    private ApplicationDAO appDAO = new ApplicationDAO();

    // Apply for job
    public boolean applyForJob(int studentId, int jobId) {
        return appDAO.apply(studentId, jobId);
    }

    // Check if applied
    public boolean hasApplied(int studentId, int jobId) {
        return appDAO.hasApplied(studentId, jobId);
    }

    // Get all applications of the student
    public List<Application> getApplicationsForStudent(int studentId) {
        return appDAO.getApplicationsForStudent(studentId);
    }
}
