package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;

public final class ServerManager implements ServerManagerInterface {
    private List<Server> servers;
    private int serverCount;

    /**
     * Constructs a new ServerManager.
     */
    public ServerManager() {
        this.servers = new ArrayList<>();
        this.serverCount = 0;
    }

    /**
     * Creates and returns a new human server.
     * @return a new human server
     */
    public Server createHumanServer() {
        serverCount++;
        return new Server(serverCount, Server.ServerType.HUMAN_SERVER);
    }

    /**
     * Creates and returns a new Self Checkout Counter.
     * @return a new Self Checkout Counter
     */
    public Server createSelfCheckoutCounter() {
        serverCount++;
        return new Server(serverCount, Server.ServerType.SELF_CHECKOUT);
    }

    /**
     * Stores a Server.
     * @param server the Server to store
     */
    public void add(Server server) {
        servers.add(server);
    }

    /**
     * Finds and returns a server that is not resting and currently
     * not serving.
     * @return a server that is not resting and currently not serving
     */
    public Server findIdleServer() {
        Server output = null;
        for (Server server : servers) {
            if (!server.isResting() && !server.isServing()) {
                output = server;
                break;
            }
        }
        return output;
    }

    /**
     * Finds and returns a server that still has capacity.
     * @return a server that still has capacity
     */
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

    /**
     * Finds and returns a server that still has capacity and 
     * has the shortest queue.
     * @return a server that still has capacity and has the shortest queue
     */
    public Server findShortestQueueServer() {
        Server output = null;
        int queueLength = Integer.MAX_VALUE;
        for (Server server : servers) {
            if (!server.isFull() && server.getQueueLength() < queueLength) {
                output = server;
                queueLength = server.getQueueLength();
            }
        }
        return output;
    }
}