package client;

import server.ClientHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientWindow extends JFrame{
    private JPanel panelWindow;
    private JTextArea messageArea;
    private JTextArea chatArea;
    private JTextArea ClientsArea;
    private JButton sendButton;
    private JButton exitButton;
    ClientHandler cl;
    Client client;

    public ClientWindow(Client c) {
        this.client = c;
        ClientsArea.setText("");
        setSize(400,250);
        add(panelWindow);
        setVisible(true);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message ="";
                message = messageArea.getText();
                if(message.length() > 0)
                {
                    client.handleMessage(client.getName() + ": " + message);
                }
            }
        });


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.handleMessage(client.getName() + ": " + "deleteme");
                System.exit(1);
            }
        });
    }

    public void writeToChatArea(String message){
        chatArea.append("\n");
        chatArea.append(message);
    }

    public void setAmountOfClients(ArrayList<String> arrList)
    {
        ClientsArea.setText("");
        if(!arrList.isEmpty()){
            for(String s : arrList)
                ClientsArea.append("\n" + s);
        }
    }
}
