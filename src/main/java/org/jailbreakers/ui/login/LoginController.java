package org.jailbreakers.ui.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.jailbreakers.obj.Layout;
import org.jailbreakers.obj.StageHandler;
import org.jailbreakers.ui.dialog.AlertDialog;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * <h1>View of {@link LoginViewModel} which handles GUI in {@link Layout#LOGIN} layout.</h1>
 * Class implements {@link Initializable} interface used for initializing view upon load.<br>
 * Values are bound via {@link javafx.beans.property.SimpleBooleanProperty} located in ViewModel.<br>
 *
 * @author JailBreakersTeam (Matej Kandráč, Martin Ragan, Ján Kočíš)
 * @version 1.0
 * @see LoginViewModel
 * @see StageHandler
 * @see AlertDialog
 * @see Layout
 * @see Initializable
 * @since 1.6.2020
 */

public class LoginController implements Initializable {

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


    /**
     * Initializes instances and events of view.<br>
     * {@link #loginButton} action event first validates every input and then calls {@link LoginViewModel#login} method with given credentials. <br>
     * {@link #registerButton} action event navigates user to registration layout using {@link StageHandler#setScene(Layout)} method. <br>
     * Upon successful login user is navigated to main layout using {@link StageHandler#setScene(Layout)} method.<br>
     * If there is an error, new {@link AlertDialog} is shown.<br>
     * Loading gif visibility is bound to loading property in ViewModel. Same goes for error label which is shown on error
     * of login.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginViewModel viewModel = new LoginViewModel();
        StageHandler handler = StageHandler.getInstance();
        loginButton.setOnAction(event -> {
            usernameField.validate();
            passwordField.validate();
            if (usernameField.getActiveValidator() == null && passwordField.getActiveValidator() == null)
                viewModel.login(usernameField.getText(), passwordField.getText());
        });

        registerButton.setOnAction(event -> {
            try {
                handler.setScene(Layout.REGISTER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        viewModel.successfulLoginProperty().addListener((observable, oldValue, newValue) -> {
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
