package org.jailbreakers.ui.splash;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import org.jailbreakers.obj.ConnectionEvent;
import org.jailbreakers.obj.DatabaseController;

/**
 * <h1>ViewModel of {@link SplashController} class operates with {@link DatabaseController} to handle logic of View.</h1>
 * Methods with database are {@link #connectToDatabase()} and {@link #cancelConnection()}. These methods call
 * corresponding method in {@link DatabaseController} to ensure single connection to database.<br>
 * Data binding variable is only {@link #connectionStatus} which is connected directly to {@link SplashController} label.
 * <br>
 *
 * @see DatabaseController
 * @see SplashController
 * @see SimpleStringProperty
 * @author JailBreakersTeam (Matej Kandráč, Martin Ragan, Ján Kočíš)
 * @version 1.0
 * @since 1.6.2020
 */

public class SplashViewModel {

    private SimpleStringProperty connectionStatus;
    private DatabaseController controller;

    /**
     * Constructor of class which initializes data binding variable and instance of {@link DatabaseController}
     */
    SplashViewModel() {
        controller = DatabaseController.getInstance();
        connectionStatus = new SimpleStringProperty();
    }

    /**
     * Asynchronous method which connects to database.
     * It sets just the data to variable {@link #connectionStatus} and {@link SplashController} handles what to do.
     */

    void connectToDatabase() {
        connectionStatus.setValue("Connecting...");
        controller.connect(new ConnectionEvent() {
            @Override
            public void onConnect() {
                Platform.runLater(() -> connectionStatus.setValue("Successfully connected."));
            }

            @Override
            public void onConnectionError(String message) {
                Platform.runLater(() -> connectionStatus.setValue("Unable to connect. Try again later"));
            }
        });
    }

    /**
     * Method cancels connecting to database.
     * It calls method {@link DatabaseController#abortConnection()} which cancels current connection if it exists.
     */

    void cancelConnection() {
        controller.abortConnection();
    }

    /**
     * Getter for data binding variable.
     * @return String property of connection status
     */

    public SimpleStringProperty connectionStatusProperty() {
        return connectionStatus;
    }
}
