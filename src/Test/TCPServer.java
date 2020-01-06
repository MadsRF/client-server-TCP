package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    // Server port
    private static final int serverPort = 9090;

    public static void main(String[] args) throws IOException {
        // Server port that the server listen on
        ServerSocket listener = new ServerSocket(serverPort);

        System.out.println("[SERVER] Waiting for client connection...");
        // activates when client connect to server, keeps waiting until then
        Socket client = listener.accept();
        System.out.println("[SERVER] Connected to client");

        // Reads input from client
        BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        // used for replying message back to client
        PrintWriter output = new PrintWriter(client.getOutputStream(),true);

        // while loop for continuous messaging.
        try {
            while (true) {
                String request = input.readLine();
                System.out.println("[Client] " + request);
                output.println(request);
            }
        } finally {
            input.close();
            output.close();
        }
    }
}
