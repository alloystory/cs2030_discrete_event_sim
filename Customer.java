package cs2030.simulator;

public class Customer {
    private int id;

    public Customer(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    @Override
    public String toString() {
        return String.format("%d", this.id);
    }
}