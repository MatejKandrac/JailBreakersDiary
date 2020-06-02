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

public class SplashController implements Initializable {


    @FXML
    private Button retryButton;

    @FXML
    private Button closeButton;

    @FXML
    private HBox buttonsParent;

    @FXML
    private Label connectingLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SplashViewModel viewModel = new SplashViewModel();

        buttonsParent.setVisible(false);

        connectingLabel.textProperty().bind(viewModel.connectionStatusProperty());

        viewModel.connectionStatusProperty().addListener((observable, oldValue, newValue) -> {
            connectingLabel.textProperty().bind(observable);
            if (newValue.equals("Successfully connected.")) {
                goToLogin();
            } else if (newValue.equals("Unable to connect. Try again later")) {
                buttonsParent.setVisible(true);
            }
        });

        retryButton.setOnAction(event -> {
            viewModel.connectToDatabase();
            buttonsParent.setVisible(false);
        });

        closeButton.setOnAction(event -> System.exit(0));

        viewModel.connectToDatabase();
    }

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
