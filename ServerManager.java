package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;

public class ServerManager {
    private List<Server> servers;
    public static final int STARTING_ID = 1;

    public ServerManager(int numberOfServers) {
        this.servers = new ArrayList<>();
        for (int i = STARTING_ID; i <= numberOfServers; i++) {
            servers.add(new Server(i));
        }
    }

    public Server findIdleServer() {
        Server output = null;
        for (Server server : servers) {
            if (!server.isBusy() && !server.hasNextEvent()) {
                output = server;
                break;
            }
        }
        return output;
    }

    public Server findAvailableServer() {
        Server output = null;
        for (Server server : servers) {
            if (!server.hasNextEvent()) {
                output = server;
                break;
            }
        }
        return output;
    }

    public Server findServerByID(int serverID) {
        return servers.get(serverID - STARTING_ID);
    }
}