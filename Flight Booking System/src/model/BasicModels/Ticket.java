package Model;
import java.io.Serializable;
public class Ticket implements Serializable {
    private int    id;
    private User   user;
    private Flight flight;
    private Seat   seat;
    private double price;
    public Ticket(int id, User user, Flight flight, Seat seat) {
        this.id     = id;
        this.user   = user;
        this.flight = flight;
        this.seat   = seat;
        this.price  = seat.getPrice();
    }
    public int    getId()     { return id; }
    public User   getUser()   { return user; }
    public Flight getFlight() { return flight; }
    public Seat   getSeat()   { return seat; }
    public double getPrice()  { return price; }
    @Override
    public String toString() {
        return "Ticket #" + id
                + " | " + flight.getOrigin() + " -> " + flight.getDestination()
                + " | Seat: " + seat.getSeatNumber()
                + " [" + seat.getSeatClass() + "]"
                + " | $" + String.format("%.2f", price);
    }
}
