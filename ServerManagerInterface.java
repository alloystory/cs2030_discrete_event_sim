package cs2030.simulator;

interface ServerManagerInterface {
    /**
     * Creates and returns a new human server.
     * @return a new human server
     */
    Server createHumanServer();

    /**
     * Creates and returns a new Self Checkout Counter.
     * @return a new Self Checkout Counter
     */
    Server createSelfCheckoutCounter();

    /**
     * Stores a Server.
     * @param server the Server to store
     */
    void add(Server server);

    /**
     * Finds and returns a server that is not resting and currently
     * not serving.
     * @return a server that is not resting and currently not serving
     */
    Server findIdleServer();

    /**
     * Finds and returns a server that still has capacity.
     * @return a server that still has capacity
     */
    Server findAvailableServer();

    /**
     * Finds and returns a server that still has capacity and 
     * has the shortest queue.
     * @return a server that still has capacity and has the shortest queue
     */
    Server findShortestQueueServer();
}