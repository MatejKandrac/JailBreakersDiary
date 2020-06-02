package org.jailbreakers.obj;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.beans.DefaultProperty;
import javafx.scene.control.TextInputControl;

@DefaultProperty(value = "icon")
public class PasswordValidator extends ValidatorBase {

    public static String passwordText;

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl) {
            JFXPasswordField field = (JFXPasswordField) srcControl.get();
            if (!passwordText.equals(field.getText())){
                message.set("Passwords doesnt match");
                hasErrors.set(true);
            }
            else if (field.getText().length() <= 5){
                message.set("Password must have more than 5 characters");
                hasErrors.set(true);
            }
            else
                hasErrors.set(false);
        }
    }

}
