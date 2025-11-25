/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PlacementManagement.database;
import java.sql.Connection;
import java.sql.DriverManager;import java.sql.SQLException;
/**
 *
 * @author HP
 */
public class DBConnection {
    private static Connection connection;

    @SuppressWarnings("CallToPrintStackTrace")
    public static Connection getConnection() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connection == null || connection.isClosed()) {
                String url = "jdbc:mysql://localhost:3306/placement_management";
                String user = "placement_user";
                String pass = "Place@123"; // change to your password
                connection = DriverManager.getConnection(url, user, pass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
