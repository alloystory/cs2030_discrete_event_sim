package cs2030.simulator;

import java.util.Deque;
import java.util.LinkedList;

public class Server {
    private int id;
    private Event currentEvent;
    private Deque<Event> futureEvents;

    public static int MAXIMUM_QUEUE_LENGTH;

    public Server(int id) {
        this.id = id;
        this.currentEvent = null;
        this.futureEvents = new LinkedList<>();
    }

    public int getID() {
        return this.id;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event event) {
        if (currentEvent != null) {
            futureEvents.offerFirst(currentEvent);
        }
        currentEvent = event;
    }

    public void addEvent(Event event) {
        if (currentEvent == null) {
            currentEvent = event;
        } else {
            this.futureEvents.offer(event);
        }
    }

    public Event deleteCurrentEvent() {
        Event output = currentEvent;
        currentEvent = futureEvents.poll();
        return output;
    }

    public boolean isBusy() {
        return currentEvent != null;
    }

    public boolean isFull() {
        return futureEvents.size() == MAXIMUM_QUEUE_LENGTH;
    }
}