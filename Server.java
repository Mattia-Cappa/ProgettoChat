import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try{
            ServerSocket mySocket = new ServerSocket(8080);
            System.out.println("Server started and listening on port 8080...");

            while(true){

                Socket clientSockets = mySocket.accept();
                System.out.println("Client connected from: " + clientSockets.getRemoteSocketAddress());
                Protocol p = new Protocol(clientSockets);
                Thread t = new Thread(p);
                t.start();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
