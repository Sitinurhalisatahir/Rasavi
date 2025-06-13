package temurasa.database;

import temurasa.models.Menu;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    public boolean insertMenu(Menu menu) {
        if (isNamaMenuExist(menu.getNama())) {
            System.err.println("Nama menu sudah ada!");
            return false;
        }
        String sql = "INSERT INTO menu (nama_item, harga, kategori, stok) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, menu.getNama());
            pstmt.setDouble(2, menu.getHarga());
            pstmt.setString(3, menu.getKategori());
            pstmt.setInt(4, menu.getStok());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Gagal menambah menu: " + e.getMessage());
            return false;
        }
    }

    public List<Menu> getAllMenu() {
        List<Menu> menus = new ArrayList<>();
        String sql = "SELECT * FROM menu";
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Menu menu = new Menu(
                    rs.getInt("id"),
                    rs.getString("nama_item"),
                    rs.getDouble("harga"),
                    rs.getString("kategori"),
                    rs.getInt("stok"),
                    rs.getString("created_at")
                );
                menus.add(menu);
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil menu: " + e.getMessage());
        }
        return menus;
    }

    public boolean updateMenu(Menu menu) {
        String sql = "UPDATE menu SET nama_item=?, harga=?, kategori=?, stok=? WHERE id=?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, menu.getNama());
            pstmt.setDouble(2, menu.getHarga());
            pstmt.setString(3, menu.getKategori());
            pstmt.setInt(4, menu.getStok());
            pstmt.setInt(5, menu.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Gagal update menu: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteMenu(int id) {
        String sql = "DELETE FROM menu WHERE id=?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Gagal hapus menu: " + e.getMessage());
            return false;
        }
    }

    public boolean kurangiStok(String namaItem, int jumlah) {
        String sql = "UPDATE menu SET stok = stok - ? WHERE nama_item = ?";
        try (Connection conn = DatabaseHelper.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, jumlah);
            pstmt.setString(2, namaItem);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Gagal mengurangi stok: " + e.getMessage());
            return false;
        }
    }

    public boolean isNamaMenuExist(String namaItem) {
        String sql = "SELECT 1 FROM menu WHERE nama_item = ?";
        try (Connection conn = DatabaseHelper.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, namaItem);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Gagal cek nama menu: " + e.getMessage());
            return false;
        }
    }
}