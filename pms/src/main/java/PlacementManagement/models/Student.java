/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PlacementManagement.models;

import java.time.LocalDate;

/**
 *
 * @author HP
 */
public class Student {

    private int studentId;
    private String rollNo;
    private String name;
    private String email;
    private String password;
    private LocalDate dob;
    private String gender;
    private String department;
    private double cgpa;
    private String phone;
    private String resumeUrl;
    private String status; // unplaced / placed

    public Student() {}

    // ======== Getters ========
    public int getStudentId() { return studentId; }
    public String getRollNo() { return rollNo; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public LocalDate getDob() { return dob; }
    public String getGender() { return gender; }
    public String getDepartment() { return department; }
    public double getCgpa() { return cgpa; }
    public String getPhone() { return phone; }
    public String getResumeUrl() { return resumeUrl; }
    public String getStatus() { return status; }

    // ======== Setters ========
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public void setGender(String gender) { this.gender = gender; }
    public void setDepartment(String department) { this.department = department; }
    public void setCgpa(double cgpa) { this.cgpa = cgpa; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }
    public void setStatus(String status) { this.status = status; }
}