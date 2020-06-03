package org.jailbreakers.ui.dialog;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.jailbreakers.obj.Layout;

import java.io.IOException;

public class AlertDialog extends VBox{
    @FXML
    private Label title;

    @FXML
    private Label message;

    @FXML
    private JFXButton neutralButton;

    @FXML
    private JFXButton negativeButton;

    @FXML
    private JFXButton positiveButton;
    private Stage windowStage;
    private Stage ownerStage;

    private static final int NEUTRAL_BUTTON = 0, POSITIVE_BUTTON = 1, NEGATIVE_BUTTON = 2;

    public AlertDialog(Window window){
        this.ownerStage = (Stage) window;
        windowStage = new Stage();
        windowStage.initModality(Modality.WINDOW_MODAL);
        windowStage.initOwner(window);
        windowStage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Layout.DIALOG.getResourceByLayout());
        try {
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
            Scene scene = new Scene(this);
            windowStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show(){
        ownerStage.setOpacity(0.9);
        windowStage.show();
    }

    public void dismiss(){
        ownerStage.setOpacity(1);
        windowStage.close();
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setMessage(String message){
        this.message.setText(message);
    }

    public void setNeutralButton(String text, DialogInterface dialogInterface){
        negativeButton.setPrefWidth(Control.USE_COMPUTED_SIZE);
        neutralButton.setVisible(true);
        neutralButton.setText(text);
        neutralButton.setOnAction(event -> dialogInterface.onDialogButtonClick(this, NEUTRAL_BUTTON));
    }

    public void setPositiveButton(String text, DialogInterface dialogInterface){
        negativeButton.setPrefWidth(Control.USE_COMPUTED_SIZE);
        positiveButton.setVisible(true);
        positiveButton.setText(text);
        positiveButton.setOnAction(event -> dialogInterface.onDialogButtonClick(this, POSITIVE_BUTTON));
    }

    public void setNegativeButton(String text, DialogInterface dialogInterface){
        negativeButton.setPrefWidth(Control.USE_COMPUTED_SIZE);
        negativeButton.setVisible(true);
        negativeButton.setText(text);
        negativeButton.setOnAction(event -> dialogInterface.onDialogButtonClick(this, NEGATIVE_BUTTON));
    }

    public interface DialogInterface{
        void onDialogButtonClick(AlertDialog dialog, int button);
    }
}