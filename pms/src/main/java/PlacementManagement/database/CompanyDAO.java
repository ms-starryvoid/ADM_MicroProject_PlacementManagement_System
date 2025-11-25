/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PlacementManagement.database;

/**
 *
 * @author HP
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import PlacementManagement.models.Company;

public class CompanyDAO {

    public List<Company> getAllCompanies() {
        List<Company> list = new ArrayList<>();
        String sql = "SELECT * FROM companies";

        try {
            Statement st = DBConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Company c = new Company();
                c.setCompanyId(rs.getInt("company_id"));
                c.setCompanyName(rs.getString("company_name"));
                c.setIndustry(rs.getString("industry"));
                c.setWebsite(rs.getString("website"));
                c.setLocation(rs.getString("location"));
                list.add(c);
            }

            rs.close();
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Company getCompanyById(int id) {
        String sql = "SELECT * FROM companies WHERE company_id = ?";
        Company c = null;

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c = new Company();
                c.setCompanyId(rs.getInt("company_id"));
                c.setCompanyName(rs.getString("company_name"));
                c.setIndustry(rs.getString("industry"));
                c.setWebsite(rs.getString("website"));
                c.setLocation(rs.getString("location"));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;
    }
    public Integer getCompanyIdByName(String name) {
        String sql = "SELECT company_id FROM companies WHERE company_name = ?";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("company_id");
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // not found
    }
}

