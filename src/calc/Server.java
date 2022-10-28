package calc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            String str;
            while (true) {
                Socket connection = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(connection.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());

                Thread thread = new HandleClient(connection, dataInputStream, dataOutputStream);
                thread.start();
            }
            serverSocket.close();
        } catch (Exception err) {
            System.err.println(err);
        }
    }

}