package org.jailbreakers.obj;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.beans.DefaultProperty;
import javafx.scene.control.TextInputControl;

@DefaultProperty(value = "icon")
public class EmailUsedValidator extends ValidatorBase {

    public static boolean isEmailUsed = false;

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl) {
            hasErrors.set(isEmailUsed);
            if (isEmailUsed)
                isEmailUsed = false;
        }
    }
}
