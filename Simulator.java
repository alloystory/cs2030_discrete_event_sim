import java.util.List;

public class Simulator {
    private List<Double> _arrivalTimes;
    private EventManager _eventManager;
    private ServerManager _serverManager;
    private StatisticsManager _statsManager;

    public Simulator(List<Double> arrivalTimes) {
        this._arrivalTimes = arrivalTimes;
        this._eventManager = new EventManager();
        this._serverManager = new ServerManager(1);
        this._statsManager = new StatisticsManager();
    }

    public void start() {
        addArrivals(1);
        while (_eventManager.hasNextEvent()) {
            Event event = _eventManager.nextEvent();
            System.out.println(event);
            simulateLogic(event);
        }
        
        System.out.println(_statsManager);
    }

    private void addArrivals(int startID) {
        for (double arrivalTime : _arrivalTimes) {
            Customer customer = new Customer(startID);
            _statsManager.incrementCustomers();
            startID++;

            Event event = new Event(customer, Event.ARRIVES, new Time(arrivalTime));
            _eventManager.add(event);
        }
    }

    private void simulateLogic(Event event) {
        switch (event.getType()) {
        case Event.ARRIVES:
            Server server = _serverManager.getServerFreeAt(event.getTime());
            if (server != null) {
                server.workOn(event);
                _eventManager.add(event.changeType(Event.SERVED));
            } else {
                _eventManager.add(event.changeType(Event.LEAVES));
            }
            break;
        default:
            break;
        }
    }
}