package applications.console_application;

import java.util.Arrays;

/**
 * The {@code Hour} class performs hour with {@code ridership} and {@code comment}.
 */
public class Hour implements Comparable<Hour> {
    /** Ridership is the number of passengers visiting a metro station per hour. */
    private int ridership;

    /** Comment on the {@code ridership} metric. */
    private String comment;

    /**
     * The constructor initialises the hour object with the default values.
     */
    public Hour() {
    }

    /**
     * The constructor initialises the hour object with the specified values.
     * @param ridership the ridership;
     * @param comment the comment.
     */
    public Hour(int ridership, String comment) {
        setRidership(ridership);
        setComment(comment);
    }

    /**
     * Gets the {@code ridership} of the hour.
     * @return the {@code ridership}.
     */
    public int getRidership() {
        if (ridership < 0) {
            return 0;
        }
        return ridership;
    }

    /**
     * Sets the {@code ridership} of the hour.
     * @param ridership the {@code ridership} to be set.
     */
    public void setRidership(int ridership) {
        if (ridership < 0) {
            this.ridership = 0;
        }

        this.ridership = ridership;
    }

    /**
     * Gets the {@code comment} for the hour.
     * @return the {@code comment}.
     */
    public String getComment() {
        if (comment == null) {
            return "";
        }

        return comment;
    }

    /**
     * Sets the {@code comment} for the hour.
     * @param comment the {@code comment} to be set.
     */
    public void setComment(String comment) {
        if (comment == null) {
            this.comment = "";
        }

        this.comment = comment;
    }

    /**
     * Gets the length of a comment in the hour.
     * @return the length of a comment.
     */
    public int getCommentLength() {
        if (comment == null) {
            return 0;
        }

        return getComment().length();
    }

    /**
     * Calculates the count of words of a comment in the hour.
     * @return the length of a comment.
     */
    public int calculateWordCountOfComment() {
        if (comment == null
                || comment.isEmpty()) {
            return 0;
        }

        String[] wordArray = comment.split(" ");

        return wordArray.length;
    }

    /**
     * Provides the string representing the Hour object.
     * @return the string representing the Hour object.
     */
    @Override
    public String toString() {
        return "Hour\t{ "
                + "ridership = " + getRidership()
                + ",\tcomment = \'" + getComment() + "\' }";
    }

    /**
     * Checks metro station this hour is equivalent to another.
     * @param obj the hour with which check the equivalence;
     * @return {@code true}, if two hours are the same and {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Hour hour)) {
            return false;
        }

        return Integer.compare(hour.getRidership(), getRidership()) == 0
                && hour.getComment().equals(getComment());
    }

    /**
     * Calculates the hash code of the hour.
     * If two objects are equal, they must have the same hash code.
     * If this method is called multiple times on the same object, it must return the same number each time.
     * @return the hash code of the hour.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(getRidership()) * getComment().hashCode();
    }

    /**
     * Compares this Hour object with another Hour object based on ridership.
     * @param h the object to be compared;
     * @return negative number, if this object is smaller, zero, if they are equal,
     * positive number, if this object is larger.
     */
    @Override
    public int compareTo(Hour h) {
        return Integer.compare(h.getRidership(), getRidership());
    }

    /**
     * Prints the array of hours.
     * @param hours array of hours to print.
     */
    public void printHourArray(Hour[] hours) {
        System.out.println("Array of hours:");

        for (Hour hour : hours) {
            System.out.println(hour);
        }
    }

    /**
     * Tests of the functionality of the {@code Hour} class.
     */
    public void testHour() {
        System.out.println("Create Hour with default constructor:");
        Hour hour = new Hour();
        System.out.println(hour);
        System.out.println("Length of comment:\t" + hour.getCommentLength());
        System.out.println("Count of words in comment:\t" + hour.calculateWordCountOfComment());

        System.out.println("\nCreate Hour with parameterized constructor:");
        System.out.println("Valid data for hour:");
        hour = new Hour(100, "Low ridership");
        System.out.println(hour);
        System.out.println("Invalid data for hour:");
        Hour invalidHour = new Hour(-200, null);
        System.out.println(invalidHour);

        System.out.println("\nSet values for the Hour:");
        hour.setRidership(200);
        hour.setComment("Medium ridership");
        System.out.println(hour);

        System.out.println("\nGet values for the Hour:");
        System.out.println("Hour\t{ "
                + "ridership = " + hour.getRidership()
                + ",\tcomment = \'" + hour.getComment() + "\' }");
        System.out.println("Get length of comment:\t" + hour.getCommentLength());
        System.out.println("Get count of words in comment:\t" + hour.calculateWordCountOfComment() + "\n");

        Hour[] hours = { hour,
                new Hour(50, "Very low ridership"),
                new Hour(200, "Medium ridership"),
                new Hour(100, "Low ridership"),
                new Hour(700, "High ridership"),
                new Hour(1200, "Very high ridership"),
                invalidHour
        };
        printHourArray(hours);

        System.out.println("\nCheck for equal values of Hours at index 0 and 1:\t" + hours[0].equals(hours[1]));
        System.out.println("Hour at index 0:\t" + hours[0]);
        System.out.println("Hour at index 1:\t" + hours[1]);
        System.out.println("Check for equal values of Hours at index 0 and 2:\t" + hours[0].equals(hours[2]));
        System.out.println("Hour at index 0:\t" + hours[0]);
        System.out.println("Hour at index 2:\t" + hours[2]);

        System.out.println("\nComparison of Hours at index 0 and 1:\t" + hours[0].compareTo(hours[1]));
        System.out.println("Hashcode of Hour at index 0:\t" + hours[0].hashCode());
        System.out.println("Hashcode of Hour at index 1:\t" + hours[1].hashCode());
        System.out.println("Comparison of Hours at index 0 and 2:\t" + hours[0].compareTo(hours[2]));
        System.out.println("Hashcode of Hour at index 0:\t" + hours[0].hashCode());
        System.out.println("Hashcode of Hour at index 2:\t" + hours[2].hashCode());
        System.out.println("Comparison of Hours at index 1 and 2:\t" + hours[1].compareTo(hours[2]));
        System.out.println("Hashcode of Hour at index 1:\t" + hours[1].hashCode());
        System.out.println("Hashcode of Hour at index 2:\t" + hours[2].hashCode());

        System.out.println("\nSort array of Hours by descending ridership:");
        Arrays.sort(hours);
        printHourArray(hours);
    }
}
