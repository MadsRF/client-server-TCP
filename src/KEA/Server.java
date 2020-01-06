package KEA;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    //server port
    private static final int PORT = 6000;
    //holds clientHandler object
    private static ArrayList<ConnectToClient> clients = new ArrayList<>();
    //Does that only 4 clients can connect at a time
    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    //Runs server
    public static void main(String[] args) throws IOException {
        //waits(listens) for conn. from clients
        ServerSocket listener = new ServerSocket(PORT);

        while(true) {
            try {
                System.out.println("[SERVER] Waiting for client connection...");
                Socket client = listener.accept();
                System.out.println("[SERVER] Connected to client");
                ConnectToClient clientThread = new ConnectToClient(client, clients);
                clients.add(clientThread);
                pool.execute(clientThread);
            }catch (IOException e){
                System.out.println("[SERVER] Failed to connect to client");
            }
        }
    }
}
