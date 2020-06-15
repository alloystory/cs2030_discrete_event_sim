import java.util.List;
import java.util.Iterator;

public class Simulator {
    private List<Double> _arrivalTimes;
    private CustomerManager _customerManager;
    private EventManager _eventManager;
    private ServerManager _serverManager;
    private StatisticsManager _statsManager;
    private boolean _debug;

    public Simulator(List<Double> arrivalTimes, int numberOfServers, boolean debug) {
        this._arrivalTimes = arrivalTimes;
        this._customerManager = new CustomerManager(arrivalTimes.size());
        this._eventManager = new EventManager();
        this._serverManager = new ServerManager(numberOfServers);
        this._statsManager = new StatisticsManager();
        this._debug = debug;
    }

    public void start() {
        if (_debug) System.out.println("# Adding arrivals");
        addArrivals();
        while (_eventManager.hasNextEvent()) {
            Event event = _eventManager.nextEvent();
            if (_debug) System.out.println("# Get next event: " + event);
            else        System.out.println(event);
            
            simulateLogic(event);
            if (_debug) System.out.println((!_eventManager.toString().equals("")) ? _eventManager + "\n" : "");
        }
        
        System.out.println(_statsManager);
    }

    private void addArrivals() {
        Iterator<Customer> customerIterator = _customerManager.iterator();
        Iterator<Double> arrivalIterator = _arrivalTimes.iterator();

        while (customerIterator.hasNext() && arrivalIterator.hasNext()) {
            Customer customer = customerIterator.next();
            double arrivalTime = arrivalIterator.next();

            Event event = new Event(customer.getID(), Event.ARRIVES, new Time(arrivalTime));
            _eventManager.add(event);
            if (_debug) System.out.println(event);
        }
        if (_debug) System.out.println();
    }

    private void simulateLogic(Event event) {
        Server server;

        switch (event.getType()) {
        case Event.ARRIVES:
            server = _serverManager.getServerFreeAt(event.getTime());
            if (server != null) {
                server.workOn(event);
                _eventManager.add(event.changeType(Event.SERVED).changeServer(server.getID()));
            } else {
                server = _serverManager.getIdleServer();
                if (server != null) {
                    server.setNextEvent(event.getID());
                    _eventManager.add(event.changeType(Event.WAITS).changeServer(server.getID()));
                } else {
                    _eventManager.add(event.changeType(Event.LEAVES));
                    _statsManager.addLeft();
                }
            }
            break;
        case Event.WAITS:
            server = _serverManager.getServer(event.getServerID());
            Event nextEvent = event.changeType(Event.SERVED).changeTime(server.nextTime());
            _eventManager.add(nextEvent);
            server.workOn(event);
            _statsManager.addWaitingTime(nextEvent.getTime().minus(event.getTime()));
            break;
        case Event.SERVED:
            server = _serverManager.getServer(event.getServerID());
            server.setNextEvent(null);
            _eventManager.add(event.changeType(Event.DONE).changeTime(server.nextTime()));
            _statsManager.addServed();
            break;
        default:
            break;
        }
    }
}