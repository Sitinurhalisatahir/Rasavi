package temurasa.models;


public class Reservation {
    private String id;
    private String date;
    private String time;
    private String tableNumber;
    private String customerID;

    public Reservation(String id, String date, String time, String tableNumber, String customerID) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.tableNumber = tableNumber;
        this.customerID = customerID;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public String getCustomerID() {
        return customerID;
    }
    
}
