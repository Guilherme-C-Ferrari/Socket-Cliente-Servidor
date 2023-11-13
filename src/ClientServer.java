import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientServer {
    static BufferedReader in;
    static Socket sckt;
    static ServerSocket serverSck;

    private static void run() {
        System.out.println("Digite:\n 1 - Criar um servidor socket\n 2 - Conectar em um servidor socket\n 3 - Conectar em um HUB Socket");
        Scanner scanner = new Scanner(System.in);
        String teste = scanner.nextLine();
        if ((teste).equals("1")) {
            try {
                serverSck = new ServerSocket(4444);
                System.out.println("Aguardando conexão");
                sckt = serverSck.accept();
                new SocketThreadListener(sckt).start();
                SocketThreadSpeaker speaker = new SocketThreadSpeaker(sckt);
                speaker.start();
                speaker.setMessage("oi");
            } catch (IOException e) {
                scanner.close();
                throw new RuntimeException(e);
            }
        } else if ((teste).equals("2")) {
            System.out.println("Qual o ip que deseja conectar?");
            String ip = scanner.nextLine();
            try {
                sckt = new Socket(ip, 4444);
                new SocketThreadSpeaker(sckt).start();
                new SocketThreadListener(sckt).start();
            } catch (IOException e) {
                scanner.close();
                throw new RuntimeException(e);
            }
        } else if ((teste).equals("3")) {
            System.out.println("Qual o ip que deseja conectar?");
            String ip = scanner.nextLine();
            String msg = "Checkin: Guilherme";
            try {
                sckt = new Socket(ip, 4444);
                try {
                    sckt.getOutputStream()
                        .write((msg+"§").getBytes());
                    sckt.getOutputStream().flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                new SocketThreadListener(sckt).start();
            } catch (IOException e) {
                scanner.close();
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Opção inválida.");
        }
        scanner.close();
    }
    public static void main(String[] args) {
        ClientServer.run();
    }
}