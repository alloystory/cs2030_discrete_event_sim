package cs2030.simulator;

public final class StatisticsManager {
    private Time totalWaitingTime;
    private int numberOfCustomersServed;
    private int numberOfCustomersLeft;

    /**
     * Constructs a new StatisticsManager.
     */
    public StatisticsManager() {
        this.totalWaitingTime = new Time();
        this.numberOfCustomersServed = 0;
        this.numberOfCustomersLeft = 0;
    }

    /**
     * Increment the waiting time.
     * @param time the Time to add
     */
    void addWaitingTime(Time time) {
        totalWaitingTime = totalWaitingTime.add(time);
    }

    /**
     * Increment the number of customer served.
     */
    void addServed() {
        numberOfCustomersServed++;
    }

    /**
     * Increment the number of customers who left.
     */
    void addLeft() {
        numberOfCustomersLeft++;
    }

    /**
     * Returns the String representation of the StatisticsManager.
     */
    @Override
    public String toString() {
        Time averageWaitingTime = new Time();
        if (numberOfCustomersServed > 0) {
            double averageWaitPeriod = totalWaitingTime.raw() / (double) numberOfCustomersServed;
            averageWaitingTime = new Time(averageWaitPeriod);
        }
        return String.format("[%s %d %d]",
                    averageWaitingTime.toString(), numberOfCustomersServed, numberOfCustomersLeft);
    }
}