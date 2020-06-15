package cs2030.simulator;

public class Customer {
    private int _id;

    public Customer(int id) {
        this._id = id;
    }

    public int getID() {
        return _id;
    }

    @Override
    public String toString() {
        return String.format("%d", _id);
    }
}