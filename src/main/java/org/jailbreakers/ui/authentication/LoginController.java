package org.jailbreakers.ui.authentication;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.jailbreakers.obj.Layout;
import org.jailbreakers.obj.StageHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable{


    @FXML
    private ImageView loadingGif;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXTextField usernameField;
    @FXML
    private Button loginButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginViewModel viewModel = new LoginViewModel();

        loginButton.setOnAction(event -> {
            try {
                StageHandler.getInstance().setScene(Layout.MAIN);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            viewModel.login(usernameField.getText(), passwordField.getText());
        });

        loadingGif.visibleProperty().bind(viewModel.loadingProperty());
    }
}
