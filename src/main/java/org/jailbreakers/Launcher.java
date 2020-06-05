package org.jailbreakers;

/**
 * <h1>Launcher class starts main program flow.</h1>
 *
 * <p>Class is used as a workaround the bug in Javafx where JAR files wont start after build.<br>
 * {@link #main(String[])} is the only method which starts main program in class {@link Main#main(String[])}
 * </p>
 *
 * @author JailBreakersTeam (Matej Kandráč, Martin Ragan, Ján Kočíš)
 * @version 1.0
 * @since 1.6.2020
 *
 * @see Main
 */

public class Launcher {

    /**
     * This method runs {@link Main#main(String[])} method which starts Javafx application.
     * @param args arguments of application
     */

    public static void main(String[] args) {
        Main.main(args);
    }

}
