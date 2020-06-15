# CS2030 Discrete Event Simulator
## Usage
1. Compile the classes using `javac -d . *.java`
2. Run the program using `java Main`

## Testing
Test files are stored in `tests/`
``` sh
java Main < tests/test.in | diff - tests/test.out
```

## Input
The program takes in the inputs in the following order:
1. `baseSeed`: the base seed for the RandomGenerator
2. `numberOfServers`: the number of servers to simulate
3. `numberOfSelfCheckoutCounters`: the number of self checkout counters to simulate
4. `maximumQueueLength`: the maximum queue length for each server
5. `numberOfCustomers`: the number of customers
6. `arrivalRate`: the arrival rate for the RandomGenerator
7. `serviceRate`: the server rate for the RandomGenerator
8. `restingRate`: the resting rate for the RandomGenerator
9. `restingProbability`: the probability of a server resting
10. `greedyCustomerProbability`: the probability of a customer being greedy

## Definitions
* Self-checkout counters do not "rest", unlike Human servers
* Greedy Customers will look for the server with the shortest queue
* Normal customers will join any non-full queue
