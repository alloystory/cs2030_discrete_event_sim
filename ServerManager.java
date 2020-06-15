import java.util.List;
import java.util.ArrayList;

public class ServerManager {
    private List<Server> _servers;
    public ServerManager(int numberOfServers) {
        this._servers = new ArrayList<>();
        for (int i = 1; i <= numberOfServers; i++) {
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
}