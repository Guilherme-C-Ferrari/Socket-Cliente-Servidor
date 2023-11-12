import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketThreadListener extends Thread {
    Socket socket;
    BufferedReader in;

    public SocketThreadListener(Socket sckt) {
        this.socket = sckt;
    }

    public void run() {
        try {
            in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
            String entrada = "";
            int ret = 0;
            System.out.println("conexao de " +
                socket.getRemoteSocketAddress().toString());
            while (!socket.isClosed() && ret >= 0) {
                ret = socket.getInputStream().read();
                entrada += (char) ret;
                if ((char) ret == '§') {
                entrada = entrada.replace("§", "");
                entrada = entrada.replace("Â", "");
                System.out.println(entrada);
                entrada = "";
                }
            }
        socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}