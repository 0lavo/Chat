package Client;

import java.io.*;
import java.net.Socket;

public class Client {
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
    private Socket socket;

    public void init() {
        try {

            bufferedWriter.write("What is the inetAddress of the server ? \n");
            bufferedWriter.flush();
            String inetAddress = bufferedReader.readLine();

            bufferedWriter.write("What is the port of the server ? \n");
            bufferedWriter.flush();
            Integer port = Integer.valueOf(bufferedReader.readLine());

            socket = new Socket(inetAddress,port);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
