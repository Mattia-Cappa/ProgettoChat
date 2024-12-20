import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try{
            Socket client = new Socket(InetAddress.getLocalHost(),2000);
            Scanner fromServer = new Scanner(client.getInputStream());
            Scanner tastiera = new Scanner(System.in);
            PrintWriter toServer = new PrintWriter(client.getOutputStream(), true);

            while (true){

            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
