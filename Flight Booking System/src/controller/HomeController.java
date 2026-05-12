package controller;

import javafx.fxml.FXML;

public class HomeController extends BaseController {


  @FXML
  private void initialize() {
    System.out.println("The Home Screen successfully loaded!");
  }


  @FXML
  private void backToLogin() {
    goTo("login-view.fxml");
  }


  @FXML
  private void searchFlights() {
    System.out.println("User clicked search! Asking the backend for data...");

    vm.searchForFlights();

    goTo("flights-view.fxml");
  }
}