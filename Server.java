package cs2030.simulator;

import java.util.Queue;
import java.util.LinkedList;

final class Server {
    private int id;
    private ServerType serverType;
    private Event currentEvent;
    private Queue<Event> futureEvents;
    private boolean isResting;

    static int MAXIMUM_QUEUE_LENGTH = 0;

    /**
     * List of Valid Server Types.
     */
    enum ServerType {
        HUMAN_SERVER(1),
        SELF_CHECKOUT(2);

        private final int serverType;
        ServerType(int serverType) {
            this.serverType = serverType;
        }
    }
    
    /**
     * Constructs a new Server.
     * @param id the id of the Server
     * @param serverType the type of the Server
     */
    Server(int id, ServerType serverType) {
        this.id = id;
        this.serverType = serverType;
        this.currentEvent = null;
        this.futureEvents = new LinkedList<>();
        this.isResting = false;
    }

    /**
     * Returns the id of the server.
     * @return the id of the server.
     */
    int getID() {
        return id;
    }

    /**
     * Returns the type of the server.
     * @return the type of the server.
     */
    ServerType getType() {
        return serverType;
    }

    /**
     * Returns the current serving event.
     * @return the current serving event.
     */
    Event getCurrentEvent() {
        return currentEvent;
    }

    /**
     * Changes the current event.
     * @param event the event to change to
     */
    void setCurrentEvent(Event event) {
        currentEvent = event;
    }

    /**
     * Deletes and returns the current serving event.
     * @return the current serving event.
     */
    Event deleteCurrentEvent() {
        Event output = currentEvent;
        currentEvent = null;
        return output;
    }

    /**
     * Add an event into the server's future events.
     * @param event the event to be added
     */
    void addFutureEvent(Event event) {
        this.futureEvents.offer(event);
    }

    /**
     * Removes and returns the first added future event.
     * @return the first added future event
     */
    Event pollFutureEvent() {
        return futureEvents.poll();
    }

    /**
     * Returns true if the server is currently serving someone.
     * @return true if the server is currently serving someone.
     */
    boolean isServing() {
        return currentEvent != null;
    }

    /**
     * Returns true if the server has reached its full capacity.
     * @return true if the server has reached its full capacity.
     */
    boolean isFull() {
        return futureEvents.size() == MAXIMUM_QUEUE_LENGTH;
    }

    /**
     * Returns true if the server has future events.
     * @return true if the server has future events.
     */
    boolean hasFutureEvents() {
        return futureEvents.size() != 0;
    }

    /**
     * Returns the queue length of the server.
     * @return the queue length of the server.
     */
    int getQueueLength() {
        return futureEvents.size();
    }

    /**
     * Returns true if the server is currently resting.
     * @return true if the server is currently resting.
     */
    boolean isResting() {
        return this.isResting;
    }

    /**
     * Sets the server to rest.
     */
    void rests() {
        this.isResting = true;
    }

    /**
     * Wake the server.
     */
    void wakes() {
        this.isResting = false;
    }

    /**
     * Returns the String representation of the Server.
     */
    @Override
    public String toString() {
        String output = null;
        switch (serverType) {
            case HUMAN_SERVER:
                output = String.format("server %d", id);
                break;
            case SELF_CHECKOUT:
                output = String.format("self-check %d", id);
                break;
            default:
                break;
        }
        return output;
    }
}