package temurasa.models;

public class MenuItem {
    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    private boolean available;

    public MenuItem(String id, String name, String description, double price, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.available = true;
    }

    // Abstract the complexity of price calculation
    public double calculateDiscountedPrice(double discountPercentage) {
        return price * (1 - discountPercentage / 100);
    }

    // Abstract availability check
    public boolean isOrderable() {
        return available && price > 0;
    }

    // Hide internal state changes
    public void toggleAvailability() {
        this.available = !this.available;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }
}