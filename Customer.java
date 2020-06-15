package cs2030.simulator;

final class Customer {
    private int id;
    private CustomerType customerType;

    /**
     * List of Valid Customer Types.
     */
    enum CustomerType {
        NORMAL_CUSTOMER(1),
        GREEDY_CUSTOMER(2);

        private final int customerType;
        CustomerType(int customerType) {
            this.customerType = customerType;
        }
    }

    /**
     * Constructs a new Customer.
     * @param id the id of the customer
     * @param customerType the customer type of the customer
     */
    Customer(int id, CustomerType customerType) {
        this.id = id;
        this.customerType = customerType;
    }

    /**
     * Returns the id of the customer.
     * @return the id of the customer
     */
    int getID() {
        return id;
    }

    /**
     * Returns the type of the customer.
     * @return the type of the customer
     */
    CustomerType getType() {
        return customerType;
    }

    /**
     * Returns the String representation of the Customer.
     */
    @Override
    public String toString() {
        String output = null;
        switch (customerType) {
            case NORMAL_CUSTOMER:
                output = String.format("%d", id);
                break;
            case GREEDY_CUSTOMER:
                output = String.format("%d(greedy)", id);
                break;
            default:
                break;
        }
        return output;
    }
}