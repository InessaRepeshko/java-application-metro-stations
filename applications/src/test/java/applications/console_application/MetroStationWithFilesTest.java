package applications.console_application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class MetroStationWithFilesTest {
    private MetroStationWithFiles metroStation;

    @BeforeEach
    public void setup() {
        metroStation = new MetroStationWithFiles("Politekhnichna", 1984, /*new ArrayList<>()*/
                Arrays.asList(
                        new Hour(1100, "Very high ridership"),
                        new Hour(110, "Low ridership"),
                        new Hour(650, "High ridership"),
                        new Hour(532, "High ridership"),
                        new Hour(60, "Very low ridership"),
                        new Hour(188, "Low ridership"),
                        new Hour(200, "Medium ridership")
                ));
        metroStation.sortByDecreasingRidership();
    }

    @Nested
    class TestMetroStationWithFiles {
        @Test
        @DisplayName("Should create Metro Station with sorted by decreasing ridership Hours")
        public void testSortedMetroStationCreation() {
            MetroStationWithFiles actual = MetroStationWithFiles.createMetroStationWithFiles();

            assertEquals(
                    metroStation,
                    actual,
                    "The Metro Station objects should match");
            assertEquals(
                    metroStation.getName(),
                    actual.getName(),
                    "The Names of Metro Stations should match");
            assertEquals(
                    metroStation.getOpened(),
                    actual.getOpened(),
                    "The Opened years of Metro Stations should match");
            assertArrayEquals(
                    metroStation.getHoursList().toArray(),
                    actual.getHoursList().toArray(),
                    "The list of Hours of Metro Stations should match");
        }

        @Test
        @DisplayName("Should match the lists of strings (lines) with the data of Metro Stations")
        public void testToListOfStrings() {
            MetroStationWithFiles actual = MetroStationWithFiles.createMetroStationWithFiles();
            assertLinesMatch(
                    metroStation.toListOfStrings(),
                    actual.toListOfStrings());
            assertEquals(
                    metroStation.toListOfStrings().toString(),
                    actual.toListOfStrings().toString());
        }

        @Test
        @DisplayName("Should match the MetroStationWithFiles objects parsed from the list of strings (lines)")
        public void testFromListOfStrings() {
            String file = "src/main/resources/applications/console_application/MetroStation.txt";
            List<String> lines = null;

            try {
                lines = Files.lines(Path.of(file)).collect(Collectors.toList());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            MetroStationWithFiles actual = new MetroStationWithFiles();
            actual.fromListOfStrings(lines);

            assertEquals(metroStation, actual);
        }
    }

    @Nested
    class TestTXTFileIO {
        @Test
        @DisplayName("Should match the input TXT file contents when writing to TXT")
        public void testWriteToTXT() throws IOException {
            String actualFile = "src/main/resources/applications/console_application/testWriteToTXT.txt";
            Path actualPath = Path.of(actualFile);
            FileUtils.writeToTXT(actualFile, metroStation);
            assertTrue(Files.exists(actualPath));

            List<String> lines = null;

            try {
                lines = Files.lines(actualPath).collect(Collectors.toList());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            assertFalse(lines.isEmpty());
            assertLinesMatch(metroStation.toListOfStrings(), lines);

            Files.deleteIfExists(actualPath);
        }

        @Test
        @DisplayName("Should throw exception when writing to TXT with invalid path to file")
        public void testWriteToTXTException() {
            String invalidPath = "invalid/path/MetroStation.txt";
            assertThrows(RuntimeException.class, () -> FileUtils.writeToTXT(invalidPath, metroStation));
        }

        @Test
        @DisplayName("Should match the MetroStationWithFiles objects when reading from TXT file")
        public void testReadFromTXT() throws IOException {
            String actualFile = "src/main/resources/applications/console_application/MetroStation.txt";
            MetroStationWithFiles actual = FileUtils.readFromTXT(actualFile);

            assertEquals(
                    metroStation,
                    actual,
                    "The Metro Station objects should match");
            assertEquals(
                    metroStation.getName(),
                    actual.getName(),
                    "The Names of Metro Stations should match");
            assertEquals(
                    metroStation.getOpened(),
                    actual.getOpened(),
                    "The Opened years of Metro Stations should match");
            assertArrayEquals(
                    metroStation.getHoursList().toArray(),
                    actual.getHoursList().toArray(),
                    "The list of Hours of Metro Stations should match");
        }

        @Test
        @DisplayName("Should throw exception when reading from TXT with invalid path to file")
        public void testReadFromTXTException() {
            String invalidPath = "invalid/path/MetroStation.txt";
            assertThrows(RuntimeException.class, () -> FileUtils.readFromTXT(invalidPath));
        }
    }

    @Nested
    class TestXMLFileSerialization {
        @Test
        @DisplayName("Should match the input XML file contents when serializing to XML")
        public void testSerializeToXML() throws IOException {
            String actualFile = "src/main/resources/applications/console_application/testSerializeToXML.txt";
            Path actualPath = Path.of(actualFile);
            FileUtils.serializeToXML(actualFile, metroStation);
            assertTrue(Files.exists(actualPath));

            String expectedFile = "src/main/resources/applications/console_application/MetroStation.xml";
            Path expectedPath = Path.of(expectedFile);
            assertTrue(Files.exists(expectedPath));

            List<String> expectedLines = null;
            List<String> actualLines = null;

            try {
                expectedLines = Files.lines(expectedPath).collect(Collectors.toList());
                actualLines = Files.lines(actualPath).collect(Collectors.toList());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            assertFalse(expectedLines.isEmpty());
            assertFalse(actualLines.isEmpty());
            assertLinesMatch(expectedLines, actualLines);

            Files.deleteIfExists(actualPath);
        }

        @Test
        @DisplayName("Should throw exception when serialize to XML with invalid path to file")
        public void testSerializeToXMLException() {
            String invalidPath = "invalid/path/MetroStation.xml";
            assertThrows(RuntimeException.class, () -> FileUtils.serializeToXML(invalidPath, metroStation));
        }

        @Test
        @DisplayName("Should match the MetroStationWithFiles objects when deserializing from XML file")
        public void testDeserializeFromXML() throws IOException {
            String actualFile = "src/main/resources/applications/console_application/MetroStation.xml";
            MetroStationWithFiles actual = FileUtils.deserializeFromXML(actualFile);

            assertEquals(
                    metroStation,
                    actual,
                    "The Metro Station objects should match");
            assertEquals(
                    metroStation.getName(),
                    actual.getName(),
                    "The Names of Metro Stations should match");
            assertEquals(
                    metroStation.getOpened(),
                    actual.getOpened(),
                    "The Opened years of Metro Stations should match");
            assertArrayEquals(
                    metroStation.getHoursList().toArray(),
                    actual.getHoursList().toArray(),
                    "The list of Hours of Metro Stations should match");
        }

        @Test
        @DisplayName("Should throw exception when deserializing from XML with invalid path to file")
        public void testDeserializeFromXMLException() {
            String invalidPath = "invalid/path/MetroStation.xml";
            assertThrows(RuntimeException.class, () -> FileUtils.deserializeFromXML(invalidPath));
        }
    }

    @Nested
    class TestJSONFileSerialization {
        @Test
        @DisplayName("Should match the input JSON file contents when serializing to JSON")
        public void testSerializeToJSON() throws IOException {
            String actualFile = "src/main/resources/applications/console_application/testSerializeToJSON.json";
            Path actualPath = Path.of(actualFile);
            FileUtils.serializeToJSON(actualFile, metroStation);
            assertTrue(Files.exists(actualPath));

            String expectedFile = "src/main/resources/applications/console_application/MetroStation.json";
            Path expectedPath = Path.of(expectedFile);
            assertTrue(Files.exists(expectedPath));

            List<String> expectedLines = null;
            List<String> actualLines = null;

            try {
                expectedLines = Files.lines(expectedPath).collect(Collectors.toList());
                actualLines = Files.lines(actualPath).collect(Collectors.toList());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            assertFalse(expectedLines.isEmpty());
            assertFalse(actualLines.isEmpty());
            assertLinesMatch(expectedLines, actualLines);

            Files.deleteIfExists(actualPath);
        }

        @Test
        @DisplayName("Should throw exception when serialize to JSON with invalid path to file")
        public void testSerializeToJSONException() {
            String invalidPath = "invalid/path/MetroStation.json";
            assertThrows(RuntimeException.class, () -> FileUtils.serializeToJSON(invalidPath, metroStation));
        }

        @Test
        @DisplayName("Should match the MetroStationWithFiles objects when deserializing from JSON file")
        public void testDeserializeFromJSON() throws IOException {
            String outputFile = "src/main/resources/applications/console_application/MetroStation.json";
            MetroStationWithFiles actual = FileUtils.deserializeFromJSON(outputFile);

            assertEquals(
                    metroStation,
                    actual,
                    "The Metro Station objects should match");
            assertEquals(
                    metroStation.getName(),
                    actual.getName(),
                    "The Names of Metro Stations should match");
            assertEquals(
                    metroStation.getOpened(),
                    actual.getOpened(),
                    "The Opened years of Metro Stations should match");
            assertArrayEquals(
                    metroStation.getHoursList().toArray(),
                    actual.getHoursList().toArray(),
                    "The list of Hours of Metro Stations should match");
        }

        @Test
        @DisplayName("Should throw exception when deserializing from JSON with invalid path to file")
        public void testDeserializeFromJSONException() {
            String invalidPath = "invalid/path/MetroStation.json";
            assertThrows(RuntimeException.class, () -> FileUtils.deserializeFromJSON(invalidPath));
        }
    }
}
