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
import PlacementManagement.models.Student;


public class StudentDAO {

    // STUDENT LOGIN
    public Student login(String email, String password) {
        String sql = "SELECT * FROM students WHERE email = ? AND password = ?";
        Student s = null;

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                s = extractStudent(rs);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }
    //INSERT STUDENT
public boolean insertStudent(Student s) {
    String sql = "INSERT INTO students (roll_no, name, email, password, dob, gender, department, cgpa, phone, resume_url, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
        ps.setString(1, s.getRollNo());
        ps.setString(2, s.getName());
        ps.setString(3, s.getEmail());
        ps.setString(4, s.getPassword());

        if (s.getDob() != null) {
            ps.setDate(5, java.sql.Date.valueOf(s.getDob()));
        } else {
            ps.setNull(5, java.sql.Types.DATE);
        }

        ps.setString(6, s.getGender());
        ps.setString(7, s.getDepartment());
        ps.setDouble(8, s.getCgpa());
        ps.setString(9, s.getPhone());
        ps.setString(10, s.getResumeUrl());
        ps.setString(11, s.getStatus());

        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
//CHECK WHETHER THE STUDENT ROLL NUMBER EXISTS
public boolean rollExists(String rollNo) {
    String sql = "SELECT student_id FROM students WHERE roll_no = ?";
    try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
        ps.setString(1, rollNo);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    } catch (Exception e) {
        e.printStackTrace();
        return true; // safer — treat exception as existing to avoid duplicates
    }
}
//CHECK WHETHER THE EMAIL EXISTS
public boolean emailExists(String email) {
    String sql = "SELECT student_id FROM students WHERE email = ?";
    try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    } catch (Exception e) {
        e.printStackTrace();
        return true;
    }
}


    // GET ALL STUDENTS
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try {
            Statement st = DBConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                list.add(extractStudent(rs));
            }

            rs.close();
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // GET STUDENT BY ID
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        Student s = null;

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                s = extractStudent(rs);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }

    // UPDATE PROFILE
    public boolean updateStudent(Student s) {
        String sql = "UPDATE students SET roll_no=?, name=?, email=?, dob=?, gender=?, department=?, cgpa=?, phone=?, resume_url=?, status=? WHERE student_id=?";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, s.getRollNo());
            ps.setString(2, s.getName());
            ps.setString(3, s.getEmail());
            ps.setDate(4, s.getDob() != null ? java.sql.Date.valueOf(s.getDob()) : null);
            ps.setString(5, s.getGender());
            ps.setString(6, s.getDepartment());
            ps.setDouble(7, s.getCgpa());
            ps.setString(8, s.getPhone());
            ps.setString(9, s.getResumeUrl());
            ps.setString(10, s.getStatus());
            ps.setInt(11, s.getStudentId());

            int rows = ps.executeUpdate();
            ps.close();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper: extract student from ResultSet
    private Student extractStudent(ResultSet rs) throws Exception {
        Student s = new Student();

        s.setStudentId(rs.getInt("student_id"));
        s.setRollNo(rs.getString("roll_no"));
        s.setName(rs.getString("name"));
        s.setEmail(rs.getString("email"));
        s.setPassword(rs.getString("password"));
        s.setDob(rs.getDate("dob") != null ? rs.getDate("dob").toLocalDate() : null);
        s.setGender(rs.getString("gender"));
        s.setDepartment(rs.getString("department"));
        s.setCgpa(rs.getDouble("cgpa"));
        s.setPhone(rs.getString("phone"));
        s.setResumeUrl(rs.getString("resume_url"));
        s.setStatus(rs.getString("status"));

        return s;
    }
    public boolean deleteResume(int studentId) {
    String sql = "UPDATE students SET resume_url = NULL WHERE student_id = ?";
    try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
        ps.setInt(1, studentId);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    public List<Student> searchStudents(Integer id, String name, String dept, String status) {

    List<Student> list = new ArrayList<>();

    StringBuilder sql = new StringBuilder(
            "SELECT * FROM students WHERE 1=1 "
    );

    if (id != null) sql.append("AND student_id = ").append(id).append(" ");
    if (!name.isEmpty()) sql.append("AND name LIKE '%").append(name).append("%' ");
    if (!dept.isEmpty()) sql.append("AND department LIKE '%").append(dept).append("%' ");
    if (!status.isEmpty()) sql.append("AND status = '").append(status).append("' ");

    try {
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet rs = st.executeQuery(sql.toString());

        while (rs.next()) {
            Student s = extractStudent(rs);
            list.add(s);
        }

        rs.close();
        st.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
public List<String> getAllDepartments() {
    List<String> list = new ArrayList<>();
    String sql = "SELECT DISTINCT department FROM students WHERE department IS NOT NULL ORDER BY department";
    try (Statement st = DBConnection.getConnection().createStatement();
         ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) list.add(rs.getString(1));
    } catch (Exception e) { e.printStackTrace(); }
    return list;
}
public String getResume(int studentId) {
    String sql = "SELECT resume_url FROM students WHERE student_id = ?";
    try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
        ps.setInt(1, studentId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getString("resume_url");
    } catch (Exception e) { e.printStackTrace(); }
    return null;
}

}
