package server;

import logs.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import packets.Packet;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;

public class ClientHandler extends AbstractClientHandler implements Closeable, Serializable {
    transient private Logger log = new Logger();
    transient SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
    public String clientName = "";

    @Autowired
    transient private Server server;

    transient private Socket clientSocket;
    transient private ObjectInputStream in;
    transient private ObjectOutputStream out;

    ClientHandlerServerPacketHandler packetHandler = new ClientHandlerServerPacketHandler(this);

    public ClientHandler(Server server, Socket socket)
    {
        this.clientSocket = socket;

        try{
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    protected Packet getMsg() throws Exception{
        Packet mes = (Packet) in.readObject();
        return mes;
    }

    void onMessage(Packet t) {
        log.logClient(" " + clientSocket.getInetAddress() + " " + t.getMessage());
        server.handler(this,t);
    }

    void sendMessage(Packet t) throws SocketException {
        try {
            out.writeObject(t);
            out.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getList() { return server.getList();}

    @Override
    public void run() {
        try {
            try {
                clientName = packetHandler.getPacketFromClient((Packet) in.readObject());
                server.sendList();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (clientName.length() > 1) {
                server.handler(this, new Packet(0, clientName + " has come"));
                log.logServer(clientSocket.getInetAddress() + " " + clientName + " has come");
            } else {
                server.handler(this, new Packet(0, clientName + " somebody was here"));
                log.logServer(clientSocket.getInetAddress() + " " + clientName + " somebody was here");
            }
        } catch (IOException e) {
            System.out.println("run error");
            e.printStackTrace();
        }

        super.run();
    }


        public void close() throws IOException {
            clientSocket.close();
        }
    }
