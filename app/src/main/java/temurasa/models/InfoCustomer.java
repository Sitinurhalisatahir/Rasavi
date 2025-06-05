package temurasa.models;

import java.util.ArrayList;
import java.util.List;


public class InfoCustomer {
    private String name;
    private String phone;
    private List<Order> orderHistory;
    private List<Reservation> reservationHistory; // Tambahkan riwayat reservasi
   

    // Constructor
    public InfoCustomer(String name) {
        this.name = name;
        this.phone = null;
        this.orderHistory = new ArrayList<>();
        this.reservationHistory = new ArrayList<>();
    }

    public InfoCustomer(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.orderHistory = new ArrayList<>();
        this.reservationHistory = new ArrayList<>();
    }

    // Method untuk menambah order ke history
    public void addOrder(Order order) {
        if (order != null) {
            orderHistory.add(order);
            System.out.println("✅ Order berhasil ditambahkan ke history customer: " + name);
        }
    }

    // Method untuk menambah reservasi ke history
    public void addReservation(Reservation reservation) {
        if (reservation != null) {
            reservationHistory.add(reservation);
            System.out.println("✅ Reservasi berhasil ditambahkan ke history customer: " + name);
        }
    }

    // Method untuk menampilkan order history
    public void displayOrderHistory() {
        System.out.println("\n=== ORDER HISTORY - " + name + " ===");
        if (orderHistory.isEmpty()) {
            System.out.println("Belum ada order history.");
            return;
        }

        for (int i = 0; i < orderHistory.size(); i++) {
            Order order = orderHistory.get(i);
            System.out.printf("%d. %s | Total: Rp%.2f | Status: %s\n",
                    i + 1, order.getOrderId(), order.getTotal(), order.getPaymentStatus());
        }
    }

    // Method untuk menampilkan reservation history
    public void displayReservationHistory() {
        System.out.println("\n=== RESERVATION HISTORY - " + name + " ===");
        if (reservationHistory.isEmpty()) {
            System.out.println("Belum ada reservation history.");
            return;
        }

        for (int i = 0; i < reservationHistory.size(); i++) {
            Reservation reservation = reservationHistory.get(i);
            System.out.printf("%d. Tanggal: %s | Waktu: %s | Tamu: %d | Status: %s\n",
                    i + 1, reservation.getReservationDate(), 
                    reservation.getReservationTime(), reservation.getPartySize(),
                    reservation.getStatus());
            
            if (reservation.getTableNumber() != null) {
                System.out.println("    Meja: " + reservation.getTableNumber());
            }
            if (reservation.getSpecialRequests() != null && 
                !reservation.getSpecialRequests().isEmpty()) {
                System.out.println("    Permintaan: " + reservation.getSpecialRequests());
            }
            System.out.println();
        }
    }

    public void displayCustomerSummary() {
        // Implementasi untuk menampilkan ringkasan customer
        System.out.println("Nama: " + name);
        // Tambahkan informasi lain sesuai kebutuhan
        displayOrderHistory();
        displayReservationHistory();
    }

    // Getters
    public String getName() { 
        return name; 
    }
    public String getPhone() { 
        return phone; 
    }
    public void setPhone(String phone) { 
        this.phone = phone; 
    } // Tambahkan setter untuk phone
    public List<Order> getOrderHistory() { 
        return new ArrayList<>(orderHistory); 
    }
    public List<Reservation> getReservationHistory() { 
        return new ArrayList<>(reservationHistory);
    } // Getter untuk reservation history
}