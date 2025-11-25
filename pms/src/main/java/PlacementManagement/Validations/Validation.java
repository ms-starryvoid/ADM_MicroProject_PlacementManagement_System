/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PlacementManagement.Validations;

import java.util.Set;

/**
 *
 * @author HP
 */
public class Validation {
    
    public static boolean isValidEmail(String email)
    {
        String valid="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(valid);        
    }
    
    public static String onInvalidEmail()
    {
        return "Invalid email format!";
    }
    public static boolean isValidPhone(String phone) {
    return phone.matches("^[1-9][0-9]{9}$");
}
public static boolean isValidCgpa(String cgpaText) {
    try {
        double cgpa = Double.parseDouble(cgpaText);
        return cgpa >= 0.0 && cgpa <= 10.0;
    } catch (Exception e) {
        return false;
    }
}
private static final Set<String> validDepartments = Set.of(
    "CSE", "IT", "ECE", "EEE", "MECH", "CIVIL", "AI", "AIML"
);

public static boolean isValidDepartment(String dept) {
    return validDepartments.contains(dept.toUpperCase());
}

    
}
