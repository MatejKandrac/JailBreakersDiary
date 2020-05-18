package org.jailbreakers.ui.splash;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
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
    private ImageView loadingGif;

    @FXML
    private HBox buttonsParent;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SplashViewModel viewModel = new SplashViewModel();

        setRetryVisible(false);

        connectingLabel.textProperty().bind(viewModel.connectionStatusProperty());

        viewModel.connectionStatusProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Successfully connected.")){
                PauseTransition transition = new PauseTransition(Duration.seconds(1));
                transition.setOnFinished(event -> {
                    try {
                        StageHandler.getInstance().setScene(Layout.LOGIN);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                transition.play();
            }
        });
        viewModel.connectToDatabase();
    }

    void setRetryVisible(boolean visibility){
        if (visibility){
            retryButton.setVisible(true);
            retryButton.setPrefWidth(85);
            buttonsParent.setSpacing(10);
        }
        else {
            retryButton.setVisible(false);
            retryButton.setPrefWidth(0);
            buttonsParent.setSpacing(0);
        }
    }

}
