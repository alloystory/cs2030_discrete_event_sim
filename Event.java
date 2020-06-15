package cs2030.simulator;

public class Event implements Comparable<Event> {
    private int id;
    private Time time;
    private Customer customer;
    private int eventType;
    private Server server;

    public static final int ARRIVES = 1;
    public static final int WAITS = 2;
    public static final int SERVED = 3;
    public static final int LEAVES = 4;
    public static final int DONE = 5;
    public static final int SERVER_REST = 6;
    public static final int SERVER_BACK = 7;

    public Event(int id) {
        this.id = id;
        this.time = null;
        this.customer = null;
        this.eventType = -1;
        this.server = null;
    }

    private Event(int id, Customer customer, int eventType, Time time, Server server) {
        this.id = id;
        this.time = time;
        this.customer = customer;
        this.eventType = eventType;
        this.server = server;
    }

    public int getID() {
        return this.id;
    }

    public Time getTime() {
        return this.time;
    }

    public int getEventType() {
        return this.eventType;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Server getServer() {
        return this.server;
    }

    public Event setTime(Time time) {
        return new Event(this.id, this.customer, this.eventType, time, this.server);
    }

    public Event setType(int eventType) {
        return new Event(this.id, this.customer, eventType, this.time, this.server);
    }

    public Event setCustomer(Customer customer) {
        return new Event(this.id, customer, this.eventType, this.time, this.server);
    }

    public Event setServer(Server server) {
        return new Event(this.id, this.customer, this.eventType, this.time, server);
    }

    @Override
    public int compareTo(Event other) {
        int output = this.getTime().compareTo(other.getTime());
        if (output == 0)
            output = other.getEventType() - this.getEventType();
        if (output == 0)
            output = this.getID() - other.getID();
        return output;
    }

    @Override
    public String toString() {
        String output = null;

        switch (eventType) {
            case ARRIVES:
                output = String.format("%s %d arrives", time.toString(), customer.getID());
                break;
            case WAITS:
                output = String.format("%s %d waits to be served by %d", time.toString(), customer.getID(), server.getID());
                break;
            case SERVED:
                output = String.format("%s %d served by %d", time.toString(), customer.getID(), server.getID());
                break;
            case LEAVES:
                output = String.format("%s %d leaves", time.toString(), customer.getID());
                break;
            case DONE:
                output = String.format("%s %d done serving by %d", time.toString(), customer.getID(), server.getID());
                break;
            case SERVER_REST:
                output = String.format("%s server %d rest", time.toString(), server.getID());
                break;
            case SERVER_BACK:
                output = String.format("%s server %d back", time.toString(), server.getID());
                break;
        }

        return output;
    }
}