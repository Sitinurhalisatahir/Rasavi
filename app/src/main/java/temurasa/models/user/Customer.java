package temurasa.models.user;

import temurasa.models.Order;
import temurasa.models.Reservation;
public class Customer extends User {
    private String phone;
    private String address;
    private double loyaltyPoints;
    
    public Customer(String id, String name, String email, String password) {
        super(id, name, email, password, "customer");
        this.loyaltyPoints = 0.0;
    }
    
    // Customer-specific methods
    public void placeOrder(Order order) {
        System.out.println("Customer " + getName() + " placing order");
        addLoyaltyPoints(order.getTotal() * 0.1); // 10% as points
    }
    
    public void makeReservation(Reservation reservation) {
        System.out.println("Customer " + getName() + " making reservation");
    }
    
    private void addLoyaltyPoints(double points) {
        this.loyaltyPoints += points;
    }
    
    // Getters
    public String getPhone() { 
        return phone; 
    }
    public String getAddress() { 
        return address;
     }
    public double getLoyaltyPoints() { 
        return loyaltyPoints;
     }
}
