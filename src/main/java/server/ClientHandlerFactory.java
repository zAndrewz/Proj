package server;

import java.net.Socket;

public class ClientHandlerFactory {
    private static  ClientHandlerFactory factory = new ClientHandlerFactory();

    public static ClientHandlerFactory getFactory() { return factory;}

    public static void setDefaultFactory(ClientHandlerFactory factory) { ClientHandlerFactory.factory = factory;}

    public ClientHandler createClientHandler(Socket socket, Server server)
    {
        ClientHandler clientHandler = null;

        clientHandler = new ClientHandler(server, socket);

        return clientHandler;
    }

}
