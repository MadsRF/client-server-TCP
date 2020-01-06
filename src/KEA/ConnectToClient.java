package KEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectToClient implements Runnable{
    private Socket client;
    private BufferedReader input;
    private PrintWriter output;
    private ArrayList<ConnectToClient> clients;

    public ConnectToClient(Socket clientSocket, ArrayList<ConnectToClient> clients) throws IOException {
        this.client = clientSocket;
        this.clients = clients;
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new PrintWriter(client.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String request = input.readLine();
                outToAll(request);
            }
        } catch (IOException e) {
            System.err.println("IO exception in client handler");
        }finally {
            System.out.println("[SERVER] closing");
            output.close();
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void outToAll(String msg) {
        for (ConnectToClient aClient : clients){
            aClient.output.println(msg);
        }
    }
}
