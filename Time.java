public class Time implements Comparable<Time> {
    private double _time;
    public Time(double time) {
        this._time = time;
    }

    public double getTime() {
        return _time;
    }

    @Override
    public int compareTo(Time other) {
        return Double.compare(this.getTime(), other.getTime());
    }

    @Override
    public String toString() {
        return String.format("%.3f", _time);
    }
}