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
        System.out.println("Digite para 1 para criar um servidor socket e 2 para conectar em um servidor socket");
        Scanner scanner = new Scanner(System.in);
        String teste = scanner.nextLine();
        if ((teste).equals("1")) {
            try {
                serverSck = new ServerSocket(4444);
                System.out.println("Vai aguardar conexao");
                sckt = serverSck.accept();
                new SocketThreadListener(sckt).start();
            } catch (IOException e) {
                scanner.close();
                throw new RuntimeException(e);
            }
        } else if ((teste).equals("2")) {
            System.out.println("Qual o ip que deseja conectar?");
            String ip = scanner.nextLine();
            try {
                sckt = new Socket(ip, 4444);
                new SocketThreadSpeaker(sckt).run();
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