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

    private double xOffset = 0, yOffset = 0;
    private boolean ignoreMove = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageHandler stageHandler = StageHandler.getInstance();
        minimize.addEventFilter(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_CLICKED){
                StageHandler.getInstance().setMinimized();
            }
            else if (event.getEventType() == MouseEvent.MOUSE_PRESSED)
                ignoreMove = true;
            else if (event.getEventType() == MouseEvent.MOUSE_RELEASED)
                ignoreMove = false;
        });
        close.addEventFilter(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_CLICKED){
                System.exit(0);
            }
            else if (event.getEventType() == MouseEvent.MOUSE_PRESSED)
                ignoreMove = true;
            else if (event.getEventType() == MouseEvent.MOUSE_RELEASED)
                ignoreMove = false;
        });
        stageTopParent.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        stageTopParent.setOnMouseDragged(event -> {
            if (!ignoreMove)
                stageHandler.setStagePosition(
                        event.getScreenX()-xOffset,
                        event.getScreenY()-yOffset);
        });
        stageHandler.getStageWidthProperty().addListener((observable, oldValue, newValue) ->
                space.setPrefWidth(newValue.doubleValue() - windowEventParent.getWidth() - titleParent.getWidth() - 30));
        windowEventParent.widthProperty().addListener((observable, oldValue, newValue) ->
                space.setPrefWidth(stageHandler.getStageWidthProperty().get() - windowEventParent.getWidth() - titleParent.getWidth() - 30));

    }
}
