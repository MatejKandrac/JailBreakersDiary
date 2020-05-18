package org.jailbreakers.ui.authentication;

import org.jailbreakers.obj.DatabaseController;

public class LoginViewModel {

    DatabaseController controller;

    LoginViewModel(){
        controller = DatabaseController.getInstance();
    }


}
