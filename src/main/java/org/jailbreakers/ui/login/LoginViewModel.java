package org.jailbreakers.ui.login;

import javafx.beans.property.SimpleBooleanProperty;
import org.jailbreakers.obj.DatabaseController;

import java.sql.SQLException;

public class LoginViewModel {

    private DatabaseController controller;
    private SimpleBooleanProperty loading, loginError, serverError, successfulLogin;

    LoginViewModel(){
        controller = DatabaseController.getInstance();
        serverError = new SimpleBooleanProperty();
        loginError = new SimpleBooleanProperty();
        successfulLogin = new SimpleBooleanProperty();
        loading = new SimpleBooleanProperty(false);
    }

    void login(String name, String password){
        loading.setValue(true);
        try {
            controller.login(name, password);
            successfulLogin.setValue(true);
        } catch (IllegalStateException exception){
            loginError.setValue(true);
        } catch (SQLException exception){
            serverError.setValue(true);
        } finally {
            loading.setValue(false);
        }
    }

    public SimpleBooleanProperty loadingProperty() {
        return loading;
    }

    public SimpleBooleanProperty loginErrorProperty() {
        return loginError;
    }

    public SimpleBooleanProperty serverErrorProperty() {
        return serverError;
    }

    public SimpleBooleanProperty successfulLoginProperty() {
        return successfulLogin;
    }
}
