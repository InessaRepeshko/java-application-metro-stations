package applications.console_application;

/**
 * An abstract class {@link MetroStationWithCollection} representing a Metro Station with a collection of operating
 * hours. Extends the {@link AbstractMetroStation} class.
 */
public abstract class MetroStationWithCollection extends AbstractMetroStation {
    /** The name of the metro station. */
    private String name;

    /** The opened year of the metro station. */
    private int opened;

    /**
     * The constructor initialises the object with the default values.
     */
    public MetroStationWithCollection() {}

    /**
     * The constructor initialises the object with the specified values with metro station {@code name}
     * and {@code opened} year.
     * @param name the name of metro station;
     * @param opened the opened year of metro station.
     */
    public MetroStationWithCollection(String name, int opened) {
        this.name = name;
        this.opened = opened;
    }

    public abstract void setHour(int i, Hour hour);

    /**
     * Gets the {@code name} for the metro station.
     * @return the {@code name} of metro station.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets the {@code name} for the metro station.
     * @param name the {@code name} of metro station to be set.
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the {@code opened} year for the metro station.
     * @return the {@code opened} year of metro station.
     */
    @Override
    public int getOpened() {
        return opened;
    }

    /**
     * Sets the {@code opened} year for the metro station.
     * @param opened the {@code opened} year of metro station to be set.
     */
    @Override
    public void setOpened(int opened) {
        this.opened = opened;
    }

    /**
     * Performs testing of the functionality of the {@code MetroStationWithCollection} class.
     */
    public void testMetroStationWithCollection() {
        System.out.println("Initial Metro Station data:");
        System.out.println(this);

        Hour[] hoursArray = {
                new Hour(23, "Very low ridership"),
                new Hour(345, "Medium ridership"),
                new Hour(87, "Low ridership"),
                new Hour(1007, "Very high ridership")
        };

        System.out.println("Get Metro Station Name and Opened Year:");
        System.out.println("Name:\t" + getName() + "\tOpened:\t" + getOpened());
        System.out.println();

        System.out.println("Reset the Operating Hours for the Metro Station:");
        setHours(hoursArray);
        System.out.println(this);

        System.out.println("Set the Operating Hour by index and get all Operating Hours:");
        setHour(0, new Hour(250, "Medium ridership"));
        hoursArray = getHours();
        for (Hour hour : hoursArray) {
            System.out.println(hour);
        }
        System.out.println();

        System.out.println("Get Operating Hour by index:");
        System.out.println(getHour(1));
        System.out.println("Get count of all Operating Hours:\t" + countHours());
        System.out.println();
    }
}
