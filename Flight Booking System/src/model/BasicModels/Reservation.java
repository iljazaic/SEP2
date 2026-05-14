package Model;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class Reservation implements Serializable {
    public static final String STATUS_PENDING   = "PENDING";
    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_CANCELLED = "CANCELLED";
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private int           id;
    private User          user;
    private List<Ticket>  tickets;
    private String        status;
    private LocalDateTime bookingTime;
    public Reservation(int id, User user) {
        this.id          = id;
        this.user        = user;
        this.tickets     = new ArrayList<>();
        this.status      = STATUS_CONFIRMED;
        this.bookingTime = LocalDateTime.now();
    }
    public int           getId()          { return id; }
    public User          getUser()        { return user; }
    public List<Ticket>  getTickets()     { return tickets; }
    public String        getStatus()      { return status; }
    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setStatus(String status) { this.status = status; }
    public void addTicket(Ticket ticket) { tickets.add(ticket); }
    public double getTotalPrice() {
        double total = 0;
        for (Ticket t : tickets) total += t.getPrice();
        return total;
    }
    @Override
    public String toString() {
        return "Reservation #" + id
                + " | User: " + user.getName()
                + " | Tickets: " + tickets.size()
                + " | Total: $" + String.format("%.2f", getTotalPrice())
                + " | Status: " + status
                + " | Booked: " + bookingTime.format(FMT);
    }
}
