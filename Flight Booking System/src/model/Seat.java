package model;
import java.io.Serializable;
public class Seat implements Serializable {
    public static final String CLASS_ECONOMY  = "ECONOMY";
    public static final String CLASS_BUSINESS = "BUSINESS";
    public static final String CLASS_FIRST    = "FIRST";
    private int     id;
    private String  seatNumber;
    private String  seatClass;
    private boolean available;
    private double  price;
    public Seat(int id, String seatNumber, String seatClass, double price) {
        this.id         = id;
        this.seatNumber = seatNumber;
        this.seatClass  = seatClass;
        this.available  = true;
        this.price      = price;
    }
    public int     getId()         { return id; }
    public String  getSeatNumber() { return seatNumber; }
    public String  getSeatClass()  { return seatClass; }
    public boolean isAvailable()   { return available; }
    public double  getPrice()      { return price; }
    public void setAvailable(boolean available) { this.available = available; }
    public void setPrice(double price)          { this.price     = price; }
    @Override
    public String toString() {
        return seatNumber + " [" + seatClass + "] $" + String.format("%.2f", price)
                + (available ? " (available)" : " (taken)");
    }
}
