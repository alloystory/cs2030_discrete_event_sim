package cs2030.simulator;

public class Server {
    private int id;
    private int currentEventID;
    private int nextEventID;

    public Server(int id) {
        this.id = id;
        this.currentEventID = -1;
        this.nextEventID = -1;
    }

    public int getID() {
        return this.id;
    }

    public int getCurrentEventID() {
        return this.currentEventID;
    }

    public int getNextEventID() {
        return this.nextEventID;
    }

    public void setCurrentEventID(int eventID) {
        this.currentEventID = eventID;
    }

    public void setNextEventID(int eventID) {
        this.nextEventID = eventID;
    }

    public void deleteNextEventID() {
        this.nextEventID = -1;
    }

    public boolean isBusy() {
        return currentEventID != -1;
    }

    public boolean hasNextEvent() {
        return nextEventID != -1;
    }
}