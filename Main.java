import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import cs2030.simulator.Simulator;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int baseSeed = scanner.nextInt();
        int numberOfServers = scanner.nextInt();
        int numberOfSelfCheckoutCounters = scanner.nextInt();
        int maximumQueueLength = scanner.nextInt();
        int numberOfCustomers = scanner.nextInt();
        double arrivalRate = scanner.nextDouble();
        double serviceRate = scanner.nextDouble();
        double restingRate = scanner.nextDouble();
        double restingProbability = scanner.nextDouble();

        Simulator simulator = new Simulator(baseSeed, numberOfServers, numberOfSelfCheckoutCounters, maximumQueueLength, numberOfCustomers, arrivalRate, serviceRate, restingRate, restingProbability);
        simulator.start();
    }
}