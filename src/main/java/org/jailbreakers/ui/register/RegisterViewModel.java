package org.jailbreakers.ui.register;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import javafx.beans.property.SimpleStringProperty;
import org.jailbreakers.obj.DatabaseController;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class RegisterViewModel {

    private DatabaseController databaseController;
    private SimpleStringProperty registerError;

    public RegisterViewModel() {
        registerError = new SimpleStringProperty();
        databaseController = DatabaseController.getInstance();
    }

    void register(String email, String pass, String confirmPass){
        if (!pass.equals(confirmPass)){
            registerError.setValue("Passwords doesnt match");
        }
        else if (pass.length() <= 5)
            registerError.setValue("Password must have more than 5 characters");
        else {
            try {
                databaseController.register(email, pass);
            } catch (SQLIntegrityConstraintViolationException exception){
                registerError.setValue("Email is used");
            } catch (CommunicationsException exception){
                registerError.setValue("Error connecting to server");
            } catch (SQLException exception) {
                registerError.setValue("Something went wrong");
                exception.printStackTrace();
            }
        }
    }

    public SimpleStringProperty registerErrorProperty() {
        return registerError;
    }
}
