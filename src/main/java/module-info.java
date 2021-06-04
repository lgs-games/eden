/**
 * This is our game launcher "eden"
 * module info.
 */
module eden {
    // need this libraries
    requires com.goxr3plus.fxborderlessscene;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires java.desktop;

    // our main module
    exports com.lgs.eden;

    // for fxml
    opens com.lgs.eden.application;
    opens com.lgs.eden.utils.helper;
    opens com.lgs.eden.views.login;
    opens com.lgs.eden.views.settings;
    opens com.lgs.eden.views.register;
    opens com.lgs.eden.views.inventory;
    opens com.lgs.eden.views.marketplace;
    opens com.lgs.eden.views.gameslist;
    opens com.lgs.eden.views.friends;
    opens com.lgs.eden.views.profile;
    opens com.lgs.eden.views.profile.listcells;
}