package org.jailbreakers.ui.login;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.jailbreakers.obj.DatabaseController;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class LoginViewModel {

    private DatabaseController controller;
    private SimpleBooleanProperty loading;
    private SimpleStringProperty registerError;

    LoginViewModel(){
        controller = DatabaseController.getInstance();
        registerError = new SimpleStringProperty();
        loading = new SimpleBooleanProperty(false);
    }

    void login(String name, String password){
        loading.setValue(true);
        try {
            controller.login(name, password);
        } catch (IllegalStateException exception){
            registerError.setValue("Could not log you in");
        } catch (CommunicationsException exception){
            registerError.setValue("Error connecting to server");
        } catch (SQLException exception) {
            registerError.setValue("Something went wrong");
            exception.printStackTrace();
        }

    }

    public SimpleBooleanProperty loadingProperty() {
        return loading;
    }
}
