module part2_lab5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires xstream;
    requires org.apache.logging.log4j;
    requires java.sql;

    opens applications.console_application to xstream, org.apache.logging.log4j;
    exports applications.console_application;
    opens applications.database_console_application to xstream, org.apache.logging.log4j;
    exports applications.database_console_application;
    opens applications.database_gui_application to javafx.fxml, xstream, java.sql, org.apache.logging.log4j;
    exports applications.database_gui_application;
}