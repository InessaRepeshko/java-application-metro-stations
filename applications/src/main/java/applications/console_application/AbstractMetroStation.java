package applications.console_application;

import java.util.Arrays;

/**
 * Abstract class representing metro station with {@code name}, {@code opened} year and operating hour data.
 * Access to the sequence of hours, {@code name} and {@code opened} year is represented by abstract methods.
 */
public abstract class AbstractMetroStation {
    /**
     * Gets the {@code name} for the metro station.
     * The derived class must provide an implementation of this method.
     * @return the {@code name}.
     */
    public abstract String getName();

    /**
     * Sets the {@code name} for the metro station.
     * The derived class must provide an implementation of this method.
     * @param name the {@code name} to be set.
     */
    public abstract void setName(String name);

    /**
     * Gets the {@code opened} year for the metro station.
     * The derived class must provide an implementation of this method.
     * @return the {@code opened}.
     */
    public abstract int getOpened();

    /**
     * Sets the {@code opened} year for the metro station.
     * The derived class must provide an implementation of this method.
     * @param opened the {@code opened} year to be set.
     */
    public abstract void setOpened(int opened);

    /**
     * Gets the {@code hour} with index {@code i}.
     * The derived class must provide an implementation of this method.
     * @param i the index of hour array element;
     * @return the object of class {@code Hour} with index {@code i}.
     */
    public abstract Hour getHour(int i);

    /**
     * Sets the {@code hour} with index {@code i}.
     * The derived class must provide an implementation of this method.
     * @param i index of {@code hour} in array of hours;
     * @param hour the object of class {@code Hour} with index {@code i} to be set.
     */
    public abstract void setHour(int i, Hour hour);

    /**
     * Gets the array of operating hours for the metro station.
     * The derived class must provide an implementation of this method.
     * @return the array of operating hours.
     */
    public abstract Hour[] getHours();

    /**
     * Sets the array of operating hours for the metro station.
     * The derived class must provide an implementation of this method.
     * @param hours the array of operating hours to be set.
     */
    public abstract void setHours(Hour[] hours);

    /**
     * Adds a link to the new operating {@code hour} at the end of the hour array.
     * The derived class must provide an implementation of this method.
     * @param hour the object of class {@code Hour} to be added;
     * @return {@code true}, if the link was added successfully, {@code false} otherwise.
     */
    public abstract boolean addHour(Hour hour);

    /**
     * Creates a new operating {@code hour} and adds a link to it at the end of the hour array.
     * The derived class must provide an implementation of this method.
     * @param ridership the ridership;
     * @param comment the comment;
     * @return {@code true}, if the link was added successfully, {@code false} otherwise.
     */
    public abstract boolean addHour(int ridership, String comment);

    /**
     * Counts the number of hours in the hours array.
     * The derived class must provide an implementation of this method.
     * @return the number of hours.
     */
    public abstract int countHours();

    /**
     * Removes the sequence of hours from hours array.
     * The derived class must provide an implementation of this method.
     */
    public abstract void removeHours();

    /**
     * Provides the string representing the object that is inherited from this abstract class.
     * @return the string representing the object that is inherited from this abstract class.
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Station:\t")
                .append("Name: \'").append(getName()).append("\'.\t")
                .append("Opened: ").append(getOpened()).append(".\t")
                .append("Hours:\n");

        if (countHours() <= 0) {
            string.append("There are no hours for this station.\n");
        } else {
            for (Hour h : getHours()) {
                string.append(h).append("\n");
            }
        }

        return string.toString();
    }

    /**
     * Checks whether this metro station is equivalent to another.
     * @param obj the metro station with which check the equivalence.
     * @return {@code true}, if two weathers are the same, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof AbstractMetroStation ms)) {
            return false;
        }

        if (!ms.getName().equals(getName())
                || Integer.compare(ms.getOpened(), getOpened()) != 0) {
            return false;
        }

        return Arrays.equals(getHours(), ms.getHours());
    }

    /**
     * Calculates the hash code of the metro station.
     * If two objects are equal, they must have the same hash code.
     * If this method is called multiple times on the same object, it must return the same number each time.
     * @return the hash code of the metro station.
     */
    @Override
    public int hashCode() {
        return getName().hashCode() * Integer.hashCode(getOpened()) * Arrays.hashCode(getHours());
    }

    /**
     * An additional static function for adding hour reference to the provided array of hours.
     * @param hours the array to which the hour is added;
     * @param hour the link that is added;
     * @return updated array of hours.
     */
    public static Hour[] addHourToArray(Hour[] hours, Hour hour) {
        if (hour.getRidership() < 0
                || hour.getComment() == null) {
            return hours;
        }

        Hour[] newHours;

        if (hours == null) {
            newHours = new Hour[1];
        } else {
            newHours = new Hour[hours.length + 1];
            System.arraycopy(hours, 0, newHours, 0, hours.length);
        }

        newHours[newHours.length - 1] = hour;

        return newHours;
    }

    /**
     * Calculates the total ridership for an array of metro station operating hours.
     * @return null, if there is no pointer to the hours array, or it is empty, the total ridership otherwise.
     */
    public Integer calculateTotalRidership() {
        if (countHours() == 0) {
            return null;
        }

        int totalRidership = 0;

        for (Hour hour : getHours()) {
            totalRidership += hour.getRidership();
        }

        return totalRidership;
    }

    /**
     * Finds the hours with the minimal ridership in the array of metro station operating hours.
     * @return null, if there is no pointer to the hours array, or it is empty,
     * array of hours with minimal ridership otherwise.
     */
    public Hour[] findHoursWithMinRidership() {
        if (countHours() == 0) {
            return null;
        }

        Hour minHour = getHours()[0];

        for (Hour hour : getHours()) {
            if (hour.getRidership() < minHour.getRidership()) {
                minHour = hour;
            }
        }

        Hour[] hours = null;

        for (Hour hour : getHours()) {
            if (hour.getRidership() == minHour.getRidership()) {
                hours = addHourToArray(hours, hour);
            }
        }

        return hours;
    }

    /**
     * Finds the hours with the maximum count of words in the comment in the array of metro station operating hours.
     * @return null, if there is no pointer to the hours array, or it is empty,
     * array of hours with the maximum word count in comment otherwise
     */
    public Hour[] findHoursWithMaxWordCountOfComment() {
        if (countHours() == 0) {
            return null;
        }

        Hour maxHour = getHours()[0];

        for (Hour hour : getHours()) {
            if (hour.calculateWordCountOfComment() > maxHour.calculateWordCountOfComment()) {
                maxHour = hour;
            }
        }

        Hour[] hours = null;

        for (Hour hour : getHours()) {
            if (hour.calculateWordCountOfComment() == maxHour.calculateWordCountOfComment()) {
                hours = addHourToArray(hours, hour);
            }
        }

        return hours;
    }

    /**
     * Finds the total ridership for an array of metro station operating hours and prints the result to the console.
     */
    public void printTotalRidership() {
        Integer totalRidership = calculateTotalRidership();
        System.out.print("Total ridership for station:\t");

        if (totalRidership == null) {
            System.out.println("There is no ridership hours.");
        } else {
            System.out.println(totalRidership);
        }
    }

    /**
     * Prints the array of hours.
     * @param hours the array of hours to be printed.
     */
    public void printHours(Hour[] hours) {
        for (Hour hour : hours) {
            System.out.println(hour);
        }
    }

    /**
     * Finds the hours with the minimal ridership in the array of metro station operating hours
     * and prints the result to the console.
     */
    public void printHoursWithMinRidership() {
        Hour[] hours = findHoursWithMinRidership();
        System.out.print("Hours with minimal ridership:\t");

        if (hours == null) {
            System.out.println("There is no ridership hours.");
        } else {
            System.out.println();
            printHours(hours);
        }
    }

    /**
     * Finds the hours with the maximum count of words in the comment in the array of metro station operating hours
     * and prints the result to the console.
     */
    public void printHoursWithMaxWordCountOfComment() {
        Hour[] hours = findHoursWithMaxWordCountOfComment();
        System.out.print("Hours with the maximum word count in a comment:\t");

        if (hours == null) {
            System.out.println("There is no ridership hours.");
        } else {
            System.out.println();
            printHours(hours);
        }
    }

    /**
     * Sorts a sequence of hours by decreasing ridership using bubble sorting.
     */
    public void sortByDecreasingRidership() {
        if (countHours() == 0) {
            return;
        }

        boolean unsorted = true;

        while (unsorted) {
            unsorted = false;

            for (int i = 0; i < getHours().length - 1; i++) {
                if (getHours()[i].getRidership() < getHours()[i + 1].getRidership()) {
                    Hour temp = getHours()[i];
                    getHours()[i] = getHours()[i + 1];
                    getHours()[i + 1] = temp;
                    unsorted = true;
                }
            }
        }
    }

    /**
     * Sorts a sequence of hours by descending comment length using insertion sorting.
     */
    public void sortByDescendingCommentLength() {
        if (countHours() == 0) {
            return;
        }

        for (int i = 0; i < getHours().length; i++) {
            Hour key = getHours()[i];
            int j;

            for (j = i - 1; j >= 0
                    && Integer.compare(getHours()[j].getCommentLength(), key.getCommentLength()) < 0; j--) {
                getHours()[j + 1] = getHours()[j];
            }

            getHours()[j + 1] = key;
        }
    }

    /**
     * An additional function for adding hours to a sequence of hours in hours array.
     * @return The object is inherited from this abstract class.
     */
    public AbstractMetroStation createMetroStationHours() {
        System.out.println("Add 6 valid Operating Hours at Metro Station:");
        System.out.print(addHour(320, "Medium ridership") + "\t");
        Hour hour = new Hour(88, "Very low ridership");
        System.out.println(addHour(hour) + "\t"
                + addHour(107, "Low ridership") + "\t"
                + addHour(688, "High ridership") + "\t"
                + addHour(1234, "Very high ridership"));

        System.out.println("Add one Operating Hour with invalid data at Metro Station:\t"
                + addHour(-1, null));

        System.out.println("Add one Operating Hour with duplicate data at Metro Station:\t"
                + addHour(1234, "Very high ridership"));

        return this;
    }

    /**
     * Calls up search methods and print results of searching.
     */
    public void showSearchResults() {
        printTotalRidership();
        printHoursWithMinRidership();
        printHoursWithMaxWordCountOfComment();
    }

    /**
     * Performs testing of search methods.
     */
    public void testSearchData() {
        System.out.println("SEARCHING RESULTS:");
        setName("Universytet");
        setOpened(1984);
        System.out.println("Search data for Metro Station without Operating Hours:");
        removeHours();
        showSearchResults();
        System.out.println();

        System.out.println("Create the Metro Station:");
        createMetroStationHours();
        System.out.println(this);
        showSearchResults();
        System.out.println();

        System.out.println("Add new two Operating Hours with min ridership and max word count in comment for searching:");
        System.out.println(addHour(75, "Very low ridership"));
        System.out.println(addHour(2000, "Maximum possible ridership for station"));
        System.out.println(this);
        showSearchResults();
    }

    /**
     * Performs testing of sorting methods.
     */
    public void testSortingData() {
        System.out.println();
        System.out.println("SORTING RESULTS:");
        setName("Derzhprom");
        setOpened(1995);
        System.out.println("Sort data for Metro Station without Operating Hours:");
        removeHours();
        sortByDecreasingRidership();
        sortByDescendingCommentLength();
        System.out.println(this);

        System.out.println("Create the Metro Station:");
        createMetroStationHours();
        System.out.println(this);

        System.out.println("Sort Operating Hours by decreasing ridership:");
        sortByDecreasingRidership();
        System.out.println(this);

        System.out.println("Sort Operating Hours by descending comment length:");
        sortByDescendingCommentLength();
        System.out.println(this);
    }
}
