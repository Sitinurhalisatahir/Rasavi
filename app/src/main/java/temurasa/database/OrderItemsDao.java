package temurasa.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import temurasa.models.OrderItem;

public class OrderItemsDao {

    public boolean insertOrderItem(int orderId, OrderItem item) {
        String sql = "INSERT INTO order_items (order_id, nama_item, quantity, harga_per_unit) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            pstmt.setString(2, item.getNamaItem());
            pstmt.setInt(3, item.getQuantity());
            pstmt.setDouble(4, item.getHargaPerUnit());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Gagal menambah order item: " + e.getMessage());
            return false;
        }
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