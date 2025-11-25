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
import PlacementManagement.models.Job;


public class JobDAO {

    // GET ALL JOBS
    public List<Job> getAllJobs() {
        List<Job> list = new ArrayList<>();
        String sql = "SELECT jp.job_id, c.company_name,jp.company_id,jp.job_role ,jp.job_location, jp.description,jp.eligibility_cgpa, jp.salary, jp.last_date FROM jobs jp JOIN companies c ON jp.company_id = c.company_id;";

        try {
            Statement st = DBConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                list.add(extractJob(rs));
            }

            rs.close();
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // GET ELIGIBLE JOBS BASED ON CGPA
    public List<Job> getEligibleJobs(double studentCgpa) {
    List<Job> list = new ArrayList<>();

    String sql = "SELECT jp.job_id, jp.company_id, c.company_name, jp.job_role, jp.job_location, " +
                 "jp.description, jp.eligibility_cgpa, jp.salary, jp.last_date " +
                 "FROM jobs jp " +
                 "JOIN companies c ON jp.company_id = c.company_id " +
                 "WHERE jp.eligibility_cgpa <= ?";

    try {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setDouble(1, studentCgpa);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(extractJob(rs));
        }

        rs.close();
        ps.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
    public Job getJobById(int jobId) {
    String sql = "SELECT jp.*, c.company_name FROM jobs jp JOIN companies c ON jp.company_id=c.company_id WHERE jp.job_id=?";
    try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
        ps.setInt(1, jobId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return extractJob(rs);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
public boolean addJob(Job job) {
    String sql = "INSERT INTO jobs (company_id, job_role, description, salary, eligibility_cgpa, job_location, last_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
        ps.setInt(1, job.getCompanyId());
        ps.setString(2, job.getJobRole());
        ps.setString(3, job.getDescription());
        ps.setString(4, job.getSalary());
        ps.setDouble(5, job.getEligibilityCgpa());
        ps.setString(6, job.getJobLocation());
        ps.setDate(7, job.getLastDate() != null ? java.sql.Date.valueOf(job.getLastDate()) : null);

        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
public boolean updateJob(Job job) {
    String sql = "UPDATE jobs SET company_id=?, job_role=?, description=?, salary=?, eligibility_cgpa=?, job_location=?, last_date=? WHERE job_id=?";
    try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {

        ps.setInt(1, job.getCompanyId());
        ps.setString(2, job.getJobRole());
        ps.setString(3, job.getDescription());
        ps.setString(4, job.getSalary());
        ps.setDouble(5, job.getEligibilityCgpa());
        ps.setString(6, job.getJobLocation());
        ps.setDate(7, job.getLastDate() != null ? java.sql.Date.valueOf(job.getLastDate()) : null);

        ps.setInt(8, job.getJobId());

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
public boolean deleteJob(int jobId) {
    String sql = "DELETE FROM jobs WHERE job_id=?";
    try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
        ps.setInt(1, jobId);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
//GET list of eligible jobs for a student
public List<Job> searchEligibleJobs(double studentCgpa, String company, String role, String location) {
    List<Job> list = new ArrayList<>();

    String sql =
        "SELECT j.*, c.company_name " +
        "FROM jobs j " +
        "JOIN companies c ON j.company_id = c.company_id " +
        "WHERE j.eligibility_cgpa <= ? " +
        (company.isEmpty() ? "" : "AND c.company_name LIKE ? ") +
        (role.isEmpty() ? "" : "AND j.job_role LIKE ? ") +
        (location.isEmpty() ? "" : "AND j.job_location LIKE ? ");

    try {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        int index = 1;

        ps.setDouble(index++, studentCgpa);

        if (!company.isEmpty()) ps.setString(index++, "%" + company + "%");
        if (!role.isEmpty()) ps.setString(index++, "%" + role + "%");
        if (!location.isEmpty()) ps.setString(index++, "%" + location + "%");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Job j = new Job();

            j.setJobId(rs.getInt("job_id"));
            j.setCompanyId(rs.getInt("company_id"));
            j.setCompanyName(rs.getString("company_name"));
            j.setJobRole(rs.getString("job_role"));
            j.setDescription(rs.getString("description"));
            j.setJobLocation(rs.getString("job_location"));
            j.setEligibilityCgpa(rs.getDouble("eligibility_cgpa"));
            j.setSalary(rs.getString("salary"));

            if (rs.getDate("last_date") != null)
                j.setLastDate(rs.getDate("last_date").toLocalDate());

            list.add(j);
        }

        rs.close();
        ps.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

public List<Job> searchJobs(String jobId, String company, String role, String location, String cgpa) {
    List<Job> list = new ArrayList<>();

    StringBuilder sql = new StringBuilder(
        "SELECT jp.*, c.company_name " +
        "FROM jobs jp JOIN companies c ON jp.company_id = c.company_id " +
        "WHERE 1=1 "
    );

    List<Object> params = new ArrayList<>();

    // -------------------------
    // Dynamic SQL Conditions
    // -------------------------

    if (jobId != null && !jobId.isEmpty()) {
        sql.append(" AND jp.job_id = ? ");
        params.add(Integer.parseInt(jobId));
    }

    if (company != null && !company.isEmpty()) {
        sql.append(" AND c.company_name LIKE ? ");
        params.add("%" + company + "%");
    }

    if (role != null && !role.isEmpty()) {
        sql.append(" AND jp.job_role LIKE ? ");
        params.add("%" + role + "%");
    }

    if (location != null && !location.isEmpty()) {
        sql.append(" AND jp.job_location LIKE ? ");
        params.add("%" + location + "%");
    }

    if (cgpa != null && !cgpa.isEmpty()) {
        sql.append(" AND jp.eligibility_cgpa <= ? ");
        params.add(Double.parseDouble(cgpa));
    }

    try {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql.toString());

        // Set values
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(extractJob(rs));
        }

        rs.close();
        ps.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}



    private Job extractJob(ResultSet rs) throws Exception {
        Job j = new Job();

        j.setJobId(rs.getInt("job_id"));
        j.setCompanyId(rs.getInt("company_id"));
        j.setCompanyName(rs.getString("company_name"));
        j.setJobRole(rs.getString("job_role"));
        j.setDescription(rs.getString("description"));
        j.setSalary(rs.getString("salary"));
        j.setEligibilityCgpa(rs.getDouble("eligibility_cgpa"));
        j.setJobLocation(rs.getString("job_location"));
        j.setLastDate(rs.getDate("last_date") != null ? rs.getDate("last_date").toLocalDate() : null);

        return j;
    }
}
