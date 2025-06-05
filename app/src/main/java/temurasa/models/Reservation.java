package temurasa.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class Reservation {
    private int id;
    private String customerName;
    private String customerPhone;
    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private int partySize;
    private Integer tableNumber;
    private String status;
    private String specialRequests;
    private LocalDateTime createdAt;

    // Konstruktor sederhana
    public Reservation(String customerName, String customerPhone, LocalDate reservationDate, LocalTime reservationTime, int partySize) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.partySize = partySize;
        this.status = "pending";
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    
    public LocalDate getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }
    
    public LocalTime getReservationTime() { return reservationTime; }
    public void setReservationTime(LocalTime reservationTime) { this.reservationTime = reservationTime; }
    
    public int getPartySize() { return partySize; }
    public void setPartySize(int partySize) { this.partySize = partySize; }
    
    public Integer getTableNumber() {
         return tableNumber; 
    }

    public void setTableNumber(Integer tableNumber) {
         this.tableNumber = tableNumber; 
    }
    
    public String getStatus() {
         return status; 
    }

    public void setStatus(String status) { 
        this.status = status; 
    }
    
    public String getSpecialRequests() { 
        return specialRequests; 
    }

    public void setSpecialRequests(String specialRequests) { 
        this.specialRequests = specialRequests; 
    }
    
    public LocalDateTime getCreatedAt() {
         return createdAt; 
    }
}