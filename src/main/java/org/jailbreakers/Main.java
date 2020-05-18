package org.jailbreakers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jailbreakers.obj.Layout;
import org.jailbreakers.obj.StageHandler;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Platform.setImplicitExit(false);
        Font.loadFont(getClass().getResource("/style/Montserrat-Regular.ttf").toExternalForm(),20);
        StageHandler stageHandler = StageHandler.getInstance();
        stageHandler.setStage(primaryStage);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Diary - JailBreakers");
        stageHandler.setScene(Layout.SPLASH);
    }
}
