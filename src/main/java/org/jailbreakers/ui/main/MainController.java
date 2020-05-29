package org.jailbreakers.ui.main;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Label dayLabel;

    @FXML
    private Label monthLabel;

    @FXML
    private JFXTextArea notesTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
