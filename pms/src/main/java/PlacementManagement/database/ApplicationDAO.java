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
import PlacementManagement.models.Application;
import PlacementManagement.models.Job;

public class ApplicationDAO {

    // APPLY FOR JOB
    public boolean apply(int studentId, int jobId) {
        // prevent duplicate applications
        if (hasApplied(studentId, jobId)) {
            return false;
        }

        String sql = "INSERT INTO applications (student_id, job_id) VALUES (?, ?)";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.setInt(2, jobId);
            ps.executeUpdate();
            ps.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // CHECK IF STUDENT HAS ALREADY APPLIED
    public boolean hasApplied(int studentId, int jobId) {
        String sql = "SELECT * FROM applications WHERE student_id = ? AND job_id = ?";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.setInt(2, jobId);
            ResultSet rs = ps.executeQuery();

            boolean exists = rs.next();

            rs.close();
            ps.close();

            return exists;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // GET ALL APPLICATIONS FOR A STUDENT
    public List<Application> getApplicationsForStudent(int studentId) {
    List<Application> list = new ArrayList<>();

    String sql = 
        "SELECT a.application_id, a.student_id, a.job_id, a.status, a.apply_date, " +
        "j.job_role, j.job_location, j.salary, j.eligibility_cgpa, j.last_date, " +
        "c.company_name " +
        "FROM applications a " +
        "JOIN jobs j ON a.job_id = j.job_id " +
        "JOIN companies c ON j.company_id = c.company_id " +
        "WHERE a.student_id = ?";

    try {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, studentId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Application app = new Application();

            app.setApplicationId(rs.getInt("application_id"));
            app.setStudentId(rs.getInt("student_id"));
            app.setJobId(rs.getInt("job_id"));
            app.setStatus(rs.getString("status"));

            Date date = rs.getDate("apply_date");
            if (date != null) app.setApplyDate(date.toLocalDate());

            // Additional job/company fields
            app.setJobRole(rs.getString("job_role"));
            app.setCompanyName(rs.getString("company_name"));
            app.setSalary(rs.getString("salary"));
            app.setJobLocation(rs.getString("job_location"));
            app.setLastDate(rs.getDate("last_date") != null 
                                ? rs.getDate("last_date").toLocalDate() 
                                : null);

            list.add(app);
        }

        rs.close();
        ps.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

    
    public List<Application> getAllApplications() {
    List<Application> list = new ArrayList<>();
    String sql = "SELECT * FROM applications";

    try {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Application a = new Application();
            a.setApplicationId(rs.getInt("application_id"));
            a.setStudentId(rs.getInt("student_id"));
            a.setJobId(rs.getInt("job_id"));
            a.setStatus(rs.getString("status"));

            // Handle apply_date safely
            Date date = rs.getDate("apply_date");
            if (date != null) {
                a.setApplyDate(date.toLocalDate());
            }

            list.add(a);
        }

        rs.close();
        ps.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
    public List<Application> searchApplications(Integer studentIdFilter,
                                                String studentNameFilter,
                                                String companyNameFilter,
                                                String departmentFilter,
                                                String jobRoleFilter,
                                                String statusFilter) {
        List<Application> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT a.application_id, a.student_id, a.job_id, a.status, a.apply_date, " +
            "s.name AS student_name, s.department AS student_dept, " +
            "j.job_role, j.job_location, j.salary, j.last_date, " +
            "c.company_name " +
            "FROM applications a " +
            "JOIN students s ON a.student_id = s.student_id " +
            "JOIN jobs j ON a.job_id = j.job_id " +
            "JOIN companies c ON j.company_id = c.company_id " +
            "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (studentIdFilter != null) {
            sql.append(" AND a.student_id = ? ");
            params.add(studentIdFilter);
        }
        if (studentNameFilter != null && !studentNameFilter.isEmpty()) {
            sql.append(" AND s.name LIKE ? ");
            params.add("%" + studentNameFilter + "%");
        }
        if (companyNameFilter != null && !companyNameFilter.isEmpty()) {
            sql.append(" AND c.company_name LIKE ? ");
            params.add("%" + companyNameFilter + "%");
        }
        if (departmentFilter != null && !departmentFilter.isEmpty()) {
            sql.append(" AND s.department LIKE ? ");
            params.add("%" + departmentFilter + "%");
        }
        if (jobRoleFilter != null && !jobRoleFilter.isEmpty()) {
            sql.append(" AND j.job_role LIKE ? ");
            params.add("%" + jobRoleFilter + "%");
        }
        if (statusFilter != null && !statusFilter.isEmpty()) {
            // statusFilter should be DB enum value (lowercase). Caller should convert.
            sql.append(" AND a.status = ? ");
            params.add(statusFilter);
        }

        sql.append(" ORDER BY a.apply_date DESC, a.application_id DESC ");

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Application a = new Application();
                a.setApplicationId(rs.getInt("application_id"));
                a.setStudentId(rs.getInt("student_id"));
                a.setJobId(rs.getInt("job_id"));
                a.setStatus(rs.getString("status")); // will be lowercase
                Date d = rs.getDate("apply_date");
                if (d != null) a.setApplyDate(d.toLocalDate());

                // job/company fields
                a.setJobRole(rs.getString("job_role"));
                a.setCompanyName(rs.getString("company_name"));
                a.setJobLocation(rs.getString("job_location"));
                a.setSalary(rs.getString("salary"));
                Date ld = rs.getDate("last_date");
                if (ld != null) a.setLastDate(ld.toLocalDate());

                // student info
                a.setStudentName(rs.getString("student_name"));
                a.setDepartment(rs.getString("student_dept"));

                list.add(a);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Update application status.
     * status must match DB enum (applied, shortlisted, rejected, selected).
     */
//    public boolean updateApplicationStatus(int applicationId, String status) {
//        String sql = "UPDATE applications SET status = ? WHERE application_id = ?";
//        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
//            ps.setString(1, status);
//            ps.setInt(2, applicationId);
//            return ps.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
    public boolean updateApplicationStatus(int applicationId, String status) {
    String updateAppSql = "UPDATE applications SET status = ? WHERE application_id = ?";
    String getStudentSql = "SELECT student_id FROM applications WHERE application_id = ?";
    String updateStudentSql = "UPDATE students SET status = 'Placed' WHERE student_id = ?";

    try {
        Connection conn = DBConnection.getConnection();

        // 1. Update application status
        PreparedStatement ps1 = conn.prepareStatement(updateAppSql);
        ps1.setString(1, status);
        ps1.setInt(2, applicationId);
        boolean updated = ps1.executeUpdate() > 0;
        ps1.close();

        if (!updated) return false;

        // If not selected, no need to update student
        if (!status.equalsIgnoreCase("selected")) {
            return true;
        }

        // 2. Fetch student_id for this application
        PreparedStatement ps2 = conn.prepareStatement(getStudentSql);
        ps2.setInt(1, applicationId);
        ResultSet rs = ps2.executeQuery();

        int studentId = -1;
        if (rs.next()) {
            studentId = rs.getInt("student_id");
        }
        rs.close();
        ps2.close();

        if (studentId == -1) return true;

        // 3. Update student status
        PreparedStatement ps3 = conn.prepareStatement(updateStudentSql);
        ps3.setInt(1, studentId);
        ps3.executeUpdate();
        ps3.close();

        return true;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

    //search the particular student application
public List<Application> searchStudentApplications(int studentId, String company, String role, String status) {
    List<Application> list = new ArrayList<>();

    String sql = 
        "SELECT a.*, j.job_role, j.job_location, j.salary, j.last_date, c.company_name " +
        "FROM applications a " +
        "JOIN jobs j ON a.job_id = j.job_id " +
        "JOIN companies c ON j.company_id = c.company_id " +
        "WHERE a.student_id = ? " +
        (company.isEmpty() ? "" : "AND c.company_name LIKE ? ") +
        (role.isEmpty() ? "" : "AND j.job_role LIKE ? ") +
        (status.isEmpty() ? "" : "AND a.status = ? ");

    try {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        int index = 1;
        ps.setInt(index++, studentId);

        if (!company.isEmpty()) ps.setString(index++, "%" + company + "%");
        if (!role.isEmpty()) ps.setString(index++, "%" + role + "%");
        if (!status.isEmpty()) ps.setString(index++, status);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Application a = new Application();

            a.setApplicationId(rs.getInt("application_id"));
            a.setStudentId(rs.getInt("student_id"));
            a.setJobId(rs.getInt("job_id"));
            a.setStatus(rs.getString("status"));
            
            a.setCompanyName(rs.getString("company_name"));
            a.setJobRole(rs.getString("job_role"));
            a.setJobLocation(rs.getString("job_location"));
            a.setSalary(rs.getString("salary"));
            a.setLastDate(rs.getDate("last_date").toLocalDate());

            if (rs.getDate("apply_date") != null)
                a.setApplyDate(rs.getDate("apply_date").toLocalDate());

            list.add(a);
        }

        ps.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

    // If you still use getAllApplications(), it can delegate to searchApplications with null filters:
    public List<Application> getAllApplicationsEnriched() {
        return searchApplications(null, null, null, null, null, null);
    }

    public List<Job> getAllJobs() {
        List<Job> list = new ArrayList<>();
        String sql = "SELECT * FROM job_postings";   // Change table name if different

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            CompanyDAO cdao = new CompanyDAO();
            Integer company_id = cdao.getCompanyIdByName(rs.getString("company_name"));
            while (rs.next()) {
                Job job = new Job();
                job.setJobId(rs.getInt("job_id"));
                job.setCompanyId(company_id);
                job.setJobRole(rs.getString("role"));
                job.setDescription(rs.getString("description"));
                job.setEligibilityCgpa(rs.getDouble("required_cgpa"));
                job.setSalary(rs.getString("salary"));
                job.setLastDate(rs.getDate("last_date").toLocalDate());

                list.add(job);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
