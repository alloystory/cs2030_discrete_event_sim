package cs2030.simulator;

import java.util.PriorityQueue;

public final class EventManager implements EventManagerInterface {
    private PriorityQueue<Event> globalEventQueue;
    private int eventCount;

    /**
     * Constructs a new EventManager.
     */
    public EventManager() {
        this.globalEventQueue = new PriorityQueue<>();
        this.eventCount = 0;
    }

    /**
     * Creates and returns a new Event.
     * @return a new Event
     */
    public Event createEvent() {
        eventCount++;
        return new Event(eventCount);
    }

    /**
     * Stores the customer.
     */
    public void add(Event event) {
        globalEventQueue.offer(event);
    }

    /**
     * Retruns true if there are more events.
     * @return true if there are more events
     */
    public boolean hasNextEvent() {
        return globalEventQueue.size() != 0;
    }

    /**
     * Finds and returns the next event.
     * @return the next event
     */
    public Event nextEvent() {
        return globalEventQueue.poll();
    }
    
    /**
     * Find all events that contains this server and increment the 
     * time field by the specified restPeriod.
     * @param server the Server to search
     * @param restPeriod the restPeriod
     */
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