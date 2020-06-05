package org.jailbreakers.obj;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jailbreakers.ui.datepicker.DatePickerController;

import java.io.IOException;

/**
 * <h1>Singleton class handling stage events as well as sharing listener of date picker.</h1>
 * Class holds an instance of itself. More about singleton classes can be read in DatabaseController JavaDoc.<br>
 * It also has instance of primary stage {@link #stage} and date picker event listener.<br>
 * @see Layout
 * @see org.jailbreakers.ui.stagetop.StageTopController
 * @see DatePickerController
 * @author JailBreakersTeam (Matej Kandráč, Martin Ragan, Ján Kočíš)
 * @version 1.0
 * @since 1.6.202
 */

public class StageHandler {

    private Stage stage;
    private static StageHandler instance;
    private DatePickerController.OnDatePickedListener onDatePickedListener;

    /**
     * Empty private constructor to ensure no duplicate instance creation
     */

    private StageHandler() {
    }

    /**
     * Setter for primary stage
     * @param stage is a primary stage of application.
     */

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Getter of singleton class instance.
     * @return instance of single class
     */

    public static StageHandler getInstance() {
        if (instance == null)
            instance = new StageHandler();
        return instance;
    }

    /**
     * Sets scene to desired layout.<br>
     * It loads scene via FXMLLoader and checks for id placeholders.<br>
     * If root is BorderPane, it looks for a child with an id of stageTop<br>
     * That child is a placeholder of StageTop so it will get replaced by {@link org.jailbreakers.ui.stagetop.StageTopController}.<br>
     * Same placeholder is for datepicker but it also is under parent with id left side.<br>
     * If datepicker placeholder is found, it will get replaced by {@link DatePickerController}.<br>
     * In the end layout is updated to stage.
     * @param layout is layout to be shown
     * @throws IOException if there is an error when loading layout
     */

    public void setScene(Layout layout) throws IOException {
        FXMLLoader loader = new FXMLLoader(layout.getResourceByLayout());
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

    /**
     *
     * @return property of stage width.
     */
    public ReadOnlyDoubleProperty getStageWidthProperty() {
        return stage.widthProperty();
    }

    /**
     * Getter of date selection listener.
     * @return listener of date selection
     */

    public DatePickerController.OnDatePickedListener getOnDatePickedListener() {
        return onDatePickedListener;
    }

    /**
     * Setter of date selection listener
     * @param onDatePickedListener listener of date selection
     */

    public void setOnDatePickedListener(DatePickerController.OnDatePickedListener onDatePickedListener) {
        this.onDatePickedListener = onDatePickedListener;
    }
}
