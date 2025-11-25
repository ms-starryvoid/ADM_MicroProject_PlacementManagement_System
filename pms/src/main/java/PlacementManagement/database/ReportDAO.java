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
import java.util.*;

public class ReportDAO {

    // Total students
    public int getTotalStudents() {
        try (Statement st = DBConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM students")) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // Placed students
    public int getPlacedStudents() {
        try (Statement st = DBConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM students WHERE status='Placed'")) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // Applications by status
    public int getApplicationsByStatus(String status) {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(
                "SELECT COUNT(*) FROM applications WHERE status=?"
        )) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // Department-wise placement count
    public List<String[]> getDepartmentStats() {
        List<String[]> list = new ArrayList<>();

        String sql =
            "SELECT department, "
            + "COUNT(*) AS total, "
            + "SUM(CASE WHEN status='Placed' THEN 1 ELSE 0 END) AS placed "
            + "FROM students "
            + "GROUP BY department";

        try (Statement st = DBConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String dept = rs.getString("department");
                int total = rs.getInt("total");
                int placed = rs.getInt("placed");
                double percent = total == 0 ? 0 : (placed * 100.0 / total);

                list.add(new String[]{
                        dept,
                        String.valueOf(total),
                        String.valueOf(placed),
                        String.format("%.2f%%", percent)
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
