package org.jailbreakers.obj;

import java.net.URL;

public enum Layout {
    SPLASH, LOGIN, MAIN, REGISTER, DIALOG;

    public URL getResourceByLayout() {
        switch (this) {
            case LOGIN:
                return getClass().getResource("/fxml/login_layout.fxml");
            case SPLASH:
                return getClass().getResource("/fxml/splash_layout.fxml");
            case MAIN:
                return getClass().getResource("/fxml/main_layout.fxml");
            case REGISTER:
                return getClass().getResource("/fxml/register_layout.fxml");
            case DIALOG:
                return getClass().getResource("/fxml/alert_dialog.fxml");
            default:
                throw new IllegalStateException("Layout resource unknown");
        }
    }
}
