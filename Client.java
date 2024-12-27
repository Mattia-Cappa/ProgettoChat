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

            ProtocolPrintWriter pp = new ProtocolPrintWriter(client);
            ProtocolScanner ps = new ProtocolScanner(client);

            Thread tp = new Thread(pp);
            Thread ts = new Thread(ps);

            ts.start();
            tp.start();

//            DA PROVARE QUANDO GESTIAMO PIù CLIENT CON ARRAYLIST
//            try {
//                Scanner tastiera = new Scanner(System.in);
//                while(true) {
//                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
//                    out.println(tastiera.nextLine());
//                    Scanner in = new Scanner(client.getInputStream());
//                    System.out.println(in.nextLine());
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
