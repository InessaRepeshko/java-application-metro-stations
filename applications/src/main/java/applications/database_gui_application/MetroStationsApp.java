package applications.database_gui_application;

import applications.database_console_application.DBUtils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * MetroStationsApp is the main class for the metro stations applications.
 * It initializes and starts the JavaFX applications.
 */
public class MetroStationsApp extends Application {
    /**
     * Logger for logging applications events.
     */
    public static Logger logger = LogManager.getLogger(MetroStationsApp.class);

    /**
     * The main entry point for JavaFX applications.
     * Sets up the primary stage with the main applications window.
     * @param primaryStage the primary stage for this applications
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = FXMLLoader.load(getClass().getResource("MetroStationsAppWindow.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Metro Stations");
            primaryStage.show();
        } catch (Exception e) {
            logger.error("Failed to start the applications.\n" + e);
        }
    }

    /**
     * The main method which serves as the entry point for the applications.
     * Sets up the logger and launches the JavaFX applications.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MetroStationsAppController.setLogger(logger);
        MetroStationsEditModeController.setLogger(logger);
        HoursEditModeController.setLogger(logger);
        DBUtils.setLogger(logger);
        logger.info("Application opened");
        launch(args);
    }
}
