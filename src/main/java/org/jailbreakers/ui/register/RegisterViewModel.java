package org.jailbreakers.ui.register;

import javafx.beans.property.SimpleBooleanProperty;
import org.jailbreakers.obj.DatabaseController;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * <h1>ViewModel of {@link RegisterController} class handles most of logic in register view. Also operates with
 * {@link DatabaseController}.</h1>
 * ViewModel holds various of Properties which are data bound to views in {@link RegisterController} and
 * {@link DatabaseController} singleton instance.<br>
 * It has only one constructor and one method which is called from View.
 *
 * @see RegisterController
 * @see SimpleBooleanProperty
 * @see DatabaseController
 * @see SQLIntegrityConstraintViolationException
 * @author JailBreakersTeam (Matej Kandráč, Martin Ragan, Ján Kočíš)
 * @version 1.0
 * @since 1.6.2020
 */
public class RegisterViewModel {

    private DatabaseController databaseController;

    /**
     * Successful register is set to true when register is successfully completed.<br>
     * Email used is set to true if email entered was used.<br>
     * Register error is set to true if register fails to complete due connection error.<br>
     * Loading is set to true on start of async events and to false on their finish.
     */
    private SimpleBooleanProperty successfulRegister,
            emailUsed, registerError, loading;

    /**
     * Constructor which initializes instances of properties and instance of {@link DatabaseController}.
     */
    public RegisterViewModel() {
        loading = new SimpleBooleanProperty(false);
        registerError = new SimpleBooleanProperty();
        successfulRegister = new SimpleBooleanProperty();
        emailUsed = new SimpleBooleanProperty();
        databaseController = DatabaseController.getInstance();
    }

    /**
     * Passes email and password of user signing up to registration method.<br>
     * When user pick's email which is used {@link #emailUsed} property is set to true and registration cancelled.<br>
     * If sql connection fails {@link #registerError} property is set to true and and registration cancelled.<br>
     * Loading property corresponds to running task.<br>
     * {@link SQLException} and {@link SQLIntegrityConstraintViolationException} exceptions are handled here and their
     * catch sets appropriate property to notify View of error.<br>
     * If register successfully finishes, {@link #successfulRegister} property is set to true.
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

    /**
     * Getter for loading property.
     * @return property of loading.
     */

    public SimpleBooleanProperty loadingProperty() {
        return loading;
    }

    /**
     * Getter for email property.
     * @return property of email.
     */

    public SimpleBooleanProperty emailUsedProperty() {
        return emailUsed;
    }

    /**
     * Getter for successful register property.
     * @return property of successful register.
     */

    public SimpleBooleanProperty successfulRegisterProperty() {
        return successfulRegister;
    }

    /**
     * Getter for register error property.
     * @return property of register error.
     */

    public SimpleBooleanProperty registerErrorProperty() {
        return registerError;
    }
}
