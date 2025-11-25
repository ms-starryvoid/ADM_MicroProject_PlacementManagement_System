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

public class Application {

    private int applicationId;
    private int studentId;
    private int jobId;
    private LocalDate applyDate;
    private String status; // applied / shortlisted / rejected / selected

    // Additional fields from JOIN
    private String jobRole;
    private String companyName;
    private String jobLocation;
    private String salary;
    private LocalDate lastDate;
    // inside Application.java
private String studentName;
private String department;

    // ======================
    // Getters
    // ======================
    public int getApplicationId() { return applicationId; }
    public int getStudentId() { return studentId; }
    public int getJobId() { return jobId; }
    public LocalDate getApplyDate() { return applyDate; }
    public String getStatus() { return status; }
    public String getStudentName() { return studentName; }
    public String getDepartment() { return department; }
    public String getJobRole() { return jobRole; }
    public String getCompanyName() { return companyName; }
    public String getJobLocation() { return jobLocation; }
    public String getSalary() { return salary; }
    public LocalDate getLastDate() { return lastDate; }

    // ======================
    // Setters
    // ======================
    public void setApplicationId(int applicationId) { this.applicationId = applicationId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setJobId(int jobId) { this.jobId = jobId; }
    public void setApplyDate(LocalDate applyDate) { this.applyDate = applyDate; }
    public void setStatus(String status) { this.status = status; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public void setDepartment(String department) { this.department = department; }
    public void setJobRole(String jobRole) { this.jobRole = jobRole; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public void setJobLocation(String jobLocation) { this.jobLocation = jobLocation; }
    public void setSalary(String salary) { this.salary = salary; }
    public void setLastDate(LocalDate lastDate) { this.lastDate = lastDate; }
}
