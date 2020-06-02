package org.jailbreakers.ui.register;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.jailbreakers.obj.DatabaseController;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class RegisterViewModel {

    private DatabaseController databaseController;
    private SimpleStringProperty registerError;
    private SimpleBooleanProperty successfulRegister,
            emailUsed;

    public RegisterViewModel() {
        registerError = new SimpleStringProperty();
        successfulRegister = new SimpleBooleanProperty();
        emailUsed = new SimpleBooleanProperty();
        databaseController = DatabaseController.getInstance();
    }

    void register(String email, String pass) {
        try {
            databaseController.register(email, pass);
            successfulRegister.setValue(true);
        } catch (SQLIntegrityConstraintViolationException exception) {
            emailUsed.setValue(true);
        } catch (CommunicationsException exception) {
            registerError.setValue("Error connecting to server");
        } catch (SQLException exception) {
            registerError.setValue("Something went wrong");
            exception.printStackTrace();
        }
    }

    public SimpleBooleanProperty emailUsedProperty() {
        return emailUsed;
    }

    public SimpleBooleanProperty successfulRegisterProperty() {
        return successfulRegister;
    }

    public SimpleStringProperty registerErrorProperty() {
        return registerError;
    }
}
