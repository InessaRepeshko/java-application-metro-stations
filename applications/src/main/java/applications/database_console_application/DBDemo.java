package applications.database_console_application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import applications.database_gui_application.MetroStationsApp;

import java.util.ArrayList;
import java.util.List;

import static applications.database_console_application.DBUtils.*;

/**
 * The {@link DBDemo} class demonstrates operations with the MySQL database.
 * It handles data related to metro stations and their operational hours.
 */
public class DBDemo {
    private static final String METROSTATIONS_FILE = "src/main/resources/applications/database_console_application/MetroStations.json";
    private static final String METROSTATIONSFORDB_FILE = "src/main/resources/applications/database_console_application/MetroStationsFromDB.json";
    public static Logger logger = determineLogger();

    /**
     * Determines and returns an appropriate logger based on the package of the calling class.
     * If the calling class belongs to "applications.database_console_application" package,
     * returns a logger configured for that package.
     * If it belongs to "applications.database_gui_application" package,
     * returns a logger configured for that package.
     * Otherwise, returns the root logger.
     * @return Logger instance based on the package of the calling class.
     */
    public static Logger determineLogger() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        for (StackTraceElement element : stackTrace) {
            String className = element.getClassName();

            if (className.startsWith("applications.database_console_application")) {
                return LogManager.getLogger("applications.database_console_application");
            } else if (className.startsWith("applications.database_gui_application")) {
                return LogManager.getLogger("applications.database_gui_application");
            }
        }

        return LogManager.getRootLogger();
    }

    /**
     * Creates a {@link MetroStations} object and populates it with sample data for demonstration purposes.
     * @return a {@link MetroStations} object containing sample data.
     */
    public static MetroStations createMetroStations() {
        MetroStationForDB metroStation = new MetroStationForDB();
        metroStation.setName("Politekhnichna");
        metroStation.setOpened(1984);
        metroStation.addHour(new HourForDB(1100, "Very high ridership"));
        metroStation.addHour(new HourForDB(110, "Low ridership"));
        metroStation.addHour(new HourForDB(650, "High ridership"));
        metroStation.addHour(new HourForDB(532, "High ridership"));
        metroStation.addHour(new HourForDB(60, "Very low ridership"));
        metroStation.addHour(new HourForDB(188, "Low ridership"));
        metroStation.addHour(new HourForDB(200, "Medium ridership"));

        MetroStations metroStations = new MetroStations();
        metroStations.add(metroStation);

        return metroStations;
    }

    /**
     * Tests adding new Operating Hours to a Metro Station and displays the result.
     */
    public static void testAddingHour() {
        System.out.println("Add Operating Hours with ridership={250, 60, 382} for \"Politekhnichna\":");
        addHour(new HourForDB(250, "Medium ridership"), "Politekhnichna");
        addHour(new HourForDB(60, "Very low ridership"), "Politekhnichna");
        addHour(new HourForDB(382, "Medium ridership"), "Politekhnichna");
        showHours();
    }

    /**
     * Tests updating Operating Hours and displays the result.
     */
    public static void testUpdatingHour() {
        System.out.println("Update the Operating Hours with id=[3,4]:");
        List<HourForDB> list = new ArrayList<>();
        list.add(getHourByHourID(3));
        updateHour(new HourForDB(3, 600, "High ridership", 1));
        list.add(getHourByHourID(3));
        System.out.println(hoursWithMSIDToString(list));

        list.clear();
        list.add(getHourByHourID(4));
        updateHour(new HourForDB(4, 1000, "VERY HIGH RIDERSHIP", 1));
        list.add(getHourByHourID(4));
        System.out.println(hoursWithMSIDToString(list));
    }

    /**
     * Tests deleting an Operating Hour from a Metro Station and displays the result.
     */
    public static void testDeletingHour() {
        System.out.println("Delete Operating Hour with id=4:");
        removeHourByHourID(4);
        showHours();
    }

    /**
     * Tests adding new Metro Station and Operating Hours for it to the database, then displays the result.
     */
    public static void testAddingMetroStation() {
        System.out.println("Add Metro Stations \"Kholodna Hora\", \"Palats Sportu\" and \"Oleksiivska\":");
        addMetroStation(new MetroStationForDB("Kholodna Hora", 1995));
        addHour(new HourForDB(180, "Low ridership"), "Kholodna Hora");
        addHour(new HourForDB(300, "Medium ridership"), "Kholodna Hora");
        addHour(new HourForDB(520, "High ridership"), "Kholodna Hora");
        addHour(new HourForDB(60, "Very low ridership"), "Kholodna Hora");
        addMetroStation(new MetroStationForDB("Palats Sportu", 1978));
        addMetroStation(new MetroStationForDB("Oleksiivska", 2016));
        showMetroStations();
    }

    /**
     * Tests updating Metro Stations and displays the result.
     */
    public static void testUpdatingMetroStation() {
        System.out.println("Update the Metro Station with id=2:");
        MetroStations metroStations = new MetroStations();
        metroStations.add(getMetroStationByID(2));
        updateMetroStation(new MetroStationForDB(2, "Maidan Konstytutsii", 1975));
        metroStations.add(getMetroStationByID(2));
        System.out.println(metroStationsToString(metroStations));
    }

    /**
     * Tests deleting Metro Station from the database and displays the result.
     */
    public static void testDeletingMetroStation() {
        System.out.println("Current Metro Stations data:");
        showMetroStations();
        System.out.println("Add Metro Stations \"Palats Sportu\" and \"Prospekt Haharina\":");
        addMetroStation(new MetroStationForDB("Palats Sportu", 1978));
        addMetroStation(new MetroStationForDB("Prospekt Haharina", 1975));
        showMetroStations();
        System.out.println("Delete Metro Station \"Palats Sportu\" by name:");
        removeMetroStationByName("Palats Sportu");
        showMetroStations();
        System.out.println("Delete Metro Station \"Prospekt Haharina\" by id=3:");
        removeMetroStationByID(3);
        showMetroStations();
    }

    /**
     * Tests search and sorting for Metro Stations by various criteria and displays the results.
     */
    public static void testSearchAndSortingMetroStations() {
        System.out.println("""

                    **************************************************************************
                    \t\t\tSEARCH AND SORTING RESULTS FOR METRO STATIONS
                    **************************************************************************""");
        System.out.println("Search Metro Station by name \"Politekhnichna\":");
        showMetroStationsWithHoursByName("Politekhnichna");
        System.out.println("Metro Stations sorted by ascending name:");
        showMetroStationsSortedByAscName();
        System.out.println("Metro Stations sorted by descending name:");
        showMetroStationsSortedByDescName();
        System.out.println("Metro Stations sorted by ascending opened year:");
        showMetroStationsSortedByAscOpened();
        System.out.println("Metro Stations sorted by descending opened year:");
        showMetroStationsSortedByDescOpened();
    }

    /**
     * Tests various search operations on the database and displays the results.
     */
    public static void testSearchHours() {
        System.out.println("""

                    **************************************************************************
                    \t\t\t\t\t\tSEARCH RESULTS FOR HOURS
                    **************************************************************************""");
        System.out.println("Current Metro Stations and Hours data:");
        showMetroStationsWithHours();

        String word = "medium";
        System.out.println("Search Hours with word \"" + word + "\" in comment for all Metro Stations:");
        showHoursWithWordInComment(word);
        word = "ow";
        System.out.println("Search Hours with word fragment \"" + word + "\" in comment for all Metro Stations:");
        showHoursWithWordInComment(word);
        word = "";
        System.out.println("Search Hours with word \"" + word + "\" in comment for all Metro Stations:");
        showHoursWithWordInComment(word);

        System.out.println("Get total ridership for all the Metro Stations:\t\t\t\t"
                + getTotalRidership());
        System.out.println("Get total ridership for \"Politekhnichna\" Metro Station:\t\t"
                + getTotalRidership("Politekhnichna"));
        System.out.println("Get total ridership for \"Kholodna Hora\" Metro Station:\t\t"
                + getTotalRidership("Kholodna Hora") + "\n");

        System.out.println("Search Hours with minimum ridership for all Metro Stations:");
        showHoursWithMinRidership();
        System.out.println("Search Hours with minimum ridership for Metro Station \"Politekhnichna\":");
        showHoursWithMinRidershipForMS("Politekhnichna");
        System.out.println("Search Hours with minimum ridership for Metro Station \"Kholodna Hora\":");
        showHoursWithMinRidershipForMS("Kholodna Hora");

        System.out.println("Search Hours with maximum comment word count for all Metro Stations:");
        showHoursWithMaxCommentWordCount();
        System.out.println("Search Hours with maximum comment word count for Metro Station \"Politekhnichna\":");
        showHoursWithMaxCommentWordCountForMS("Politekhnichna");
        System.out.println("Search Hours with maximum comment word count for Metro Station \"Kholodna Hora\":");
        showHoursWithMaxCommentWordCountForMS("Kholodna Hora");
    }

    /**
     * Tests sorting Operating Hours for a Metro Station by various criteria and displays the results.
     */
    public static void testSortingHours() {
        System.out.println("""

                    **************************************************************************
                    \t\t\t\t\t\tSORTING RESULTS FOR HOURS
                    **************************************************************************""");
        System.out.println("Operating Hours sorted by ascending ridership:");
        showHoursSortedByAscRidership();
        System.out.println("\nOperating Hours sorted by descending ridership:");
        showHoursSortedByDescRidership();
        System.out.println("\nOperating Hours sorted by ascending comment length:");
        showHoursSortedByAscCommentLength();
        System.out.println("\nOperating Hours sorted by descending comment length:");
        showHoursSortedByDescCommentLength();
    }

    /**
     * Demonstrates the program functionality. Data is imported from a JSON file,
     * various operations are performed, and the results are exported to another JSON file.
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        DBUtils.setLogger(logger);
        logger.info("Program started");

        System.out.println("""
                **************************************************************************
                \t\t\t\t\t\t\tBASIC FUNCTIONALITY
                **************************************************************************""");
        MetroStations metroStations = createMetroStations();
        exportToJSON(metroStations, METROSTATIONS_FILE);
        System.out.println("\nExport for the initial data of Metro Stations to JSON file completed");
        metroStations = importFromJSON(METROSTATIONS_FILE);
        System.out.println("\nImport for the initial data of Metro Stations from JSON file completed");
        createConnection();
        System.out.println("\nDatabase connection established");

        if (createDatabase()) {
            System.out.println("\nAdd all the Metro Stations and Hours to the database:");
            addMetroStationsWithHours(metroStations);
            showMetroStationsWithHours();

            testAddingHour();
            testUpdatingHour();
            testDeletingHour();

            testDeletingMetroStation();
            testUpdatingMetroStation();
            testAddingMetroStation();

            testSearchAndSortingMetroStations();

            testSearchHours();
            testSortingHours();

            exportToJSON(METROSTATIONSFORDB_FILE);
            System.out.println("\nExport for the current data of Metro Stations to JSON file completed");
            closeConnection();
            System.out.println("\nDatabase connection closed");
            logger.info("Program closed");
        }
    }
}
