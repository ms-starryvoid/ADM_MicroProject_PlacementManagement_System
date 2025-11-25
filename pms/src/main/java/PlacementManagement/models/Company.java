/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PlacementManagement.models;

/**
 *
 * @author HP
 */

public class Company {

    private int companyId;
    private String companyName;
    private String industry;
    private String website;
    private String location;

    // Getters
    public int getCompanyId() { return companyId; }
    public String getCompanyName() { return companyName; }
    public String getIndustry() { return industry; }
    public String getWebsite() { return website; }
    public String getLocation() { return location; }

    // Setters
    public void setCompanyId(int companyId) { this.companyId = companyId; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public void setIndustry(String industry) { this.industry = industry; }
    public void setWebsite(String website) { this.website = website; }
    public void setLocation(String location) { this.location = location; }
}

