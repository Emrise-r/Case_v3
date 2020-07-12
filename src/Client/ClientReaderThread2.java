package Client;

import java.io.*;
import java.net.Socket;

public class ClientReaderThread2 implements Runnable {

    Socket clientSocket;
    BufferedReader bis;

    public ClientReaderThread2(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                //doc tin nhan
                bis = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String line;
                if ((line = bis.readLine()) != null) {
                    ClientProgram2.textAreaDisplay_2.appendText(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {}
        }
    }
}