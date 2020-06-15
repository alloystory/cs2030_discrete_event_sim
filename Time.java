package cs2030.simulator;

final class Time implements Comparable<Time> {
    private double time;

    /**
     * Constructs a new time based on a double.
     * @param time the double time
     */
    Time(double time) {
        this.time = time;
    }

    /**
     * Constructs a new time 0.
     */
    Time() {
        this(0);
    }

    /**
     * Returns the raw form of the time.
     * @return the double wrapped in the time
     */
    double raw() {
        return this.time;
    }

    /**
     * Adds two Time together.
     * @param other the time to be added
     * @return a new Time object with both times added
     */
    Time add(Time other) {
        return new Time(this.raw() + other.raw());
    }

    /**
     * Minus a time from the other time.
     * @param other the time to minused
     * @return a new Time object with the time deducted
     */
    Time minus(Time other) {
        return new Time(this.raw() - other.raw());
    }

    /**
     * Compares two time chronologically.
     * @param other the time to compare
     * @return 1 if other is earlier. 0 if the times are the same. -1 if other is later.
     */
    @Override
    public int compareTo(Time other) {
        return Double.compare(this.raw(), other.raw());
    }

    /**
     * Returns the String representation of the Time.
     * @return the String representation of the Time
     */
    @Override
    public String toString() {
        return String.format("%.3f", time);
    }
}