package cs2030.simulator;

public class GreedyCustomer extends Customer {
    public GreedyCustomer(int id) {
        super(id);
    }

    @Override
    public String toString() {
        return String.format("%d(greedy)", this.id);
    }
}