package temurasa.models;

public class Menu extends BaseModel {
    private String nama;
    private double harga;
    private String kategori;
    private int stok;
    private String createdAt;

    public Menu(int id, String nama, double harga, String kategori, int stok, String createdAt) {
        super(id);
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
        this.stok = stok;
        this.createdAt = createdAt;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
