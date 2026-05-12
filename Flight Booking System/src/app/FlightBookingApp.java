package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FlightBookingApp extends Application {

  private static Stage stage;

  @Override
  public void start(Stage primaryStage) throws Exception {
    stage = primaryStage;
    stage.setTitle("FlyGo Flight Booking");
    stage.setResizable(false);


    setRoot("login-view.fxml");
    stage.show();
  }


  public static void setRoot(String fxmlFile) throws Exception {

    FXMLLoader loader = new FXMLLoader(FlightBookingApp.class.getResource("/resources/com/student/flygo/" + fxmlFile));


    Scene scene = new Scene(loader.load(), 393, 852);
    stage.setScene(scene);
  }


  public static void main(String[] args) {
    launch(args);
  }
}