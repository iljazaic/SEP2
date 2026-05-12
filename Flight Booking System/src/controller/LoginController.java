package controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends BaseController {

  @FXML private TextField emailField;
  @FXML private PasswordField passwordField;

  @FXML
  private void initialize() {

    emailField.textProperty().bindBidirectional(vm.emailProperty());

    passwordField.setText("12345678");
  }

  @FXML
  private void login() {
    System.out.println("User logging in with email: " + vm.emailProperty().get());

    // This is the magic command to switch screens!
    goTo("home-view.fxml");
  }

  @FXML
  private void openSignUp() {
    goTo("signup-view.fxml");
  }
}