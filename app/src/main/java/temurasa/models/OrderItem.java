package temurasa.models;

public class OrderItem {
    private String namaItem;
    private int quantity;
    private double hargaPerUnit;

    public OrderItem(String namaItem, int quantity, double harga) {
        this.namaItem = namaItem;
        this.quantity = quantity;
        this.hargaPerUnit = harga;
    }

    public String getNamaItem() {
        return namaItem;
    }

    public void setNamaItem(String namaItem) {
        this.namaItem = namaItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getHargaPerUnit() {
        return hargaPerUnit;
    }

    public void setHargaPerUnit(double hargaPerUnit) {
        this.hargaPerUnit = hargaPerUnit;
    }
}