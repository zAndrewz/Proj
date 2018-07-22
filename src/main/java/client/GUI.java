package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame{
    private JButton enterButton;
    private JTextField hostField;
    private JTextField portField;
    private JTextField nameField;
    private JPanel form;
    LoginData data = new LoginData();

    public GUI() {
        setSize(250,100);
        add(form);
        setVisible(true);
        hostField.setText("localhost");
        portField.setText("8090");
        nameField.setText("Bobo");

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setData(data);
            }
        });
    }

    public void setData(LoginData data)
    {
        data.setHost(hostField.getText());
        data.setPort(Integer.valueOf(portField.getText()));
        data.setUsername(nameField.getText());
        setVisible(false);
        new Client(data);
    }
}
