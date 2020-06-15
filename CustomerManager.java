package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class CustomerManager implements Iterable<Customer> {
    private List<Customer> customers;
    public static final int STARTING_ID = 1;

    public CustomerManager(int numberOfCustomers) {
        this.customers = new ArrayList<>();
        for (int i = STARTING_ID; i <= numberOfCustomers; i++) {
            this.customers.add(new Customer(i));
        }
    }

    public Customer findCustomerByID(int customerID) {
        return this.customers.get(customerID - STARTING_ID);
    }

    @Override
    public Iterator<Customer> iterator() {
        return this.customers.iterator();
    }
}