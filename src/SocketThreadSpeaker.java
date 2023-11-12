import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SocketThreadSpeaker extends Thread {
    Socket socket;
    BufferedReader in;

    public SocketThreadSpeaker(Socket sckt) {
        this.socket = sckt;
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        try {
            String msg;
            do {
                System.out.println("informe a mensagem:");
                msg = scanner.nextLine();
                socket.getOutputStream()
                        .write((msg+"§").getBytes());
                socket.getOutputStream().flush();
            } while (msg.length() > 0);
            socket.close();
        } catch (IOException e) {
            scanner.close();
            throw new RuntimeException(e);
        }
        scanner.close();
    }
}