package org.jailbreakers.ui.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import org.jailbreakers.obj.DatabaseController;
import org.jailbreakers.obj.StageHandler;
import org.jailbreakers.ui.datepicker.DatePickerController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable, DatePickerController.OnDatePickedListener {
    @FXML
    private Label dayLabel;

    @FXML
    private Label monthLabel;

    @FXML
    private JFXTextArea notesTextArea;

    @FXML
    private JFXButton saveButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageHandler handler = StageHandler.getInstance();
        handler.setOnDatePickedListener(this);

        String notesData = DatabaseController.getInstance().user.getNote().getContent();
        if(notesData != null){
            notesTextArea.setText(notesData);
        }
        else{
            notesTextArea.setPromptText("How was your day?");
        }
        saveButton.setOnAction(event -> {
            try {
                DatabaseController.getInstance().updateNotes(notesTextArea.getText().toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //Pocuj... mna klepne zo sama
            //Sme pisali v appke ckj klepne ma... Aleee potom toto napisal ta dpc.... FAkt ma nastal ja to vidim len neviem co mam napisat
            // lebo ja mam mrtvicu prave Ne... Ckj napisal vela sprav idem pozret

        });
    }

    @Override
    public void onDatePicked(int year, int month, int day) {// tak reku pridam ho? jop... zatial len tak aby bolo nemusis sa hrat aby to bolo pekne
        System.out.println(year + " " + (month+1) + " " + day); //nwm preco mesiace zacinaju od 0... no a tu mas nachystane pocuj Matej pozri si mail od bajuzsa ta vypne
    }
}
