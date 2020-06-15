package cs2030.simulator;

public class StatisticsManager {
    private Time _totalWaitingTime;
    private int _numberOfCustomersServed;
    private int _numberOfCustomersLeft;

    public StatisticsManager() {
        this._totalWaitingTime = new Time();
        this._numberOfCustomersServed = 0;
        this._numberOfCustomersLeft = 0;
    }

    public void addWaitingTime(Time time) {
        this._totalWaitingTime = _totalWaitingTime.add(time);
    }

    public void addServed() {
        this._numberOfCustomersServed++;
    }

    public void addLeft() {
        this._numberOfCustomersLeft++;
    }

    @Override
    public String toString() {
        Time _averageWaitingTime = new Time(_totalWaitingTime.raw() / (double) _numberOfCustomersServed);
        return String.format("[%s %d %d]", _averageWaitingTime.toString(), _numberOfCustomersServed, _numberOfCustomersLeft);
    }
}