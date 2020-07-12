package Server;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientOnline extends Thread {
    private Socket clientSocket;
    private Server_v2 server;
    BufferedWriter bos;
    BufferedReader bis;
    private News news = null;
    String line = null;

    public ClientOnline(Socket clientSocket, Server_v2 server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try {

            while (true) {
                bos = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                bis = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //doc tin tu 1 client roi gui lai cho cac client dang ket noi
                if ((line = bis.readLine()) != null) {
                    RunServer.textAreaDisplay.appendText(line + "\n");
                    server.serverSendAll(line);
                    String text = line;
                    botNews();
                }
            }

        } catch (IOException e) {
            server.removeClientOnline(this);
            RunServer.textAreaDisplay.appendText("So client online: " + server.connectionList.size());
            RunServer.textAreaDisplay.appendText("socket: " + "disconnect");
        }
    }

    //gui tin den 1 client trong list
    public void sendMessage(String message) {
        try {
            bos.write(message);
            bos.newLine();
            bos.flush();
        } catch (IOException e) {}
    }
    public void botNews() {

        String text = line.replaceAll("\\[(.*?)\\]: ", "");
        if(text.equalsIgnoreCase("_news")) {
            news = new News(this.server);
            news.start();
        }

        if (news != null) {
            Pattern pattern = Pattern.compile("_(n|N)ews (\\d)");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                news.startMainNews(Integer.parseInt(matcher.group(2)));
                news = null;
            }
        }
    }
}
