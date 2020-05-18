package org.jailbreakers.ui.stagetop;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.jailbreakers.obj.StageHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class StageTopController implements Initializable {

    @FXML
    private AnchorPane stageTopParent;

    @FXML
    private FontAwesomeIconView minimize;

    @FXML
    private FontAwesomeIconView close;

    private double xOffset = 0, yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minimize.setOnMouseClicked(event -> StageHandler.getInstance().setMinimized());
        close.setOnMouseClicked(event -> System.exit(0));
        stageTopParent.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        stageTopParent.setOnMouseDragged(event ->
                StageHandler.getInstance().setStagePosition(
                        event.getScreenX()-xOffset,
                        event.getScreenY()-yOffset));
    }
}
