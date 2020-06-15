import java.util.PriorityQueue;

public class EventManager {
    private PriorityQueue<Event> _queue;

    public EventManager() {
        this._queue = new PriorityQueue<Event>();
    }

    public void add(Event event) {
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