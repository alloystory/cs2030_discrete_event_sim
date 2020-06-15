public class Event {
    private Customer _customer;
    private int _eventType;
    private Time _time;

    public static final int ARRIVES = 1;

    public Event(Customer customer, int eventType, Time time) {
        this._customer = customer;
        this._eventType = eventType;
        this._time = time;
    }

    public Time getTime() {
        return _time;
    }

    @Override
    public String toString() {
        String output = null;

        switch (_eventType) {
        case 1:
            output = String.format("%s arrives at %s", _customer.toString(), _time.toString());
        }

        return output;
    }
}