module JailBreakersDiary {
    exports org.jailbreakers;
    exports org.jailbreakers.obj;
    exports org.jailbreakers.ui.login;
    exports org.jailbreakers.ui.main;
    exports org.jailbreakers.ui.listpicker;
    exports org.jailbreakers.ui.datepicker;
    opens org.jailbreakers;
    opens org.jailbreakers.ui.listpicker;
    opens org.jailbreakers.ui.datepicker;
    opens org.jailbreakers.ui.login;
    opens org.jailbreakers.ui.splash;
    opens org.jailbreakers.ui.stagetop;
    opens org.jailbreakers.ui.register;
    opens org.jailbreakers.ui.dialog;
    opens org.jailbreakers.ui.main;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.materialicons;
    requires com.jfoenix;
}