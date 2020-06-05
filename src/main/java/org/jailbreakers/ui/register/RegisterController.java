package org.jailbreakers.ui.register;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.jailbreakers.obj.EmailUsedValidator;
import org.jailbreakers.obj.Layout;
import org.jailbreakers.obj.PasswordValidator;
import org.jailbreakers.obj.StageHandler;
import org.jailbreakers.ui.dialog.AlertDialog;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * <h1>View of {@link RegisterViewModel} which handles GUI in {@link Layout#REGISTER} layout.</h1>
 * There is no database connection held since it is the job of ViewModel.<br>
 * Class uses validators in FXML layout file and checks for problems.<br>
 * Validators automatically show on error. See the fields in FXML document of {@link Layout#REGISTER} layout.<br>
 * Class implements {@link Initializable} interface from JavaFx package which allows initialization of view.<br>
 *
 * @see RegisterViewModel
 * @see StageHandler
 * @see AlertDialog
 * @see Layout
 * @see Initializable
 * @author JailBreakersTeam (Matej Kandráč, Martin Ragan, Ján Kočíš)
 * @version 1.0
 * @since 1.6.2020
 */

public class RegisterController implements Initializable {
    @FXML
    private Button registerButton;
    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXPasswordField confirmPasswordField;
    @FXML
    private ImageView loadingGif;
    @FXML
    private Button backButton;

    /**
     * After initialization of class sets listeners on buttons.<br>
     * {@link #registerButton} action event first validates every input and then calls {@link RegisterViewModel#register} method with given credentials.<br>
     * {@link #backButton} action event navigates back to login screen.<br>
     * When error happens during registration {@link AlertDialog} dialog is created and displayed.<br>
     * When registration is successful user is navigated to {@link Layout#MAIN} layout.<br>
     * Observers bound to values in ViewModel are handled here.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RegisterViewModel viewModel = new RegisterViewModel();
        registerButton.setOnAction(event -> {
            usernameField.validate();
            PasswordValidator.passwordText = passwordField.getText();
            passwordField.validate();
            confirmPasswordField.validate();
            if (!noErrors())
                viewModel.register(usernameField.getText(), passwordField.getText());
        });

        backButton.setOnAction(event -> {
            try {
                StageHandler.getInstance().setScene(Layout.LOGIN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        loadingGif.visibleProperty().bind(viewModel.loadingProperty());

        viewModel.registerErrorProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                AlertDialog dialog = new AlertDialog(registerButton.getScene().getWindow());
                dialog.setNeutralButton("Close", (dialog1, button) -> dialog1.dismiss());
                dialog.setTitle("Error");
                dialog.setMessage("Something went wrong.\nPlease try again later.");
                dialog.show();
            }
            viewModel.registerErrorProperty().setValue(false);
        });

        viewModel.emailUsedProperty().addListener((observable, oldValue, newValue) -> {
            EmailUsedValidator.isEmailUsed = true;
            usernameField.validate();
        });

        viewModel.successfulRegisterProperty().addListener((observable, oldValue, newValue) -> {
            EmailUsedValidator.isEmailUsed = false;
            usernameField.validate();
            try {
                StageHandler.getInstance().setScene(Layout.MAIN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Method checks if there is any active validator on required fields and returns if there are errors.
     * @return true if there are no errors and vice versa.
     */

    private boolean noErrors() {
        return usernameField.getActiveValidator() != null ||
                passwordField.getActiveValidator() != null ||
                confirmPasswordField.getActiveValidator() != null;
    }
}
