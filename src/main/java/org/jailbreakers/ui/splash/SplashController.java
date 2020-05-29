package org.jailbreakers.ui.splash;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
    private Label connectingLabel;

    @FXML
    private Button skipButton;

    @FXML
    private HBox buttonsParent;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SplashViewModel viewModel = new SplashViewModel();

        setRetryVisible(false);

        connectingLabel.textProperty().bind(viewModel.connectionStatusProperty());

        viewModel.connectionStatusProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Successfully connected.")) {
                goToLogin();
            } else if (newValue.equals("Unable to connect. Try again later")) {
                setRetryVisible(true);
            }
        });

        retryButton.setOnAction(event -> {
            viewModel.connectToDatabase();
            setRetryVisible(false);
        });

        skipButton.setOnAction(event -> {
            viewModel.cancelConnection();
            goToLogin();
        });

        viewModel.connectToDatabase();
    }

    private void goToLogin() {
        try {
            StageHandler.getInstance().setScene(Layout.LOGIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setRetryVisible(boolean visibility) {
        if (visibility) {
            retryButton.setVisible(true);
            retryButton.setPrefWidth(85);
            buttonsParent.setSpacing(10);
        } else {
            retryButton.setVisible(false);
            retryButton.setPrefWidth(0);
            buttonsParent.setSpacing(0);
        }
    }

}
