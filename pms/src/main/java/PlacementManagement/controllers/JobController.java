/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package PlacementManagement.controllers;

import PlacementManagement.models.Job;
import PlacementManagement.database.JobDAO;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 *
 * @author HP
 */
import java.util.List;

public class JobController {

    private JobDAO jobDAO = new JobDAO();

    // Get all jobs
    public List<Job> getAllJobs() {
        return jobDAO.getAllJobs();
    }

    // Get eligible jobs for student
    public List<Job> getEligibleJobs(double studentCgpa) {
        return jobDAO.getEligibleJobs(studentCgpa);
    }

    // Add more admin job operations later
}
