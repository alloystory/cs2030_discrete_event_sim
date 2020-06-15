package cs2030.simulator;

public interface CustomerManagerInterface {
    /**
     * Creates a new Customer.
     * @return a new Customer
     */
    Customer createCustomer();

    /**
     * Creates a new greedy customer.
     * @return a new greedy customer
     */
    Customer createGreedyCustomer();

    /**
     * Stores a customer.
     * @param customer the customer to be stored
     */
    void add(Customer customer);
}