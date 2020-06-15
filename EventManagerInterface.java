package cs2030.simulator;

interface EventManagerInterface {
    /**
     * Creates a new Event.
     * @return a new Event
     */
    Event createEvent();

    /**
     * Stores an Event.
     * @param event the event to be stored
     */
    void add(Event event);

    /**
     * Returns true if there are more events.
     * @return true if there are more events
     */
    boolean hasNextEvent();

    /**
     * Returns the next event.
     * @return the next event
     */
    Event nextEvent();

    /**
     * Find all events that contains this server and increment the 
     * time field by the specified restPeriod.
     * @param server the Server to search
     * @param restPeriod the restPeriod
     */
    void delayEventsByServer(Server server, Time restPeriod);
}