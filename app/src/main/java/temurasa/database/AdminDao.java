package temurasa.database;

import java.sql.*;

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
        // Jangan hash di sini, hash di luar saja!
        String sql = "INSERT INTO admin(username, password) VALUES(?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Register gagal: " + e.getMessage());
            return false;
        }
    }

    // Login admin
    public boolean authenticate(String username, String password) {
        // Jangan hash di sini, hash di luar saja!
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Login gagal: " + e.getMessage());
            return false;
        }
    }
}
