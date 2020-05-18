package org.jailbreakers.ui.authentication;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable{


    @FXML
    private Button loginButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginViewModel viewModel = new LoginViewModel();
        loginButton.setOnAction(event -> System.out.println("LOGIN"));
    }
}
