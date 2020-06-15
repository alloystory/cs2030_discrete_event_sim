package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;

public class ServerManager {
    private List<Server> servers;
    private int serverCount;

    public ServerManager() {
        this.servers = new ArrayList<>();
        this.serverCount = 0;
    }

    public Server createHumanServer() {
        serverCount++;
        return new Server(serverCount);
    }

    public Server createSelfCheckoutCounter() {
        serverCount++;
        return new SelfCheckoutCounter(serverCount);
    }

    public void add(Server server) {
        servers.add(server);
    }

    public Server findIdleServer() {
        Server output = null;
        for (Server server : servers) {
            if (!server.isResting() && !server.isBusy()) {
                output = server;
                break;
            }
        }
        return output;
    }

    public Server findAvailableServer() {
        Server output = null;
        for (Server server : servers) {
            if (!server.isFull()) {
                output = server;
                break;
            }
        }
        return output;
    }
}