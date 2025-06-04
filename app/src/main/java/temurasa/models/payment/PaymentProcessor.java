package temurasa.models.payment;

public abstract class PaymentProcessor {
    protected String transactionId;
    protected boolean isActive;

    public PaymentProcessor() {
        this.transactionId = generateTransactionId();
        this.isActive = true;
    }

    public abstract boolean verifyTransaction();

    public String getTransactionId(){
        return transactionId;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    protected String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }
    
    protected void logTransaction(String message) {
        System.out.println("[" + transactionId + "] " + message);
    }
}
