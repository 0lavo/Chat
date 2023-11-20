package Server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public final static int port = 8000;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private boolean serverOn = false;
    private ServerWorker serverWorker;
    private ArrayList<ServerWorker> serverWorkerArrayList;
    private ExecutorService fixedPool = Executors.newFixedThreadPool(30);
    private BufferedWriter bufferedWriter;

    public void powerOn() {
        serverOn = true;
        try {
            serverSocket = new ServerSocket(port);
            while (serverOn) {
                clientSocket = serverSocket.accept();
                serverWorker = new ServerWorker(clientSocket, this);
                serverWorkerArrayList.add(serverWorker);
                fixedPool.submit(serverWorker);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void messageTreatment(String message, Socket socket) {
        switch (message) {
            case "/quit":
                finishConnection(socket);
                break;
            case "/list":
                listClients(socket);
                break;
            default:
                sendMessageAll(message, socket);
                break;
        }
    }
    private void finishConnection(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void listClients(Socket socket) {

        int numberOfClients = 1;

        try {

            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("---------------");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        for (ServerWorker s : serverWorkerArrayList) {
            if (s.getSocket() == socket) {
                continue;
            }

            try {

                bufferedWriter.write(numberOfClients + " - " + s.getClientName());
                numberOfClients++;

            }catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            bufferedWriter.write("---------------");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendMessageAll(String message, Socket socket) {

        for (ServerWorker s : serverWorkerArrayList) {
            try {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(s.getSocket().getOutputStream()));
                bufferedWriter.write(s.getClientName() + ": " + message);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}