package cs2030.simulator;

public class Simulator {
    private ServerManager serverManager;
    private CustomerManager customerManager;
    private EventManager eventManager;
    private StatisticsManager statisticsManager;
    private RandomGenerator randomGenerator;

    private int numberOfCustomers;
    private Time arrivalTime;

    public Simulator(int baseSeed, int numberOfServers, int maximumQueueLength, int numberOfCustomers, double arrivalRate, double serviceRate) {
        this.serverManager = new ServerManager();
        this.customerManager = new CustomerManager();
        this.eventManager = new EventManager();
        this.statisticsManager = new StatisticsManager();
        this.randomGenerator = new RandomGenerator(baseSeed, arrivalRate, serviceRate);
        this.numberOfCustomers = numberOfCustomers;
        this.arrivalTime = new Time();

        for (int i = 0; i < numberOfServers; i++) {
            serverManager.add(serverManager.createServer());
        }

        Server.MAXIMUM_QUEUE_LENGTH = maximumQueueLength;
    }

    public void start() {
        addArrival();
        while (eventManager.hasNextEvent()) {
            Event event = eventManager.nextEvent();
            System.out.println(event);

            int eventType = event.getEventType();
            switch (eventType) {
                case Event.ARRIVES:
                    arriveEventHandler(event);
                    break;
                case Event.SERVED:
                    servedEventHandler(event);
                    break;
                case Event.LEAVES:
                    leftEventHandler();
                    break;
                case Event.DONE:
                    doneEventHandler(event);
                    break;
                default:
                    break;
            }
        }
        System.out.println(statisticsManager);
    }

    private void addArrival() {
        if (numberOfCustomers != 0) {
            Customer customer = customerManager.createCustomer();
            customerManager.add(customer);
            numberOfCustomers--;
            
            Event event = eventManager.createEvent();
            event = event.setType(Event.ARRIVES).setCustomer(customer).setTime(arrivalTime);
            eventManager.add(event);

            Time interArrivalTime = new Time(randomGenerator.genInterArrivalTime());
            arrivalTime = arrivalTime.add(interArrivalTime);
        }
    }

    private void arriveEventHandler(Event event) {
        Server server = serverManager.findIdleServer();
        Event nextEvent = event.setType(Event.LEAVES);

        if (server != null) {
            nextEvent = event.setType(Event.SERVED).setServer(server);
            server.addEvent(nextEvent);
        } else {
            server = serverManager.findAvailableServer();
            if (server != null) {
                nextEvent = event.setType(Event.WAITS).setServer(server);
                server.addEvent(nextEvent);
            }
        }
        
        eventManager.add(nextEvent);
        addArrival();
    }

    private void servedEventHandler(Event event) {
        Time serviceTime = new Time(randomGenerator.genServiceTime());
        Time nextTime = serviceTime.add(event.getTime());
        Event nextEvent = event.setType(Event.DONE).setTime(nextTime);
        eventManager.add(nextEvent);

        statisticsManager.addServed();
    }

    private void doneEventHandler(Event event) {
        Server server = event.getServer();
        server.deleteCurrentEvent();
        if (server.isBusy()) {
            Event serverCurrentEvent = server.deleteCurrentEvent();
            Event nextEvent = serverCurrentEvent.setType(Event.SERVED).setTime(event.getTime());
            server.setCurrentEvent(nextEvent);
            eventManager.add(nextEvent);

            Time waitingTime = nextEvent.getTime().minus(serverCurrentEvent.getTime());
            statisticsManager.addWaitingTime(waitingTime);
        }
    }


    private void leftEventHandler() {
        statisticsManager.addLeft();
    }
}