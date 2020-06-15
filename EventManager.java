package cs2030.simulator;

import java.util.PriorityQueue;

public class EventManager {
    private PriorityQueue<Event> eventQueue;
    private int currentEventID;

    public static final int STARTING_ID = 1;
    
    public EventManager() {
        this.eventQueue = new PriorityQueue<Event>();
        this.currentEventID = STARTING_ID;
    }

    public void add(Event event) {
        if (event.getID() == -1) {
            event = event.changeID(currentEventID);
            currentEventID++;
        }
        this.eventQueue.offer(event);
    }

    public boolean hasNextEvent() {
        return !this.eventQueue.isEmpty();
    }

    public Event nextEvent() {
        return this.eventQueue.poll();
    }

    public Event findEventByID(int eventID) {
        PriorityQueue<Event> duplicatedQueue = new PriorityQueue<>(eventQueue);
        Event output = null;
        while (!duplicatedQueue.isEmpty()) {
            Event event = duplicatedQueue.poll();
            if (event.getID() == eventID) {
                output = event;
                break;
            }
        }
        return output;
    }

    @Override
    public String toString() {
        PriorityQueue<Event> duplicatedQueue = new PriorityQueue<>(eventQueue);
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