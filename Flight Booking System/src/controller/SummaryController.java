package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Flight;
import model.Seat;

public class SummaryController extends BaseController {

  @FXML private Label flightLabel, routeLabel, dateLabel, seatLabel, baseFareLabel, taxesLabel, totalLabel;

  @FXML
  private void initialize() {
    Flight f = vm.selectedFlightProperty().get();
    Seat s = vm.selectedSeatProperty().get();
    if (f == null || s == null) return;

    flightLabel.setText("Flight #" + f.getId());
    routeLabel.setText(f.getOrigin() + " ➔ " + f.getDestination());
    dateLabel.setText(f.getDepartureTime().toLocalDate().toString());
    seatLabel.setText(s.getSeatNumber() + " (" + s.getSeatClass() + ")");

    double fare = s.getPrice();
    double tax = fare * 0.15;

    baseFareLabel.setText("$" + String.format("%.2f", fare));
    taxesLabel.setText("$" + String.format("%.2f", tax));
    totalLabel.setText("$" + String.format("%.2f", fare + tax));
  }

  @FXML
  private void continueToPayment() { goTo("payment-view.fxml"); }

  @FXML
  private void back() { goTo("seat-view.fxml"); }
}