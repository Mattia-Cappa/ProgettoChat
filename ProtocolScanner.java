import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ProtocolScanner implements Runnable{
    private Socket socket;
    private ClientGUI gui;

    public ProtocolScanner(Socket s, ClientGUI gui){
        this.socket = s;
        this.gui = gui;
    }

    public void run(){
        try {
            // Appena un client viene creato in mezzo alla prima riga della GUI viene stampata la data corrente
            Date d = new Date();
            SimpleDateFormat f = new SimpleDateFormat("dd MMMM yyyy");
            String msg = f.format(d);
            int lineWidth = 50; // Larghezza totale della riga
            int padding = (lineWidth - msg.length()) / 2;
            String paddedMessage = " ".repeat(Math.max(0, padding)) + msg; // Creo messaggio con spaziature a sinistra
            gui.addMessage(paddedMessage);

            Scanner in = new Scanner(socket.getInputStream());
            while(true) {
                String message = in.nextLine();
                gui.addMessage(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
