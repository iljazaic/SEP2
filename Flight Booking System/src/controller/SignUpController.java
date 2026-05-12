package controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController extends BaseController {

  @FXML private TextField nameField;
  @FXML private TextField emailField;
  @FXML private PasswordField passwordField;
  @FXML private PasswordField confirmPasswordField;

  @FXML
  private void initialize() {
    System.out.println("Sign Up screen loaded.");
  }

  @FXML
  private void createAccount() {
    String name = nameField.getText();
    String email = emailField.getText();
    String pass = passwordField.getText();
    String confirm = confirmPasswordField.getText();


    if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
      showWarning("Missing Information", "Please fill out all the fields.");
      return;
    }


    if (!pass.equals(confirm)) {
      showWarning("Password Error", "Your passwords do not match!");
      return;
    }

    System.out.println("SUCCESS! New account created for: " + name);


    goTo("login-view.fxml");
  }

  @FXML
  private void backToLogin() {
    goTo("login-view.fxml");
  }
}