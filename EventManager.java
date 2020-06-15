package cs2030.simulator;

import java.util.PriorityQueue;

public class EventManager {
    private PriorityQueue<Event> globalEventQueue;
    private int eventCount;

    public EventManager() {
        this.globalEventQueue = new PriorityQueue<>();
        this.eventCount = 0;
    }

    public Event createEvent() {
        eventCount++;
        return new Event(eventCount);
    }

    public void add(Event event) {
        globalEventQueue.offer(event);
    }

    public boolean hasNextEvent() {
        return globalEventQueue.size() != 0;
    }

    public Event nextEvent() {
        return globalEventQueue.poll();
    }
}