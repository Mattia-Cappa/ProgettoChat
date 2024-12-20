import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try{
            ServerSocket mySocket = new ServerSocket(8080);
            System.out.println("Server started and listening on port 8080...");
            int idClient=0;

            while(true){
                idClient++;
                Socket clientSockets = mySocket.accept();
                System.out.println("Client connected from: " + clientSockets.getRemoteSocketAddress());
                Protocol p = new Protocol(clientSockets, idClient);
                Thread t = new Thread(p);
                t.start();

            }




        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
