package calc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class HandleClient extends Thread {
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    final Socket socket;

    // Constructor
    public HandleClient(Socket connection, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.socket = connection;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {
        String message;
        String operation;
        String mainText = "Qual operação deseja realizar?[ + | - | * | / ]..\n" +
                "Digite Sair para encerrar a conexão.";

        // Ask user what he wants
        try {
            dataOutputStream.writeUTF(mainText);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {

            try {
                // receive the answer from client
                message = dataInputStream.readUTF().trim();

                if (message.equals("sair")) {
                    this.socket.close();
                    System.out.println("Conexão encerrada.");
                    break;
                }

                // write on output stream based on the
                // answer from the client
                switch (message) {

                    case "+":
                        operation = plus();
                        break;

                    case "-":
                        operation = minus();
                        break;

                    case "*":
                        operation = mult();
                        break;

                    case "/":
                        operation = div();
                        break;

                    default:
                        operation = "Entrada incorreta";
                        break;
                }

                operation = operation + "\n" + mainText;

                dataOutputStream.writeUTF(operation);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // closing resources
            this.dataInputStream.close();
            this.dataOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int readFirst() {
        String unsanitezed;
        try {
            do {
                dataOutputStream.writeUTF("Digite o primeiro número:");
                unsanitezed = dataInputStream.readUTF().trim();
            } while (!unsanitezed.matches("^[0-9]+$"));
            return Integer.parseInt(unsanitezed);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int readSecond() {
        String unsanitezed;
        try {
            do {
                dataOutputStream.writeUTF("Digite o segundo número:");
                unsanitezed = dataInputStream.readUTF().trim();
            } while (!unsanitezed.matches("^[0-9]+$"));
            return Integer.parseInt(unsanitezed);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String plus() {
        int total = readFirst() + readSecond();
        return "O total é: " + total;
    }

    private String minus() {
        int total = readFirst() - readSecond();
        return "O total é: " + total;
    }

    private String mult() {
        int total = readFirst() * readSecond();
        return "O total é: " + total;
    }

    private String div() {
        int total = readFirst() * readSecond();
        return "O total é: " + total;
    }

}
