import java.util.List;

public class Simulator {
    List<Double> _arrivalTimes;
    public Simulator(List<Double> arrivalTimes) {
        this._arrivalTimes = arrivalTimes;
    }

    public void start() {
        EventManager eventManager = new EventManager();
        ServerManager serverManager = new ServerManager(1);
        StatisticsManager statsManager = new StatisticsManager();
        int customerID = 1;

        for (double arrivalTime : _arrivalTimes) {
            Customer customer = new Customer(customerID);
            statsManager.incrementCustomers();
            customerID++;

            Event event = new Event(customer, Event.ARRIVES, new Time(arrivalTime));
            eventManager.addEvent(event);
        }

        while (eventManager.hasNextEvent()) {
            Event event = eventManager.nextEvent();
            System.out.println(event);
            
            Server server = serverManager.getServerFreeAt(event.getTime());
            if (server != null) {
                server.workOn(event);
                System.out.println("Customer served; next service @ " + server.nextTime());
            } else {
                System.out.println("Customer leaves");
            }
        }

        System.out.println(statsManager);
    }
}