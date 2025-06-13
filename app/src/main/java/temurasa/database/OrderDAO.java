package temurasa.database;

import temurasa.models.Order;
import temurasa.models.OrderItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    public int insertOrder(Order order) {
        String sql = "INSERT INTO orders (nama_pembeli, tanggal, kasir, total_harga) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, order.getNamaPembeli());
            pstmt.setString(2, order.getTanggal().toString());
            pstmt.setString(3, order.getKasir());
            pstmt.setDouble(4, order.getTotal_pembelian());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // return generated order id
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
        String sql = "SELECT * FROM orders";
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("id"),
                    rs.getString("nama_pembeli"),
                    rs.getString("kasir"),
                    rs.getTimestamp("tanggal").toLocalDateTime(),
                    rs.getDouble("total_harga")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil orders: " + e.getMessage());
        }
        return orders;
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                items.add(new OrderItem(
                    rs.getString("nama_item"),
                    rs.getInt("quantity"),
                    rs.getDouble("harga_per_unit")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil order items: " + e.getMessage());
        }
        return items;
    }
}
