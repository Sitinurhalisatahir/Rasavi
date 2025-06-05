package temurasa.models.user;

public class User {
    private String name;
    private String email;
    private String password;


    public User(String id, String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = encryptPassword(password); 
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
    public boolean validateCredentials(String email, String password) {
        return this.email.equals(email) && this.password.equals(encryptPassword(password));
    }

    }
