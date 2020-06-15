public class Customer {
    private int _id;

    public Customer(int id) {
        this._id = id;
    }

    @Override
    public String toString() {
        return String.format("%d", _id);
    }
}