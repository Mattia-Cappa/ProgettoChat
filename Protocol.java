import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Protocol implements Runnable {

    private final Socket myClient;
    int idClient;

    Protocol(Socket myClient, int idClient) {
        this.myClient = myClient;
        this.idClient = idClient;
    }

    @Override
    public void run() {

        try {
            Scanner fromClient = new Scanner(myClient.getInputStream());
            PrintWriter toClient = new PrintWriter(myClient.getOutputStream(), true);
            String msgReceived;


            while (true){
                msgReceived = fromClient.nextLine();
                toClient.print("#CLI" + idClient + ": " + msgReceived + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }


}
