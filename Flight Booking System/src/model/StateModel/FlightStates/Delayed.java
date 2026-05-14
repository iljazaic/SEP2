package Model.StateModel.FlightStates;

import Model.Flight;

public class Delayed implements FlightStateInterface{

    @Override
    public Flight OnFetchFlight(){
        return null;
    }

    @Override
    public int OnFetchDelay(){
        return 0;
    }

    @Override
    public void OnReceiveUpdate(){
    }

    @Override
    public void OnPushNotification(){
    }

}