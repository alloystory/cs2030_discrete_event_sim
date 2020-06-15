package cs2030.simulator;

public final class Simulator {
    private EventManager eventManager;
    private ServerManager serverManager;
    private CustomerManager customerManager;
    private StatisticsManager statisticsManager;
    private RandomGenerator randomGenerator;

    private int numberOfCustomers;
    private int numberOfServers;
    private int numberOfSelfCheckoutCounters;
    private Time nextArrivalTime;
    private double restingProbability;
    private double greedyCustomerProbability;

    /**
     * Constructs a new Simulator.
     * @param baseSeed the base seed for the RandomGenerator
     * @param numberOfServers the number of servers to simulate
     * @param numberOfSelfCheckoutCounters the number of self checkout counters to simulate
     * @param maximumQueueLength the maximum queue length for each server
     * @param numberOfCustomers the number of customers
     * @param arrivalRate the arrival rate for the RandomGenerator
     * @param serviceRate the server rate for the RandomGenerator
     * @param restingRate the resting rate for the RandomGenerator
     * @param restingProbability the probability of a server resting
     * @param greedyCustomerProbability the probability of a customer being greedy
     */
    public Simulator(int baseSeed,
                    int numberOfServers,
                    int numberOfSelfCheckoutCounters,
                    int maximumQueueLength,
                    int numberOfCustomers,
                    double arrivalRate,
                    double serviceRate,
                    double restingRate,
                    double restingProbability,
                    double greedyCustomerProbability) {

        this.randomGenerator = new RandomGenerator(baseSeed, arrivalRate, serviceRate, restingRate);
        this.numberOfCustomers = numberOfCustomers;
        this.numberOfServers = numberOfServers;
        this.numberOfSelfCheckoutCounters = numberOfSelfCheckoutCounters;
        this.nextArrivalTime = new Time();
        this.restingProbability = restingProbability;
        this.greedyCustomerProbability = greedyCustomerProbability;

        Server.MAXIMUM_QUEUE_LENGTH = maximumQueueLength;
    }

    /**
     * Sets the Managers required in the simulation.
     * @param eventManager the object that implements the EventManagerInterface
     * @param serverManager the object that implements the ServerManagerInterface
     * @param customerManager the object that implements the CustomerManagerInterface
     * @param statisticsManager the object that implements the StatisticsManagerInterface
     */
    public void setManagers(EventManager eventManager,
                            ServerManager serverManager,
                            CustomerManager customerManager,
                            StatisticsManager statisticsManager) {
        this.eventManager = eventManager;
        this.serverManager = serverManager;
        this.customerManager = customerManager;
        this.statisticsManager = statisticsManager;
    }

    /**
     * Starts the simulation.
     */
    public void start() {
        createServers();
        simulateArrival();
        simulateLogic();
        System.out.println(statisticsManager);
    }

    private void createServers() {
        for (int i = 0; i < numberOfServers; i++) {
            serverManager.add(serverManager.createHumanServer());
        }

        for (int i = 0; i < numberOfSelfCheckoutCounters; i++) {
            serverManager.add(serverManager.createSelfCheckoutCounter());
        }
    }

    private void simulateArrival() {
        if (numberOfCustomers != 0) {
            Customer customer = null;
            if (randomGenerator.genCustomerType() < greedyCustomerProbability) {
                customer = customerManager.createGreedyCustomer();
            } else {
                customer = customerManager.createCustomer();
            }
            customerManager.add(customer);
            numberOfCustomers--;
            
            Event event = eventManager.createEvent();
            event = event.setType(Event.EventType.ARRIVES)
                            .setCustomer(customer).setTime(nextArrivalTime);
            eventManager.add(event);

            Time interArrivalTime = new Time(randomGenerator.genInterArrivalTime());
            nextArrivalTime = nextArrivalTime.add(interArrivalTime);
        }
    }

    private void simulateLogic() {
        while (eventManager.hasNextEvent()) {
            Event event = eventManager.nextEvent();
            System.out.println(event);

            Event.EventType eventType = event.getType();
            switch (eventType) {
                case ARRIVES:
                    arriveEventHandler(event);
                    break;
                case SERVED:
                    servedEventHandler(event);
                    break;
                case LEAVES:
                    leftEventHandler();
                    break;
                case DONE:
                    doneEventHandler(event);
                    break;
                case SERVER_REST:
                    serverRestHandler(event);
                    break;
                case SERVER_BACK:
                    serverBackHandler(event);
                    break;
                default:
                    break;
            }
        }
    }

    private void arriveEventHandler(Event event) {
        Server server = serverManager.findIdleServer();
        Event nextEvent = event.setType(Event.EventType.LEAVES);

        if (server != null) {
            nextEvent = event.setType(Event.EventType.SERVED).setServer(server);
            server.setCurrentEvent(nextEvent);
        } else {
            Customer customer = event.getCustomer();
            if (customer.getType() == Customer.CustomerType.GREEDY_CUSTOMER) {
                server = serverManager.findShortestQueueServer();
            } else {
                server = serverManager.findAvailableServer();
            }

            if (server != null) {
                nextEvent = event.setType(Event.EventType.WAITS).setServer(server);
                server.addFutureEvent(nextEvent);
            }
        }
        
        eventManager.add(nextEvent);
        simulateArrival();
    }

    private void servedEventHandler(Event event) {
        Time serviceTime = new Time(randomGenerator.genServiceTime());
        Time nextTime = serviceTime.add(event.getTime());
        Event nextEvent = event.setType(Event.EventType.DONE).setTime(nextTime);
        eventManager.add(nextEvent);

        statisticsManager.addServed();
    }

    private void doneEventHandler(Event event) {
        Server server = event.getServer();
        boolean pendingRest = false;
        if (server.getType() == Server.ServerType.HUMAN_SERVER &&
                randomGenerator.genRandomRest() < restingProbability) {
            Event nextEvent = event.setType(Event.EventType.SERVER_REST);
            eventManager.add(nextEvent);
            pendingRest = true;
        }
        
        server.deleteCurrentEvent();
        if (!pendingRest && server.hasFutureEvents()) {
            Event serverFutureEvent = server.pollFutureEvent();
            Event nextEvent = serverFutureEvent.setType(Event.EventType.SERVED)
                                                .setTime(event.getTime());
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
        server.rests();
        eventManager.delayEventsByServer(server, restPeriod);

        Time nextTime = event.getTime().add(restPeriod);
        Event nextEvent = event.setType(Event.EventType.SERVER_BACK).setTime(nextTime);
        eventManager.add(nextEvent);
    }

    private void serverBackHandler(Event event) {
        Server server = event.getServer();
        server.wakes();
        
        Event serverFutureEvent = server.pollFutureEvent();
        server.setCurrentEvent(serverFutureEvent);
        
        if (serverFutureEvent != null) {
            Event nextEvent = serverFutureEvent.setType(Event.EventType.SERVED)
                                                .setTime(event.getTime());
            server.setCurrentEvent(nextEvent);
            eventManager.add(nextEvent);

            Time waitingTime = nextEvent.getTime().minus(serverFutureEvent.getTime());
            statisticsManager.addWaitingTime(waitingTime);
        }
    }
}