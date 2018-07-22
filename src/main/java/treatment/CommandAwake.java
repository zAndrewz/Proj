package treatment;

import packets.Packet;
import server.ClientHandler;
import server.Server;

public class CommandAwake extends Treating{
    @Override
    public boolean treat(Server server, ClientHandler cl, Packet t) {
        return treatNext(server, cl, t);
    }
}
