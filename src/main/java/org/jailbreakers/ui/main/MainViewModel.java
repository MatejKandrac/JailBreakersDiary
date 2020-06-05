package org.jailbreakers.ui.main;

import javafx.beans.property.SimpleBooleanProperty;
import org.jailbreakers.obj.DatabaseController;
import org.jailbreakers.ui.register.RegisterController;

import java.sql.SQLException;

/**
 * <h1>ViewModel of {@link RegisterController} class handles most of logic in register window. Also operates with
 * {@link DatabaseController}.</h1>
 * Only property held here is {@link #databaseError} property representing error in database update or fetch.<br>
 * There is also singleton instance of {@link DatabaseController}.<br>
 * Method {@link #saveNote(String, String)} saves note to database and {@link #fetchNoteByDate(String, NoteEventListener)}
 * method downloads note from database by date.<br>
 * Integrated interface {@link NoteEventListener} is used for returning downloaded note from {@link DatabaseController}.
 *
 * @author JailBreakersTeam (Matej Kandráč, Martin Ragan, Ján Kočíš)
 * @version 1.0
 * @since 1.6.2020
 */
public class MainViewModel {

    private DatabaseController databaseController;
    private SimpleBooleanProperty databaseError;

    /**
     * Constructor off class which initializes instances of properties and {@link DatabaseController} class.
     */

    public MainViewModel() {
        databaseError = new SimpleBooleanProperty();
        databaseController = DatabaseController.getInstance();
    }

    /**
     * Calls {@link DatabaseController#updateNote(String, String)} method that saves or updates user's notes.
     *
     * @param content is text of note itself
     * @param date    is date that note is being saved at
     */

    void saveNote(String content, String date) {
        try {
            databaseController.updateNote(content, date);
        } catch (SQLException exception) {
            databaseError.setValue(true);
        }
    }

    /**
     *
     * @param date      is date of note you want to download
     * @param listener  is listener and method {@link NoteEventListener#onNewNote(String)} is used for getting downloaded note.
     */

    void fetchNoteByDate(String date, NoteEventListener listener) {
        try {
            databaseController.fetchNoteByDate(date, listener::onNewNote);
        } catch (SQLException exception) {
            databaseError.setValue(true);
        }
    }

    /**
     * Getter of database error property
     * @return property of failed database event
     */

    public SimpleBooleanProperty databaseErrorProperty() {
        return databaseError;
    }

    /**
     * Interface used for acquiring new downloaded note.
     */

    interface NoteEventListener {
        void onNewNote(String note);
    }
}