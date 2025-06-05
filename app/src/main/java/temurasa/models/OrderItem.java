package temurasa.models;

public class OrderItem {
    private MenuItem menuItem;
    private int quantity;
    private double subtotal;
    private String notes;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.notes = "";
        calculateSubtotal();
    }

    public OrderItem(MenuItem menuItem, int quantity, String notes) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.notes = notes;
        calculateSubtotal();
    }

    private void calculateSubtotal() {
        this.subtotal = menuItem.getPrice() * quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateSubtotal();
    }

    // Getters
    public MenuItem getMenuItem() { 
        return menuItem;
    }
    public int getQuantity() {
        return quantity; 
    }
    public double getSubtotal() {
        return subtotal;
    }
    public String getNotes() { 
        return notes;
    }
    public void setNotes(String notes) { 
        this.notes = notes; 
    }

    @Override
    public String toString() {
        return String.format("%s x%d = Rp%.2f", menuItem.getName(), quantity, subtotal);
    }
}