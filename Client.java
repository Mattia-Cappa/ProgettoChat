import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try{
            Socket client = new Socket(InetAddress.getLocalHost(),8080);

            PrintWriter out = new PrintWriter(client.getOutputStream(),true);

            ClientGUI gui = new ClientGUI(out);


            ProtocolPrintWriter pp = new ProtocolPrintWriter(client);
            ProtocolScanner ps = new ProtocolScanner(client, gui);

            Thread tp = new Thread(pp);
            Thread ts = new Thread(ps);

            ts.start();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
