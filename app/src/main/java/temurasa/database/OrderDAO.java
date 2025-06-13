package temurasa.database;

import temurasa.models.Order;
import temurasa.models.OrderItem;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public int insertOrder(Order order) {
        String sql = "INSERT INTO orders (nama_pembeli, kasir, tanggal, total_harga) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, order.getNamaPembeli());
            pstmt.setString(2, order.getKasir());

            // Set tanggal secara manual
            LocalDateTime tanggal = order.getTanggal() != null ? order.getTanggal() : LocalDateTime.now();
            pstmt.setString(3, tanggal.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            pstmt.setDouble(4, order.getTotal_pembelian());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Gagal menambah order: " + e.getMessage());
        }
        return -1;
    }

    public boolean insertOrderItems(int orderId, List<OrderItem> items) {
        String sql = "INSERT INTO order_items (order_id, nama_item, quantity, harga_per_unit) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (OrderItem item : items) {
                pstmt.setInt(1, orderId);
                pstmt.setString(2, item.getNamaItem());
                pstmt.setInt(3, item.getQuantity());
                pstmt.setDouble(4, item.getHargaPerUnit());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            return true;
        } catch (SQLException e) {
            System.err.println("Gagal menambah order items: " + e.getMessage());
            return false;
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, nama_pembeli, kasir, tanggal, total_harga FROM orders ORDER BY tanggal DESC";

        try (Connection conn = DatabaseHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {

                LocalDateTime tanggal;
                try {
                    String tanggalStr = rs.getString("tanggal");
                    if (tanggalStr != null && !tanggalStr.isEmpty()) {
                        // Gunakan format standar yang konsisten: yyyy-MM-dd HH:mm:ss
                        tanggal = LocalDateTime.parse(tanggalStr,
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    } else {
                        tanggal = LocalDateTime.now();
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing date: " + e.getMessage());
                    tanggal = LocalDateTime.now();
                }

                Order order = new Order(
                        rs.getInt("id"),
                        rs.getString("nama_pembeli"),
                        rs.getString("kasir"),
                        tanggal,
                        rs.getDouble("total_harga"));
                orders.add(order);
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil orders: " + e.getMessage());
            e.printStackTrace();
        }
        return orders;
    }
}