package Client.Service;

import java.io.*;
import java.net.Socket;

public class ClientReader implements Runnable{
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    public ClientReader(Socket socket) {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
