package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Flight;
import model.Reservation;
import model.Seat;
import model.User;

public class ConfirmationController extends BaseController {

  @FXML private Label routeLabel, dateLabel, timeLabel, seatLabel, passengerLabel, totalLabel, flightLabel;

  @FXML
  private void initialize() {
    Flight f = vm.selectedFlightProperty().get();
    Seat s = vm.selectedSeatProperty().get();


    User currentUser = new User(99, "FlyGo Customer", vm.emailProperty().get(), "password", User.ROLE_CLIENT);


    Reservation res = vm.bookFinalTicket(currentUser);


    flightLabel.setText("Flight #" + f.getId());
    routeLabel.setText(f.getOrigin() + " ➔ " + f.getDestination());
    dateLabel.setText(f.getDepartureTime().toLocalDate().toString());
    timeLabel.setText(f.getDepartureTime().toLocalTime().toString());
    seatLabel.setText(s.getSeatNumber());
    passengerLabel.setText(vm.emailProperty().get());

    if (res != null) {
      totalLabel.setText("$" + String.format("%.2f", res.getTotalPrice()));
    }
  }

  @FXML
  private void backHome() {
    vm.selectedFlightProperty().set(null);
    vm.selectedSeatProperty().set(null);
    goTo("home-view.fxml");
  }
}