package temurasa.models.user;

public class User {
    private String name;
    private String email;
    private String password;
    protected String role;

    public User(String id, String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = encryptPassword(password); 
        this.role = role;
    }
    public String getName() { 
        return name; 
    }
    public String getEmail() { 
        return email;
     }
    
    public void setPassword(String password) {
        this.password = encryptPassword(password);
    }
    
    private String encryptPassword(String password) {
        return "hashed_" + password;
    }
    protected boolean validateCredentials(String email, String password) {
        return this.email.equals(email) && this.password.equals(encryptPassword(password));
    }

    }
    
    

