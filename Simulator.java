package cs2030.simulator;

import java.util.List;
import java.util.Iterator;

public class Simulator {
    private CustomerManager customerManager;
    private EventManager eventManager;
    private ServerManager serverManager;
    private StatisticsManager statsManager;
    private RandomGenerator randomGenerator;
    private boolean debug;
    public static final Time STARTING_TIME = new Time();

    public Simulator(int baseSeed, int numberOfServers, int numberOfCustomers, double arrivalRate, double serviceRate, boolean debug) {
        this.customerManager = new CustomerManager(numberOfCustomers);
        this.eventManager = new EventManager();
        this.serverManager = new ServerManager(numberOfServers);
        this.statsManager = new StatisticsManager();
        this.randomGenerator = new RandomGenerator(baseSeed, arrivalRate, serviceRate);
        this.debug = debug;
    }

    public void start() {
        if (this.debug) System.out.println("# Adding arrivals");
        addArrivals();
        
        while (eventManager.hasNextEvent()) {
            Event event = eventManager.nextEvent();
            if (this.debug) System.out.println("# Get next event: " + event);
            else            System.out.println(event);
            
            simulateLogic(event);
            if (this.debug) System.out.println((!eventManager.toString().equals("")) ? eventManager + "\n" : "");
        }
        
        System.out.println(this.statsManager);
    }

    private void addArrivals() {
        Time arrivalTime = STARTING_TIME;
        for (Customer customer : customerManager) {
            Event event = new Event(customer.getID(), Event.ARRIVES, arrivalTime);
            if (this.debug) System.out.println(event);
            eventManager.add(event);
            arrivalTime = arrivalTime.add(new Time(randomGenerator.genInterArrivalTime()));
        }
        if (this.debug) System.out.println();
    }

    private void simulateLogic(Event event) {
        Server server;

        switch (event.getEventType()) {
        case Event.ARRIVES:
            server = serverManager.findIdleServer();
            if (server != null) {
                eventManager.add(event.changeEventType(Event.SERVED).changeServerID(server.getID()));
            } else {
                server = serverManager.findAvailableServer();
                if (server != null) {
                    eventManager.add(event.changeEventType(Event.WAITS).changeServerID(server.getID()));
                } else {
                    eventManager.add(event.changeEventType(Event.LEAVES));
                }
            }
            break;
        
        case Event.WAITS:
            server = serverManager.findServerByID(event.getServerID());
            server.setNextEventID(event.getID());
            Event serverCurrentEvent = eventManager.findEventByID(server.getCurrentEventID());
            Event nextEvent = event.changeEventType(Event.SERVED).changeTimestamp(serverCurrentEvent.getTimestamp());
            eventManager.add(nextEvent);
            statsManager.addWaitingTime(nextEvent.getTimestamp().minus(event.getTimestamp()));
            break;
        
        case Event.SERVED:
            server = serverManager.findServerByID(event.getServerID());
            server.setCurrentEventID(event.getID());
            Time nextEventTimestamp = event.getTimestamp().add(new Time(randomGenerator.genServiceTime()));
            eventManager.add(event.changeEventType(Event.DONE).changeTimestamp(nextEventTimestamp));
            statsManager.addServed();
            break;

        case Event.LEAVES:
            this.statsManager.addLeft();

        case Event.DONE:
            server = serverManager.findServerByID(event.getServerID());
            server.setCurrentEventID(server.getNextEventID());
            server.deleteNextEventID();
        
        default:
            break;
        }
    }
}