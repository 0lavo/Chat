package org.olavo.clientPackage.Service;

import java.io.*;
import java.net.Socket;

public class ClientWriter implements Runnable{
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private BufferedWriter bufferedWriter;

    public ClientWriter(Socket socket) {
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            bufferedWriter.write(bufferedReader.readLine() + "\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
