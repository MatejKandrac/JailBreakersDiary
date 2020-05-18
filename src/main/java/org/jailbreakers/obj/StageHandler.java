package org.jailbreakers.obj;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class StageHandler {

    private Stage stage;
    private static StageHandler instance;

    private StageHandler(){}

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public static StageHandler getInstance() {
        if (instance == null)
            instance = new StageHandler();
        return instance;
    }

    public void setScene(Layout layout) throws IOException {
        URL resPath = layout.getResourceByLayout();
        FXMLLoader loader = new FXMLLoader(resPath);
        Scene scene = new Scene(loader.load());
        if (scene.getRoot() instanceof BorderPane){
            BorderPane root = (BorderPane) scene.getRoot();
            for (int i = 0; i < root.getChildren().size(); i++) {
                if (root.getChildren().get(i).getId() != null){
                    if (root.getChildren().get(i).getId().equals("stageTop")){
                        FXMLLoader stageTop = new FXMLLoader(getClass().getResource("/fxml/stage_top.fxml"));
                        root.getChildren().set(i, stageTop.load());
                    }
                }
            }
        }
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void setStagePosition(double x, double y){
        stage.setX(x);
        stage.setY(y);
    }

    public void setMinimized(){
        stage.setIconified(true);
    }
}
