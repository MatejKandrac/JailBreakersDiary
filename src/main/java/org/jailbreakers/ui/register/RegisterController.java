package org.jailbreakers.ui.register;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RegisterViewModel viewModel = new RegisterViewModel();
        loadingGif.setVisible(false);
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

        viewModel.registerErrorProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
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
            System.out.println("Register successful");
            try {
                StageHandler.getInstance().setScene(Layout.MAIN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private boolean noErrors(){
        return usernameField.getActiveValidator() != null ||
                passwordField.getActiveValidator() != null ||
                confirmPasswordField.getActiveValidator() != null;
    }
}
