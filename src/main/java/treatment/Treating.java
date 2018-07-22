package treatment;

import packets.Packet;
import server.ClientHandler;
import server.Server;

public abstract class Treating {
    private Treating next;

    public Treating linkWith(Treating next)
    {
        this.next = next;
        return next;
    }

    public abstract boolean treat(Server server, ClientHandler cl, Packet t);

    protected boolean treatNext(Server server, ClientHandler cl, Packet t)
    {
        if(next == null)
        {
            return true;
        }
        return next.treat(server, cl, t);
    }
}
