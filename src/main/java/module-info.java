module eden {
    requires com.goxr3plus.fxborderlessscene;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires java.desktop;

    exports com.lgs.eden;
    exports com.lgs.eden.application;

    opens com.lgs.eden.application;
    opens com.lgs.eden.utils.helper;

    opens com.lgs.eden.views.login;
    opens com.lgs.eden.views.settings;
    opens com.lgs.eden.views.register;
    opens com.lgs.eden.views.profile;
    opens com.lgs.eden.views.profile.listcells;
}