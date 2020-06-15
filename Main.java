import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfServers = scanner.nextInt();
        List<Double> arrivalTimes = new ArrayList<>();
        while (scanner.hasNextDouble()) {
            arrivalTimes.add(scanner.nextDouble());
        }
        Simulator simulator = new Simulator(arrivalTimes, numberOfServers, false);
        simulator.start();
    }
}