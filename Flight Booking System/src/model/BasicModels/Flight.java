package Model;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class Flight implements Serializable {
    public static final String STATUS_SCHEDULED = "SCHEDULED";
    public static final String STATUS_DELAYED   = "DELAYED";
    public static final String STATUS_CANCELLED = "CANCELLED";
    public static final String STATUS_COMPLETED = "COMPLETED";
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private int           id;
    private String        origin;
    private String        destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String        status;
    private List<Seat>    seats;
    public Flight(int id, String origin, String destination,
                  LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.id            = id;
        this.origin        = origin;
        this.destination   = destination;
        this.departureTime = departureTime;
        this.arrivalTime   = arrivalTime;
        this.status        = STATUS_SCHEDULED;
        this.seats         = new ArrayList<>();
    }
    public int           getId()            { return id; }
    public String        getOrigin()        { return origin; }
    public String        getDestination()   { return destination; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public LocalDateTime getArrivalTime()   { return arrivalTime; }
    public String        getStatus()        { return status; }
    public List<Seat>    getSeats()         { return seats; }
    public void setStatus(String status)                 { this.status        = status; }
    public void setDepartureTime(LocalDateTime dateTime) { this.departureTime = dateTime; }
    public void setArrivalTime(LocalDateTime dateTime)   { this.arrivalTime   = dateTime; }
    public List<Seat> getAvailableSeats() {
        List<Seat> available = new ArrayList<>();
        for (Seat s : seats)
            if (s.isAvailable()) available.add(s);
        return available;
    }
    @Override
    public String toString() {
        return "[" + id + "] " + origin + " -> " + destination
                + " | Dep: " + departureTime.format(FMT)
                + " | Arr: " + arrivalTime.format(FMT)
                + " | " + status
                + " | Seats available: " + getAvailableSeats().size();
    }
}
