package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;

public final class CustomerManager implements CustomerManagerInterface {
    private List<Customer> customers;
    private int customerCount;

    /**
     * Constructs a new CustomerManager.
     */
    public CustomerManager() {
        this.customers = new ArrayList<>();
        this.customerCount = 0;
    }

    /**
     * Creates and returns a new normal customer.
     * @return a new normal customer
     */
    public Customer createCustomer() {
        customerCount++;
        return new Customer(customerCount, Customer.CustomerType.NORMAL_CUSTOMER);
    }

    /**
     * Creates and returns a new greedy customer.
     * @return a new greedy customer
     */
    public Customer createGreedyCustomer() {
        customerCount++;
        return new Customer(customerCount, Customer.CustomerType.GREEDY_CUSTOMER);
    }

    /**
     * Stores the customer.
     * @param customer the Customer to be added
     */
    public void add(Customer customer) {
        customers.add(customer);
    }
}