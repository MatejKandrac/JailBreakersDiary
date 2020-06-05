package org.jailbreakers.obj;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jailbreakers.ui.datepicker.DatePickerController;

import java.io.IOException;
import java.net.URL;

public class StageHandler {

    private Stage stage;
    private static StageHandler instance;
    private DatePickerController.OnDatePickedListener onDatePickedListener;

    private StageHandler() {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static StageHandler getInstance() {
        if (instance == null)
            instance = new StageHandler();
        return instance;
    }

    /**
     * <h1>Loads/sets given layout.</h1>
     * Checks if layout has root {@link BorderPane} and if so, replaces StageTop.
     *
     * @param layout is layout resource file path
     * @throws IOException
     */

    public void setScene(Layout layout) throws IOException {
        URL resPath = layout.getResourceByLayout();
        FXMLLoader loader = new FXMLLoader(resPath);
        Scene scene = new Scene(loader.load());
        if (scene.getRoot() instanceof BorderPane) {
            BorderPane root = (BorderPane) scene.getRoot();
            for (int i = 0; i < root.getChildren().size(); i++) {
                if (root.getChildren().get(i).getId() != null) {
                    if (root.getChildren().get(i).getId().equals("stageTop")) {
                        FXMLLoader stageTop = new FXMLLoader(Layout.STAGE_TOP.getResourceByLayout());
                        root.setTop(stageTop.load());
                    } else if (root.getChildren().get(i).getId().equals("leftSide")) {
                        VBox left = (VBox) root.getChildren().get(i);
                        for (int i1 = 0; i1 < left.getChildren().size(); i1++) {
                            if (left.getChildren().get(i1) != null)
                                if (left.getChildren().get(i1).getId().equals("datepicker")) {
                                    FXMLLoader datePicker = new FXMLLoader(Layout.DATE_PICKER.getResourceByLayout());
                                    left.getChildren().set(i1, datePicker.load());
                                }
                        }
                    }
                }
            }
        }
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * <h1>Sets stage's position when moved.</h1>
     *
     * @param x is x coordinate of stage
     * @param y is x coordinate of stage
     */

    public void setStagePosition(double x, double y) {
        stage.setX(x);
        stage.setY(y);
    }

    /**
     * <h1>Minimizes stage/window.</h1>
     */

    public void setMinimized() {
        stage.setIconified(true);
    }


    public ReadOnlyDoubleProperty getStageWidthProperty() {
        return stage.widthProperty();
    }

    public DatePickerController.OnDatePickedListener getOnDatePickedListener() {
        return onDatePickedListener;
    }

    public void setOnDatePickedListener(DatePickerController.OnDatePickedListener onDatePickedListener) {
        this.onDatePickedListener = onDatePickedListener;
    }
}
