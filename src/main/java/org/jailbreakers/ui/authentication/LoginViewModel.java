package org.jailbreakers.ui.authentication;

import javafx.beans.property.SimpleBooleanProperty;
import org.jailbreakers.obj.DatabaseController;

public class LoginViewModel {

    private DatabaseController controller;
    private SimpleBooleanProperty loading;

    LoginViewModel(){
        controller = DatabaseController.getInstance();
        loading = new SimpleBooleanProperty(false);
    }

    void login(String name, String password){
        loading.setValue(true);
    }

    public SimpleBooleanProperty loadingProperty() {
        return loading;
    }
}
