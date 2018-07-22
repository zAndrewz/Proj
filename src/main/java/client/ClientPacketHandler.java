package client;

import packets.Packet;

import java.util.ArrayList;

public class ClientPacketHandler {

    ArrayList<String> listOfClients = new ArrayList();
    Client client;
    int type;

    public ClientPacketHandler(Client client)
    {
        this.client = client;
    }

    public void getPacketFromServer(Packet packet){
        type = packet.getType();
        if(type == 0) {
            String message = unwrapMessagePack(packet);
            client.cw.writeToChatArea(message);
        }

        if(type == 1){
            listOfClients = unwrapListPack(packet);
            client.cw.setAmountOfClients(listOfClients);
        }
    }

    public <T> void sendPacketToServer(int id, T t)
    {
        if(id == 0)
        {
            wrapAndSendMessagePack((String)t);
        }
    }

    public ArrayList<String> unwrapListPack(Packet<ArrayList<String>> pack)
    {
        ArrayList<String> unwrap = new ArrayList<>();
        unwrap.addAll(pack.getMessage());
        return unwrap;
    }

    public void wrapAndSendMessagePack(String message)
    {
        Packet<String> messagePack = new Packet<>();
        messagePack.setMessage(message);
        messagePack.setType(0);
        client.sendMessage(messagePack);
    }

    public String unwrapMessagePack(Packet<String> pack) {
        return pack.getMessage();
    }
}
