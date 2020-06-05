package org.jailbreakers.ui.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.jailbreakers.obj.Layout;
import org.jailbreakers.obj.StageHandler;
import org.jailbreakers.ui.datepicker.DatePickerController;
import org.jailbreakers.ui.dialog.AlertDialog;
import org.jailbreakers.ui.register.RegisterViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * <h1>View of {@link MainViewModel} which handles GUI in {@link Layout#MAIN} layout.</h1>
 * There is no database connection held since it is the job of ViewModel.<br>
 * Class implements {@link Initializable} interface from JavaFx package which allows initialization of view.<br>
 * It also implements {@link DatePickerController.OnDatePickedListener} which is listening for new date chosen.<br>
 *
 * @author JailBreakersTeam (Matej Kandráč, Martin Ragan, Ján Kočíš)
 * @version 1.0
 * @see RegisterViewModel
 * @see StageHandler
 * @see AlertDialog
 * @see Layout
 * @see Initializable
 * @since 1.6.2020
 */


public class MainController implements Initializable, DatePickerController.OnDatePickedListener {

    @FXML
    private Label dayText;

    @FXML
    private Label monthText;

    @FXML
    private JFXTextArea notesTextArea;

    @FXML
    private JFXButton save;

    /**
     * Currently selected date in format of YYYY-MM-dd
     */
    private String selectedDate;
    /**
     * Instance of ViewModel
     */
    private MainViewModel viewModel;

    /**
     * Method initializes events on views.<br>
     * It initializes instances of {@link MainViewModel} and {@link StageHandler} class.<br>
     * Also adds this class as a listener via {@link StageHandler#setOnDatePickedListener(DatePickerController.OnDatePickedListener)} method.
     * It is used for listening to {@link DatePickerController} date selected.<br>
     * Save button calls method in ViewModel to save currently written text if date is selected.<br>
     * If there is an error in database, {@link AlertDialog} is opened and observable value is reset.<br>
     */
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
            if (newValue) {
                AlertDialog dialog = new AlertDialog(save.getScene().getWindow());
                dialog.setTitle("Error");
                dialog.setMessage("Server could not be contacted.\nPlease try again later.");
                dialog.setNeutralButton("Close", (dialog1, button) -> dialog1.dismiss());
                dialog.show();
            }
            viewModel.databaseErrorProperty().setValue(false);
        });

    }

    /**
     * Method is called by {@link DatePickerController#onDaySelected(int)} method which lets you handle new date picked.<br>
     * It sets label text to selected day and shows month by name using {@link DatePickerController#monthNames} static constant array.<br>
     * Selected date is formatted and set as selected date.<br>
     * Method calls {@link MainViewModel#fetchNoteByDate(String, MainViewModel.NoteEventListener)} and sets fetched text to
     * notes text area.
     *
     * @param year is an int of selected year
     * @param month is an int of selected month in range from 0 to 11
     * @param day is an int of selected day
     */

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
