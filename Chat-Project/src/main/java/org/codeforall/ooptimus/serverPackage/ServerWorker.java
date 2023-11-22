package org.olavo.serverPackage;

import java.io.*;
import java.net.Socket;

public class ServerWorker implements Runnable{
    private Socket socket;
    private BufferedReader bufferedReader;
    private Server server;
    private String clientName;
    private BufferedWriter bufferedWriter;

    public ServerWorker(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("----- Welcome to my server what is your name ? ----- \n");
            bufferedWriter.flush();
            clientName = bufferedReader.readLine();
            System.out.println(clientName + " has entered the server");
            while (!socket.isClosed()) {
                server.messageTreatment(bufferedReader.readLine(), socket);
            }

       } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public String getClientName() {
        return clientName;
    }
}
