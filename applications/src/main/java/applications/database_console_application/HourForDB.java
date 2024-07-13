package applications.database_console_application;

import applications.console_application.Hour;


/**
 * Represents an Hour record for the database, extending the base {@link Hour} class.
 */
public class HourForDB extends Hour {
    /**
     * The unique identifier of the Hour.
     */
    private long id = -1;

    /**
     * The unique identifier of the Metro Station to which the Hour belongs.
     */
    private long metroStationID = -1;

    /**
     * The name of the Metro Station to which the Hour belongs.
     */
    private String metroStationName;

    /**
     * The constructor initialises the Hour object with the default values.
     */
    public HourForDB() {}

    /**
     * The constructor initialises the Hour object with the specified values.
     * @param ridership the ridership of the Hour;
     * @param comment the comment to ridership of the Hour.
     */
    public HourForDB(int ridership, String comment) { super(ridership, comment);}

    /**
     * The constructor initialises the Hour object with the specified values.
     * @param id the unique identifier of the Hour;
     * @param ridership the ridership of the Hour;
     * @param comment the comment to ridership of the Hour;
     * @param metroStationID the unique identifier of the Metro Station to which the Hour belongs.
     */
    public HourForDB(long id, int ridership, String comment, long metroStationID) {
        super(ridership, comment);
        setId(id);
        setMetroStationID(metroStationID);
    }

    /**
     * Gets the unique identifier of the Hour.
     * @return the unique identifier of the Hour.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the Hour.
     * @param id the unique identifier of the Hour.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the unique identifier of the Metro Station to which the Hour belongs.
     * @return the unique identifier of the Metro Station.
     */
    public long getMetroStationID() {
        return metroStationID;
    }

    /**
     * Sets the unique identifier of the Metro Station to which the Hour belongs.
     * @param metroStationID the unique identifier of the Metro Station.
     */
    public void setMetroStationID(long metroStationID) {
        this.metroStationID = metroStationID;
    }

    /**
     * Gets the name of the Metro Station to which the Hour belongs.
     * @return the name of the Metro Station.
     */
    public String getMetroStationName() {
        return metroStationName;
    }

    /**
     * Sets the name of the Metro Station to which the Hour belongs.
     * @param metroStationName the name of the Metro Station.
     */
    public void setMetroStationName(String metroStationName) {
        this.metroStationName = metroStationName;
    }

    /**
     * Provides a string representation of Hour list data.
     * @return string representation of Hour list data.
     */
    @Override
    public String toString() {
        return getId() +
                "\t\t  " + getRidership() +
                "\t\t\t" + getComment() + "\n";
    }

    /**
     * Provides a string representation of Hour list data including its Metro Station ID.
     * @return string representation of Hour list data.
     */
    public String toStringWithMSID() {
        return getId() +
                "\t  " + getRidership() +
                "\t\t" + getComment() +
                " \t\t\t\t" + getMetroStationID() + "\n";
    }

    /**
     * Provides a string representation of Hour list data including its Metro Station name.
     * @return string representation of Hour list data.
     */
    public String toStringWithMSName() {
        return getMetroStationName() +
                "\t" + getId() +
                "\t\t" + getRidership() +
                "  \t\t" + getComment() + "\n";
    }

    /**
     * Provides a string representation of Hour list data line by line for its fields.
     * @return string representation of Hour list data line by line.
     */
    public String toStringLines() {
        return "HourID:\t\t\t\t\t" + getId() +
                "\nRidership:\t\t\t\t" + getRidership() +
                "\nComment:\t\t\t\t" + getComment() +
                "\nMetroStationID:\t\t" + getMetroStationID() + "\n";
    }
}
