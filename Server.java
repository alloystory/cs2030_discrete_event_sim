public class Server {
    private int _id;
    private Time _nextTime;
    public static final Time SERVICE_TIME = new Time(1.0);

    public Server(int id) {
        this._id = id;
        this._nextTime = new Time(0);
    }

    public void workOn(Event event) {
        _nextTime = event.getTime().add(SERVICE_TIME);
    }

    public Time nextTime() {
        return _nextTime;
    }
}