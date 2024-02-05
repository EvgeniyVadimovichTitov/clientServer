package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client extends JFrame{
    private final static int POS_X = 200;
    private final static int POS_Y = 250;
    private final static int WIDTH = 400;
    private  final static int HEIGHT = 400;

    private final JPanel panelConnect = new JPanel(new GridLayout(2,2));
    private final TextField Tlogin = new TextField();
    private final TextField Tpassword = new TextField();
    private final JButton btnConnect = new JButton("Connect");
    private  final JPanel panelMessage = new JPanel();
    private final TextField Tmessage = new TextField(25);
    private final JButton btnSend = new JButton("Send");
    private final TextArea chatC = new TextArea();
    private Server server;
    private String user;
    private String psw;
    private boolean isConnect;
    private boolean isLoging;

    public Client(Server server){
        isConnect = false;
        isLoging=false;
        this.server=server;
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user= Tlogin.getText();
                psw = Tpassword.getText();
                connect();
                login();
            }
        });

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isConnect&&isLoging&& server.isWork()){
                    server.getChat().append(user+": "+Tmessage.getText()+"\n");
                    server.sendMail(user+": "+Tmessage.getText()+"\n");
                    Tmessage.setText("");
                }
            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat-Client");
        setAlwaysOnTop(true);

        panelConnect.add(Tlogin);
        panelConnect.add(Tpassword);
        panelConnect.add(btnConnect);
        add(panelConnect, BorderLayout.NORTH);

        add(chatC);



        panelMessage.add(Tmessage);
        panelMessage.add(btnSend);
        add(panelMessage, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void connect(){
        if(server.isWork()){
            chatC.append("Connection on server established\n");
            isConnect = true;
        }else{
            chatC.append("Connection on server not established\n");}
    }

    private void login(){
        if (isConnect == false){return;}
        if(server.login(user, psw)){
            chatC.append(user +" welcome!\n");
            isLoging = true;
        }else{
            chatC.append("Incorrect login or password\n");}
    }

    public boolean isLoging() {
        return isLoging;
    }

    public TextArea getChatC() {
        return chatC;
    }
}
