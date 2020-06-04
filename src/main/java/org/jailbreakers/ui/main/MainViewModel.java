package org.jailbreakers.ui.main;

import javafx.beans.property.SimpleBooleanProperty;
import org.jailbreakers.obj.DatabaseController;

import java.sql.SQLException;

public class MainViewModel {

    private DatabaseController databaseController;
    private SimpleBooleanProperty databaseError;

    public MainViewModel() {
        databaseError = new SimpleBooleanProperty();
        databaseController = DatabaseController.getInstance();
    }

    void saveNote(String content, String date){
        try {
            databaseController.updateNote(content, date);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    void fetchNoteByDate(String date, NoteEventListener listener){
        try {
            databaseController.fetchNoteByDate(date, listener::onNewNote);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public SimpleBooleanProperty databaseErrorProperty() {
        return databaseError;
    }

    interface NoteEventListener{
        void onNewNote(String note);
    }
}