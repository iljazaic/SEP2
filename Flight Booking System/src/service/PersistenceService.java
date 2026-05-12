package Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Model.*;
public class PersistenceService {
    private static PersistenceService instance;
    private final List<User>        users        = new ArrayList<>();
    private final List<Flight>      flights      = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();
    private int userIdCounter        = 1;
    private int flightIdCounter      = 1;
    private int reservationIdCounter = 1;
    private int ticketIdCounter      = 1;
    private int seatIdCounter        = 1;
    private PersistenceService() {
        seedData();
    }
    public static PersistenceService getInstance() {
        if (instance == null) {
            instance = new PersistenceService();
        }
        return instance;
    }
    private void seedData() {
        users.add(new User(userIdCounter++, "Admin User",  "admin@flights.com", "admin123", User.ROLE_ADMIN));
        users.add(new User(userIdCounter++, "Alice Smith", "alice@mail.com",    "pass1",    User.ROLE_CLIENT));
        users.add(new User(userIdCounter++, "Bob Jones",   "bob@mail.com",      "pass2",    User.ROLE_CLIENT));
        addSampleFlight("Copenhagen", "London",
                LocalDateTime.of(2026, 6, 10, 8, 0),
                LocalDateTime.of(2026, 6, 10, 10, 30));
        addSampleFlight("London", "New York",
                LocalDateTime.of(2026, 6, 11, 14, 0),
                LocalDateTime.of(2026, 6, 11, 22, 0));
        addSampleFlight("Copenhagen", "Paris",
                LocalDateTime.of(2026, 6, 12, 9, 30),
                LocalDateTime.of(2026, 6, 12, 11, 45));
        addSampleFlight("Paris", "Rome",
                LocalDateTime.of(2026, 6, 13, 16, 0),
                LocalDateTime.of(2026, 6, 13, 18, 0));
    }
    private void addSampleFlight(String origin, String destination,
                                  LocalDateTime dep, LocalDateTime arr) {
        Flight f = new Flight(flightIdCounter++, origin, destination, dep, arr);
        for (int i = 1; i <= 10; i++)
            f.getSeats().add(new Seat(seatIdCounter++, "E" + i, Seat.CLASS_ECONOMY,  99.00 + i));
        for (int i = 1; i <= 4; i++)
            f.getSeats().add(new Seat(seatIdCounter++, "B" + i, Seat.CLASS_BUSINESS, 299.00 + i * 10));
        for (int i = 1; i <= 2; i++)
            f.getSeats().add(new Seat(seatIdCounter++, "F" + i, Seat.CLASS_FIRST,    699.00 + i * 50));
        flights.add(f);
    }
    public int nextReservationId() { return reservationIdCounter++; }
    public int nextTicketId()      { return ticketIdCounter++; }
    public int nextUserId()        { return userIdCounter++; }
    public int nextFlightId()      { return flightIdCounter++; }
    public int nextSeatId()        { return seatIdCounter++; }
    public List<User> getUsers() { return users; }
    public User findUserByEmail(String email) {
        for (User u : users)
            if (u.getEmail().equalsIgnoreCase(email)) return u;
        return null;
    }
    public User findUserById(int id) {
        for (User u : users)
            if (u.getId() == id) return u;
        return null;
    }
    public void addUser(User user)     { users.add(user); }
    public boolean removeUser(int id)  { return users.removeIf(u -> u.getId() == id); }
    public List<Flight> getFlights() { return flights; }
    public Flight findFlightById(int id) {
        for (Flight f : flights)
            if (f.getId() == id) return f;
        return null;
    }
    public void addFlight(Flight flight)    { flights.add(flight); }
    public boolean removeFlight(int id)     { return flights.removeIf(f -> f.getId() == id); }
    public List<Reservation> getReservations() { return reservations; }
    public Reservation findReservationById(int id) {
        for (Reservation r : reservations)
            if (r.getId() == id) return r;
        return null;
    }
    public List<Reservation> getReservationsForUser(int userId) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations)
            if (r.getUser().getId() == userId) result.add(r);
        return result;
    }
    public void addReservation(Reservation r)    { reservations.add(r); }
    public boolean removeReservation(int id)     { return reservations.removeIf(r -> r.getId() == id); }
}
