package cs2030.simulator;

import java.util.List;
import java.util.Iterator;

public class Simulator {
    private CustomerManager _customerManager;
    private EventManager _eventManager;
    private ServerManager _serverManager;
    private StatisticsManager _statsManager;
    private RandomGenerator _randomGenerator;
    private boolean _debug;
    public static final Time STARTING_TIME = new Time();

    public Simulator(int baseSeed, int numberOfServers, int numberOfCustomers, double arrivalRate, double serviceRate, boolean debug) {
        this._customerManager = new CustomerManager(numberOfCustomers);
        this._eventManager = new EventManager();
        this._serverManager = new ServerManager(numberOfServers);
        this._statsManager = new StatisticsManager();
        this._randomGenerator = new RandomGenerator(baseSeed, arrivalRate, serviceRate);
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
        Time arrivalTime = STARTING_TIME;
        for (Customer customer : _customerManager) {
            Event event = new Event(customer.getID(), Event.ARRIVES, arrivalTime);
            _eventManager.add(event);
            if (_debug) System.out.println(event);
            arrivalTime = arrivalTime.add(new Time(_randomGenerator.genInterArrivalTime()));
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