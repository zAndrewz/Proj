package server;

import packets.Packet;

public abstract class AbstractClientHandler implements Runnable {

    private boolean errored = false;

    abstract protected Packet getMsg() throws Exception;

    abstract void onMessage(Packet T);

    abstract void sendMessage(Packet t) throws Exception;

    @Override
    public void run() {
        while (!errored){
            try {
                Packet msg = getMsg();
                onMessage(msg);
            }catch (Exception e)
            {
                errored = true;
            }
        }
    }
}
