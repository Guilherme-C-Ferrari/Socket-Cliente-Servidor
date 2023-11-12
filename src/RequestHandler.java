import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class RequestHandler extends Thread {
    private Socket socket;
    RequestHandler (Socket sckt) {
        this.socket = sckt;
    }

    @Override
    public void run() {
        try {
            System.out.println( "Received a connection" );

            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
            Scanner scanner = new Scanner(System.in);

            String msg;
            String line = in.readLine();
            while( line != null && line.length() > 0 ) {

                System.out.println("informe a mensagem:");
                msg = scanner.nextLine();
                socket.getOutputStream()
                        .write((msg+"§").getBytes());
                socket.getOutputStream().flush();
                line = in.readLine();
                System.out.println(in);
            }

            in.close();
            socket.close();
            scanner.close();

            System.out.println( "Connection closed" );
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }
}