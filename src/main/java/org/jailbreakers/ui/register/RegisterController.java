package org.jailbreakers.ui.register;

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
        registerButton.setOnAction(event -> viewModel.register(usernameField.getText(), passwordField.getText(), confirmPasswordField.getText()));
        backButton.setOnAction(event -> {
            try {
                StageHandler.getInstance().setScene(Layout.LOGIN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        viewModel.registerErrorProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });
    }
}
