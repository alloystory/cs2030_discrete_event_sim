public class Event implements Comparable<Event> {
    private Customer _customer;
    private int _eventType;
    private Time _time;

    public static final int ARRIVES = 1;
    public static final int SERVED = 2;
    public static final int LEAVES = 3;

    public Event(Customer customer, int eventType, Time time) {
        this._customer = customer;
        this._eventType = eventType;
        this._time = time;
    }

    public Time getTime() {
        return _time;
    }

    public int getType() {
        return _eventType;
    }

    public Event changeType(int et) {
        return new Event(_customer, et, _time);
    }

    public Event changeTime(Time time) {
        return new Event(_customer, _eventType, time);
    }

    @Override
    public int compareTo(Event other) {
        return this.getTime().compareTo(other.getTime());
    }

    @Override
    public String toString() {
        String output = null;

        switch (_eventType) {
        case ARRIVES:
            output = String.format("%s %s arrives", _time.toString(), _customer.toString());
            break;
        case SERVED:
            output = String.format("%s %s served", _time.toString(), _customer.toString());
            break;
        case LEAVES:
            output = String.format("%s %s leaves", _time.toString(), _customer.toString());
            break;
        }

        return output;
    }
}