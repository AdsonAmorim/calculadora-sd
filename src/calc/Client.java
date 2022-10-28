package calc;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            Socket socket = new Socket("127.0.0.1", 9999);

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String response;

            while (true) {
                response = dataInputStream.readUTF();

                String message = scanner.nextLine();

                dataOutputStream.writeUTF(message);

                if (message.contains("sair")) {
                    socket.close();
                    break;
                }
            }

            scanner.close();
            dataInputStream.close();
            dataOutputStream.close();
        } catch (Exception err) {
            System.err.println(err);
        }
    }

}