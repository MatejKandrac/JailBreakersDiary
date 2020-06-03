package org.jailbreakers.ui.register;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.jailbreakers.obj.DatabaseController;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class RegisterViewModel {

    private DatabaseController databaseController;
    private SimpleBooleanProperty successfulRegister,
            emailUsed, registerError;

    public RegisterViewModel() {
        registerError = new SimpleBooleanProperty();
        successfulRegister = new SimpleBooleanProperty();
        emailUsed = new SimpleBooleanProperty();
        databaseController = DatabaseController.getInstance();
    }

    void register(String email, String pass) {
        System.out.println("REGISTERING");
        try {
            databaseController.register(email, pass);
            successfulRegister.setValue(true);
        } catch (SQLIntegrityConstraintViolationException exception) {
            emailUsed.setValue(true);
        } catch (SQLException exception) {
            registerError.setValue(true);
        }
    }

    public SimpleBooleanProperty emailUsedProperty() {
        return emailUsed;
    }

    public SimpleBooleanProperty successfulRegisterProperty() {
        return successfulRegister;
    }

    public SimpleBooleanProperty registerErrorProperty() {
        return registerError;
    }
}
