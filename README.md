# TemuRasa

### Deskripsi Singkat

Temurasa adalah sistem manajemen restoran berbasis Java dengan antarmuka grafis (GUI) yang dirancang khusus untuk mengelola operasional restoran secara efisien dan profesional. Aplikasi ini mengimplementasikan prinsip-prinsip Object-Oriented Programming (OOP) yang solid dan memanfaatkan database SQLite untuk penyimpanan data yang handal dan terstruktur

---

### Fitur Utama

### Login dan Register
- Admin akan diarahkan login atau register
- Saat Admin belum punya akun akan diarahkan ke register untuk membuat username dan password baru
- Saat Admin sudah punya akun akan, admin hanya perlu login saja

  ### UI Login dan Register
   <img width="384" alt="image" src="https://github.com/user-attachments/assets/bfb0da3c-23ab-43be-970d-3b3400888bfd" />

  ## Point Of Sale
  <img width="673" alt="image" src="https://github.com/user-attachments/assets/762c789f-401b-42d6-a399-7e7990279e56" />

  1. Input nama customer
  2. Menu Items berisi nama dan kategori
  3. Saat telah menambahkan orderan akan masuk di tabel Orderan Saat Ini. Pada tabel Orderan Saat Ini akan akan mencetak Itemnya apa, kuantitas dan Harga Per-Unit dan     a       Subtotalnya lalu mencetak total harga
  4. pada tombol di bawah tabel Orderan yang berisi: - Hapus Item: Menghapus item tertentu atau yang dipilih
                                                     - Kurangkan Item: Mengurangi satu item yang dipillih
                                                     - Hapus Order: Menghapus semua isi pada tabel Orderan Saat Ini
                                                     - Proses Order: Memproses hasil order ke laporan penjualan


### Manajemen Menu
<img width="670" alt="image" src="https://github.com/user-attachments/assets/31cb12fd-cbd5-4e73-bd4e-36b8aaad610e" />

1. Menu Items berisi nama, kategori, harga, dan stok
2. Tabel Menu Items Details yang berisi Nama, Kategori (Makanan dan Minuman), Harga dan Stok
3. Tombol di bawah tabel Menu Items Details: -Tambah: Menambahkan nama, kategori, harga, dan stok pada Menu Items
                                             - Update: Memperbarui seperti nama, kategori, dll
                                             - Hapus: Menghapus semua yang ada pada tabel Menu Items Details
   

### Laporan Penjualan
<img width="955" alt="image" src="https://github.com/user-attachments/assets/e319db80-002b-4be5-9694-e16e860264c9" />

Berisi data-data ID, Nama Pelanggan, Tanggal, Total, dan Kasir

###  Database
1. Penyimpanan data produk dan transaksi secara permanen menggunakan **SQLite**.
2. Otomatis membuat tabel 'admin' 'menu' 'order' dan 'orderitem' saat aplikasi pertama kali dijalankan jika belum ada menggunakan file .db.




  
  

  -
