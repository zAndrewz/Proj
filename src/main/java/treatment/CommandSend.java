package treatment;

import packets.Packet;
import server.ClientHandler;
import server.Server;

public class CommandSend extends Treating{
    @Override
    public boolean treat(Server server, ClientHandler cl, Packet t) {
        if(!t.toString().isEmpty()){
            server.sendAllExcept(cl,t);
            return true;
        }
        return treatNext(server, cl, t);
    }
}
