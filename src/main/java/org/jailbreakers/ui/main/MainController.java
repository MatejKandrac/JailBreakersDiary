package org.jailbreakers.ui.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.jailbreakers.obj.StageHandler;
import org.jailbreakers.ui.datepicker.DatePickerController;
import org.jailbreakers.ui.dialog.AlertDialog;

import java.net.URL;
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

    private String selectedDate;
    private MainViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewModel = new MainViewModel();
        StageHandler handler = StageHandler.getInstance();
        handler.setOnDatePickedListener(this);

        save.setOnAction(event -> {
            if (selectedDate != null)
                viewModel.saveNote(notesTextArea.getText(), selectedDate);
        });

        viewModel.databaseErrorProperty().addListener((observable, oldValue, newValue) -> {
            AlertDialog dialog = new AlertDialog(save.getScene().getWindow());
            dialog.setTitle("Error");
            dialog.setMessage("Server could not be contacted.\nPlease try again later.");
            dialog.setNeutralButton("Close", (dialog1, button) -> dialog1.dismiss());
            dialog.show();
        });

    }

    @Override
    public void onDatePicked(int year, int month, int day) {
        dayText.setText(String.valueOf(day));
        monthText.setText(String.valueOf(DatePickerController.monthNames[month]));
        String monthFormat = (month % 12 + 1) < 10 ? ("0" + (month % 12 + 1)) : "" + (month % 12 + 1);
        String dayFormat = day < 10 ? ("0" + day) : ("" + day);
        selectedDate = year + "-" + monthFormat + "-" + dayFormat;
        viewModel.fetchNoteByDate(selectedDate, note -> notesTextArea.setText(note));
    }
}
