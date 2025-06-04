package temurasa.models.payment;

public class DigitalWallet extends PaymentProcessor implements Payable {
    private String walletId;
    private double balance;
    private String encryptionKey;

    public DigitalWallet(String walletId, double balance) {
        super();
        this.walletId = walletId;
        this.balance = balance;
        this.encryptionKey = generateEncryptionKey();
    }

    @Override
    public boolean processPayment(double amount) {
        logTransaction("Processing digital wallet payment of $" + amount);

        if (!validatePayment(amount)) {
            return false;
        }

        if (!verifyTransaction()) {
            return false;
        }

        // Deduct dari balance
        balance -= amount;

        logTransaction("Payment successful. Remaining balance: $" + balance);
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "Digital Wallet (" + walletId + ")";
    }

    @Override
    public boolean validatePayment(double amount) {
        if (amount <= 0) {
            logTransaction("Invalid amount: " + amount);
            return false;
        }
        return true;
    }

    @Override
    public boolean verifyTransaction() {
        // Verifikasi untuk digital wallet
        return isActive &&
                walletId != null &&
                !walletId.isEmpty() &&
                encryptionKey != null &&
                balance >= 0;
    }

    // Specific methods untuk DigitalWallet
    public String getWalletId() {
        return walletId;
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(double amount) {
        if (amount > 0) {
            balance += amount;
            logTransaction("Balance added: $" + amount + ". New balance: $" + balance);
        }
    }

    private String generateEncryptionKey() {
        return "ENC" + System.currentTimeMillis() + walletId.hashCode();
    }

    public boolean authenticateUser(String pin) {
        // Simulasi autentikasi
        return pin != null && pin.length() == 4;
    }
}