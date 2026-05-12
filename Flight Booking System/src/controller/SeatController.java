package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Flight;
import model.Seat;

public class SeatController extends BaseController {

  @FXML private Label flightLabel;
  @FXML private Label routeLabel;
  @FXML private Label selectedSeatLabel;
  @FXML private GridPane seatGrid;

  @FXML
  private void initialize() {
    Flight f = vm.selectedFlightProperty().get();
    if (f == null) return;

    routeLabel.setText(f.getOrigin() + " ➔ " + f.getDestination());
    flightLabel.setText("Flight ID: " + f.getId());


    int row = 0; int col = 0;
    for (Seat seat : f.getSeats()) {
      Button seatBtn = new Button(seat.getSeatNumber());
      seatBtn.setPrefSize(50, 40);

      if (!seat.isAvailable()) {
        seatBtn.setDisable(true);
        seatBtn.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-weight: bold;"); // Red for taken
      } else {
        seatBtn.setStyle("-fx-background-color: #e2e8f0; -fx-text-fill: black; -fx-font-weight: bold;"); // Gray for available


        seatBtn.setOnAction(e -> {
          vm.selectedSeatProperty().set(seat);
          selectedSeatLabel.setText("Selected: " + seat.getSeatNumber() + " - $" + seat.getPrice());
        });
      }

      seatGrid.add(seatBtn, col, row);
      col++;
      if (col > 3) { col = 0; row++; } // 4 seats per row
    }
  }

  @FXML
  private void continueToSummary() {
    if (vm.selectedSeatProperty().get() != null) {
      goTo("summary-view.fxml");
    } else {
      showWarning("Select a Seat", "Please click on an available seat to continue.");
    }
  }

  @FXML
  private void back() {
    goTo("flights-view.fxml");
  }
}