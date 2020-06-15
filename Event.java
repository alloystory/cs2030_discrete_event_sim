package cs2030.simulator;

public class Event implements Comparable<Event> {
    private int id;
    private Time timeStamp;
    private int customerID;
    private int eventType;
    private int serverID;

    public static final int ARRIVES = 1;
    public static final int WAITS = 2;
    public static final int SERVED = 3;
    public static final int LEAVES = 4;
    public static final int DONE = 5;

    public Event(int customerID, int eventType, Time timeStamp) {
        this.id = -1;
        this.customerID = customerID;
        this.eventType = eventType;
        this.timeStamp = timeStamp;
        this.serverID = -1;
    }

    public Event(int id, int customerID, int eventType, Time timeStamp, int serverID) {
        this(customerID, eventType, timeStamp);
        this.id = id;
        this.serverID = serverID;
    }

    public int getID() {
        return this.id;
    }

    public Time getTimestamp() {
        return this.timeStamp;
    }

    public int getEventType() {
        return this.eventType;
    }

    public int getServerID() {
        return this.serverID;
    }

    public Event changeID(int id) {
        return new Event(id, this.customerID, this.eventType, this.timeStamp, this.serverID);
    }

    public Event changeTimestamp(Time timeStamp) {
        return new Event(this.id, this.customerID, this.eventType, timeStamp, this.serverID);
    }

    public Event changeEventType(int eventType) {
        return new Event(this.id, this.customerID, eventType, this.timeStamp, this.serverID);
    }

    public Event changeServerID(int serverID) {
        return new Event(this.id, this.customerID, this.eventType, this.timeStamp, serverID);
    }

    @Override
    public int compareTo(Event other) {
        int output = this.getTimestamp().compareTo(other.getTimestamp());
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
            output = String.format("%s %d arrives", timeStamp.toString(), customerID);
            break;
        case WAITS:
            output = String.format("%s %d waits to be served by %d", timeStamp.toString(), customerID, serverID);
            break;
        case SERVED:
            output = String.format("%s %d served by %d", timeStamp.toString(), customerID, serverID);
            break;
        case LEAVES:
            output = String.format("%s %d leaves", timeStamp.toString(), customerID);
            break;
        case DONE:
            output = String.format("%s %d done serving by %d", timeStamp.toString(), customerID, serverID);
            break;
        }

        return output;
    }
}