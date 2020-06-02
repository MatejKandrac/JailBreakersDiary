package org.jailbreakers;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jailbreakers.obj.Layout;
import org.jailbreakers.obj.StageHandler;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * <p><code>Main</code> class starts Javafx application and shows its first window.<br>
 * Main class ensures the window will be opened via {@link #start(Stage)}
 * method which is inherited from Javafx {@link Application} class.<br>
 * Class loads font which is used in .css files of application.<br>
 * {@link StageHandler} class is used to load and show the layout to user.
 * </p>
 * @author JailBreakersTeam (Matej Kandráč, Martin Ragan, Ján Kočíš)
 * @version 1.0
 * @since 1.6.2020
 *
 * @see Application
 * @see Font
 * @see Stage
 * @see StageHandler
 */

public class Main extends Application {

    /**
     * Method starts {@link Application#launch(String...)} method inherited from {@link Application} class.
     *
     * @param args are arguments of the application
     */

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Method inherited from {@link Application} class which is launched after new {@link Stage} was created.<br>
     * Method loads font from resources using {@link Font#loadFont(String, double)} method for usage in app and sets basic settings for primaryStage.<br>
     *
     * {@link StageHandler} class is used to load and show new layout to user by calling {@link StageHandler#setScene(Layout)} method.
     *
     * @param primaryStage is a window of application
     * @throws IOException is thrown when {@link StageHandler#setScene(Layout)} fails to load a FXML resource.
     * @throws URISyntaxException is thrown when icon of application fails to load.
     */

    @Override
    public void start(Stage primaryStage) throws IOException, URISyntaxException {
        Font.loadFont(getClass().getResource("/style/Montserrat-Regular.ttf").toExternalForm(),20);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Diary - JailBreakers");
        primaryStage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toURI().toString()));
        StageHandler stageHandler = StageHandler.getInstance();
        stageHandler.setStage(primaryStage);
        stageHandler.setScene(Layout.SPLASH);
    }
}
