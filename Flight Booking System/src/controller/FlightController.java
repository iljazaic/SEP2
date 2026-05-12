package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Flight;
import java.time.format.DateTimeFormatter;

public class FlightController extends BaseController {

  @FXML private VBox flightsBox; // The container from flights-view.fxml
  @FXML private Label subtitleLabel;

  @FXML
  private void initialize() {
    // Set the subtitle text based on what the user typed on the Home screen
    subtitleLabel.setText(vm.fromCityProperty().get() + " to " + vm.toCityProperty().get());

    // We loop through the real flights the Logic Guy's backend found!
    for (Flight flight : vm.getFlightResults()) {
      flightsBox.getChildren().add(createFlightCard(flight));
    }
  }

  // This creates a visual "Card" for each real flight
  private VBox createFlightCard(Flight flight) {
    VBox card = new VBox(5);
    card.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 18;");

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    // Notice how we use the Logic Guy's exact methods here! (getOrigin, getDestination)
    Label route = new Label(flight.getOrigin() + " ➔ " + flight.getDestination());
    route.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

    Label times = new Label("Departs: " + flight.getDepartureTime().format(timeFormatter) +
        " | Arrives: " + flight.getArrivalTime().format(timeFormatter));
    times.setStyle("-fx-text-fill: #64748B;");

    Label status = new Label("Status: " + flight.getStatus());

    card.getChildren().addAll(route, times, status);

    // THE ACTION: When the user clicks this card, save it and go to the Seat Screen!
    card.setOnMouseClicked(event -> {
      vm.selectedFlightProperty().set(flight);
      System.out.println("User selected Flight ID: " + flight.getId());
      goTo("seat-view.fxml");
    });

    return card;
  }

  @FXML
  private void back() {
    goTo("home-view.fxml");
  }
}