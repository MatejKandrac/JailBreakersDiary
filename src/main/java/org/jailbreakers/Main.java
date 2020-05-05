package org.jailbreakers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Platform.setImplicitExit(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login_layout.fxml"));
        Scene sc = new Scene(loader.load());
        Font.loadFont(getClass().getResource("/style/Montserrat-Regular.ttf").toExternalForm(),20);
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.setScene(sc);
        primaryStage.setTitle("Diary - JailBreakers");
        primaryStage.show();
    }
}
