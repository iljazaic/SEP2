package service;
import model.*;
import java.time.LocalDateTime;
import java.util.List;
public class AdminService {
    private final PersistenceService persistenceService;
    public AdminService() {
        this.persistenceService = PersistenceService.getInstance();
    }
    public Flight addFlight(String origin, String destination,
                            LocalDateTime departure, LocalDateTime arrival,
                            int economySeats, int businessSeats, int firstSeats) {
        int flightId = persistenceService.nextFlightId();
        Flight flight = new Flight(flightId, origin, destination, departure, arrival);
        for (int i = 1; i <= economySeats; i++)
            flight.getSeats().add(new Seat(persistenceService.nextSeatId(),
                    "E" + i, Seat.CLASS_ECONOMY, 99.00 + i));
        for (int i = 1; i <= businessSeats; i++)
            flight.getSeats().add(new Seat(persistenceService.nextSeatId(),
                    "B" + i, Seat.CLASS_BUSINESS, 299.00 + i * 10));
        for (int i = 1; i <= firstSeats; i++)
            flight.getSeats().add(new Seat(persistenceService.nextSeatId(),
                    "F" + i, Seat.CLASS_FIRST, 699.00 + i * 50));
        persistenceService.addFlight(flight);
        System.out.println("[AdminService] Flight added: " + flight);
        return flight;
    }
    public boolean removeFlight(int flightId) {
        Flight flight = persistenceService.findFlightById(flightId);
        if (flight == null) {
            System.out.println("[AdminService] Flight not found.");
            return false;
        }
        for (Reservation r : persistenceService.getReservations()) {
            if (!Reservation.STATUS_CANCELLED.equals(r.getStatus())) {
                for (Ticket t : r.getTickets()) {
                    if (t.getFlight().getId() == flightId) {
                        r.setStatus(Reservation.STATUS_CANCELLED);
                        break;
                    }
                }
            }
        }
        persistenceService.removeFlight(flightId);
        System.out.println("[AdminService] Flight #" + flightId + " removed.");
        return true;
    }
    public boolean updateFlightStatus(int flightId, String newStatus) {
        Flight flight = persistenceService.findFlightById(flightId);
        if (flight == null) {
            System.out.println("[AdminService] Flight not found.");
            return false;
        }
        flight.setStatus(newStatus);
        System.out.println("[AdminService] Flight #" + flightId + " → " + newStatus);
        return true;
    }
    public List<Reservation> getAllReservations() {
        return persistenceService.getReservations();
    }
    public boolean cancelAnyReservation(int reservationId) {
        Reservation r = persistenceService.findReservationById(reservationId);
        if (r == null) {
            System.out.println("[AdminService] Reservation not found.");
            return false;
        }
        if (Reservation.STATUS_CANCELLED.equals(r.getStatus())) {
            System.out.println("[AdminService] Already cancelled.");
            return false;
        }
        for (Ticket t : r.getTickets()) {
            t.getSeat().setAvailable(true);
        }
        r.setStatus(Reservation.STATUS_CANCELLED);
        System.out.println("[AdminService] Reservation #" + reservationId + " cancelled.");
        return true;
    }
    public List<User> getAllUsers() {
        return persistenceService.getUsers();
    }
    public User addUser(String name, String email, String password, String role) {
        if (persistenceService.findUserByEmail(email) != null) {
            System.out.println("[AdminService] Email already in use.");
            return null;
        }
        User user = new User(persistenceService.nextUserId(), name, email, password, role);
        persistenceService.addUser(user);
        System.out.println("[AdminService] User added: " + user);
        return user;
    }
    public boolean removeUser(int userId) {
        boolean removed = persistenceService.removeUser(userId);
        System.out.println(removed
                ? "[AdminService] User #" + userId + " removed."
                : "[AdminService] User not found.");
        return removed;
    }
    public List<Flight> getAllFlights() {
        return persistenceService.getFlights();
    }
}
