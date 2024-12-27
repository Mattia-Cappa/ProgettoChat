import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
            sendAll("connected.");
            while (true){
                msgReceived = fromClient.nextLine();
                sendAll(msgReceived);
                //System.out.println(msgReceived);
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
        toClient.println(msg);
    }

    private void sendAll(String msg){
        Date d = new Date();

        for (Protocol protocol : protocolList) {
            protocol.sendMessage("#CLI" + idClient + ": " + msg+" ("+d.toString()+")");
        }
    }


}
