package applications.console_application;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

/**
 * Utility class for handling file operations related to MetroStation objects.
 * It implements the following approaches to working with files:
 * <p> - outputting data to a text file using Stream API with subsequent reading;
 * <p> - serialization of objects into an XML file and a JSON file and corresponding deserialization using the XStream library;
 * <p> - recording events related to program execution in the system log;
 */
public class FileUtils {
    private static Logger logger = null;

    /**
     * Sets the logger to be used by this utility class.
     * @param logger The logger instance.
     */
    public static void setLogger(Logger logger)  {
        FileUtils.logger = logger;
    }

    /**
     * Writes MetroStationWithFiles object to a TXT file using Stream API.
     * @param outputFilePath The path of the output TXT file.
     * @param metroStation   The MetroStationWithFiles object to write.
     */
    public static void writeToTXT(String outputFilePath, MetroStationWithFiles metroStation) {
        if (logger != null) {
            logger.info("Write to TXT file");
        }

        try {
            Files.write(Path.of(outputFilePath), metroStation.toListOfStrings());
        } catch (IOException e) {
            if (logger != null) {
                logger.error(e.toString());
            }

            throw new RuntimeException(e);
        }
    }

    /**
     * Reads MetroStationWithFiles object from a TXT file using Stream API.
     * @param inputFilePath The path of the input TXT file.
     * @return The MetroStationWithFiles object read from the file.
     */
    public static MetroStationWithFiles readFromTXT(String inputFilePath) {
        if (logger != null) {
            logger.info("Read from TXT file");
        }

        try {
            MetroStationWithFiles metroStation = new MetroStationWithFiles();
            metroStation.fromListOfStrings(
                    Files.lines(Path.of(inputFilePath))
                            .collect(Collectors.toList())
            );

            return metroStation;
        } catch (IOException e) {
            if (logger != null) {
                logger.error(e.toString());
            }

            throw new RuntimeException(e);
        }
    }

    /**
     * Serializes MetroStationWithFiles object to an XML file using the XStream library.
     * @param outputFilePath The path of the output XML file.
     * @param metroStation   The MetroStationWithFiles object to serialize.
     */
    public static void serializeToXML(String outputFilePath, MetroStationWithFiles metroStation) {
        if (logger != null) {
            logger.info("Serializing to XML");
        }

        XStream xStream = new XStream();
        xStream.alias("metroStation", MetroStationWithFiles.class);
        xStream.alias("hour", Hour.class);
        String xml = xStream.toXML(metroStation);

        try {
            Files.write(Path.of(outputFilePath), xml.getBytes());
        } catch (IOException e) {
            if (logger != null) {
                logger.error(e.toString());
            }

            throw new RuntimeException(e);
        }
    }

    /**
     * Deserializes MetroStationWithFiles object from a JSON file using the XStream library.
     * @param inputFilePath The path of the input JSON file.
     * @return The MetroStationWithFiles object deserialized from the file.
     */
    public static MetroStationWithFiles deserializeFromXML(String inputFilePath) {
        if (logger != null) {
            logger.info("Deserializing from XML");
        }

        try {
            XStream xStream = new XStream();
            xStream.addPermission(AnyTypePermission.ANY);

            xStream.alias("metroStation", MetroStationWithFiles.class);
            xStream.alias("hour", Hour.class);

            String xmlContent = Files.lines(Path.of(inputFilePath)).collect(Collectors.joining());
            MetroStationWithFiles metroStation = (MetroStationWithFiles) xStream.fromXML(xmlContent);

            return metroStation;
        } catch (Exception e) {
            if (logger != null) {
                logger.error(e.toString());
            }

            throw new RuntimeException(e);
        }
    }

    /**
     * Serializes MetroStationWithFiles object to a JSON file using the XStream library.
     * @param outputFilePath The path to the output JSON file.
     * @param metroStation   The MetroStationWithFiles object to be serialized.
     */
    public static void serializeToJSON(String outputFilePath, MetroStationWithFiles metroStation) {
        if (logger != null) {
            logger.info("Serializing to JSON");
        }

        XStream xStream = new XStream(new JettisonMappedXmlDriver());

        xStream.alias("metroStation", MetroStationWithFiles.class);
        xStream.alias("hour", Hour.class);

        String json = xStream.toXML(metroStation);

        try {
            Files.write(Path.of(outputFilePath), json.getBytes());
        } catch (IOException e) {
            if (logger != null) {
                logger.error(e.toString());
            }

            throw new RuntimeException(e);
        }
    }

    /**
     * Deserializes MetroStationWithFiles object from a JSON file using the XStream library.
     * @param inputFilePath The path to the input JSON file.
     * @return The deserialized MetroStationWithFiles object.
     */
    public static MetroStationWithFiles deserializeFromJSON(String inputFilePath) {
        if (logger != null) {
            logger.info("Deserializing from JSON");
        }

        try {
            XStream xStream = new XStream(new JettisonMappedXmlDriver());
            xStream.addPermission(AnyTypePermission.ANY);

            xStream.alias("metroStation", MetroStationWithFiles.class);
            xStream.alias("hour", Hour.class);

            String jsonContent = Files.lines(Path.of(inputFilePath)).collect(Collectors.joining());
            MetroStationWithFiles metroStation = (MetroStationWithFiles) xStream.fromXML(jsonContent);

            return metroStation;
        } catch (Exception e) {
            if (logger != null) {
                logger.error(e.toString());
            }

            throw new RuntimeException(e);
        }
    }
}
