package Model.StateModel.FlightStates;

import Model.Flight;

public interface FlightStateInterface {
    public Flight OnFetchFlight();
    public int OnFetchDelay();
    public void OnReceiveUpdate();
    public void OnPushNotification();
}
