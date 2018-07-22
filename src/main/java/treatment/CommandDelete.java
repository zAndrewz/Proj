package treatment;

import packets.Packet;
import server.ClientHandler;
import server.Server;

public class CommandDelete extends Treating {
    String message = "";

    @Override
    public boolean treat(Server server, ClientHandler cl, Packet t) {
        if(t.getType() == 0)
            message = (String) t.getMessage();
        if(message.contains("deleteme")) {
            server.userQuit(cl);
            return true;
        }
        return treatNext(server, cl, t);
    }
}
