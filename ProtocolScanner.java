import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ProtocolScanner implements Runnable{
    private Socket socket;

    public ProtocolScanner(Socket s){
        this.socket = s;
    }

    public void run(){
        try {
            while(true) {
                Scanner in = new Scanner(socket.getInputStream());
                System.out.println(in.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
