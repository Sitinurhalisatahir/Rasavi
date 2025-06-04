package temurasa.models.payment;

public interface Payable {
    boolean processPayment(double amount);
    String getPaymentMethod();
    boolean validatePayment(double amount);
}
