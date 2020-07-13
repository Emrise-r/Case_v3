package Client;

import java.io.*;
import java.net.Socket;

public class ClientReaderThread implements Runnable {

    Socket clientSocket;
    BufferedReader bis;

    public ClientReaderThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {

                bis = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String line;
                if ((line = bis.readLine()) != null) {
//                    System.out.println(line);
                    ClientProgram.textAreaDisplay_1.appendText(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {}
        }
    }
}