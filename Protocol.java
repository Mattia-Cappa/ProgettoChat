import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Protocol implements Runnable {

    private static ArrayList<Protocol> protocolList = new ArrayList<Protocol>();

    private final Socket myClient;
    private int idClient;
    Scanner fromClient;
    PrintWriter toClient;


    Protocol(Socket myClient, int idClient) {
        this.myClient = myClient;
        this.idClient = idClient;
        protocolList.add(this);

    }

    @Override
    public void run() {

        try {
            fromClient = new Scanner(myClient.getInputStream());
            toClient = new PrintWriter(myClient.getOutputStream(), true);

            // Invia l'ID del client al momento della connessione
            toClient.println(idClient);

            String msgReceived;

            while (true){
                msgReceived = fromClient.nextLine();
                sendAll(msgReceived);
                //System.out.println(msgReceived);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendMessage(String msg){
        toClient.println(msg);
    }

    private void sendAll(String msg){
        for (Protocol protocol : protocolList) {
            protocol.sendMessage("#CLI" + this.idClient + ": " + msg);
        }
    }


}
