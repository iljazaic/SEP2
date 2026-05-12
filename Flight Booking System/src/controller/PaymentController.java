package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PaymentController extends BaseController {

  @FXML private Label cardNameLabel;

  @FXML
  private void initialize() {

    cardNameLabel.setText(vm.emailProperty().get());
  }

  @FXML
  private void pay() {
    goTo("confirmation-view.fxml");
  }

  @FXML
  private void back() {
    goTo("summary-view.fxml");
  }
}