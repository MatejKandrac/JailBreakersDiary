package org.jailbreakers.ui.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.jailbreakers.obj.DatabaseController;
import org.jailbreakers.obj.StageHandler;
import org.jailbreakers.ui.datepicker.DatePickerController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable, DatePickerController.OnDatePickedListener {

    @FXML
    private Label dayText;

    @FXML
    private Label monthText;

    @FXML
    private JFXTextArea notesTextArea;

    @FXML
    private JFXButton save;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageHandler handler = StageHandler.getInstance();
        handler.setOnDatePickedListener(this);

        save.setOnAction(event -> {
            try {
                DatabaseController.getInstance().updateNotes(notesTextArea.getText());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });
    }

    @Override
    public void onDatePicked(int year, int month, int day) {
        System.out.println(year + " " + (month+1) + " " + day);
        dayText.setText(String.valueOf(day));
        monthText.setText(String.valueOf(DatePickerController.monthNames[month]));
    }
}
