package org.jailbreakers.obj;

import java.net.URL;

/**
 * Enum that holds resource paths for every screen and resources and provides them by custom name.
 */
public enum Layout {
    SPLASH, LOGIN, MAIN, REGISTER, DIALOG, DATE_PICKER, LIST_PICKER, STAGE_TOP;

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
            case DATE_PICKER:
                return getClass().getResource("/fxml/date_picker.fxml");
            case LIST_PICKER:
                return getClass().getResource("/fxml/list_picker.fxml");
            case STAGE_TOP:
                return getClass().getResource("/fxml/stage_top.fxml");
            default:
                throw new IllegalStateException("Layout resource unknown");
        }
    }
}
