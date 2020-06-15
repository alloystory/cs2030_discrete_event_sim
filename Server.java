package cs2030.simulator;

import java.util.Deque;
import java.util.LinkedList;

public class Server {
    protected int id;
    protected Event currentEvent;
    protected Deque<Event> futureEvents;
    protected boolean isResting;

    public static int MAXIMUM_QUEUE_LENGTH;

    public Server(int id) {
        this.id = id;
        this.currentEvent = null;
        this.futureEvents = new LinkedList<>();
        this.isResting = false;
    }

    public int getID() {
        return this.id;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event event) {
        currentEvent = event;
    }

    public void addFutureEvent(Event event) {
        this.futureEvents.offer(event);
    }

    public void addFirstFutureEvent(Event event) {
        this.futureEvents.offerFirst(event);
    }

    public Event deleteCurrentEvent() {
        Event output = currentEvent;
        currentEvent = null;
        return output;
    }

    public Event pollFutureEvent() {
        return futureEvents.poll();
    }

    public boolean isBusy() {
        return currentEvent != null;
    }

    public boolean hasWaitingEvents() {
        return futureEvents.size() != 0;
    }

    public boolean isFull() {
        return futureEvents.size() == MAXIMUM_QUEUE_LENGTH;
    }

    public boolean isResting() {
        return this.isResting;
    }

    public void setResting() {
        this.isResting = true;
    }

    public void setAwake() {
        this.isResting = false;
    }

    public boolean equals(Server other) {
        return this.getID() == other.getID();
    }

    @Override
    public String toString() {
        return String.format("server %d", id);
    }
}