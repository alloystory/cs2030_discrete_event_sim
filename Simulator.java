package cs2030.simulator;

public class Simulator {
    private ServerManager serverManager;
    private CustomerManager customerManager;
    private EventManager eventManager;
    private StatisticsManager statisticsManager;
    private RandomGenerator randomGenerator;

    private int numberOfCustomers;
    private double restingProbability;
    private Time arrivalTime;

    public Simulator(int baseSeed, int numberOfServers, int numberOfSelfCheckoutCounters, int maximumQueueLength, int numberOfCustomers, double arrivalRate, double serviceRate, double restingRate, double restingProbability) {
        this.serverManager = new ServerManager();
        this.customerManager = new CustomerManager();
        this.eventManager = new EventManager();
        this.statisticsManager = new StatisticsManager();
        this.randomGenerator = new RandomGenerator(baseSeed, arrivalRate, serviceRate, restingRate);
        this.numberOfCustomers = numberOfCustomers;
        this.restingProbability = restingProbability;
        this.arrivalTime = new Time();

        for (int i = 0; i < numberOfServers; i++) {
            serverManager.add(serverManager.createHumanServer());
        }

        for (int i = 0; i < numberOfSelfCheckoutCounters; i++) {
            serverManager.add(serverManager.createSelfCheckoutCounter());
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
                case Event.SERVER_REST:
                    serverRestHandler(event);
                    break;
                case Event.SERVER_BACK:
                    serverBackHandler(event);
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
            server.setCurrentEvent(nextEvent);
        } else {
            server = serverManager.findAvailableServer();
            if (server != null) {
                nextEvent = event.setType(Event.WAITS).setServer(server);
                server.addFutureEvent(nextEvent);
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
        boolean isResting = false;
        if (!(server instanceof SelfCheckoutCounter) && randomGenerator.genRandomRest() < restingProbability) {
            Event nextEvent = event.setType(Event.SERVER_REST);
            eventManager.add(nextEvent);
            isResting = true;
        }
        
        server.deleteCurrentEvent();
        if (!isResting && server.hasWaitingEvents()) {
            Event serverFutureEvent = server.pollFutureEvent();
            Event nextEvent = serverFutureEvent.setType(Event.SERVED).setTime(event.getTime());
            server.setCurrentEvent(nextEvent);
            eventManager.add(nextEvent);

            Time waitingTime = nextEvent.getTime().minus(serverFutureEvent.getTime());
            statisticsManager.addWaitingTime(waitingTime);
        }
    }

    private void leftEventHandler() {
        statisticsManager.addLeft();
    }

    private void serverRestHandler(Event event) {
        Server server = event.getServer();
        Time restPeriod = new Time(randomGenerator.genRestPeriod());
        server.setResting();
        eventManager.delayEventsByServer(server, restPeriod);

        Time nextTime = event.getTime().add(restPeriod);
        Event nextEvent = event.setType(Event.SERVER_BACK).setTime(nextTime);
        eventManager.add(nextEvent);
    }

    private void serverBackHandler(Event event) {
        Server server = event.getServer();
        server.setAwake();
        
        Event serverFutureEvent = server.pollFutureEvent();
        server.setCurrentEvent(serverFutureEvent);
        
        if (serverFutureEvent != null) {
            Event nextEvent = serverFutureEvent.setType(Event.SERVED).setTime(event.getTime());
            server.setCurrentEvent(nextEvent);
            eventManager.add(nextEvent);

            Time waitingTime = nextEvent.getTime().minus(serverFutureEvent.getTime());
            statisticsManager.addWaitingTime(waitingTime);
        }
    }
}