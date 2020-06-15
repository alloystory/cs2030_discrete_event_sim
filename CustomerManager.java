package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;

public class CustomerManager {
    private List<Customer> customers;
    private int customerCount;

    public CustomerManager() {
        this.customers = new ArrayList<>();
        this.customerCount = 0;
    }

    public Customer createCustomer() {
        customerCount++;
        return new Customer(customerCount);
    }

    public void add(Customer customer) {
        customers.add(customer);
    }
}