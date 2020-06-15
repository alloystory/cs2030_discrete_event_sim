package cs2030.simulator;

public class Server {
    private int _id;
    private Time _nextTime;
    private Integer _nextEventID;

    public static final Time SERVICE_TIME = new Time(1.0);

    public Server(int id) {
        this._id = id;
        this._nextTime = new Time(0);
        this._nextEventID = null;
    }

    public int getID() {
        return _id;
    }

    public boolean hasNextEvent() {
        return _nextEventID != null;
    }

    public void setNextEvent(Integer nextEventID) {
        this._nextEventID = nextEventID;
    }


    public void workOn(Event event) {
        if (event.getTime().compareTo(_nextTime) > 0) {
            _nextTime = event.getTime().add(SERVICE_TIME);
        } else {
            _nextTime = _nextTime.add(SERVICE_TIME);
        }
    }

    public Time nextTime() {
        return _nextTime;
    }
}