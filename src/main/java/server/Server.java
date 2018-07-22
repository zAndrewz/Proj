package server;


import logs.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import packets.Packet;
import treatment.*;

import javax.annotation.PostConstruct;
import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
public class Server implements Closeable {
    private Treating treat;
    private SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
    private ServerSocket serverSocket;
    private Socket socket;
    private Properties properties;

    @Autowired
    private Messages msg;

    private List<ClientHandler> list = new ArrayList<>();

    @Autowired
    private Logger log;

    @Autowired
    private ClientHandlerFactory chf;

    @Value("${server.port}")
    int port;

    public Server(){}

    @PostConstruct
    public void init() throws Exception {
        serverSocket = new ServerSocket(port);
        this.treat = new CommandDelete();
        this.treat.linkWith(new CommandSleep()).linkWith(new CommandAwake()).linkWith(new CommandSend());
        log.logServer(msg.serverStarted(port));
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            log.logServer(format.format(new Date() + " " + msg.clientConnect(String.valueOf(socket.getInetAddress()))));
            ClientHandler clientHandler = chf.createClientHandler(socket, this);
            list.add(clientHandler);
            System.out.println("[ADD] " + list.size());

            new Thread(clientHandler, "clientHandler").start();
        }
    }

        public void sendList() {
        ArrayList<String> clientNames = new ArrayList<>();
        for(ClientHandler cl : list){
            clientNames.add(cl.clientName);
        }
        sendAll(new Packet<>(1, clientNames));
    }

    public void userQuit(ClientHandler c){
        log.logServer(msg.clientLeft(c.clientName));
        Packet<String> mes = new Packet<>();
        mes.setType(0);
        mes.setMessage(msg.clientLeft(c.clientName));
        sendAll(mes);
        removeClient(c);
        sendList();

        try{
            c.close();
        }catch(IOException e){
            System.out.println("user Quit meth error");
            e.printStackTrace();
        }
    }

    public void removeClient(ClientHandler c) {list.remove(c);}

    public void sendAll(Packet msg) {
        for(ClientHandler c : list) {
            try{
                c.sendMessage(msg);
            }catch (SocketException e){
                list.remove(c);
                System.out.println("[DELETE] " + list.size());
            }
        }
    }

    public <T> void sendAllExcept(ClientHandler ch, Packet msg) {
        for(ClientHandler c: list)
        {
            if(c.equals(ch))
                continue;
            else
                try
                {
                    c.sendMessage(msg);
                }catch (SocketException e){
                    list.remove(c);
                    System.out.println("[DELETE] " + list.size());
                }
        }
    }

    public void handler(ClientHandler ch, Packet t){
        boolean success = false;
        do {
            success = treat.treat(this, ch, t);
        }while (!success);
    }

    @Override
    public void close() throws IOException {
        for (ClientHandler c : list) {
            c.close();
            System.out.println("[CLOSED] " + list.size());
        }
    }

    public int getList() {
        return list.size();
    }
}
