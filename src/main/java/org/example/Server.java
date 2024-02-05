package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Server extends JFrame {

    private final static int POS_X = 500;
    private final static int POS_Y = 250;

    private final static int WIDTH = 400;
    private  final static int HEIGHT = 300;
    private final JButton buttonStart = new JButton("Start");
    private final JButton buttonStop = new JButton("Stop");
    private final JTextArea chat = new JTextArea();


    private final JPanel buttons = new JPanel(new GridLayout(1,2));
    private boolean isWork;
    private HashMap<String, String> users = new HashMap<>();
    private ArrayList<Client> active = new ArrayList<>();

    public Server(){

        users.put("user1", "qwer");
        users.put("user2", "rewq");
        isWork = false;
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isWork) {
                    chat.append("Server is run!\n");

                } else {
                    read();
                    isWork = true;
                    chat.append("Server start...\n");
                }
            }
        });
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isWork) {
                    isWork = false;
                    chat.append("Server stop...\n");
                    write();
                }
                else {
                    chat.append("Server don't run!\n");
                }
            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Server");
        setAlwaysOnTop(true);

        add(chat, BorderLayout.CENTER);
        buttons.add(buttonStart);
        buttons.add(buttonStop);
        add(buttons, BorderLayout.SOUTH);


        setVisible(true);
    }

    public boolean isWork() {
        return isWork;
    }
    public JTextArea getChat() {
        return chat;
    }
    public boolean login(String key, String value){
        if(users.containsKey(key)){
            if (users.get(key).equals(value)){
                chat.append(key+" connect. \n");
                return true;
            }
        }
        chat.append(key+" пользователь не найден. \n");
        return false;
    }
    public  void addActive(Client cl){
        active.add(cl);
    }
    public void sendMail(String sms){
        for (Client el:active) {
            if(el.isLoging()){
                el.getChatC().append(sms);
            }
        }
    }
    private void write(){
        try(FileWriter fw = new FileWriter("LogServer.txt", false)){
            fw.write(chat.getText());
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void read(){
        try(FileReader fr = new FileReader("LogServer.txt")){
            Scanner sc = new Scanner(fr);
            String s = "";
            while(sc.hasNextLine()){
                s+=sc.nextLine()+"\n";
            }
            chat.append(s);
            sc.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}

