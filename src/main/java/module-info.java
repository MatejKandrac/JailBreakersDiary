module JailBreakersDiary {
    exports org.jailbreakers;
    exports org.jailbreakers.obj;
    exports org.jailbreakers.ui.authentication;
    opens org.jailbreakers;
    opens org.jailbreakers.ui.authentication;
    opens org.jailbreakers.ui.splash;
    opens org.jailbreakers.ui.stagetop;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.materialicons;
    requires com.jfoenix;
}