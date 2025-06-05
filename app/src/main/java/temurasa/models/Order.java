package temurasa.models;

import temurasa.models.payment.Payable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Order {
    private String orderId;
    private String customerId; // Nama pelanggan atau ID
    private List<OrderItem> items;
    private double total;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private LocalDateTime orderTime;
    private String cashierId; // Untuk pembayaran tunai
    private Payable paymentMethod;

    public enum OrderStatus {
        PENDING, CONFIRMED, PREPARING, READY, COMPLETED, CANCELLED
    }

    public enum PaymentStatus {
        UNPAID, PENDING, PAID, FAILED, REFUNDED
    }

    // Constructor menerima SimpleCustomer atau String customerName
    public Order(InfoCustomer customer) {
        this(customer.getName());
    }

    public Order(String customerName) {
        this.orderId = "ORD" + System.currentTimeMillis();
        this.customerId = customerName;
        this.items = new ArrayList<>();
        this.total = 0.0;
        this.status = OrderStatus.PENDING;
        this.paymentStatus = PaymentStatus.UNPAID;
        this.orderTime = LocalDateTime.now();
    }

    public void addItem(OrderItem item) {
        items.add(item);
        calculateTotal();
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        calculateTotal();
    }

    private void calculateTotal() {
        total = items.stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
    }

    public boolean processPayment(Payable paymentMethod) {
        this.paymentMethod = paymentMethod;
        setPaymentStatus(PaymentStatus.PENDING);

        System.out.println("=== PROSES PEMBAYARAN PESANAN ===");
        System.out.println("Order ID: " + orderId);
        System.out.println("Total: Rp" + total);
        System.out.println("Metode: " + paymentMethod.getPaymentMethod());

        boolean success = paymentMethod.processPayment(total);

        if (success) {
            setPaymentStatus(PaymentStatus.PAID);
            setStatus(OrderStatus.CONFIRMED);
            System.out.println("✅ Pembayaran berhasil untuk Order " + orderId);
        } else {
            setPaymentStatus(PaymentStatus.FAILED);
            System.out.println("❌ Pembayaran gagal untuk Order " + orderId);
        }

        return success;
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public String getCashierId() {
        return cashierId;
    }

    public Payable getPaymentMethod() {
        return paymentMethod;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    @Override
    public String toString() {
        return String.format("Order[%s] - Customer: %s, Total: Rp%.2f, Status: %s, Payment: %s",
                orderId, customerId, total, status, paymentStatus);
    }
}
