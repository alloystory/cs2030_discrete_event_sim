package cs2030.simulator;

public class SelfCheckoutCounter extends Server {
    public SelfCheckoutCounter(int id) {
        super(id);
    }

    @Override
    public void setResting() {
        this.isResting = false;
    }

    @Override
    public String toString() {
        return String.format("self-check %d", id);
    }
}