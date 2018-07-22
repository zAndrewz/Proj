package client;

import packets.Packet;

import javax.swing.*;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Closeable {
    private String name = "";
    private Socket socket;
    boolean flag = false;

    private ObjectOutputStream outMessage;
    private ObjectInputStream inMessage;

    private ClientPacketHandler packetHandler;
    ClientWindow cw;

    public Client(LoginData data)
    {
        name = data.getUsername();
        packetHandler = new ClientPacketHandler(this);

        try{
            socket = new Socket(data.getHost(), data.getPort());
            outMessage = new ObjectOutputStream(socket.getOutputStream());
            inMessage = new ObjectInputStream(socket.getInputStream());
        }catch (IOException e) {
            e.printStackTrace();
        }

        cw = new ClientWindow(this);
        cw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cw.setVisible(true);
        run();
    }

    public void setFlag(boolean flag) {this.flag = flag;}

    public void run() {
        handleMessage(name);
        final boolean[] flag = {true};
        new Thread(()-> {
            while(flag[0]){
                if(socket.isConnected()){
                    try{
                        final Packet packet =  (Packet) inMessage.readObject();
                        System.out.println("Client recv:" + packet);
                        if(packet.getType() == 0)
                        {
                            new Thread(new Runnable() {
                                public void run() {
                                    cw.writeToChatArea((String) packet.getMessage());
                                    packet.setType(3);
                                }
                            }).start();
                        }
                        if (packet.getType() == 1)
                        {
                            new Thread(new Runnable() {
                                public void run() {
                                    cw.setAmountOfClients((ArrayList<String>)packet.getMessage());
                                }
                            }).start();
                        }
                    }catch (ClassNotFoundException e)
                    {
                        e.printStackTrace();
                    } catch (IOException e)
                    {
                        System.out.println("Error here");
                        e.printStackTrace();
                        flag[0] = false;
                    }
                }
            }
        }).start();
    }

    public <T> void handleMessage(T t) { packetHandler.sendPacketToServer(0, t);}

    public <T> void sendMessage(Packet<String> t){
        try{
            outMessage.writeObject(t);
            outMessage.flush();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public void close () { handleMessage("deleteme");}
}
