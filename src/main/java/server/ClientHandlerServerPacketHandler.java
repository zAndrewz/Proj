package server;

import packets.Packet;

import java.net.SocketException;
import java.util.ArrayList;

public class ClientHandlerServerPacketHandler {
    private ArrayList<String> listOfClients;
    private ClientHandler clientHandler;
    int type;

    public ClientHandlerServerPacketHandler(ClientHandler cl) {
        this.clientHandler = cl;
    }

    public <T> T getPacketFromClient(Packet packet)
    {
        this.type = packet.getType();
        if(type == 0){
            T t = (T) unwrapMessagePack(packet);
            return t;
        }else {
            return null;
        }
    }

    public <T> void sendPacketToClient(int id, T t){
        if(id == 0){
            wrapAndSendMessagePack((String) t);
        }

        if(id == 1){
            wrapAndSendListPack((ArrayList) t);
        }
    }

    public void wrapAndSendListPack(ArrayList<String> list)
    {
        Packet listPack = new Packet<>();
        listPack.setType(type);
        listPack.setMessage(list);

        try{
            clientHandler.sendMessage(listPack);
        }catch (SocketException e)
        {
            e.printStackTrace();
        }
    }

    public void wrapAndSendMessagePack(String message) {
        Packet messagePack = new Packet<>();
        messagePack.setMessage(message);
        messagePack.setType(type);

        try{
            clientHandler.sendMessage(messagePack);
        }catch (SocketException e)
        {
            e.printStackTrace();
        }
    }

    public String unwrapMessagePack(Packet<String> pack) { return pack.getMessage();}
}