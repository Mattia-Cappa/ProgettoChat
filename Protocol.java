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
    private static int numClient=0;
    private int idClient=0;
    Scanner fromClient;
    PrintWriter toClient;
    Date d;
    SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");;

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

            // Invia l'ID del client al momento della connessione
            sendMessage(idClient+"");

            // Notifica tutti gli altri client che un nuovo client si è connesso tranne il client in questione
            sendtoAll("connected.");

            while (true){
                d = new Date();

                msgReceived = fromClient.nextLine();
                if (msgReceived.equals("-HELP")){
                    sendMessage("#CLI" + idClient + " (" + f.format(d) + ") : " + msgReceived);
                    sendMessageServer("Comandi disponibili: -HELP, -SHUTDOWN, -PRIVATE n msg, -LIST.");
                }
                else if (msgReceived.equals("-SHUTDOWN")){
                    sendMessage("#CLI" + idClient + " (" + f.format(d) + ") : " + msgReceived);
                    shutdownServer();
                }
                else if (msgReceived.startsWith("-PRIVATE")) { // Invia un messaggio privato a un altro client
                    sendMessage("#CLI" + idClient + " (" + f.format(d) + ") : " + msgReceived);
                    String[] commandParts = msgReceived.split(" ", 3);
                    if (commandParts.length > 2) {
                        int targetId = Integer.parseInt(commandParts[1]);
                        privateMessage(targetId, commandParts[2]);
                    } else {
                        sendMessageServer("You have to specify the ID and the message.");
                    }
                }
                else if (msgReceived.equals("-LIST")){
                    sendMessage("#CLI" + idClient + " (" + f.format(d) + ") : " + msgReceived);
                    String container="";
                    for (Protocol protocol : protocolList) {
                        container += "#CLI" + (protocol.idClient) + " ";
                    }
                    sendMessage("#CLI" + idClient + " (" + f.format(d) + ") : " + container);
                }
                else {
                    sendAll(msgReceived);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        } catch (NoSuchElementException e) {
            sendAll("disconnected.");
            protocolList.remove(this);
            try {
                myClient.close();
                fromClient.close();
                toClient.close();
            } catch (IOException ez) {
                ez.printStackTrace();
            }
        }

    }

    private void sendMessage(String msg){
        toClient.println(msg);
    }

    private void sendMessageServer(String msg){
        d = new Date();
        toClient.println("#SERVER" + " (" + f.format(d) + ")" + " : " + msg);
    }

    private void sendAll(String msg){
        for (Protocol protocol : protocolList) {
            d = new Date();
            if(idClient== protocol.idClient){

                protocol.sendMessage("#TU" + " ("+ f.format(d) +")" + " : " + msg);
            }else{
                protocol.sendMessage("#CLI" + idClient + " ("+ f.format(d) +")" + " : " + msg);

            }
        }
    }

    private void sendtoAll(String msg) {
        for (Protocol protocol : protocolList) {
            // Invia il messaggio a tutti gli altri client, escludendo quello corrente
            if (protocol != this) {
                d = new Date();
                protocol.sendMessage("#CLI" + idClient + " (" + f.format(d) + ") : " + msg);
            }
        }
    }

    private void shutdownServer() {
        sendAll("Closing.");
        for (Protocol protocol : protocolList) {
            try {
                protocol.myClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(0); // Termina il server
    }

    private void privateMessage(int targetId, String msg) {
        for (Protocol protocol : protocolList) {
            if (protocol.idClient == targetId) {
                protocol.sendMessage("[Private message from #CLI" + idClient + "]: " + msg);
                sendMessage("[Private message for #CLI" + targetId + "] : " + msg);
                return;
            }
        }
        sendMessageServer("Client with ID #" + targetId + " not found.");
    }
}
