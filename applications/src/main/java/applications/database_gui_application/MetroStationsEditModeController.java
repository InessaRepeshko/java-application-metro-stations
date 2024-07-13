package applications.database_gui_application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import applications.database_console_application.MetroStationForDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static applications.database_gui_application.MetroStationsAppController.*;
import static applications.database_console_application.DBUtils.*;


/**
 * Controller class for managing the edit modes (ADD, EDIT, REMOVE) for "MetroStations" table data.
 */
public class MetroStationsEditModeController implements Initializable{
    @FXML private TextField textFieldAddName;
    @FXML private TextField textFieldAddOpened;

    @FXML private TextField textFieldEditMetroStationID;
    @FXML private TextField textFieldEditName;
    @FXML private TextField textFieldEditOpened;
    @FXML private TextArea textAreaEditSearchResults;

    @FXML private TextField textFieldRemoveMetroStationID;
    @FXML private TextArea textAreaRemoveSearchResults;

    private MetroStationForDB metroStation;
    private Stage stage;
    private String message;

    /**
     * Enumeration to determine the edit mode types for "MetroStations" Table data.
     */
    public enum Mode {
        ADD,
        EDIT,
        REMOVE
    }

    /**
     * Logger to record the logs of this controller.
     */
    private static Logger logger = null;

    /**
     * Sets the logger to be used by this controller class.
     * @param logger The logger instance.
     */
    public static void setLogger(Logger logger)  {
        MetroStationsEditModeController.logger = logger;
    }

    /**
     * Initializes the controller class.
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (logger != null) {
            logger.info("The Edit mode window of the table \"Metro Stations\" is initialized");
        } else {
            Logger logger = LogManager.getLogger(MetroStationsEditModeController.class);
            MetroStationsEditModeController.setLogger(logger);
        }
    }

    /**
     * Sets the stage for this controller.
     * @param editStage The stage to be set.
     */
    public void setStage(Stage editStage) {
        this.stage = editStage;
    }

    /**
     * Closes the edit mode window.
     * @param event The action event that triggered this method.
     */
    @FXML
    private void closeEditMode(ActionEvent event) {
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Adds a new metro station record to the database.
     * @param event The action event that triggered this method.
     */
    @FXML
    private void addMetroStationToDB(ActionEvent event) {
        try {
            metroStation = new MetroStationForDB();
            updateName(event, Mode.ADD);
            updateOpened(event, Mode.ADD);

            long id = addMetroStation(metroStation);

            if (id > 0) {
                message = "Metro Station added to the database";
                showResult(
                        message + ": ",
                        getMetroStationByID(id).
                                toStringLines());
            } else {
                message = "Could not add the Metro Station to the database";
                showError(message + ".");
            }

            logger.info(
                    message + ":\n" +
                    metroStationToString(
                            getMetroStationByID(id)));
        } catch (RuntimeException e) {
            message = "Failed to add the Metro Station to the database.";
            logger.error(message + "\n" + e);
            showError(message, e.getMessage());
        }
    }

    /**
     * Clears the fields used for adding a new metro station.
     * @param event The action event that triggered this method.
     */
    @FXML
    private void clearAddFields(ActionEvent event) {
        textFieldAddName.clear();
        textFieldAddOpened.clear();
        logger.info("The fields of the \"Add\" tab are cleared");
    }

    /**
     * Searches for a metro station by its ID for editing purposes.
     * @param event The action event that triggered this method.
     */
    @FXML
    private void searchMetroStationByIDForEdit(ActionEvent event) {
        try {
            textAreaEditSearchResults.clear();
            metroStation = new MetroStationForDB();
            updateMetroStationID(event, Mode.EDIT);

            MetroStationForDB newMetroStation = getMetroStationByID(metroStation.getId());

            if (newMetroStation.getId() > 0) {
                message = metroStationToString(newMetroStation);

            } else {
                message = "Missing Metro Station.";
            }

            textAreaEditSearchResults.setText(message);
            logger.info("Search for the Metro Station for the tab \"Edit\":\n" +  message);
        } catch (RuntimeException e) {
            message = "Failed Metro Station search for tab \"Edit\".";
            logger.error(message + "\n" + e);
            showError(message, e.getMessage());
        }
    }

    /**
     * Updates an existing metro station record in the database.
     * @param event The action event that triggered this method.
     */
    @FXML
    private void updateMetroStationInDB(ActionEvent event) {
        try {
            metroStation = new MetroStationForDB();
            updateMetroStationID(event, Mode.EDIT);
            updateName(event, Mode.EDIT);
            updateOpened(event, Mode.EDIT);

            if (updateMetroStation(metroStation)) {
                message = "Metro Station updated in the database to";
                showResult(
                        message + ": ",
                        getMetroStationByID(metroStation.getId())
                                .toStringLines());
            } else {
                message = "Could not update the Metro Station data in the database";
                showError(message + ".");
            }

            logger.info(
                    message + ":\n" +
                    metroStationToString(
                            getMetroStationByID(metroStation.getId())));
        } catch (RuntimeException e) {
            message = "Failed to update the Metro Station in the database.";
            logger.error(message + "\n" + e);
            showError(message, e.getMessage());
        }
    }

    /**
     * Clears the fields used for editing a metro station.
     * @param event The action event that triggered this method.
     */
    @FXML
    private void clearEditFields(ActionEvent event) {
        textFieldEditName.clear();
        textFieldEditOpened.clear();
        textFieldEditMetroStationID.clear();
        textAreaEditSearchResults.clear();
        logger.info("The fields of the \"Edit\" tab are cleared");
    }

    /**
     * Searches for a metro station by its ID for removal purposes.
     * @param event The action event that triggered this method.
     */
    @FXML
    private void searchMetroStationByIDForRemove(ActionEvent event) {
        try {
            textAreaRemoveSearchResults.clear();
            metroStation = new MetroStationForDB();
            updateMetroStationID(event, Mode.REMOVE);

            MetroStationForDB newMetroStation = getMetroStationByID(metroStation.getId());

            if (newMetroStation.getId() > 0) {
                message = metroStationToString(newMetroStation);
            } else {
                message = "Missing Metro Station.";
            }

            textAreaRemoveSearchResults.setText(message);
            logger.info("Search for the Metro Station for the tab \"Remove\":\n" + message);
        } catch (RuntimeException e) {
            message = "Failed Metro Station search for tab \"Remove\".";
            logger.error(message + "\n" + e);
            showError(message, e.getMessage());
        }
    }

    /**
     * Removes an existing metro station record from the database.
     * @param event The action event that triggered this method.
     */
    @FXML
    private void removeMetroStationFromDB(ActionEvent event) {
        try {
            metroStation = new MetroStationForDB();
            updateMetroStationID(event, Mode.REMOVE);

            if (removeMetroStationByID(metroStation.getId())) {
                message = "Metro Station removed from the database.";

            } else {
                message = "Could not remove the Metro Station data from the database.";
            }

            logger.info(message);
            showResult(message);
        } catch (RuntimeException e) {
            message = "Failed to remove the Metro Station from the database.";
            logger.error(message + "\n" + e);
            showError(message, e.getMessage());
        }
    }

    /**
     * Clears the fields used for removing a metro station.
     * @param event The action event that triggered this method.
     */
    @FXML
    private void clearRemoveFields(ActionEvent event) {
        textFieldRemoveMetroStationID.clear();
        textAreaRemoveSearchResults.clear();
        logger.info("The fields of the \"Remove\" tab are cleared");
    }

    /**
     * Updates the metro station ID based on the mode (EDIT or REMOVE).
     * @param event The action event that triggered this method.
     * @param mode  The mode indicating whether the ID is for editing or removing.
     */
    private void updateMetroStationID(ActionEvent event, Mode mode) {
        try {
            String text = switch (mode) {
                case EDIT -> textFieldEditMetroStationID.getText();
                case REMOVE -> textFieldRemoveMetroStationID.getText();
                default -> throw new IllegalArgumentException("Wrong data passed for Metro Station. Mode: " + mode + ".");
            };

            long id;

            try {
                id = Long.parseLong(text);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Enter a natural number for the \"MetroStationID\" field for the Metro Station.");
            }

            if (id > 0) {
                metroStation.setId(id);
                logger.info(mode + " MetroStation: field Metro Station ID is set to \"" + metroStation.getId() + "\".");
            } else {
                throw new IllegalArgumentException("Enter a natural number for the \"MetroStationID\" field to search Metro Station.");
            }

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Updates the name of the metro station based on the mode (ADD or EDIT).
     * @param event The action event that triggered this method.
     * @param mode  The mode indicating whether the name is for adding or editing.
     */
    private void updateName(ActionEvent event, Mode mode) {
        String name = switch (mode) {
            case ADD -> textFieldAddName.getText();
            case EDIT -> textFieldEditName.getText();
            default -> throw new IllegalArgumentException("Wrong data passed for Metro Station. Mode: " + mode + ".");
        };

        if (!name.isEmpty()) {
            metroStation.setName(name);
            logger.info(mode + " MetroStation: field Metro Station name is set to \"" + metroStation.getName() + "\".");
        } else {
            throw new IllegalArgumentException("Enter a string for the \"Name\" field for the Metro Station.");
        }
    }

    /**
     * Updates the opened year of the metro station based on the mode (ADD or EDIT).
     * @param event The action event that triggered this method.
     * @param mode  The mode indicating whether the opened year is for adding or editing.
     */
    private void updateOpened(ActionEvent event, Mode mode) {
        try {
            String text = switch (mode) {
                case ADD -> textFieldAddOpened.getText();
                case EDIT -> textFieldEditOpened.getText();
                default -> throw new IllegalArgumentException("Wrong data passed for Metro Station. Mode: " + mode + ".");
            };

            int opened;

            try {
                opened = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Enter a natural number in range [1863; 3000] for the \"Opened\" field for the Metro Station.");
            }

            if (opened >= 1863 && opened <= 3000) {
                metroStation.setOpened(opened);
                logger.info(mode + " MetroStations: field Metro Station opened year is set to \"" + metroStation.getOpened() + "\".");
            } else {
                throw new IllegalArgumentException("Enter a natural number in range [1863; 3000] for the \"Opened\" field for the Metro Station.");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
