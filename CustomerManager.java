import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class CustomerManager implements Iterable<Customer> {
    private List<Customer> _customers;
    public static final int STARTING_ID = 1;

    public CustomerManager(int numberOfCustomers) {
        this._customers = new ArrayList<>();
        for (int i = STARTING_ID; i <= numberOfCustomers; i++) {
            _customers.add(new Customer(i));
        }
    }

    public Customer getCustomer(int index) {
        return _customers.get(index - STARTING_ID);
    }

    @Override
    public Iterator<Customer> iterator() {
        return _customers.iterator();
    }
}