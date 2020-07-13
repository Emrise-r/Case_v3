package Server;

import java.io.*;
import java.net.*;
import java.util.*;


public class Server_v2 extends Thread{

    //Tao list ten cac client dang ket noi
    List<ClientOnline> connectionList = new ArrayList<>();

    public List<ClientOnline> getConnectionList() {
        return connectionList;
    }
    public void removeClientOnline(ClientOnline clientOnline) {
        this.connectionList.remove(clientOnline);
    }

    //gui tin tu server den cac user trong list
    public void serverSendAll(String mess) {
        for (ClientOnline clientOnline : this.connectionList) {
            clientOnline.sendMessage(mess);
        }
    }

    @Override
    public void run() {

        try {
            //tao server socket
            ServerSocket serverSocket = new ServerSocket(4321);

            while (true) {

                RunServer.textAreaDisplay.appendText("Waiting for User \n");
                Socket clientSocket = serverSocket.accept();
                RunServer.textAreaDisplay.appendText("Client has accepted! \n");
                //Khi nguoi dung ket noi, tao doi tuong client online de quan ly user
                ClientOnline clientOnline = new ClientOnline(clientSocket, this);
                connectionList.add(clientOnline);
                RunServer.textAreaDisplay.appendText("Số client đã kết nối: " + connectionList.size() + "\n");
                clientOnline.start();

            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
