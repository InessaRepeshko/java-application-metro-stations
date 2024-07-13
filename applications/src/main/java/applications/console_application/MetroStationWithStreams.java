package applications.console_application;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents Metro Station data with a list of Hours.
 * Stream API tools are used to process sequences of elements.
 * This class is inherited from the {@link MetroStationWithList}.
 */
public class MetroStationWithStreams extends MetroStationWithList {
    /** The constructor initialises the Metro Station object with the default values. */
    public MetroStationWithStreams() {
    }

    /**
     * The constructor initialises the Metro Station object with the specified values with {@code name},
     * {@code opened} year and operating {@code hours}.
     * @param name the name of Metro Station;
     * @param opened the opened year of Metro Station;
     * @param hours the Operating Hours of Metro Station.
     */
    public MetroStationWithStreams(String name, int opened, List<Hour> hours) {
        super(name, opened);
        setHoursList(hours);
    }

    /**
     * The constructor initialises the Metro Station object with the specified values with {@code name} and {@code opened} year.
     * @param name the name of metro station;
     * @param opened the opened year of metro station.
     */
    public MetroStationWithStreams(String name, int opened) {
        super(name, opened);
    }

    /**
     * Sets the list of Hours for the Metro Station.
     * @param hours the list of Operating Hours.
     */
    @Override
    public void setHoursList(List<Hour> hours) {
        super.setHoursList(hours.stream().collect(Collectors.toList()));
    }

    /**
     * Sets the list of Operating Hours for the Metro Station.
     * @param hours the array of Operating Hours.
     */
    @Override
    public void setHours(Hour[] hours) {
        setHoursList(Arrays.asList(hours));
    }

    /** Overridden decreasing ridership sorting method using Stream API with the help of the {@link Comparator} interface. */
    @Override
    public void sortByDecreasingRidership() {
        setHoursList(getHoursList().stream()
                .sorted(Comparator.comparing(Hour::getRidership).reversed())
                .collect(Collectors.toList()));
    }

    /** Overridden descending comment length sorting method using Stream API with the help of the {@link Comparator} interface. */
    @Override
    public void sortByDescendingCommentLength() {
        setHoursList(getHoursList().stream()
                .sorted(Comparator.comparing(Hour::getCommentLength).reversed())
                .collect(Collectors.toList()));
    }

    /**
     * Calculates the total ridership for an array of Metro Station Operating Hours.
     * @return null if the list of Hours is empty or if there will be problems with the calculation;
     * the total ridership otherwise.
     */
    @Override
    public Integer calculateTotalRidership() {
        if (getHoursList().isEmpty()) {
            return null;
        }

        return getHoursList().stream().mapToInt(Hour::getRidership).sum();
    }

    /**
     * Finds the hours with the minimal ridership in the list of Metro Station Operating Hours.
     * @return an array of {@code Hour} objects with the minimal ridership;
     * if the list of Hours is empty, returns null.
     */
    @Override
    public Hour[] findHoursWithMinRidership() {
        Hour hourWithMinRidership = getHoursList().stream()
                .min(Comparator.comparing(Hour::getRidership))
                .orElse(null);

        if (hourWithMinRidership == null) {
            return null;
        }

        return getHoursList().stream()
                .filter(hour -> hour.getRidership() == hourWithMinRidership.getRidership())
                .toArray(Hour[]::new);
    }

    /**
     * Finds the hours with the maximum count of words in the comment in the list of Metro Station Operating Hours.
     * @return An array of {@code Hour} objects with the  maximum count of words in the comment;
     * if the list of Hours is empty, returns null.
     */
    @Override
    public Hour[] findHoursWithMaxWordCountOfComment() {
        Hour hourWithMaxWordCountOfComment = getHoursList().stream()
                .max(Comparator.comparing(Hour::calculateWordCountOfComment))
                .orElse(null);

        if (hourWithMaxWordCountOfComment == null) {
            return null;
        }

        return getHoursList().stream()
                .filter(hour -> hour.calculateWordCountOfComment() == hourWithMaxWordCountOfComment.calculateWordCountOfComment())
                .toArray(Hour[]::new);
    }

    /**
     * Finds hours with a word fragment at the start or end of the comment.
     * @param text The word fragment to search for.
     * @return An array of Hour objects.
     */
    public Hour[] findHoursWithWordFragmentAtStartOrEndOfComment(String text) {
        return Arrays.stream(getHours())
                .filter((hour -> {
                    for (String word : hour.getComment().split("\\s")) {
                        if (word.startsWith(text) || word.endsWith(text)) {
                            return true;
                        }
                    }

                    return false;
                }))
                .toArray(Hour[]::new);
    }

    /**
     * Demonstrates the functionality of the {@code TramStationWithStreams}  class.
     * Prints the created Metro Station, performs a search test and a sorting test.
     */
    public void testMetroStationWithStreams() {
        System.out.println("The Metro Station created:\n" + this);
        this.testSearchData();
        this.testSortingData();
    }

    /**
     * Creates a new instance of {@code TramStationWithStreams} with predefined values.
     * @return the object of class {@code TramStationWithStreams}.
     */
    public static MetroStationWithStreams createMetroStationWithStreams() {
        return new MetroStationWithStreams("Politekhnichna", 1984,
                Arrays.asList(
                        new Hour(1100, "Very high ridership"),
                        new Hour(110, "Low ridership"),
                        new Hour(650, "High ridership"),
                        new Hour(532, "High ridership"),
                        new Hour(60, "Very low ridership"),
                        new Hour(188, "Low ridership"),
                        new Hour(200, "Medium ridership"),
                        new Hour(200, "Medium ridership")
                ));
    }
}
