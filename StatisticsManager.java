public class StatisticsManager {
    private int _numberOfCustomers;
    public StatisticsManager() {
        this._numberOfCustomers = 0;
    }

    public void incrementCustomers() {
        this._numberOfCustomers++;
    }

    @Override
    public String toString() {
        return String.format("Number of customers: %d", _numberOfCustomers);
    }
}