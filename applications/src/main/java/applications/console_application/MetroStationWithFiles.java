package applications.console_application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a metro station with file-related operations.
 * Stream API tools are used for processing and outputting sequences.
 * This class is inherited from the {@link MetroStationWithStreams}.
 */
public class MetroStationWithFiles extends MetroStationWithStreams {
    /**
     * Default constructor.
     */
    public MetroStationWithFiles() {
    }

    /**
     * Constructor with parameters.
     * @param name   The name of the metro station.
     * @param opened The year the metro station was opened.
     * @param hours  The list of hours for the metro station.
     */
    public MetroStationWithFiles(String name, int opened, List<Hour> hours) {
        super(name, opened, hours);
    }

    /**
     * Converts the metro station information to a list of strings using Stream API.
     * @return The list of strings representing the metro station information.
     */
    public List<String> toListOfStrings() {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("Station: Name:\t'" + getName() + "'.\tOpened:\t" + getOpened() + ".\tHours:");

        if (!getHoursList().isEmpty()) {
            getHoursList().forEach(hour ->
                    stringList.add("Hour\t{ ridership = " + hour.getRidership() + ",\tcomment = '" + hour.getComment() + "' }"));
        } else {
            stringList.add("There are no hours for this station.");
        }

        return stringList;
    }

    /**
     * Populates the metro station information from a list of strings using Stream API.
     * @param stringList The list of strings containing the metro station information.
     */
    public void fromListOfStrings(List<String> stringList) {
        Matcher matcher = Pattern.compile("Name:\\s*'([^']*)'.").matcher(stringList.get(0));
        setName(matcher.find() ? matcher.group(1) : "");
        matcher = Pattern.compile("Opened:\\s*(\\d{4}).").matcher(stringList.get(0));
        setOpened(Integer.parseInt(matcher.find() ? matcher.group(1) : "0"));
        stringList.remove(0);

        stringList.forEach(s -> {
            Matcher match = Pattern.compile("ridership\\s*=\\s*(\\d+)").matcher(s);
            String ridership = match.find() ? match.group(1) : "0";
            match = Pattern.compile("comment\\s*=\\s*'([^']*)'").matcher(s);
            String comment = match.find() ? match.group(1) : "";
            addHour(Integer.parseInt(ridership), comment);
        });
    }

    /**
     * Creates a sample MetroStationWithFiles object for demonstration purposes.
     * @return A sample MetroStationWithFiles object.
     */
    public static MetroStationWithFiles createMetroStationWithFiles() {
        MetroStationWithFiles metroStation = new MetroStationWithFiles("Politekhnichna", 1984, /*new ArrayList<>()*/
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

        return metroStation;
    }
}
