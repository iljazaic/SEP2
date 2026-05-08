package service;
import model.*;
import java.util.ArrayList;
import java.util.List;
public class TicketService {
    protected PersistenceService persistenceService;
    public TicketService() {
        this.persistenceService = PersistenceService.getInstance();
    }
    public List<Flight> getAllActiveFlights() {
        List<Flight> result = new ArrayList<>();
        for (Flight f : persistenceService.getFlights()) {
            if (!Flight.STATUS_CANCELLED.equals(f.getStatus())) result.add(f);
        }
        return result;
    }
    public List<Flight> searchFlights(String origin, String destination) {
        List<Flight> result = new ArrayList<>();
        for (Flight f : getAllActiveFlights()) {
            boolean matchOrigin = (origin == null) || f.getOrigin().equalsIgnoreCase(origin);
            boolean matchDest   = (destination == null) || f.getDestination().equalsIgnoreCase(destination);
            if (matchOrigin && matchDest) result.add(f);
        }
        return result;
    }
    public Reservation bookTicket(User user, int flightId, int seatId) {
        Flight flight = persistenceService.findFlightById(flightId);
        if (flight == null) {
            System.out.println("[TicketService] Flight not found.");
            return null;
        }
        if (Flight.STATUS_CANCELLED.equals(flight.getStatus())) {
            System.out.println("[TicketService] Flight is cancelled.");
            return null;
        }
        Seat seat = findSeatInFlight(flight, seatId);
        if (seat == null) {
            System.out.println("[TicketService] Seat not found on this flight.");
            return null;
        }
        if (!seat.isAvailable()) {
            System.out.println("[TicketService] Seat is already taken.");
            return null;
        }
        seat.setAvailable(false);
        Ticket      ticket      = new Ticket(persistenceService.nextTicketId(), user, flight, seat);
        Reservation reservation = new Reservation(persistenceService.nextReservationId(), user);
        reservation.addTicket(ticket);
        persistenceService.addReservation(reservation);
        return reservation;
    }
    public boolean cancelReservation(User user, int reservationId) {
        Reservation reservation = persistenceService.findReservationById(reservationId);
        if (reservation == null) {
            System.out.println("[TicketService] Reservation not found.");
            return false;
        }
        if (reservation.getUser().getId() != user.getId()) {
            System.out.println("[TicketService] Reservation does not belong to this user.");
            return false;
        }
        if (Reservation.STATUS_CANCELLED.equals(reservation.getStatus())) {
            System.out.println("[TicketService] Reservation is already cancelled.");
            return false;
        }
        for (Ticket t : reservation.getTickets()) {
            t.getSeat().setAvailable(true);
        }
        reservation.setStatus(Reservation.STATUS_CANCELLED);
        return true;
    }
    public List<Reservation> getMyReservations(User user) {
        return persistenceService.getReservationsForUser(user.getId());
    }
    private Seat findSeatInFlight(Flight flight, int seatId) {
        for (Seat s : flight.getSeats())
            if (s.getId() == seatId) return s;
        return null;
    }
}
