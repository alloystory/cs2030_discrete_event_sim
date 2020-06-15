package cs2030.simulator;

final class Event implements Comparable<Event> {
    private int id;
    private Time time;
    private Customer customer;
    private EventType eventType;
    private Server server;

    /**
     * List of Valid Event Types.
     */
    static enum EventType {
        ARRIVES(1),
        WAITS(2),
        SERVED(3),
        LEAVES(4),
        DONE(5),
        SERVER_REST(6),
        SERVER_BACK(7);

        private final int typeCode;
        EventType(int typeCode) {
            this.typeCode = typeCode;
        }
    }

    /**
     * Constructs a new Event.
     * @param id the id of the Event
     */
    Event(int id) {
        this.id = id;
        this.time = null;
        this.customer = null;
        this.eventType = null;
        this.server = null;
    }

    private Event(int id, Customer customer, EventType eventType, Time time, Server server) {
        this.id = id;
        this.time = time;
        this.customer = customer;
        this.eventType = eventType;
        this.server = server;
    }

    /**
     * Returns the id of the Event.
     * @return the id of the Event
     */
    int getID() {
        return this.id;
    }

    /**
     * Returns the time of the Event.
     * @return the time of the Event
     */
    Time getTime() {
        return this.time;
    }

    /**
     * Returns the type of the Event.
     * @return the type of the Event
     */
    EventType getType() {
        return this.eventType;
    }

    /**
     * Returns the customer of the Event.
     * @return the customer of the Event
     */
    Customer getCustomer() {
        return this.customer;
    }

    /**
     * Returns the server of the Event.
     * @return the server of the Event
     */
    Server getServer() {
        return this.server;
    }

    /**
     * Returns a new Event with a different time.
     * @param time the new time
     * @return a new Event with a different time
     */
    Event setTime(Time time) {
        return new Event(this.id, this.customer, this.eventType, time, this.server);
    }

    /**
     * Returns a new Event with a different event type.
     * @param eventType the new event type
     * @return a new Event with a different event type
     */
    Event setType(EventType eventType) {
        return new Event(this.id, this.customer, eventType, this.time, this.server);
    }

    /**
     * Returns a new Event with a different customer.
     * @param customer the new customer
     * @return a new Event with a different customer
     */
    Event setCustomer(Customer customer) {
        return new Event(this.id, customer, this.eventType, this.time, this.server);
    }

    /**
     * Returns a new Event with a different server.
     * @param server the new server
     * @return a new Event with a different server
     */
    Event setServer(Server server) {
        return new Event(this.id, this.customer, this.eventType, this.time, server);
    }

    /**
     * Compare two Event by ascending time, followed by descending priority and ascending id number.
     * @param other the Event to compare with 
     * @return the value 0 if the event has the same time, type and id 
     */    
    @Override
    public int compareTo(Event other) {
        int output = this.getTime().compareTo(other.getTime());
        if (output == 0) {
            output = other.getType().compareTo(this.getType());
        }
        
        if (output == 0) {
            output = this.getID() - other.getID();
        }
        return output;
    }

    /**
     * Returns the String representation of the Event.
     * @return the String representation of the Event.
     */
    @Override
    public String toString() {
        String output = null;

        switch (eventType) {
            case ARRIVES:
                output = String.format("%s %s arrives",
                            time.toString(), customer.toString());
                break;
            case WAITS:
                output = String.format("%s %s waits to be served by %s",
                            time.toString(), customer.toString(), server.toString());
                break;
            case SERVED:
                output = String.format("%s %s served by %s",
                            time.toString(), customer.toString(), server.toString());
                break;
            case LEAVES:
                output = String.format("%s %s leaves",
                            time.toString(), customer.toString());
                break;
            case DONE:
                output = String.format("%s %s done serving by %s",
                            time.toString(), customer.toString(), server.toString());
                break;
            case SERVER_REST:
                output = String.format("%s %s rest",
                            time.toString(), server.toString());
                break;
            case SERVER_BACK:
                output = String.format("%s %s back",
                            time.toString(), server.toString());
                break;
            default:
                break;
        }

        return output;
    }
}