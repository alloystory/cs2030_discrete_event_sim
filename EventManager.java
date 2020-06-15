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

    public void delayEventsByServer(Server server, Time restPeriod) {
        PriorityQueue<Event> duplicatedQueue = new PriorityQueue<>();
        while (!globalEventQueue.isEmpty()) {
            Event event = globalEventQueue.poll();
            if (event.getServer() != null && event.getServer().equals(server)) {
                Time nextTime = event.getTime().add(restPeriod);
                event = event.setTime(nextTime);
            }
            duplicatedQueue.offer(event);
        }
        globalEventQueue = duplicatedQueue;
    }
}