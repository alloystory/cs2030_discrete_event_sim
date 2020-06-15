import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import cs2030.simulator.Simulator;
import cs2030.simulator.EventManager;
import cs2030.simulator.ServerManager;
import cs2030.simulator.CustomerManager;
import cs2030.simulator.StatisticsManager;

public final class Main {
    /**
     * Main Program to run.
     * @param args input arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Simulator simulator = new Simulator(scanner.nextInt(),
                                            scanner.nextInt(),
                                            scanner.nextInt(),
                                            scanner.nextInt(),
                                            scanner.nextInt(),
                                            scanner.nextDouble(),
                                            scanner.nextDouble(),
                                            scanner.nextDouble(),
                                            scanner.nextDouble(),
                                            scanner.nextDouble());
        
        EventManager eventManager = new EventManager();
        ServerManager serverManager = new ServerManager();
        CustomerManager customerManager = new CustomerManager();
        StatisticsManager statisticsManager = new StatisticsManager();
        simulator.setManagers(eventManager, serverManager, customerManager, statisticsManager);
        simulator.start();
    }
}