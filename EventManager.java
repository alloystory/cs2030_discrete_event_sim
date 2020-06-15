import java.util.Queue;
import java.util.LinkedList;

public class EventManager {
    private Queue<Event> _queue;

    public EventManager() {
        this._queue = new LinkedList<Event>();
    }

    public void addEvent(Event event) {
        _queue.offer(event);
    }

    public boolean hasNextEvent() {
        return !_queue.isEmpty();
    }

    public Event nextEvent() {
        return _queue.poll();
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        String separator = "";

        for (Event event : _queue) {
            output.append(separator + event.toString());
            separator = "\n";
        }
        return output.toString();
    }
}