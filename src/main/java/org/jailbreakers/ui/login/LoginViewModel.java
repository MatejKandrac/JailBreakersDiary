package org.jailbreakers.ui.login;

import javafx.beans.property.SimpleBooleanProperty;
import org.jailbreakers.obj.DatabaseController;
import org.jailbreakers.ui.register.RegisterController;

import java.sql.SQLException;

/**
 * <h1>ViewModel of {@link LoginController} class handles most of logic in login window. Also operates with
 * {@link DatabaseController}.</h1>
 * Holds various of properties bound to View.<br>
 * {@link #loading} indicates async task running, {@link #loginError} is property used for notifying that user was
 * not found in database, {@link #serverError} represents error connecting to server and {@link #successfulLogin}
 * is successful login request.<br>
 * Class also has instance of {@link DatabaseController} for handling database events.<br>
 *
 * @see DatabaseController
 * @see LoginController
 * @see SimpleBooleanProperty
 * @author JailBreakersTeam (Matej Kandráč, Martin Ragan, Ján Kočíš)
 * @version 1.0
 * @since 1.6.202
 */

public class LoginViewModel {

    private DatabaseController controller;
    private SimpleBooleanProperty loading, loginError, serverError, successfulLogin;

    /**
     * Constructor of class initializing instances of properties and {@link DatabaseController} instance.<br>
     * Loading default value is set to false.
     */

    LoginViewModel(){
        controller = DatabaseController.getInstance();
        serverError = new SimpleBooleanProperty();
        loginError = new SimpleBooleanProperty();
        successfulLogin = new SimpleBooleanProperty();
        loading = new SimpleBooleanProperty(false);
    }

    /**
     * Method tries to log user in and checks for errors during login. <br>
     * Since method is asynchronous, loading is shown to user while server is contacted. <br>
     * Calls {@link DatabaseController#login(String, String)} method which contacts database.<br>
     * After logging in, loading is set to false.
     *
     * @param email is an email of user trying to log in
     * @param password is a password of user trying to log in
     */

    void login(String email, String password){
        loading.setValue(true);
        try {
            controller.login(email, password);
            successfulLogin.setValue(true);
        } catch (IllegalStateException exception){
            loginError.setValue(true);
        } catch (SQLException exception){
            serverError.setValue(true);
        } finally {
            loading.setValue(false);
        }
    }

    /**
     * Getter for loading property
     * @return loading property
     */

    public SimpleBooleanProperty loadingProperty() {
        return loading;
    }

    /**
     * Getter for login error property
     * @return login error property
     */

    public SimpleBooleanProperty loginErrorProperty() {
        return loginError;
    }

    /**
     * Getter for server error property
     * @return server error property
     */

    public SimpleBooleanProperty serverErrorProperty() {
        return serverError;
    }

    /**
     * Getter for successful login property
     * @return successful login property
     */

    public SimpleBooleanProperty successfulLoginProperty() {
        return successfulLogin;
    }
}
