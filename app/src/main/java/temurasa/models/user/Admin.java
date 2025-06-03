package temurasa.models.user;

public class Admin extends User{
     private String department;
    
    public Admin(String id, String name, String email, String password, String department) {
        super(id, name, email, password, "admin"); // Call parent constructor
        this.department = department;
    }
    
    // Admin-specific methods
    public void manageMenu() {
        System.out.println("Admin " + getName() + " managing menu");
    }
    
    public void viewReports() {
        System.out.println("Viewing admin reports");
    }
    
    // Override parent method if needed
    @Override
    public String toString() {
        return "Admin: " + getName() + " from " + department;
    }
    
}
