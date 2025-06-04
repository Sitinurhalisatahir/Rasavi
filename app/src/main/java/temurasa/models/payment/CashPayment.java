package temurasa.models.payment;

public class CashPayment extends PaymentProcessor implements Payable {
    private double cashReceived;
    private double change;

    public CashPayment() {
        super();
    }

    @Override
    public boolean processPayment(double amount) {
        logTransaction("Processing cash payment of $" + amount);
        
        if (!validatePayment(amount)) {
            return false;
        }
        
        if (!verifyTransaction()) {
            return false;
        }
        
        // Hitung kembalian
        this.change = cashReceived - amount;
        logTransaction("Payment successful. Change: $" + change);
        return true;
    }
    @Override
    public String getPaymentMethod() {
        return "Cash Payment";
    }
    
    @Override
    public boolean validatePayment(double amount) {
        if (amount <= 0) {
            logTransaction("Invalid amount: " + amount);
            return false;
        }
        
        if (cashReceived < amount) {
            logTransaction("Insufficient cash. Required: $" + amount + ", Received: $" + cashReceived);
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean verifyTransaction() {
        // Untuk cash, verifikasi sederhana
        return isActive && cashReceived > 0;
    }
    
    // Specific methods untuk CashPayment
    public void setCashReceived(double cashReceived) {
        this.cashReceived = cashReceived;
    }
    
    public double getChange() {
        return change;
    }
    
    public double getCashReceived() {
        return cashReceived;
    }
}