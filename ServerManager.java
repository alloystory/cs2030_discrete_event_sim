import java.util.List;
import java.util.ArrayList;

public class ServerManager {
    private List<Server> _servers;
    public static final int STARTING_ID = 1;

    public ServerManager(int numberOfServers) {
        this._servers = new ArrayList<>();
        for (int i = STARTING_ID; i <= numberOfServers; i++) {
            _servers.add(new Server(i));
        }
    }

    public Server getServerFreeAt(Time time) {
        Server output = null;
        for (Server server : _servers) {
            Time serverNextTime = server.nextTime();
            if (time.compareTo(serverNextTime) >= 0) {
                output = server;
                break;
            }
        }
        return output;
    }

    public Server getIdleServer() {
        Server output = null;
        for (Server server : _servers) {
            if (!server.hasNextEvent()) {
                output = server;
                break;
            }
        }
        return output;
    }

    public Server getServer(int index) {
        return _servers.get(index - STARTING_ID);
    }
}