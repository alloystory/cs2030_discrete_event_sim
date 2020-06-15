package cs2030.simulator;

public class StatisticsManager {
    private Time totalWaitingTime;
    private int numberOfCustomersServed;
    private int numberOfCustomersLeft;

    public StatisticsManager() {
        this.totalWaitingTime = new Time();
        this.numberOfCustomersServed = 0;
        this.numberOfCustomersLeft = 0;
    }

    public void addWaitingTime(Time time) {
        this.totalWaitingTime = totalWaitingTime.add(time);
    }

    public void addServed() {
        this.numberOfCustomersServed++;
    }

    public void addLeft() {
        this.numberOfCustomersLeft++;
    }

    @Override
    public String toString() {
        Time averageWaitingTime = new Time();
        if (numberOfCustomersServed > 0)
            averageWaitingTime = new Time(totalWaitingTime.raw() / (double) numberOfCustomersServed);
        return String.format("[%s %d %d]", averageWaitingTime.toString(), numberOfCustomersServed, numberOfCustomersLeft);
    }
}