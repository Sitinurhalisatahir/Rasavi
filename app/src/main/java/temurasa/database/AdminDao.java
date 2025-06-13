package temurasa.database;

import java.sql.*;
import temurasa.util.PasswordUtils;

public class AdminDao {

    // Cek apakah username sudah ada di database
    public boolean isAdminExist(String username) {
        String sql = "SELECT 1 FROM admin WHERE username = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Cek admin gagal: " + e.getMessage());
            return false;
        }
    }

    // Register admin baru
    public boolean insertAdmin(String username, String password) {
        String hashedPassword = PasswordUtils.hashPassword(password);
        String sql = "INSERT INTO admin(username, password) VALUES(?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Register gagal: " + e.getMessage());
            return false;
        }
    }

    // Login admin
    public boolean authenticate(String username, String password) {
        String hashedPassword = PasswordUtils.hashPassword(password);
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Login gagal: " + e.getMessage());
            return false;
        }
    }
}
