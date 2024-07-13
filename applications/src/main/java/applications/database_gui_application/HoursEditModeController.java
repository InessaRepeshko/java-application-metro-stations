package applications.database_gui_application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import applications.database_console_application.HourForDB;
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
 * Controller class for managing the edit modes (ADD, EDIT, REMOVE) for "Hours" table data.
 */
public class HoursEditModeController implements Initializable{
    @FXML private TextField textFieldAddRidership;
    @FXML private TextField textFieldAddComment;
    @FXML private TextField textFieldAddMetroStationID;

    @FXML private TextField textFieldEditHourID;
    @FXML private TextField textFieldEditRidership;
    @FXML private TextField textFieldEditComment;
    @FXML private TextField textFieldEditMetroStationID;
    @FXML private TextArea textAreaEditSearchResults;

    @FXML private TextField textFieldRemoveHourID;
    @FXML private TextArea textAreaRemoveSearchResults;

    private HourForDB hour;
    private Stage stage;
    private String message;

    /**
     * Enumeration to determine the edit mode types for "Hours" Table data.
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
        HoursEditModeController.logger = logger;
    }

    /**
     * Initializes the controller class.
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (logger != null) {
            logger.info("The Edit mode window of the table \"Hours\" is initialized");
        } else {
            Logger logger = LogManager.getLogger(HoursEditModeController.class);
            HoursEditModeController.setLogger(logger);
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
     * Adds a new hour record to the database.
     * @param event The action event that triggered this method.
     */
    @FXML
    private void addHourToDB(ActionEvent event) {
        try {
            hour = new HourForDB();
            updateRidership(event, Mode.ADD);
            updateComment(event, Mode.ADD);
            updateMetroStationID(event, Mode.ADD);

            long id = addHour(hour);

            if (id > 0) {
                message = "Hour added to the database";
                showResult(message +": ",
                        getHourByHourID(id)
                                .toStringLines());
            } else {
                message = "Could not add the Hour to the database";
                showError(message + ".");
            }

            logger.info(
                    message + ":\n" +
                    hourToString(
                            getHourByHourID(id)));
        } catch (RuntimeException e) {
            message = "Failed to add the Hour to the database.";
            logger.error(message + "\n" + e);
            showError(message, e.getMessage());
        }
    }

    /**
     * Clears the fields used for adding a new hour.
     * @param event The action event that triggered this method.
     */
    @FXML
    private void clearAddFields(ActionEvent event) {
        textFieldAddRidership.clear();
        textFieldAddComment.clear();
        textFieldAddMetroStationID.clear();

        logger.info("The fields of the \"Add\" tab are cleared");
    }

    /**
     * Searches for an hour by its ID for editing purposes.
     * @param event The action event that triggered this method.
     */
    @FXML
    private void searchHourByIDForEdit(ActionEvent event) {
        try {
            textAreaEditSearchResults.clear();
            hour = new HourForDB();
            updateHourID(event, Mode.EDIT);

            HourForDB newHour = getHourByHourID(hour.getId());

            if (newHour.getId() > 0) {
                message = hourToString(newHour);
            } else {
                message = "Missing Hour.";
            }

            textAreaEditSearchResults.setText(message);
            logger.info("Search for the Hour for the tab \"Edit\":\n" + message);
        } catch (RuntimeException e) {
            message = "Failed Hour search for tab \"Edit\".";
            logger.error(message + "\n" + e);
            showError(message, e.getMessage());
        }
    }

    /**
     * Updates an existing hour record in the database.
     * @param event The action event that triggered this method.
     */
    @FXML
    private void updateHourInDB(ActionEvent event) {
        try {
            hour = new HourForDB();
            updateHourID(event, Mode.EDIT);
            updateRidership(event, Mode.EDIT);
            updateComment(event, Mode.EDIT);
            updateMetroStationID(event, Mode.EDIT);

            if (updateHour(hour)) {
                message = "Hour updated in the database to";
                showResult(
                        message + ": ",
                        getHourByHourID(hour.getId())
                                .toStringLines());
            } else {
                message = "Could not update the Hour data in the database";
                showError(message + ".");
            }

            logger.info(
                    message + ":\n" +
                    hourToString(
                            getHourByHourID(hour.getId())));
        } catch (RuntimeException e) {
            message = "Failed to update the Hour in the database.";
            logger.error(message + "\n" + e);
            showError(message, e.getMessage());
        }
    }

    /**
     * Clears the fields used for editing an hour.
     * @param event The action event that triggered this method.
     */
    @FXML
    private void clearEditFields(ActionEvent event) {
        textFieldEditHourID.clear();
        textFieldEditRidership.clear();
        textFieldEditComment.clear();
        textFieldEditMetroStationID.clear();
        textAreaEditSearchResults.clear();
        logger.info("The fields of the \"Edit\" tab are cleared");
    }

    /**
     * Searches for an hour by its ID for removal purposes.
     * @param event The action event that triggered this method.
     */
    @FXML
    private void searchHourByIDForRemove(ActionEvent event) {
        try {
            textAreaRemoveSearchResults.clear();
            hour = new HourForDB();
            updateHourID(event, Mode.REMOVE);

            HourForDB newHour = getHourByHourID(hour.getId());

            if (newHour.getId() > 0) {
                message = hourToString(newHour);
            } else {
                message = "Missing Hour.";
            }

            textAreaRemoveSearchResults.setText(message);
            logger.info("Search for the Hour for the tab \"Remove\":\n" + message);
        } catch (RuntimeException e) {
            message = "Failed Hour search for tab \"Remove\".";
            logger.error(message + "\n" + e);
            showError(message, e.getMessage());
        }
    }

    /**
     * Removes an existing hour record from the database.
     * @param event The action event that triggered this method.
     */
    @FXML
    private void removeHourFromDB(ActionEvent event) {
        try {
            hour = new HourForDB();
            updateHourID(event, Mode.REMOVE);

            if (removeHourByHourID(hour.getId())) {
                message = "Hour removed from the database.";
            } else {
                message = "Could not remove the Hour data from the database.";
            }

            logger.info(message);
            showResult(message);
        } catch (RuntimeException e) {
            message = "Failed to remove the Hour from the database.";
            logger.error(message + "\n" + e);
            showError(message, e.getMessage());
        }
    }

    /**
     * Clears the fields used for removing an hour.
     * @param event The action event that triggered this method.
     */
    @FXML
    private void clearRemoveFields(ActionEvent event) {
        textFieldRemoveHourID.clear();
        textAreaRemoveSearchResults.clear();
        logger.info("The fields of the \"Remove\" tab are cleared");
    }

    /**
     * Updates the hour ID based on the mode (EDIT or REMOVE).
     * @param event The action event that triggered this method.
     * @param mode  The mode indicating whether the ID is for editing or removing.
     */
    private void updateHourID(ActionEvent event, Mode mode) {
        try {
            String text = switch (mode) {
                case EDIT -> textFieldEditHourID.getText();
                case REMOVE -> textFieldRemoveHourID.getText();
                default -> throw new IllegalArgumentException("Wrong data passed for Hour. Mode: " + mode + ".");
            };

            long hourID;

            try {
                hourID = Long.parseLong(text);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Enter a natural number for the \"HourID\" field for the Hour.");
            }

            if (hourID > 0) {
                hour.setId(hourID);
                logger.info(mode + " Hours: field Hour ID is set to \"" + hour.getId() + "\".");
            } else {
                throw new IllegalArgumentException("Enter a natural number for the \"HourID\" field to search Hour.");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Updates the ridership based on the mode (ADD or EDIT).
     * @param event The action event that triggered this method.
     * @param mode  The mode indicating whether the ridership is for adding or editing.
     */
    private void updateRidership(ActionEvent event, Mode mode) {
        try {
            String text = switch (mode) {
                case ADD -> textFieldAddRidership.getText();
                case EDIT -> textFieldEditRidership.getText();
                default -> throw new IllegalArgumentException("Wrong data passed for Hour. Mode: " + mode + ".");
            };

            int ridership;

            try {
                ridership = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Enter a positive number for the \"Ridership\" field for the Hour.");
            }

            if (ridership >= 0) {
                hour.setRidership(ridership);
                logger.info(mode + " Hours: field Hour ridership is set to \"" + hour.getRidership() + "\".");
            } else {
                throw new IllegalArgumentException("Enter a positive number for the \"Ridership\" field for the Hour.");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Updates the comment based on the mode (ADD or EDIT).
     * @param event The action event that triggered this method.
     * @param mode  The mode indicating whether the metro station ID is for adding or editing.
     */
    private void updateComment(ActionEvent event, Mode mode) {
        String comment = switch (mode) {
            case ADD -> textFieldAddComment.getText();
            case EDIT -> textFieldEditComment.getText();
            default -> throw new IllegalArgumentException("Wrong data passed for Hour. Mode: " + mode + ".");
        };

        if (!comment.isEmpty()) {
            hour.setComment(comment);
            logger.info(mode + " Hours: field Hour comment is set to \"" + hour.getComment() + "\".");
        } else {
            throw new IllegalArgumentException("Enter a string for the \"Comment\" field for the Hour.");
        }
    }

    /**
     * Updates the metro station ID based on the mode (ADD or EDIT).
     * @param event The action event that triggered this method.
     * @param mode  The mode indicating whether the metro station ID is for adding or editing.
     */
    private void updateMetroStationID(ActionEvent event, Mode mode) {
        try {
            String text = switch (mode) {
                case ADD -> textFieldAddMetroStationID.getText();
                case EDIT -> textFieldEditMetroStationID.getText();
                default -> throw new IllegalArgumentException("Wrong data passed for Hour. Mode: " + mode + ".");
            };

            long metroStationID;

            try {
                metroStationID = Long.parseLong(text);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Enter a natural number for the \"MetroStationID\" field for the Hour.");
            }

            if (metroStationID > 0) {
                if (getMetroStationByID(metroStationID).getId() > 0) {
                    hour.setMetroStationID(metroStationID);
                    logger.info(mode + " Hours: field Hour ID is set to \"" + hour.getMetroStationID() + "\"");
                } else {
                    throw new IllegalArgumentException("Enter an existing ID for the \"MetroStationID\" field for the Hour.");
                }
            } else {
                throw new IllegalArgumentException("Enter a natural number for the \"MetroStationID\" field for the Hour.");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
