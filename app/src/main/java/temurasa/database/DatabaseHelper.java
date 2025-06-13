package temurasa.database;

import java.sql.*;

public class DatabaseHelper {
    public static final String URL = "jdbc:sqlite:temurasa.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Membuat semua tabel yang dibutuhkan
    public static void createTables() {
        String createAdminTable = "CREATE TABLE IF NOT EXISTS admin (" +
                "username TEXT PRIMARY KEY," +
                "password TEXT NOT NULL" +
                ");";

        String createMenuTable = "CREATE TABLE IF NOT EXISTS menu (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nama_item TEXT NOT NULL, " +
                "harga REAL NOT NULL, " +
                "kategori TEXT NOT NULL, " +
                "stok INTEGER NOT NULL, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        String createOrderTable = "CREATE TABLE IF NOT EXISTS orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nama_pembeli TEXT NOT NULL, " +
                "tanggal TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "kasir TEXT NOT NULL, " +
                "total_harga REAL NOT NULL" +
                ");";

        String createOrderItemTable = "CREATE TABLE IF NOT EXISTS order_items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "order_id INTEGER NOT NULL, " +
                "nama_item TEXT NOT NULL, " +
                "quantity INTEGER NOT NULL, " +
                "harga_per_unit REAL NOT NULL" +
                ");";

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute(createAdminTable);
            stmt.execute(createMenuTable);
            stmt.execute(createOrderTable);
            stmt.execute(createOrderItemTable);
            System.out.println("Tabel berhasil dibuat.");
        } catch (SQLException e) {
            System.err.println("Gagal membuat tabel: " + e.getMessage());
        }
    }
}
