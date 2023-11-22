package org.olavo.clientPackage;

import org.olavo.clientPackage.Service.ClientReader;
import org.olavo.clientPackage.Service.ClientWriter;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
    private Socket socket;
    private ExecutorService fixedPool = Executors.newFixedThreadPool(2);

    public void init() {
        try {

            bufferedWriter.write("What is the inetAddress of the server ? \n");
            bufferedWriter.flush();
            String inetAddress = bufferedReader.readLine();

            bufferedWriter.write("What is the port of the server ? \n");
            bufferedWriter.flush();
            Integer port = Integer.valueOf(bufferedReader.readLine());

            socket = new Socket(inetAddress,port);
            fixedPool.submit(new ClientReader(socket));
            fixedPool.submit(new ClientWriter(socket));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
