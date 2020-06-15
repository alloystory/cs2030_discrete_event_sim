package cs2030.simulator;

public class Event implements Comparable<Event> {
    private int _id;
    private int _customerID;
    private int _eventType;
    private Time _time;
    private int _serverID;

    public static final int ARRIVES = 1;
    public static final int WAITS = 2;
    public static final int SERVED = 3;
    public static final int LEAVES = 4;
    public static final int DONE = 5;

    public Event(int customerID, int eventType, Time time) {
        this._customerID = customerID;
        this._eventType = eventType;
        this._time = time;
        this._serverID = -1;
    }

    public Event(int customerID, int eventType, Time time, int serverID) {
        this(customerID, eventType, time);
        this._serverID = serverID;
    }

    public void setID(int id) {
        this._id = id;
    }

    public int getID() {
        return _id;
    }

    public Time getTime() {
        return _time;
    }

    public int getType() {
        return _eventType;
    }

    public int getServerID() {
        return _serverID;
    }

    public Event changeType(int eventType) {
        return new Event(_customerID, eventType, _time, _serverID);
    }

    public Event changeTime(Time time) {
        return new Event(_customerID, _eventType, time, _serverID);
    }

    public Event changeServer(int serverID) {
        return new Event(_customerID, _eventType, _time, serverID);
    }

    @Override
    public int compareTo(Event other) {
        int output = this.getTime().compareTo(other.getTime());
        if (output == 0)
            output = other.getType() - this.getType();
        if (output == 0)
            output = this.getID() - other.getID();
        return output;
    }

    @Override
    public String toString() {
        String output = null;

        switch (_eventType) {
        case ARRIVES:
            output = String.format("%s %d arrives", _time.toString(), _customerID);
            break;
        case WAITS:
            output = String.format("%s %d waits to be served by %d", _time.toString(), _customerID, _serverID);
            break;
        case SERVED:
            output = String.format("%s %d served by %d", _time.toString(), _customerID, _serverID);
            break;
        case LEAVES:
            output = String.format("%s %d leaves", _time.toString(), _customerID);
            break;
        case DONE:
            output = String.format("%s %d done serving by %d", _time.toString(), _customerID, _serverID);
            break;
        }

        return output;
    }
}