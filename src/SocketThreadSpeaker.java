import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class SocketThreadSpeaker extends Thread {
    Socket socket;
    private String mensagem;
    BufferedReader in;

    public SocketThreadSpeaker(Socket sckt) {
        this.socket = sckt;
        this.mensagem = null;
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        try {
            String msg;
            do {
                // System.out.println("informe a mensagem:");
                // msg = scanner.nextLine();
                while (mensagem == null && !socket.isClosed()) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                socket.getOutputStream()
                        .write((mensagem+"§").getBytes());
                mensagem = null;
                socket.getOutputStream().flush();
            } while (!isInterrupted());
            socket.close();
        }catch (SocketException e){
            System.out.println(e.getMessage());
        
        } catch (IOException e) {
            scanner.close();
            throw new RuntimeException(e);
        }
        scanner.close();
    }

    public void setMessage (String msg) {
        this.mensagem = msg;
    }
}