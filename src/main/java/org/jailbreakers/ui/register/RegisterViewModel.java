package org.jailbreakers.ui.register;

import javafx.beans.property.SimpleBooleanProperty;
import org.jailbreakers.obj.DatabaseController;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class RegisterViewModel {

    private DatabaseController databaseController;
    private SimpleBooleanProperty successfulRegister,
            emailUsed, registerError, loading;

    public RegisterViewModel() {
        loading = new SimpleBooleanProperty(false);
        registerError = new SimpleBooleanProperty();
        successfulRegister = new SimpleBooleanProperty();
        emailUsed = new SimpleBooleanProperty();
        databaseController = DatabaseController.getInstance();
    }

    /**
     * Passes email and password of user signing up to registration method.
     * When user pick's email that is used {@link #emailUsed} property is set to true and registration cancelled.
     * If sql connection fails {@link #registerError} property is set to true and and registration cancelled.
     *
     * @param email is email of new user trying to register
     * @param pass  is password of new user trying to register
     */

    void register(String email, String pass) {
        loading.setValue(true);
        try {
            databaseController.register(email, pass);
            successfulRegister.setValue(true);
        } catch (SQLIntegrityConstraintViolationException exception) {
            emailUsed.setValue(true);
        } catch (SQLException exception) {
            registerError.setValue(true);
        } finally {
            loading.setValue(false);
        }
    }

    public SimpleBooleanProperty loadingProperty() {
        return loading;
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
