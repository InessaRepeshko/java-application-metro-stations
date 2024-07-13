package applications.console_application;

import java.util.*;

/**
 * Represents metro station data with an ArrayList of operating hours.
 * This class is inherited from the abstract {@link MetroStationWithCollection}.
 */
public class MetroStationWithList extends MetroStationWithCollection {
    /** List of operating hours of the metro station. */
    private List<Hour> hoursList = new ArrayList<>();

    /**
     * The constructor initialises the metro station object with the default values.
     */
    public MetroStationWithList() {}

    /**
     * The constructor initialises the metro station object with the specified values with {@code name},
     * {@code opened} year and operating {@code hours}.
     * @param name the name of metro station;
     * @param opened the opened year of metro station;
     * @param hours the operating hours of metro station.
     */
    public MetroStationWithList(String name, int opened, ArrayList<Hour> hours) {
        super(name, opened);
        Set<Hour> uniqueSet = new LinkedHashSet<>(hours);
        this.hoursList = new ArrayList<>(uniqueSet);
    }

    /**
     * The constructor initialises the metro station object with the specified values with {@code name} and {@code opened} year.
     * @param name the name of metro station;
     * @param opened the opened year of metro station.
     */
    public MetroStationWithList(String name, int opened) {
        super(name, opened);
    }

    /**
     * Gets the array of operating hours for the metro station.
     * @return the array of hours.
     */
    @Override
    public Hour[] getHours() {
        return hoursList.toArray(new Hour[0]);
    }

    /**
     * Gets the list of operating hours for the metro station.
     * @return the list of operating hours for the metro station.
     */
    public List<Hour> getHoursList() {
        return hoursList;
    }

    /**
     * Sets the list of operating hours for the metro station.
     * @param hours the array of hours to be set.
     */
    @Override
    public void setHours(Hour[] hours) {
        Set<Hour> uniqueSet = new LinkedHashSet<>(Arrays.asList(hours));
        this.hoursList = new ArrayList<>(uniqueSet);
    }

    /**
     * Sets the list of Operating Hours for the Metro Station.
     * @param hours the list of Hours
     */
    protected void setHoursList(List<Hour> hours) {
        this.hoursList = hours;
    }

    /**
     * Gets the {@code hour} with index {@code i} from the hours list.
     * @return the object of class {@code Hour} with index {@code i}.
     */
    @Override
    public Hour getHour(int i) {
        return hoursList.get(i);
    }

    /**
     * Sets the {@code hour} with index {@code i} to hours list.
     * @param i index of {@code hour} in hours list;
     * @param hour the object of class {@code Hour} with index {@code i} to be set.
     */
    @Override
    public void setHour(int i, Hour hour) {
        if (hoursList.contains(hour)) {
            return;
        }

        hoursList.set(i, hour);
    }

    /**
     * Adds a link to the new {@code hour} at the end of the hours list.
     * @param hour the object of class {@code Hour} to be added to the hours list;
     * @return {@code true}, if the link was added successfully, {@code false} otherwise.
     */
    @Override
    public boolean addHour(Hour hour) {
        if (hoursList.contains(hour)) {
            return false;
        }

        return hoursList.add(hour);
    }

    /**
     * Creates a new {@code hour} and adds a link to it at the end of the sequence at the hours list.
     * @param ridership the ridership;
     * @param comment the comment;
     * @return {@code true}, if the link was added successfully, {@code false} otherwise.
     */
    @Override
    public boolean addHour(int ridership, String comment) {
        return addHour(new Hour(ridership, comment));
    }

    /**
     * Counts the number of hours in the sequence at hours list.
     * @return the number of hours.
     */
    @Override
    public int countHours() {
        return hoursList.size();
    }

    /**
     * Removes the sequence of hours from hours list.
     */
    @Override
    public void removeHours() {
        hoursList.clear();
    }

    /**
     * Overridden decreasing ridership sorting method using the standard sort function of class {@code Collections}.
     * Is provided by the implementation of the Comparable interface for the {@code Hour} class.
     */
    @Override
    public void sortByDecreasingRidership() {
        Collections.sort(hoursList);
    }

    /**
     * Overridden descending comment length sorting method using the default sort function of interface {@code List}.
     * Is provided by {@code Comparator}.
     */
    @Override
    public void sortByDescendingCommentLength() {
        hoursList.sort(Comparator.comparing(Hour::getCommentLength).reversed());
    }
}
