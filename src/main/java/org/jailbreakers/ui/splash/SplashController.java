package org.jailbreakers.ui.splash;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.jailbreakers.obj.Layout;
import org.jailbreakers.obj.StageHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * <h1>View of {@link SplashViewModel} which handles GUI in {@link Layout#LOGIN} layout.</h1>
 * It does not operate with database since it is the job of ViewModel, but handles simple tasks and calls
 * corresponding method in ViewModel.<br>
 * Class implements {@link Initializable} interface from JavaFx package which allows initialization of view.<br>
 *
 * @see SplashController
 * @see StageHandler
 * @see Layout
 * @see Initializable
 * @author JailBreakersTeam (Matej Kandráč, Martin Ragan, Ján Kočíš)
 * @version 1.0
 * @since 1.6.2020
 */

public class SplashController implements Initializable {


    @FXML
    private Button retryButton;

    @FXML
    private Button closeButton;

    @FXML
    private HBox buttonsParent;

    @FXML
    private Label connectingLabel;

    /**
     * Initialize method for View.<br>
     * {@link SplashViewModel} is initialized here.<br>
     * {@link #connectingLabel} is bound to value in ViewModel which shows the current status of connection to user.<br>
     * {@link #retryButton} upon press calls corresponding method in ViewModel and hides buttons.<br>
     * {@link #closeButton} upon press calls corresponding method in ViewModel and exits the app.<br>
     * {@link SplashViewModel#connectionStatusProperty()} is observed and if it is successful, switches window to
     * {@link Layout#LOGIN} via {@link StageHandler} class.<br>
     * It starts connecting to database instantaneously upon View creation.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SplashViewModel viewModel = new SplashViewModel();

        connectingLabel.textProperty().bind(viewModel.connectionStatusProperty());

        retryButton.setOnAction(event -> {
            viewModel.connectToDatabase();
            buttonsParent.setVisible(false);
        });

        closeButton.setOnAction(event -> {
            viewModel.cancelConnection();
            System.exit(0);
        });

        viewModel.connectionStatusProperty().addListener((observable, oldValue, newValue) -> {
            connectingLabel.textProperty().bind(observable);
            if (newValue.equals("Successfully connected.")) {
                goToLogin();
            } else if (newValue.equals("Unable to connect. Try again later")) {
                buttonsParent.setVisible(true);
            }
        });

        viewModel.connectToDatabase();
    }

    /**
     * Changes window to {@link Layout#LOGIN} using {@link StageHandler}.<br>
     * It waits for one second so user can see last View label update using {@link PauseTransition} to avoid invalid
     * thread changes.<br>
     * On finish of transition it calls method {@link StageHandler#setScene(Layout)} to change window.
     */

    private void goToLogin() {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> {
            try {
                StageHandler.getInstance().setScene(Layout.LOGIN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delay.play();
    }

}
