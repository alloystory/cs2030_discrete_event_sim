public class Time implements Comparable<Time> {
    private double _time;    
    public Time(double time) {
        this._time = time;
    }

    public Time() {
        this(0);
    }

    public double raw() {
        return _time;
    }

    public Time add(Time other) {
        return new Time(this.raw() + other.raw());
    }

    public Time minus(Time other) {
        return new Time(this.raw() - other.raw());
    }

    @Override
    public int compareTo(Time other) {
        return Double.compare(this.raw(), other.raw());
    }

    @Override
    public String toString() {
        return String.format("%.3f", _time);
    }
}