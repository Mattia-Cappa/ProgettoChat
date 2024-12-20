import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ProtocolPrintWriter implements Runnable{
    private Socket socket;

    public ProtocolPrintWriter(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        try {
            Scanner tastiera = new Scanner(System.in);
            while (true) {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(tastiera.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
