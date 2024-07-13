package applications.database_console_application;

import applications.console_application.MetroStationWithFiles;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a Metro Station record for the database, extending the base {@link MetroStationWithFiles} class.
 */
public class MetroStationForDB extends MetroStationWithFiles {
    /**
     * The unique identifier of the Metro Station.
     */
    private long id = -1;

    /**
     * The list of Hours for the Metro Station.
     */
    private List<HourForDB> hours = new ArrayList<>();

    /**
     * The constructor initialises the Metro Station object with the default values.
     */
    public MetroStationForDB() {}

    /**
     * Constructor initialises the Metro Station object with the specified values.
     * @param name the name of the Metro Station;
     * @param opened the year the Metro Station was opened.
     */
    public MetroStationForDB(String name, int opened) {
        setName(name);
        setOpened(opened);
    }

    /**
     * Constructor initialises the Metro Station object with the specified values.
     * @param id the unique identifier of the Metro Station;
     * @param name the name of the Metro Station;
     * @param opened the year the Metro Station was opened.
     */
    public MetroStationForDB(long id, String name, int opened) {
        setId(id);
        setName(name);
        setOpened(opened);
    }

    /**
     * Constructor with parameters.
     * @param name   The name of the Metro Station.
     * @param opened The year the Metro Station was opened.
     * @param hours  The list of Hours for the Metro Station.
     */
    public MetroStationForDB(String name, int opened, List<HourForDB> hours) {
        setName(name);
        setOpened(opened);
        setHourList(hours);
    }

    /**
     * Gets the unique identifier of the Metro Station.
     * @return the unique identifier of the Metro Station.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the Metro Station.
     * @param id the unique identifier of the Metro Station.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the list of Hours for the Metro Station.
     * @return the list of Hours for the Metro Station.
     */
    public List<HourForDB> getHourList() {
        return hours;
    }

    /**
     * Sets the list of Hours for the Metro Station.
     * @param hourList the list of Hours for the Metro Station.
     */
    public void setHourList(List<HourForDB> hourList) {
        this.hours.clear();
        this.hours.addAll(hourList);
    }

    /**
     * Adds an Hour to the Metro Station's Hour list.
     * @param hour the Hour to add.
     */
    public void addHour(HourForDB hour) {
        this.hours.add(hour);
    }

    /**
     * Adds a list of Hours to the Metro Station's Hour list.
     * @param hours the list of Hours to add.
     */
    public void addHours(List<HourForDB> hours) {
        this.hours.addAll(hours);
    }

    /**
     * Provides a string representation of Metro Station data.
     * @return string representation of Metro Station data.
     */
    @Override
    public String toString() {
        return getId() +
                "\t\t\t\t  " + getName() +
                "\t\t" + getOpened() + "\n";
    }

    /**
     * Provides a string representation of Metro Station data line by line for its fields.
     * @return string representation of Metro Station data line by line.
     */
    public String toStringLines() {
        return "MetroStationID:\t\t" + getId() +
                "\nName:\t\t\t\t\t" + getName() +
                "\nOpened:\t\t\t\t" + getOpened() + "\n";
    }
}
