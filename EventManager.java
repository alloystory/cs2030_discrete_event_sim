import java.util.PriorityQueue;

public class EventManager {
    private PriorityQueue<Event> _queue;
    private int _currentID;
    public static final int STARTING_ID = 1;
    
    public EventManager() {
        this._queue = new PriorityQueue<Event>();
        this._currentID = STARTING_ID;
    }

    public void add(Event event) {
        event.setID(_currentID);
        _currentID++;
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
        PriorityQueue<Event> duplicatedQueue = new PriorityQueue<>(_queue);
        StringBuilder output = new StringBuilder();
        String separator = "";

        while (!duplicatedQueue.isEmpty()) {
            Event event = duplicatedQueue.poll();
            output.append(separator + event.toString());
            separator = "\n";
        }
        return output.toString();
    }
}