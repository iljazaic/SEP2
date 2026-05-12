package controller;

import app.FlightBookingApp;
import javafx.scene.control.Alert;
import viewmodel.BookingViewModel;

public abstract class BaseController {


  protected static final BookingViewModel vm = new BookingViewModel();

  protected void goTo(String fxmlFile) {
    try {
      // This calls the screen-switcher we built in app.FlightBookingApp!
      FlightBookingApp.setRoot(fxmlFile);
    } catch (Exception e) {
      e.printStackTrace();
      showWarning("Navigation Error", "Could not load screen: " + fxmlFile);
    }
  }

  protected void showWarning(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(title);
    alert.setContentText(message);
    alert.showAndWait();
  }
}