package viewmodel;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Flight;
import service.TicketService;
import model.Seat;
import model.User;
import model.Reservation;

import java.util.List;

public class BookingViewModel {

  // The variables that the UI screen will bind to
  private final StringProperty fromCity = new SimpleStringProperty("Copenhagen");
  private final StringProperty toCity = new SimpleStringProperty("London");
  private final StringProperty email = new SimpleStringProperty("");
  private final ObjectProperty<Flight> selectedFlight = new SimpleObjectProperty<>();
  private final ObservableList<Flight> flightResults = FXCollections.observableArrayList();
  private final TicketService ticketService;


  public BookingViewModel() {
    this.ticketService = new TicketService();
  }


  public StringProperty fromCityProperty() { return fromCity; }
  public StringProperty toCityProperty() { return toCity; }
  public StringProperty emailProperty() { return email; }
  public ObservableList<Flight> getFlightResults() { return flightResults; }
  public ObjectProperty<Flight> selectedFlightProperty() { return selectedFlight; }

  private final ObjectProperty<Seat> selectedSeat = new SimpleObjectProperty<>();
  public ObjectProperty<Seat> selectedSeatProperty() { return selectedSeat; }


  public Reservation bookFinalTicket(User user) {
    Flight flight = selectedFlight.get();
    Seat seat = selectedSeat.get();

    if (flight != null && seat != null) {
      System.out.println("Booking ticket for Flight ID: " + flight.getId() + ", Seat: " + seat.getSeatNumber());
      return ticketService.bookTicket(user, flight.getId(), seat.getId());
    }
    return null;
  }

  public void searchForFlights() {
    System.out.println("Asking the backend for flights from " + fromCity.get() + " to " + toCity.get());


    List<Flight> results = ticketService.searchFlights(fromCity.get(), toCity.get());

    // Put the results into our JavaFX list so the screen updates
    flightResults.setAll(results);

    System.out.println("Found " + results.size() + " flights!");
  }

  public BooleanExpression departDateProperty()
  {
    return null;
  }
}