package applications.database_gui_application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import applications.database_console_application.DBUtils;
import applications.database_console_application.HourForDB;
import applications.database_console_application.MetroStationForDB;
import applications.database_console_application.MetroStations;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static applications.database_console_application.DBUtils.*;


/**
 * Controller class for managing the Metro Stations applications.
 * Handles the UI and operations for searching, sorting, and editing Metro Station data.
 */
public class MetroStationsAppController implements Initializable {
    @FXML private TextField textFieldSearchText;
    @FXML private TextField textFieldSearchMSName;
    @FXML private TextArea textAreaSearchResults;

    @FXML private TableView<MetroStationForDB> tableViewMetroStations;
    @FXML private TableColumn<MetroStationForDB, Integer> tableColumnMSMetroStationID;
    @FXML private TableColumn<MetroStationForDB, String> tableColumnMSName;
    @FXML private TableColumn<MetroStationForDB, Integer> tableColumnMSOpened;
    private ObservableList<MetroStationForDB> observableListMetroStations;

    @FXML private TableView<HourForDB> tableViewHours;
    @FXML private TableColumn<HourForDB, Integer> tableColumnHourHourID;
    @FXML private TableColumn<HourForDB, Integer> tableColumnHourRidership;
    @FXML private TableColumn<HourForDB, String> tableColumnHourComment;
    @FXML private TableColumn<HourForDB, Integer> tableColumnHourMetroStationID;
    private ObservableList<HourForDB> observableListHours;

    private MetroStations metroStations;
    private String message;

    /**
     * Logger to record the logs of this controller.
     */
    private static Logger logger = null;

    /**
     * Sets the logger to be used by this controller class.
     * @param logger The logger instance.
     */
    public static void setLogger(Logger logger)  {
        MetroStationsAppController.logger = logger;
    }

    /**
     * Initializes the controller class and sets up the database connection and table views.
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createConnection();
        createDatabase();

        tableViewMetroStations.setPlaceholder(new Label(""));
        tableViewMetroStations.setEditable(false);

        tableViewHours.setPlaceholder(new Label(""));
        tableViewHours.setEditable(false);

        if (logger != null) {
            logger.info("Metro Stations Application main window is initialized");
        } else {
            Logger logger = LogManager.getLogger(MetroStationsAppController.class);
            MetroStationsAppController.setLogger(logger);
        }
    }

    /**
     * Shows a result message in an information alert.
     * @param message The message to be displayed.
     */
    public static void showResult(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    /**
     * Displays a confirmation dialog with the specified header and content.
     * @param header The header text to display in the dialog.
     * @param content The content text to display in the dialog.
     */
    public static void showResult(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Shows an error message in an error alert.
     * @param message The message to be displayed.
     */
    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    /**
     * Displays an error message with the specified header and content.
     * @param header The header text to display in the dialog.
     * @param content The content text to display in the dialog.
     */
    public static void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Opens the edit mode window for Metro Stations table.
     */
    @FXML
    private void showEditModeForMetroStations() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MetroStationsEditModeWindow.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Edit \"MetroStations\" Table");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tableViewMetroStations.getScene().getWindow());

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            MetroStationsEditModeController controller = loader.getController();
            controller.setStage(stage);
            stage.showAndWait();

            logger.info("The Edit mode window of the table \"MetroStations\" is closed");
            updateTableMetroStations(getMetroStations(Sort.UNSORTED).getList());
        } catch (IOException e) {
            message = "Failed to open the the Edit mode window of the table \"MetroStations\".";
            logger.error(message + "\n" + e);
            showError(message);
        }
    }

    /**
     * Opens the edit mode window for Hours table.
     */
    @FXML
    private void showEditModeForHours() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HoursEditModeWindow.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Edit \"Hours\" Table");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tableViewHours.getScene().getWindow());

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            HoursEditModeController controller = loader.getController();
            controller.setStage(stage);
            stage.showAndWait();

            logger.info("The Edit mode window of the table \"Hours\" is closed");
            updateTableHours(getHours(Sort.UNSORTED));
        } catch (IOException e) {
            message = "Failed to open the the Edit mode window of the table \"Hours\".";
            logger.error(message + "\n" + e);
            showError(message);
        }
    }

    /**
     * Returns a FileChooser configured with JSON file filters.
     * @param title The title of the FileChooser dialog.
     * @return A configured FileChooser.
     */
    private static FileChooser getFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("All files (*.*)", "*.*"));

        fileChooser.setTitle(title);

        return fileChooser;
    }

    /**
     * Creates a new file and resets the applications state.
     * @param event The action event triggered by the user.
     */
    @FXML
    private void fileNew(ActionEvent event) {
        metroStations = new MetroStations();

        textFieldSearchText.setText("");
        textFieldSearchMSName.setText("");
        textAreaSearchResults.setText("");

        tableViewMetroStations.setItems(null);
        tableViewMetroStations.setPlaceholder(new Label(""));
        tableColumnMSMetroStationID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnMSName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnMSOpened.setCellValueFactory(new PropertyValueFactory<>("opened"));

        tableViewHours.setItems(null);
        tableViewHours.setPlaceholder(new Label(""));
        tableColumnHourHourID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnHourRidership.setCellValueFactory(new PropertyValueFactory<>("ridership"));
        tableColumnHourComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        tableColumnHourMetroStationID.setCellValueFactory(new PropertyValueFactory<>("metroStationID"));

        observableListMetroStations = null;
        observableListHours = null;

        logger.info("New file created in applications");
    }

    /**
     * Opens an existing JSON file and loads its data into the applications.
     * @param event The action event triggered by the user.
     */
    @FXML
    private void fileOpen(ActionEvent event) {
        FileChooser fileChooser = getFileChooser("Open JSON file");
        File file;

        if ((file = fileChooser.showOpenDialog(null)) != null) {
            try {
                metroStations = DBUtils.importFromJSON(file.getCanonicalPath());
                addMetroStationsWithHours(metroStations);

                textFieldSearchText.setText("");
                textFieldSearchMSName.setText("");
                textAreaSearchResults.setText("");

                tableViewMetroStations.setItems(null);
                tableViewMetroStations.setPlaceholder(new Label(""));

                tableViewHours.setItems(null);
                tableViewHours.setPlaceholder(new Label(""));

                observableListMetroStations = null;
                observableListHours = null;

                updateTableMetroStations(
                        getMetroStations(Sort.UNSORTED).getList());
                updateTableHours(
                        getHours(Sort.UNSORTED));

                logger.info("JSON file opened");
            } catch (IOException | RuntimeException e) {
                if (e instanceof RuntimeException) {
                    message = "Invalid JSON file format";
                } else {
                    message = "JSON file not found";
                    logger.error(message + "\n" + e);
                }

                showError(message + ".");
            }
        }
    }

    /**
     * Saves the current data to a JSON file.
     * @param event The action event triggered by the user.
     */
    @FXML
    private void fileSave(ActionEvent event) {
        FileChooser fileChooser = getFileChooser("Save JSON file");
        File file;

        if ((file = fileChooser.showSaveDialog(null)) != null) {
            try {
                DBUtils.exportToJSON(file.getCanonicalPath());
                message = "Saved to JSON file";
                logger.info(message);
                showResult(message + ".");
            } catch (IOException | RuntimeException e) {
                message = "Error writing to JSON file";

                if (e instanceof IOException) {
                    logger.error(message + "\n" + e);
                }

                showError(message + ".");
            }
        }
    }

    /**
     * Exits the applications. Closes any open connections and logs the applications close event.
     * @param event the action event that triggered this method
     */
    @FXML
    private void fileExit(ActionEvent event) {
        closeConnection();
        logger.info("Application closed");
        Platform.exit();
    }

    /**
     * Displays an informational alert about the applications.
     * Shows the version and logs the display event.
     * @param event the action event that triggered this method
     */
    @FXML
    private void helpAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About...");
        alert.setHeaderText("Passenger flows of metro stations");
        alert.setContentText("Version 1.0");
        alert.showAndWait();
        logger.info("About program shows");
    }

    /**
     * Sorts the metro stations table by ascending name.
     * Retrieves and updates the table with the sorted list and logs the action.
     */
    @FXML
    private void runSortMetroStationsByAscName() {
        updateTableMetroStations(
                getMetroStations(Sort.SORTED_BY_ASC_NAME)
                        .getList());

        logger.info("Table \"MetroStations\" sorted by ascending name");
    }

    /**
     * Sorts the metro stations table by descending name.
     * Retrieves and updates the table with the sorted list and logs the action.
     */
    @FXML
    private void runSortMetroStationsByDescName() {
        updateTableMetroStations(
                getMetroStations(Sort.SORTED_BY_DESC_NAME)
                        .getList());

        logger.info("Table \"MetroStations\" sorted by descending name");
    }

    /**
     * Sorts the metro stations table by ascending year opened.
     * Retrieves and updates the table with the sorted list and logs the action.
     */
    @FXML
    private void runSortMetroStationsByAscOpened() {
        updateTableMetroStations(
                getMetroStations(Sort.SORTED_BY_ASC_OPENED)
                        .getList());

        logger.info("Table \"MetroStations\" sorted by ascending opened year");
    }

    /**
     * Sorts the metro stations table by descending year opened.
     * Retrieves and updates the table with the sorted list and logs the action.
     */
    @FXML
    private void runSortMetroStationsByDescOpened() {
        updateTableMetroStations(
                getMetroStations(Sort.SORTED_BY_DESC_OPENED)
                        .getList());

        logger.info("Table \"MetroStations\" sorted by descending opened year");
    }

    /**
     * Sorts the hours table by ascending ridership.
     * Retrieves and updates the table with the sorted list and logs the action.
     */
    @FXML
    private void runSortHoursByAscRidership() {
        updateTableHours(
                getHours(Sort.SORTED_ASC_RIDERSHIP));

        logger.info("Table \"Hours\" sorted by ascending ridership");
    }

    /**
     * Sorts the hours table by descending ridership.
     * Retrieves and updates the table with the sorted list and logs the action.
     */
    @FXML
    private void runSortHoursByDescRidership() {
        updateTableHours(
                getHours(Sort.SORTED_DESC_RIDERSHIP));

        logger.info("Table \"Hours\" sorted by descending ridership");
    }

    /**
     * Sorts the hours table by ascending comment length.
     * Retrieves and updates the table with the sorted list and logs the action.
     */
    @FXML
    private void runSortHoursByAscCommentLength() {
        updateTableHours(
                getHours(Sort.SORTED_ASC_COMMENT_LENGTH));

        logger.info("Table \"Hours\" sorted by ascending comment length");
    }

    /**
     * Sorts the hours table by descending comment length.
     * Retrieves and updates the table with the sorted list and logs the action.
     */
    @FXML
    private void runSortHoursByDescCommentLength() {
        updateTableHours(
                getHours(Sort.SORTED_DESC_COMMENT_LENGTH));

        logger.info("Table \"Hours\" sorted by descending comment length");
    }

    /**
     * Searches for hours by a specific word in the comment.
     * Displays the search results in the text area and logs the action.
     * @param event the action event that triggered this method
     */
    @FXML
    private void searchHoursByCommentWord(ActionEvent event) {
        textAreaSearchResults.setText("");
        String word = textFieldSearchText.getText();

        List<HourForDB> list = findHoursWithWordInComment(word);

        if (!list.isEmpty()) {
            textAreaSearchResults.appendText(hoursToString(list));
        } else {
            textAreaSearchResults.appendText("Missing Hours.");
        }

        logger.info("Search for Hours by word");
    }

    /**
     * Searches for metro stations by name.
     * Displays the search results in the text area and logs the action.
     * @param event the action event that triggered this method
     */
    @FXML
    private void searchMetroStation(ActionEvent event) {
        textAreaSearchResults.setText("");
        MetroStations metroStations = new MetroStations();
        String metroStationName;

        if ((metroStationName = textFieldSearchMSName.getText()).isEmpty()) {
            MetroStations mss = getMetroStationsWithHours();

            if (!mss.getList().isEmpty()) {
                metroStations.addAll(mss.getList());
            }
        } else {
            MetroStationForDB ms = getMetroStationWithHoursByName(metroStationName);

            if (ms.getId() > 0) {
                metroStations.add(ms);
            }
        }

        if (!metroStations.getList().isEmpty()) {
            textAreaSearchResults.appendText(metroStationsWithHoursToString(metroStations));
        } else if (!metroStationName.isEmpty()) {
            textAreaSearchResults.appendText("Missing Metro Station with the name \"" + metroStationName + "\".");
        } else {
            textAreaSearchResults.appendText("Missing Metro Stations.");
        }

        logger.info("Search for Metro Stations by name");
    }

    /**
     * Searches for the total ridership of metro stations.
     * Displays the total ridership in the text area and logs the action.
     * @param event the action event that triggered this method
     */
    @FXML
    private void searchTotalRidership(ActionEvent event) {
        textAreaSearchResults.setText("");
        int totalRidership;
        String metroStationName;
        StringBuilder stringBuilder = new StringBuilder();

        if ((metroStationName = textFieldSearchMSName.getText()).isEmpty()) {
            totalRidership = getTotalRidership();

            if (totalRidership >= 0) {
                stringBuilder.append("Total ridership for all the Metro Stations")
                        .append("\":\t")
                        .append(totalRidership);
            } else {
                stringBuilder.append("Missing Metro Stations.");
            }
        } else {
            totalRidership = getTotalRidership(metroStationName);

            if (totalRidership >= 0) {
                stringBuilder.append("Total ridership for the Metro Station \"")
                        .append(metroStationName)
                        .append("\":\t")
                        .append(totalRidership);
            } else {
                stringBuilder.append("Missing Metro Station with the name \"")
                        .append(metroStationName)
                        .append("\".");
            }
        }

        textAreaSearchResults.appendText(stringBuilder.toString());

        logger.info("Search for Hours by minimal ridership");
    }

    /**
     * Searches for hours with minimal ridership.
     * Displays the search results in the text area and logs the action.
     * @param event the action event that triggered this method
     */
    @FXML
    private void searchHoursWithMinRidership(ActionEvent event) {
        textAreaSearchResults.setText("");
        List<HourForDB> list = new ArrayList<>();
        String metroStationName;

        if ((metroStationName = textFieldSearchMSName.getText()).isEmpty()) {
            list.addAll(findHours(Search.SEARCH_BY_MIN_RIDERSHIP));
        } else {
            list.addAll(findHours(Search.SEARCH_BY_MIN_RIDERSHIP, metroStationName));
        }

        if (!list.isEmpty()) {
            textAreaSearchResults.appendText(hoursToString(list));
        } else if (!metroStationName.isEmpty()) {
            textAreaSearchResults.appendText("Missing Hours for the Metro Station \"" + metroStationName + "\".");
        } else {
            textAreaSearchResults.appendText("Missing Hours.");
        }

        logger.info("Search for Hours by minimal ridership");
    }

    /**
     * Searches for hours with maximum comment word count.
     * Displays the search results in the text area and logs the action.
     * @param event the action event that triggered this method
     */
    @FXML
    private void searchHoursWithMaxCommentWordCount(ActionEvent event) {
        textAreaSearchResults.setText("");
        List<HourForDB> list = new ArrayList<>();
        String metroStationName;

        if ((metroStationName = textFieldSearchMSName.getText()).isEmpty()) {
            list.addAll(findHours(Search.SEARCH_BY_MAX_COMMENT_WORD_COUNT));
        } else {
            list.addAll(findHours(Search.SEARCH_BY_MAX_COMMENT_WORD_COUNT, metroStationName));
        }

        if (!list.isEmpty()) {
            textAreaSearchResults.appendText(hoursToString(list));
        } else if (!metroStationName.isEmpty()) {
            textAreaSearchResults.appendText("Missing Hours for the Metro Station \"" + metroStationName + "\".");
        } else {
            textAreaSearchResults.appendText("Missing Hours.");
        }

        logger.info("Search for Hours by maximum word count of comment");
    }

    /**
     * Updates the Hours table view with the provided list of hours.
     * @param hourList the list of hours to display in the table
     */
    public void updateTableHours(List<HourForDB> hourList) {
        observableListHours = FXCollections.observableList(hourList);
        tableViewHours.setItems(observableListHours);

        tableColumnHourHourID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnHourRidership.setCellValueFactory(new PropertyValueFactory<>("ridership"));
        tableColumnHourComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        tableColumnHourMetroStationID.setCellValueFactory(new PropertyValueFactory<>("metroStationID"));

        logger.info("Table \"Hours\" updated");
    }

    /**
     * Updates the MetroStations table view with the provided list of metro stations.
     * @param metroStationList the list of metro stations to display in the table
     */
    public void updateTableMetroStations(List<MetroStationForDB> metroStationList) {
        observableListMetroStations = FXCollections.observableList(metroStationList);
        tableViewMetroStations.setItems(observableListMetroStations);

        tableColumnMSMetroStationID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnMSName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnMSOpened.setCellValueFactory(new PropertyValueFactory<>("opened"));

        logger.info("Table \"MetroStations\" updated");
    }
}
