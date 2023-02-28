module com.ui.employeapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector;


    opens com.ui.employeapp to javafx.fxml;
    exports com.ui.employeapp;
    opens com.bean to javafx.fxml;
    exports com.bean;
}