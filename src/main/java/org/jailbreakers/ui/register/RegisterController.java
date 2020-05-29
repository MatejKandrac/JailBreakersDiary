package org.jailbreakers.ui.register;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private ImageView loadingGif;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadingGif.setVisible(false);
    }
}
