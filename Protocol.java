import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Protocol implements Runnable {

    private static ArrayList<Protocol> protocolList = new ArrayList<Protocol>();

    private final Socket myClient;
    private static ArrayList<Integer> idList = new ArrayList<Integer>();
    private static int numClient=0;
    private int idClient=0;
    Scanner fromClient;
    PrintWriter toClient;
    Date d = new Date();

    Protocol(Socket myClient) {
        this.myClient = myClient;
        protocolList.add(this);
        numClient++;
        idClient=numClient;
    }

    @Override
    public void run() {

        try {
            fromClient = new Scanner(myClient.getInputStream());
            toClient = new PrintWriter(myClient.getOutputStream(), true);
            String msgReceived;
            sendMessage(d.toString());
            sendAll("connected.");
            while (true){
                msgReceived = fromClient.nextLine();
                sendAll(msgReceived);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } catch (NoSuchElementException e) {
            sendAll("disconnected.");
            idClient++;
            protocolList.remove(this);
        }

    }

    private void sendMessage(String msg){
        if (msg.equals(d.toString())){
            int lineWidth = 98; // Larghezza totale della riga
            int padding = (lineWidth - msg.length()) / 2;

            String paddedMessage = " ".repeat(Math.max(0, padding)) + msg; // Creo messaggio con spaziature a sinistra

            toClient.println(paddedMessage); // Invia il messaggio centrato
        } else {
            toClient.println(msg);
        }
    }

    private void sendAll(String msg){
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
        for (Protocol protocol : protocolList) {
            protocol.sendMessage("#CLI" + idClient + " ("+ f.format(d) +")" + " : " + msg);
        }
    }


}
