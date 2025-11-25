/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PlacementManagement.models;

/**
 *
 * @author HP
 */
import java.time.LocalDate;


public class Job {

    private int jobId;
    private int companyId;
    private String companyName;
    private String jobRole;
    private String description;
    private String salary;
    private double eligibilityCgpa;
    private String jobLocation;
    private LocalDate lastDate;

    // Getters
    public int getJobId() { return jobId; }
    public int getCompanyId() { return companyId; }
    public String getJobRole() { return jobRole; }
    public String getCompanyName() { return companyName; }
    public String getDescription() { return description; }
    public String getSalary() { return salary; }
    public double getEligibilityCgpa() { return eligibilityCgpa; }
    public String getJobLocation() { return jobLocation; }
    public LocalDate getLastDate() { return lastDate; }

    // Setters
    public void setJobId(int jobId) { this.jobId = jobId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }
    public void setJobRole(String jobRole) { this.jobRole = jobRole; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public void setDescription(String description) { this.description = description; }
    public void setSalary(String salary) { this.salary = salary; }
    public void setEligibilityCgpa(double eligibilityCgpa) { this.eligibilityCgpa = eligibilityCgpa; }
    public void setJobLocation(String jobLocation) { this.jobLocation = jobLocation; }
    public void setLastDate(LocalDate lastDate) { this.lastDate = lastDate; }
}
