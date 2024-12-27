import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
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
            while(true) {
                Scanner in = new Scanner(socket.getInputStream());
                String message = in.nextLine();
                gui.addMessage(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
