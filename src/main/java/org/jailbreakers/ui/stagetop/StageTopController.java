package org.jailbreakers.ui.stagetop;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import org.jailbreakers.obj.StageHandler;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * <h1>Handles input on window's top bar.</h1>
 * When {@link #minimize} button is clicked window minimizes.
 * When {@link #close} button is clicked application is closed/exited.
 * When user drags by {@link #stageTopParent} pane whole window is being moved by mouse.
 * This view does not need ViewModel because it doesnt need connection to database.
 * Works with {@link StageHandler} class to handle stage events.
 *
 * @author JailBreakersTeam (Matej Kandráč, Martin Ragan, Ján Kočíš)
 * @version 1.0
 * @see StageHandler
 * @since 29.5.2020
 */

public class StageTopController implements Initializable {

    @FXML
    private HBox titleParent;

    @FXML
    private HBox windowEventParent;

    @FXML
    private Region space;

    @FXML
    private AnchorPane stageTopParent;

    @FXML
    private FontAwesomeIconView minimize;

    @FXML
    private FontAwesomeIconView close;

    /**
     * These variables store position clicked in window.
     */
    private double xOffset = 0, yOffset = 0;
    /**
     * Block moving stage if minimize or close button was pressed.
     */
    private boolean ignoreMove = false;

    /**
     * Initialize method with all event setting for views.
     * Notice that {@link StageHandler} class instance is used to handle moving events.
     * <br>
     * {@link #minimize} button minimizes the stage and block moving of stage if pressed and unlocks it upon release.
     * <br>
     * {@link #close} does the same as minimize except its function is to close the app.
     * <br>
     * {@link #stageTopParent} represents top of {@link javafx.stage.Stage}. It saves position upon press to handle
     * stage moving from spot it was originally pressed. If its dragged and {@link #ignoreMove} variable is not active,
     * it moves the Stage to position of the mouse using {@link #xOffset} and {@link #yOffset}.
     * <br>
     * It also changes size of space between {@link #titleParent} and {@link #windowEventParent} based on actual size
     * of window using observable {@link StageHandler#getStageWidthProperty()}.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageHandler stageHandler = StageHandler.getInstance();
        minimize.addEventFilter(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                stageHandler.setMinimized();
            } else if (event.getEventType() == MouseEvent.MOUSE_PRESSED)
                ignoreMove = true;
            else if (event.getEventType() == MouseEvent.MOUSE_RELEASED)
                ignoreMove = false;
        });
        close.addEventFilter(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                System.exit(0);
            } else if (event.getEventType() == MouseEvent.MOUSE_PRESSED)
                ignoreMove = true;
            else if (event.getEventType() == MouseEvent.MOUSE_RELEASED)
                ignoreMove = false;
        });
        stageTopParent.addEventFilter(MouseEvent.ANY, event -> {
            if(event.getEventType() == MouseEvent.MOUSE_PRESSED){
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
            else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED){
                if (!ignoreMove)
                    stageHandler.setStagePosition(
                            event.getScreenX() - xOffset,
                            event.getScreenY() - yOffset);
            }
        });
        stageHandler.getStageWidthProperty().addListener((observable, oldValue, newValue) ->
                space.setPrefWidth(newValue.doubleValue() - windowEventParent.getWidth() - titleParent.getWidth() - 30));
        windowEventParent.widthProperty().addListener((observable, oldValue, newValue) ->
                space.setPrefWidth(stageHandler.getStageWidthProperty().get() - windowEventParent.getWidth() - titleParent.getWidth() - 30));
    }
}
