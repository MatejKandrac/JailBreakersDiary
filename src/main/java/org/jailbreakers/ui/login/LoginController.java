package org.jailbreakers.ui.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.jailbreakers.obj.DatabaseController;
import org.jailbreakers.obj.Layout;
import org.jailbreakers.obj.StageHandler;
import org.jailbreakers.ui.dialog.AlertDialog;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginController implements Initializable{

    @FXML
    private Button registerButton;
    @FXML
    private ImageView loadingGif;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXTextField usernameField;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginViewModel viewModel = new LoginViewModel();

        loginButton.setOnAction(event -> {
            usernameField.validate();
            passwordField.validate();
            if (usernameField.getActiveValidator() == null && passwordField.getActiveValidator() == null)
                viewModel.login(usernameField.getText(), passwordField.getText());
        });

        registerButton.setOnAction(event -> {
            try {
                StageHandler.getInstance().setScene(Layout.REGISTER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        viewModel.successfulLoginProperty().addListener((observable, oldValue, newValue) -> {
            StageHandler handler = StageHandler.getInstance();
            try {
                handler.setScene(Layout.MAIN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        viewModel.serverErrorProperty().addListener((observable, oldValue, newValue) -> {
            AlertDialog dialog = new AlertDialog(loginButton.getScene().getWindow());
            dialog.setTitle("Error");
            dialog.setMessage("We could not contact server.\nPlease try again later.");
            dialog.setNeutralButton("Close", (dialog1, button) -> dialog1.dismiss());
            dialog.show();
        });

        loadingGif.visibleProperty().bind(viewModel.loadingProperty());

        errorLabel.visibleProperty().bind(viewModel.loginErrorProperty());

    }
}
