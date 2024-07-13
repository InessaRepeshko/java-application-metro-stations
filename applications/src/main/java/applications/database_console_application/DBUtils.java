package applications.database_console_application;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Utility class for handling database operations related to metro stations and their ridership hours.
 * Works with MySQL database server.
 */
public class DBUtils {
    /**
     * Logger for logging database operations.
     */
    private static Logger logger = null;

    /**
     * Sets the logger to be used by this utility class.
     * @param logger The logger instance.
     */
    public static void setLogger(Logger logger)  {
        DBUtils.logger = logger;
    }
    /**
     * Enum representing different sorting options of Metro Stations or Operating Hours data for querying metro stations.
     */
    public enum Sort {
        UNSORTED,
        SORTED_BY_ASC_NAME,
        SORTED_BY_DESC_NAME,
        SORTED_BY_ASC_OPENED,
        SORTED_BY_DESC_OPENED,
        SORTED_ASC_RIDERSHIP,
        SORTED_DESC_RIDERSHIP,
        SORTED_ASC_COMMENT_LENGTH,
        SORTED_DESC_COMMENT_LENGTH
    }

    /**
     * Enum representing different search options for querying Operating Hours data.
     */
    public enum Search {
        SEARCH_BY_MIN_RIDERSHIP,
        SEARCH_BY_MAX_COMMENT_WORD_COUNT
    }

    /**
     * Constants containing necessary SQL queries.
     */
    public static final String DROP_TABLES = """
            DROP TABLES IF EXISTS hours, metroStations;
            """;
    public static final String DROP_DATABASE = """
            DROP DATABASE IF EXISTS metroStationsDB;
            """;
    public static final String CREATE_DATABASE = """
            CREATE DATABASE metroStationsDB;
            """;
    public static final String CREATE_TABLE_METROSTATIONS = """
            CREATE TABLE metroStationsDB.metroStations (
              MetroStationID INT NOT NULL AUTO_INCREMENT,
              Name VARCHAR(128) NULL,
              Opened INT NULL,
              PRIMARY KEY (MetroStationID));
            """;
    public static final String CREATE_TABLE_HOURS = """
            CREATE TABLE metroStationsDB.hours (
              HourID INT NOT NULL AUTO_INCREMENT,
              Ridership INT NULL,
              Comment VARCHAR(256) NULL,
              MetroStationID INT NULL,
              PRIMARY KEY (HourID),
              INDEX MetroStationID_idx (MetroStationID ASC) VISIBLE,
              CONSTRAINT MetroStationID
                FOREIGN KEY (MetroStationID)
                REFERENCES metroStationsDB.metroStations (MetroStationID)
                ON DELETE NO ACTION
                ON UPDATE NO ACTION);
            """;
    private static final String INSERT_INTO_METROSTATIONS = """
        INSERT INTO metroStationsDB.metroStations (Name, Opened)
        VALUES (?, ?);
        """;
    private static final String INSERT_INTO_HOURS = """
        INSERT INTO metroStationsDB.hours (Ridership, Comment, MetroStationID)
        VALUES (?, ?, ?);
        """;
    private static final String UPDATE_METROSTATION = """
        UPDATE metroStationsDB.metroStations
        SET Name = ?, Opened = ?
        WHERE MetroStationID = ?;
        """;
    private static final String UPDATE_HOUR = """
        UPDATE metroStationsDB.hours
        SET Ridership = ?, Comment = ?, MetroStationID = ?
        WHERE HourID = ?;
        """;
    private static final String SELECT_METROSTATIONS = """
            SELECT *
            FROM metroStationsDB.metroStations;
            """;
    private static final String SELECT_METROSTATIONS_ORDER_BY_ASC_NAME = """
            SELECT *
            FROM metroStationsDB.metroStations
            ORDER BY Name ASC;
            """;
    private static final String SELECT_METROSTATIONS_ORDER_BY_DESC_NAME = """
            SELECT *
            FROM metroStationsDB.metroStations
            ORDER BY Name DESC;
            """;
    private static final String SELECT_METROSTATIONS_ORDER_BY_ASC_OPENED = """
            SELECT *
            FROM metroStationsDB.metroStations
            ORDER BY Opened ASC;
            """;
    private static final String SELECT_METROSTATIONS_ORDER_BY_DESC_OPENED = """
            SELECT *
            FROM metroStationsDB.metroStations
            ORDER BY Opened DESC;
            """;
    private static final String SELECT_METROSTATION_BY_ID = """
            SELECT *
            FROM metroStationsDB.metroStations
            WHERE MetroStationID = ?;
            """;
    private static final String SELECT_METROSTATION_BY_NAME = """
            SELECT *
            FROM metroStationsDB.metroStations
            WHERE Name = ?;
            """;
    private static final String SELECT_HOURS = """
            SELECT *
            FROM metroStationsDB.hours;
            """;
    private static final String SELECT_HOURS_BY_HOURID = """
            SELECT *
            FROM metroStationsDB.hours
            WHERE HourID = ?;
            """;
    private static final String SELECT_HOURS_BY_METROSTATIONID = """
            SELECT *
            FROM metroStationsDB.hours
            WHERE MetroStationID = ?;
            """;
    private static final String SELECT_HOURS_BY_COMMENT_WORD = """
             SELECT h.HourID, h.Ridership, h.Comment, h.MetroStationID, m.Name
             FROM metroStationsDB.hours h
             INNER JOIN metroStationsDB.metroStations m ON h.MetroStationID = m.MetroStationID
             WHERE h.Comment LIKE '%key_word%';
            """;
    private static final String SELECT_TOTAL_RIDERSHIP = """
            SELECT SUM(Ridership) AS TotalRidership
            FROM metroStationsDB.hours;
            """;
    private static final String SELECT_TOTAL_RIDERSHIP_FOR_METROSTATION = """
            SELECT SUM(h.Ridership) AS TotalRidership
            FROM metroStationsDB.hours h
            JOIN metroStationsDB.metroStations ms ON h.MetroStationID = ms.MetroStationID
            WHERE ms.Name = ?;
            """;
    private static final String SELECT_HOURS_BY_MIN_RIDERSHIP = """
            SELECT h.HourID, h.Ridership, h.Comment, h.MetroStationID, ms.Name
            FROM metroStationsDB.hours h
            JOIN metroStationsDB.metroStations ms ON h.MetroStationID = ms.MetroStationID
            WHERE h.Ridership = (
                SELECT MIN(h2.Ridership)
                FROM metroStationsDB.hours h2
                JOIN metroStationsDB.metroStations ms2 ON h2.MetroStationID = ms2.MetroStationID
            );
            """;
    private static final String SELECT_HOURS_BY_MIN_RIDERSHIP_BY_METROSTATION = """
            SELECT h.HourID, h.Ridership, h.Comment, h.MetroStationID, ms.Name
            FROM metroStationsDB.hours h
            JOIN metroStationsDB.metroStations ms ON h.MetroStationID = ms.MetroStationID
            WHERE h.Ridership = (
                SELECT MIN(h2.Ridership)
                FROM metroStationsDB.hours h2
                JOIN metroStationsDB.metroStations ms2 ON h2.MetroStationID = ms2.MetroStationID
                WHERE ms2.Name = ?
            ) AND ms.Name = ?;
            """;
    private static final String SELECT_HOURS_BY_MAX_COMMENT_WORD_COUNT = """
            SELECT h.HourID, h.Ridership, h.Comment, h.MetroStationID, ms.Name
            FROM metroStationsDB.hours h
            JOIN metroStationsDB.metroStations ms ON h.MetroStationID = ms.MetroStationID
            WHERE (LENGTH(h.Comment) - LENGTH(REPLACE(h.Comment, ' ', '')) + 1) = (
                SELECT MAX(LENGTH(h2.Comment) - LENGTH(REPLACE(h2.Comment, ' ', '')) + 1)
                FROM metroStationsDB.hours h2
                JOIN metroStationsDB.metroStations ms2 ON h2.MetroStationID = ms2.MetroStationID
            );
            """;
    private static final String SELECT_HOURS_BY_MAX_COMMENT_WORD_COUNT_BY_METROSTATION = """
            SELECT h.HourID, h.Ridership, h.Comment, h.MetroStationID, ms.Name
            FROM metroStationsDB.hours h
            JOIN metroStationsDB.metroStations ms ON h.MetroStationID = ms.MetroStationID
            WHERE ms.Name = ?
            AND (LENGTH(h.Comment) - LENGTH(REPLACE(h.Comment, ' ', '')) + 1) = (
                SELECT MAX(LENGTH(h2.Comment) - LENGTH(REPLACE(h2.Comment, ' ', '')) + 1)
                FROM metroStationsDB.hours h2
                JOIN metroStationsDB.metroStations ms2 ON h2.MetroStationID = ms2.MetroStationID
                WHERE ms2.Name = ?);
            """;
    private static final String SELECT_HOURS_ORDER_BY_ASC_RIDERSHIP = """
            SELECT *
            FROM metroStationsDB.hours
            ORDER BY Ridership ASC;
            """;
    private static final String SELECT_HOURS_ORDER_BY_DESC_RIDERSHIP = """
             SELECT *
             FROM metroStationsDB.hours
             ORDER BY Ridership DESC;
            """;
    private static final String SELECT_HOURS_ORDER_BY_ASC_COMMENT_LENGTH = """
             SELECT *
             FROM metroStationsDB.hours
             ORDER BY LENGTH(Comment) ASC;
            """;
    private static final String SELECT_HOURS_ORDER_BY_DESC_COMMENT_LENGTH = """
             SELECT *
             FROM metroStationsDB.hours
             ORDER BY LENGTH(Comment) DESC;
            """;
    private static final String DELETE_METROSTATION_BY_ID = """
            DELETE FROM metroStationsDB.metroStations
            WHERE MetroStationID = ?;
            """;
    private static final String DELETE_METROSTATION_BY_NAME = """
            DELETE FROM metroStationsDB.metroStations
            WHERE Name = ?;
            """;
    private static final String DELETE_HOUR_BY_HOURID = """
            DELETE FROM metroStationsDB.hours
            WHERE HourID = ?;
            """;
    private static Connection connection;

    /**
     * Deserializes Merto Stations data from the specified XML file.
     * @param fileName the file name;
     * @return the {@link MetroStations} object.
     */
    public static MetroStations importFromJSON(String fileName) {
        logger.info("Import from JSON file");

        try {
            XStream xStream = new XStream(new JettisonMappedXmlDriver());
            xStream.addPermission(AnyTypePermission.ANY);

            xStream.alias("metroStations", MetroStations.class);
            xStream.alias("metroStation", MetroStationForDB.class);
            xStream.alias("hour", HourForDB.class);

            return (MetroStations) xStream.fromXML(new File(fileName));
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Serializes Metro Stations data from database to the specified JSON file.
     * @param fileName the file name.
     */
    public static void exportToJSON(String fileName) {
        exportToJSON(
                getMetroStationsWithHours(),
                fileName);
    }

    /**
     * Serializes Metro Stations data to the specified JSON file.
     * @param metroStations the {@link MetroStations} object;
     * @param fileName the file name.
     */
    public static void exportToJSON(MetroStations metroStations, String fileName) {
        logger.info("Export from JSON file");

        XStream xStream = new XStream(new JettisonMappedXmlDriver());

        xStream.alias("metroStations", MetroStations.class);
        xStream.alias("metroStation", MetroStationForDB.class);
        xStream.alias("hour", HourForDB.class);

        String xml = xStream.toXML(metroStations);

        try (FileWriter fw = new FileWriter(fileName);
             PrintWriter out = new PrintWriter(fw)) {
            out.println(xml);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the connection to the database.
     */
    public static void createConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mysql?user=root&password=Root_1234");

            if (logger != null) {
                logger.info("Database connection established");
            } else {
                Logger logger = LogManager.getLogger(DBUtils.class);
                DBUtils.setLogger(logger);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Closes the connection to the database.
     */
    public static void closeConnection() {
        logger.info("Database connection closed");

        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a database and tables with dropping previous ones.
     * @return {@code true} if creation is successful, {@code false} otherwise.
     */
    public static boolean createDatabase() {
        logger.info("Database created");

        try {
            Statement statement = connection.createStatement();

            statement.executeUpdate(DROP_TABLES);
            statement.executeUpdate(DROP_DATABASE);

            statement.executeUpdate(CREATE_DATABASE);
            statement.executeUpdate(CREATE_TABLE_METROSTATIONS);
            statement.executeUpdate(CREATE_TABLE_HOURS);

            return true;
        } catch (SQLException e) {
            logger.error(e.toString());
            return false;
        }
    }

    /**
     * Adds {@link MetroStationForDB} data to the database.
     * @param metroStation the {@link MetroStationForDB} whose data is being added;
     * @return {@code true} if addition is successful, {@code false} otherwise.
     */
    public static long addMetroStation(MetroStationForDB metroStation) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_METROSTATIONS, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, metroStation.getName());
            preparedStatement.setInt(2, metroStation.getOpened());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds all data from the {@link MetroStations} object with MetroStations and theirs Hours to the database.
     * @param metroStations the object with {@link MetroStations} data;
     * @return {@code true} if addition is successful, {@code false} otherwise.
     */
    public static boolean addMetroStationsWithHours(MetroStations metroStations) {
        metroStations.getList()
                .forEach(metroStation -> {
                    addMetroStation(metroStation);
                    metroStation.getHourList()
                            .forEach(hour ->
                                addHour(hour, metroStation.getName())
                            );
                });

        return !metroStations.getList().isEmpty();
    }

    /**
     * Adds an Hour record to the database with the specified data.
     * @param ridership the ridership of the Hour;
     * @param comment the comment of the Hour;
     * @param metroStationID the ID of the Metro Station;
     * @return {@code true} if addition is successful, {@code false} otherwise.
     */
    public static boolean addHour(int ridership, String comment, long metroStationID) {
        try {
            MetroStationForDB metroStation = getMetroStationByID(metroStationID);

            if (metroStation.getId() > 0) {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_HOURS);
                preparedStatement.setInt(1, ridership);
                preparedStatement.setString(2, comment);
                preparedStatement.setLong(3, metroStation.getId());

                return preparedStatement.executeUpdate() == 1;
            }

            return false;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds an Hour record to the database with the specified data.
     * @param hour the Hour object to be added;
     * @return The ID of the newly created record in the database, or -1 if the record was not added successfully.
     */
    public static long addHour(HourForDB hour) {
        try {
            MetroStationForDB metroStation = getMetroStationByID(hour.getMetroStationID());

            if (metroStation.getId() > 0) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        INSERT_INTO_HOURS,
                        Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, hour.getRidership());
                preparedStatement.setString(2, hour.getComment());
                preparedStatement.setLong(3, metroStation.getId());

                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    return -1;
                }
            }

            return -1;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds an Hour record to the database for a specified Metro Station.
     * @param metroStationName the name of the Metro Station;
     * @param hour the Hour record to be added.
     * @return {@code true} if addition is successful, {@code false} otherwise.
     */
    public static boolean addHour(HourForDB hour, String metroStationName) {
        try {
            long metroStationID = getMetroStationIDByName(metroStationName);

            if (metroStationID > 0) {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_HOURS);
                preparedStatement.setInt(1, hour.getRidership());
                preparedStatement.setString(2, hour.getComment());
                preparedStatement.setLong(3, metroStationID);

                return preparedStatement.executeUpdate() == 1;
            }

            return false;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates {@link MetroStationForDB} data at the database.
     * @param metroStation the {@link MetroStationForDB} whose data is being updated;
     * @return {@code true} if updating is successful, {@code false} otherwise.
     */
    public static boolean updateMetroStation(MetroStationForDB metroStation) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_METROSTATION);
            preparedStatement.setString(1, metroStation.getName());
            preparedStatement.setInt(2, metroStation.getOpened());
            preparedStatement.setLong(3, metroStation.getId());

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates {@link HourForDB} data at the database.
     * @param hour the {@link HourForDB} whose data is being updated;
     * @return {@code true} if updating is successful, {@code false} otherwise.
     */
    public static boolean updateHour(HourForDB hour) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_HOUR);
            preparedStatement.setInt(1, hour.getRidership());
            preparedStatement.setString(2, hour.getComment());
            preparedStatement.setLong(3, hour.getMetroStationID());
            preparedStatement.setLong(4, hour.getId());

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes a Metro Station record from the database for a specified metro station based on its ID.
     * @param metroStationID the ID of the metro station;
     * @return {@code true} if deletion is successful, {@code false} otherwise.
     */
    public static boolean removeMetroStationByID(long metroStationID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_METROSTATION_BY_ID);
            preparedStatement.setLong(1, metroStationID);

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes a Metro Station record from the database for a specified metro station based on its name.
     * @param metroStationName the name of the Metro Station;
     * @return {@code true} if deletion is successful, {@code false} otherwise.
     */
    public static boolean removeMetroStationByName(String metroStationName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_METROSTATION_BY_NAME);
            preparedStatement.setString(1, metroStationName);

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes an hour record from the database for a specified metro station based on ridership.
     * @param hourID the HourID value of the hour record to be removed;
     * @return {@code true} if deletion is successful, {@code false} otherwise.
     */
    public static boolean removeHourByHourID(long hourID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_HOUR_BY_HOURID);
            preparedStatement.setLong(1, hourID);

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates and returns a {@link MetroStationForDB} object from the result dataset.
     * @param resultSet the result dataset from which the Metro Station data is retrieved;
     * @return the created Metro Station;
     * @throws SQLException an exception related to a SQL query error.
     */
    public static MetroStationForDB getMetroStationFromResultSet(ResultSet resultSet) throws SQLException {
        MetroStationForDB metroStation = new MetroStationForDB();
        metroStation.setId(resultSet.getLong("MetroStationID"));
        metroStation.setName(resultSet.getString("Name"));
        metroStation.setOpened(resultSet.getInt("Opened"));

        return metroStation;
    }

    /**
     * Creates and returns a {@link MetroStationForDB} object from the result dataset.
     * @param resultSet the result dataset from which the Metro Station data is retrieved;
     * @return the created Metro Station with Operating Hours data;
     * @throws SQLException an exception related to a SQL query error.
     */
    public static MetroStationForDB getMetroStationWithHoursFromResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            MetroStationForDB metroStation = getMetroStationFromResultSet(resultSet);

            metroStation.addHours(
                    getHoursByMetroStationID(metroStation.getId()));

            return metroStation;
        }

        return new MetroStationForDB();
    }

    /**
     * Creates and returns a {@link MetroStations} object from the result dataset.
     * @param resultSet the result dataset from which the Metro Stations data is retrieved;
     * @return the created Metro Stations;
     * @throws SQLException an exception related to a SQL query error.
     */
    public static MetroStations getMetroStationsFromResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()) {
            MetroStations metroStations = new MetroStations();

            while (resultSet.next()) {
                metroStations.add(
                        getMetroStationFromResultSet(resultSet));
            }

            return metroStations;
        }

        return new MetroStations();
    }

    /**
     * Creates the {@link HourForDB} list, which stores the data about Hours
     * from the result set retrieved from the database.
     * @return list of {@link HourForDB} retrieved from the given result set.
     */
    public static List<HourForDB> getHoursFromResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()) {
            List<HourForDB> hours = new ArrayList<>();

            while (resultSet.next()) {
                hours.add(getHourFromResultSet(resultSet));
            }

            return hours;
        }

        return new ArrayList<>();
    }

    /**
     * Creates the {@link HourForDB} list, which stores the data about Hours including its MetroStation Name
     * from the result set retrieved from the database.
     * @return list of {@link HourForDB} retrieved from the given result set.
     */
    public static List<HourForDB> getHoursWithMSNameFromResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()) {
            List<HourForDB> hours = new ArrayList<>();

            while (resultSet.next()) {
                HourForDB hour = getHourFromResultSet(resultSet);
                hour.setMetroStationName(resultSet.getString("Name"));
                hours.add(hour);
            }

            return hours;
        }

        return new ArrayList<>();
    }

    /**
     * Creates a {@link MetroStationForDB} object by filling it with data from the database.
     * @param metroStationID ID of the Metro Station;
     * @return an {@link MetroStationForDB} object filled with data from the database.
     */
    public static MetroStationForDB getMetroStationByID(long metroStationID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_METROSTATION_BY_ID);
            preparedStatement.setLong(1, metroStationID);

            ResultSet resultSet = preparedStatement.executeQuery();
            MetroStationForDB metroStation = new MetroStationForDB();

            if (resultSet.next()) {
                metroStation = getMetroStationFromResultSet(resultSet);

            }

            resultSet.close();

            return metroStation;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a {@link MetroStationForDB} object by filling it with data from the database.
     * @param metroStationName name of the Metro Station;
     * @return an {@link MetroStationForDB} object filled with data from the database.
     */
    public static MetroStationForDB getMetroStationByName(String metroStationName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_METROSTATION_BY_NAME);
            preparedStatement.setString(1, metroStationName);

            ResultSet resultSet = preparedStatement.executeQuery();
            MetroStationForDB metroStation = new MetroStationForDB();

            if (resultSet.next()) {
                metroStation = getMetroStationFromResultSet(resultSet);
            }

            resultSet.close();

            return metroStation;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays data about Metro Station and its Operating Hours by Name on the console.
     * @param metroStationName name of the Metro Station;
     * @return an {@link MetroStationForDB} object filled with data from the database.
     */
    public static MetroStationForDB getMetroStationWithHoursByName(String metroStationName) {
        MetroStationForDB metroStation = getMetroStationByName(metroStationName);

        if (metroStation.getId() > 0) {
            metroStation.addHours(
                    getHoursByMetroStationID(metroStation.getId()));
        }

        return metroStation;
    }

    /**
     * Gets the Metro Station ID by Metro Station name.
     * @param metroStationName Metro Station name;
     * @return Metro Station ID in the database.
     */
    public static long getMetroStationIDByName(String metroStationName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_METROSTATION_BY_NAME);
            preparedStatement.setString(1, metroStationName);

            ResultSet resultSet = preparedStatement.executeQuery();
            long id;

            if (resultSet.next()) {
                id = resultSet.getLong("MetroStationID");
            } else {
                id = -1;
            }

            resultSet.close();

            return id;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the {@link MetroStations}, which stores all the data from the database.
     * @param sorting the order sorted data defined by the {@link Sort} list;
     * @return the {@link MetroStations} object where the data about Metro Stations is stored.
     */
    public static MetroStations getMetroStations(Sort sorting) {
        try {
            String query = switch (sorting) {
                case UNSORTED -> SELECT_METROSTATIONS;
                case SORTED_BY_ASC_NAME -> SELECT_METROSTATIONS_ORDER_BY_ASC_NAME;
                case SORTED_BY_DESC_NAME -> SELECT_METROSTATIONS_ORDER_BY_DESC_NAME;
                case SORTED_BY_ASC_OPENED -> SELECT_METROSTATIONS_ORDER_BY_ASC_OPENED;
                case SORTED_BY_DESC_OPENED -> SELECT_METROSTATIONS_ORDER_BY_DESC_OPENED;
                default -> throw new IllegalArgumentException("Unexpected value: " + sorting);
            };

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            MetroStations metroStations = getMetroStationsFromResultSet(resultSet);
            resultSet.close();

            return metroStations;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays all data about Metro Stations and its Operating Hours from the database on the console, sequentially for each Metro Station.
     */
    public static MetroStations getMetroStationsWithHours() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_METROSTATIONS);

            MetroStations metroStations = getMetroStationsFromResultSet(resultSet);
            resultSet.close();

            if (!metroStations.getList().isEmpty()) {
                metroStations.getList()
                        .forEach(metroStation ->
                                metroStation.addHours(
                                        getHoursByMetroStationID(metroStation.getId())));
            }

            return metroStations;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the {@link HourForDB} object, which stores all the data from the database.
     * @return the {@link HourForDB} object where the data about Hour is stored.
     */
    public static HourForDB getHourByHourID(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HOURS_BY_HOURID);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            HourForDB hour = new HourForDB();

            if (resultSet.next()) {
                hour = getHourFromResultSet(resultSet);
            }

            resultSet.close();

            return hour;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the {@link HourForDB} object, which stores the data about one Hour.
     * from the result set retrieved from the database.
     * @return the {@link HourForDB} object retrieved from the given result set.
     */
    public static HourForDB getHourFromResultSet(ResultSet resultSet) throws SQLException {
        HourForDB hour = new HourForDB();
        hour.setId(resultSet.getLong("HourID"));
        hour.setRidership(resultSet.getInt("Ridership"));
        hour.setComment(resultSet.getString("Comment"));
        hour.setMetroStationID(resultSet.getLong("MetroStationID"));

        return hour;
    }

    /**
     * Gets data about Hours form database.
     * @param sorting the order sorted data defined by the {@link Sort} list;
     * @return a list of {@link HourForDB} objects retrieved from the database.
     */
    public static List<HourForDB> getHours(Sort sorting) {
        try {
            String query = switch (sorting) {
                case UNSORTED -> SELECT_HOURS;
                case SORTED_ASC_RIDERSHIP -> SELECT_HOURS_ORDER_BY_ASC_RIDERSHIP;
                case SORTED_DESC_RIDERSHIP -> SELECT_HOURS_ORDER_BY_DESC_RIDERSHIP;
                case SORTED_ASC_COMMENT_LENGTH -> SELECT_HOURS_ORDER_BY_ASC_COMMENT_LENGTH;
                case SORTED_DESC_COMMENT_LENGTH -> SELECT_HOURS_ORDER_BY_DESC_COMMENT_LENGTH;
                default -> throw new IllegalArgumentException("Unexpected value: " + sorting);
            };

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            List<HourForDB> list = getHoursFromResultSet(resultSet);
            resultSet.close();

            return list;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the {@link HourForDB} list, which stores all the data about Hours with certain MetroStationID
     * from the database.
     * @return the {@link HourForDB} list where the data about Hours is stored.
     */
    public static List<HourForDB> getHoursByMetroStationID(long metroStationID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HOURS_BY_METROSTATIONID);
            preparedStatement.setLong(1, metroStationID);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<HourForDB> list = getHoursFromResultSet(resultSet);
            resultSet.close();

            return list;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays data on the console about all Operating Hours with a certain word or word fragment in the comments.
     * @param word word of word fragment to search for.
     * @return a list of hours retrieved from the database.
     */
    public static List<HourForDB> findHoursWithWordInComment(String word) {
        try {
            String query = SELECT_HOURS_BY_COMMENT_WORD.replace("key_word", word);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<HourForDB> list = getHoursWithMSNameFromResultSet(resultSet);
            resultSet.close();

            return list;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds the Hours records according to a specified {@link Search} search rule.
     * @param searching the {@link Search} rule for searching data;
     * @return a list of hours retrieved from the database.
     */
    public static List<HourForDB> findHours(Search searching) {
        String query = switch (searching) {
            case SEARCH_BY_MIN_RIDERSHIP -> SELECT_HOURS_BY_MIN_RIDERSHIP;
            case SEARCH_BY_MAX_COMMENT_WORD_COUNT -> SELECT_HOURS_BY_MAX_COMMENT_WORD_COUNT;
            default -> throw new IllegalArgumentException("Unexpected value: " + searching);
        };

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<HourForDB> list = getHoursWithMSNameFromResultSet(resultSet);
            resultSet.close();

            return list;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds the Hours records according to a specified {@link Search} search rule for a specified Metro Station.
     * @param searching the {@link Search} rule for searching data;
     * @param metroStationName the name of the Metro Station;
     * @return a list of hours retrieved from the database.
     */
    public static List<HourForDB> findHours(Search searching, String metroStationName) {
        String query = switch (searching) {
            case SEARCH_BY_MIN_RIDERSHIP -> SELECT_HOURS_BY_MIN_RIDERSHIP_BY_METROSTATION;
            case SEARCH_BY_MAX_COMMENT_WORD_COUNT -> SELECT_HOURS_BY_MAX_COMMENT_WORD_COUNT_BY_METROSTATION;
            default -> throw new IllegalArgumentException("Unexpected value: " + searching);
        };

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, metroStationName);
            preparedStatement.setString(2, metroStationName);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<HourForDB> list = getHoursWithMSNameFromResultSet(resultSet);
            resultSet.close();

            return list;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the total ridership for all Operating Hours in the database.
     * @return a total ridership for all Hours retrieved from the database.
     */
    public static int getTotalRidership() {
        try {
            if (getMetroStations(Sort.UNSORTED).getList().isEmpty()
                    || getHours(Sort.UNSORTED).isEmpty()) {
                return -1;
            }

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TOTAL_RIDERSHIP);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int totalRidership = resultSet.getInt("TotalRidership");
            resultSet.close();

            return totalRidership;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the total ridership for Operating Hours of a specified Metro Station in the database.
     * @param metroStationName the name of the Metro Station;
     * @return a total ridership for Hours of a specified Metro Station retrieved from the database.
     */
    public static int getTotalRidership(String metroStationName) {
        try {
            if (getMetroStationByName(metroStationName).getId() < 1
                    || getHours(Sort.UNSORTED).isEmpty()) {
                return -1;
            }

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TOTAL_RIDERSHIP_FOR_METROSTATION);
            preparedStatement.setString(1, metroStationName);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int totalRidership = resultSet.getInt("TotalRidership");
            resultSet.close();

            return totalRidership;
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a string representation of the data about Metro Station on the console.
     * @param metroStation the {@link MetroStationForDB} object to print.
     */
    public static String metroStationToString(MetroStationForDB metroStation) {
        return "MetroStationID" +
                "\t  Name" +
                "\t\t\t\t  Opened\n" +
                metroStation.toString();
    }

    /**
     * Creates a string representation of the data about Metro Station and its Operating Hours on the console.
     * @param metroStation the {@link MetroStationForDB} object to print.
     */
    public static String metroStationWithHoursToString(MetroStationForDB metroStation) {
        return metroStationToString(metroStation) +
                hoursToString(metroStation.getHourList());
    }

    /**
     * Creates a string representation of the data about Metro Stations on the console.
     * @param metroStations the {@link MetroStations} object to print.
     */
    public static String metroStationsToString(MetroStations metroStations) {
        if (!metroStations.getList().isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MetroStationID")
                    .append("\t  Name")
                    .append("\t\t\t\t  Opened\n");
            metroStations.getList()
                    .forEach(metroStation ->
                            stringBuilder.append(metroStation.toString()));

            return stringBuilder.toString();
        }

         return "";
    }

    /**
     * Creates a string representation of the data about Metro Stations and their Operating Hours on the console.
     * @param metroStations the {@link MetroStations} object to print.
     */
    public static String metroStationsWithHoursToString(MetroStations metroStations) {
        StringBuilder stringBuilder = new StringBuilder();
        metroStations.getList()
                .forEach(metroStation -> {
                    stringBuilder.append(metroStationToString(metroStation));
                    stringBuilder.append(hoursToString(metroStation.getHourList()));
                });

        return stringBuilder.toString();
    }

    /**
     * Creates a string representation of the data about Operating Hour
     * from the {@link HourForDB} object on the console.
     * @param hour the {@link HourForDB} object to print.
     */
    public static String hourToString(HourForDB hour) {
        return "HourID" +
                "\t  Ridership" +
                "\t\tComment\n" +
                hour.toStringWithMSID();
    }

    /**
     * Creates a string representation of the data about Operating Hours
     * from the list of {@link HourForDB} on the console.
     * @param hours list of {@link HourForDB} to print.
     */
    public static String hoursToString(List<HourForDB> hours) {
        if (!hours.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("HourID")
                    .append("\t  Ridership")
                    .append("\t\tComment\n");
            hours.forEach(hour ->
                    stringBuilder.append(hour.toString()));

            return stringBuilder.toString();
        }

        return "";
    }

    /**
     * Creates a string representation of the data about Operating Hours including their Metro Station ID
     * from the list of {@link HourForDB} on the console.
     * @param hours list of {@link HourForDB} to print.
     */
    public static String hoursWithMSIDToString(List<HourForDB> hours) {
        if (!hours.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("HourID")
                    .append("\t  Ridership")
                    .append("\t\tComment")
                    .append(" \t\t\t\tMetroStationID\n");
            hours.forEach(hour ->
                    stringBuilder.append(hour.toStringWithMSID()));

            return stringBuilder.toString();
        }

        return "";
    }

    /**
     * Creates a string representation of the data about Operating Hours including theirs Metro Station Name
     * from the list of {@link HourForDB} on the console.
     * @param hours list of {@link HourForDB} to print.
     */
    public static String hoursWithMSNameToString(List<HourForDB> hours) {
        if (!hours.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Name")
                    .append("\t\t\tHourID")
                    .append("\tRidership")
                    .append("\tComment\n");
            hours.forEach(hour ->
                    stringBuilder.append(hour.toStringWithMSName()));

            return stringBuilder.toString();
        }

        return "";
    }

    /**
     *  Displays all the Metro Stations data from database.
     */
    public static void showMetroStations() {
        System.out.print(
                metroStationsToString(
                        getMetroStationsWithHours()) + "\n");
    }

    /**
     *  Displays all the Metro Stations data with theirs Operating Hours from database.
     */
    public static void showMetroStationsWithHours() {
        System.out.print(
                metroStationsWithHoursToString(
                        getMetroStationsWithHours()) + "\n");
    }

    /**
     *  Displays all the Metro Stations data with theirs Operating Hours from database
     *  for a specified Metro Station.
     *  @param metroStationName the name of MetroStation to search.
     */
    public static void showMetroStationsWithHoursByName(String metroStationName) {
        System.out.print(
                metroStationWithHoursToString(
                        getMetroStationWithHoursByName(metroStationName)) + "\n");
    }

    /**
     *  Displays the Metro Stations data from database sorted by ascending name.
     */
    public static void showMetroStationsSortedByAscName() {
        System.out.print(
                metroStationsToString(
                        getMetroStations(Sort.SORTED_BY_ASC_NAME)) + "\n");
    }

    /**
     *  Displays the Metro Stations data from database sorted by descending name.
     */
    public static void showMetroStationsSortedByDescName() {
        System.out.print(
                metroStationsToString(
                        getMetroStations(Sort.SORTED_BY_DESC_NAME)) + "\n");
    }

    /**
     *  Displays the Metro Stations data from database sorted by ascending opened year.
     */
    public static void showMetroStationsSortedByAscOpened() {
        System.out.print(
                metroStationsToString(
                        getMetroStations(Sort.SORTED_BY_ASC_OPENED)) + "\n");
    }

    /**
     *  Displays the Metro Stations data from database sorted by descending opened year.
     */
    public static void showMetroStationsSortedByDescOpened() {
        System.out.print(
                metroStationsToString(
                        getMetroStations(Sort.SORTED_BY_DESC_OPENED)) + "\n");
    }

    /**
     * Displays Operating Hours data from the database.
     */
    public static void showHours() {
        System.out.println(
                hoursWithMSIDToString(
                        getHours(Sort.UNSORTED)));
    }

    /**
     *  Displays the results of a search for Operating Hours data
     *  with the specified word or word fragment in the comment.
     */
    public static void showHoursWithWordInComment(String word) {
        System.out.println(
                hoursWithMSNameToString(
                        findHoursWithWordInComment(word)));
    }

    /**
     *  Displays the results of a search for Operating Hours data
     *  with the minimum ridership.
     */
    public static void showHoursWithMinRidership() {
        System.out.print(
                hoursWithMSNameToString(
                        findHours(Search.SEARCH_BY_MIN_RIDERSHIP)) + "\n");
    }

    /**
     *  Displays the results of a search for Operating Hours data
     *  with the minimum ridership for a specified Metro Station.
     * @param metroStationName the name of MetroStation to search.
     */
    public static void showHoursWithMinRidershipForMS(String metroStationName) {
        System.out.print(
                hoursWithMSNameToString(
                        findHours(
                                Search.SEARCH_BY_MIN_RIDERSHIP,
                                metroStationName)) + "\n");
    }

    /**
     *  Displays the results of a search for Operating Hours data
     *  with the maximum word count of comment.
     */
    public static void showHoursWithMaxCommentWordCount() {
        System.out.print(
                hoursWithMSNameToString(
                        findHours(
                                Search.SEARCH_BY_MAX_COMMENT_WORD_COUNT)) + "\n");
    }

    /**
     *  Displays the results of a search for Operating Hours data
     *  with the maximum word count of comment for a specified Metro Station.
     */
    public static void showHoursWithMaxCommentWordCountForMS(String metroStationName) {
        System.out.print(
                hoursWithMSNameToString(
                        findHours(
                                Search.SEARCH_BY_MAX_COMMENT_WORD_COUNT,
                                metroStationName)) + "\n");
    }

    /**
     * Displays Operating Hours data sorted by ascending ridership.
     */
    public static void showHoursSortedByAscRidership() {
        System.out.print(
                hoursWithMSIDToString(
                        getHours(Sort.SORTED_ASC_RIDERSHIP)));
    }

    /**
     * Displays Operating Hours data sorted by descending ridership.
     */
    public static void showHoursSortedByDescRidership() {
        System.out.print(
                hoursWithMSIDToString(
                        getHours(Sort.SORTED_DESC_RIDERSHIP)));
    }

    /**
     * Displays Operating Hours data sorted by ascending comment length.
     */
    public static void showHoursSortedByAscCommentLength() {
        System.out.print(
                hoursWithMSIDToString(
                        getHours(Sort.SORTED_ASC_COMMENT_LENGTH)));
    }

    /**
     * Displays Operating Hours data sorted by descending comment length.
     */
    public static void showHoursSortedByDescCommentLength() {
        System.out.print(
                hoursWithMSIDToString(
                        getHours(Sort.SORTED_DESC_COMMENT_LENGTH)));
    }
}