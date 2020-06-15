import java.util.List;

public class Simulator {
    List<Double> _arrivalTimes;
    public Simulator(List<Double> arrivalTimes) {
        this._arrivalTimes = arrivalTimes;
    }

    public void start() {
        EventManager eventManager = new EventManager();
        StatisticsManager statsManager = new StatisticsManager();
        int customerID = 1;

        for (double arrivalTime : _arrivalTimes) {
            Customer customer = new Customer(customerID);
            statsManager.incrementCustomers();
            customerID++;

            Event event = new Event(customer, Event.ARRIVES, new Time(arrivalTime));
            eventManager.addEvent(event);
        }

        System.out.println(eventManager);
        System.out.println(statsManager);
    }
}