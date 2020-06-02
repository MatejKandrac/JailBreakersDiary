package org.jailbreakers.ui.splash;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.jailbreakers.obj.ConnectionEvent;
import org.jailbreakers.obj.DatabaseController;

public class SplashViewModel {

    private SimpleStringProperty connectionStatus;
    private SimpleBooleanProperty requestSuccessfulWithLogin;
    private SimpleBooleanProperty connectionError;

    private DatabaseController controller;

    SplashViewModel(){
        controller = DatabaseController.getInstance();
        connectionStatus = new SimpleStringProperty();
        requestSuccessfulWithLogin = new SimpleBooleanProperty();
        connectionError = new SimpleBooleanProperty();
    }

    void connectToDatabase(){
        connectionStatus.setValue("Connecting...");
        controller.connect(new ConnectionEvent() {
            @Override
            public void onConnect() {
                Platform.runLater(() -> {
                    connectionStatus.setValue("Successfully connected.");

                });
            }

            @Override
            public void onConnectionError(String message) {
                Platform.runLater(() -> connectionStatus.setValue("Unable to connect. Try again later"));
            }
        });
    }

    void cancelConnection(){
        controller.abortConnection();
    }

    public SimpleStringProperty connectionStatusProperty() {
        return connectionStatus;
    }
}
