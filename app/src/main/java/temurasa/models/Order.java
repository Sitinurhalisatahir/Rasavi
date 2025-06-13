package temurasa.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order extends BaseModel {
    private String nama_pembeli;
    private List<OrderItem> items;
    private String kasir;
    private LocalDateTime tanggal;
    private double total_pembelian;

    public Order(int id, String nama_pembeli, String kasir, LocalDateTime tanggal, double pembelian) {
        super(id);
        this.nama_pembeli = nama_pembeli;
        this.kasir = kasir;
        this.tanggal = tanggal;
        this.total_pembelian = pembelian;
        this.items = new ArrayList<>();
    }

    //Setter getter
    public String getNamaPembeli() {
        return nama_pembeli;
    }

    public void setNamaPembeli(String nama_pembeli) {
        this.nama_pembeli = nama_pembeli;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getKasir() {
        return kasir;
    }

    public void setKasir(String kasir) {
        this.kasir = kasir;
    }

    public LocalDateTime getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDateTime tanggal) {
        this.tanggal = tanggal;
    }

    public double getTotal_pembelian() {
        return total_pembelian;
    }

    public void setTotal_pembelian(double total_pembelian) {
        this.total_pembelian = total_pembelian;
    }
}