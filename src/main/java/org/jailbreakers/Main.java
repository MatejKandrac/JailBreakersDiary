package org.jailbreakers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login_layout.fxml"));
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle("Diary - JailBreakers");
        primaryStage.show();
    }
}
