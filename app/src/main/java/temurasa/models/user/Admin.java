package temurasa.models.user;

import temurasa.models.Order;
import temurasa.models.InfoCustomer;
import temurasa.models.Reservation;

import java.util.List;
import java.util.ArrayList;

public class Admin extends User {
    private List<Order> monitoredOrders;
    private List<InfoCustomer> customers; // Daftar pelanggan

    public Admin(String id, String name, String email, String password) {
        super(id, name, email, password);
        this.monitoredOrders = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    // Admin-specific methods
    public void manageMenu() {
        System.out.println("Pengaturan Menu");
    }

    public void viewReports() {
        System.out.println("Melihat laporan admin");
    }

    // CRUD Customer
    public void addCustomer(InfoCustomer customer) {
        customers.add(customer);
        System.out.println("✅ Customer " + customer.getName() + " ditambahkan.");
    }

    public void deleteCustomer(String name) {
        InfoCustomer customer = findCustomerByName(name);
        if (customer != null) {
            customers.remove(customer);
            System.out.println("✅ Customer " + name + " dihapus.");
        } else {
            System.out.println("❌ Customer tidak ditemukan.");
        }
    }

    public InfoCustomer findCustomerByName(String name) {
        return customers.stream()
                .filter(customer -> customer.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    // Membuat order untuk customer
    public void createOrderForCustomer(String customerName, Order order) {
        InfoCustomer customer = findCustomerByName(customerName);
        if (customer != null) {
            customer.addOrder(order);
            addOrderForMonitoring(order);
            System.out.println("✅ Order untuk customer " + customerName + " berhasil dibuat.");
        } else {
            System.out.println("❌ Customer tidak ditemukan.");
        }
    }

    // Membuat reservasi untuk customer
    public void createReservationForCustomer(String customerName, Reservation reservation) {
        InfoCustomer customer = findCustomerByName(customerName);
        if (customer != null) {
            customer.addReservation(reservation);
            System.out.println("✅ Reservasi untuk customer " + customerName + " berhasil dibuat.");
        } else {
            System.out.println("❌ Customer tidak ditemukan.");
        }
    }

    // Melihat riwayat customer
    public void viewCustomerHistory(String customerName) {
        InfoCustomer customer = findCustomerByName(customerName);
        if (customer != null) {
            customer.displayOrderHistory();
            customer.displayReservationHistory();
        } else {
            System.out.println("❌ Customer tidak ditemukan.");
        }
    }

    // Monitoring payments
    public void monitorPayments() {
        System.out.println("\n=== MONITORING PEMBAYARAN ===");
        System.out.println("Admin " + getName() + " memantau status pembayaran");

        for (Order order : monitoredOrders) {
            displayPaymentStatus(order);
        }
    }

    public void addOrderForMonitoring(Order order) {
        monitoredOrders.add(order);
        System.out.println("Order " + order.getOrderId() + " ditambahkan ke monitoring");
    }

    public void displayPaymentStatus(Order order) {
        String statusIcon = getPaymentStatusIcon(order.getPaymentStatus());
        System.out.println(String.format("%s Order: %s | Customer: %s | Total: Rp%.2f | Status: %s | Pembayaran: %s",
                statusIcon, order.getOrderId(), order.getCustomerId(),
                order.getTotal(), order.getStatus(), order.getPaymentStatus()));
    }

    private String getPaymentStatusIcon(Order.PaymentStatus status) {
        switch (status) {
            case PAID:
                return "✅";
            case PENDING:
                return "⏳";
            case FAILED:
                return "❌";
            case UNPAID:
                return "⚠️";
            case REFUNDED:
                return "🔄";
            default:
                return "❓";
        }
    }

    public void approvePayment(String orderId) {
        Order order = findOrderById(orderId);
        if (order != null && order.getPaymentStatus() == Order.PaymentStatus.PENDING) {
            order.setPaymentStatus(Order.PaymentStatus.PAID);
            order.setStatus(Order.OrderStatus.CONFIRMED);
            System.out.println("✅ Admin " + getName() + " menyetujui pembayaran untuk Order " + orderId);
        }
    }

    public void rejectPayment(String orderId, String reason) {
        Order order = findOrderById(orderId);
        if (order != null) {
            order.setPaymentStatus(Order.PaymentStatus.FAILED);
            System.out.println(
                    "❌ Admin " + getName() + " menolak pembayaran untuk Order " + orderId + ". Alasan: " + reason);
        }
    }

    private Order findOrderById(String orderId) {
        return monitoredOrders.stream()
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);
    }

    public List<Order> getPaidOrders() {
        return monitoredOrders.stream()
                .filter(order -> order.getPaymentStatus() == Order.PaymentStatus.PAID)
                .toList();
    }

    public List<Order> getUnpaidOrders() {
        return monitoredOrders.stream()
                .filter(order -> order.getPaymentStatus() == Order.PaymentStatus.UNPAID ||
                        order.getPaymentStatus() == Order.PaymentStatus.PENDING)
                .toList();
    }

    @Override
    public String toString() {
        return "Admin: " + getName();
    }

    public List<Order> getMonitoredOrders() {
        return monitoredOrders;
    }
}