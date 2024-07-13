package applications.console_application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static applications.console_application.FileUtils.*;

/**
 * The {@code MetroStationWithFilesDemo} class is a demonstration of functionality of the {@link MetroStationWithFiles}.
 */
public class MetroStationWithFilesDemo {
    private static final String TXT_FILE_PATH = "src/main/resources/applications/console_application/MetroStation.txt";
    private static final String XML_FILE_PATH = "src/main/resources/applications/console_application/MetroStation.xml";
    private static final String JSON_FILE_PATH = "src/main/resources/applications/console_application/MetroStation.json";

    /**
     * Performs demonstration of functionality of the {@link MetroStationWithFiles}.
     * The {@code args} are not used.
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(MetroStationWithFilesDemo.class);
        FileUtils.setLogger(logger);
        logger.info("Program started");

        MetroStationWithFiles metroStation = MetroStationWithFiles.createMetroStationWithFiles();

        writeToTXT(TXT_FILE_PATH, metroStation);
        metroStation = readFromTXT(TXT_FILE_PATH);
        logger.info("Metro Station read from TXT file:\n" + metroStation);

        serializeToXML(XML_FILE_PATH, metroStation);
        metroStation = deserializeFromXML(XML_FILE_PATH);
        logger.info("Metro Station deserialize from XML file:\n" + metroStation);

        serializeToJSON(JSON_FILE_PATH, metroStation);
        metroStation = deserializeFromJSON(JSON_FILE_PATH);
        logger.info("Metro Station deserialize from JSON file:\n" + metroStation);

        logger.info("Program finished");
    }
}
