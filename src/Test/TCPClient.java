package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {
    //Local host address
    private static final String serverIP = "127.0.0.1";
    //port number the server uses
    private static final int serverPort = 9090;

    public static void main(String[] args) throws IOException {
        // choose socket to connect to
        Socket socket = new Socket(serverIP, serverPort);

        // gets input from server through socket
        BufferedReader input = new BufferedReader((new InputStreamReader(socket.getInputStream())));

        // gets "data" from keyboard
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        // Sends data through socket - uses autoFlush true is to flush output-buffer meaning that it'll allow it to send.
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        // while loop for continuous messaging.
        while (true) {
            System.out.println("> ");
            String command = keyboard.readLine();

            // if received quit, exit loop
            if (command.equals("quit")) {
                break;
            }else {
                //prints keyboard message in compiler
                output.println(command);
                String serverResponse = input.readLine();
                System.out.println("[Server] " + serverResponse);
            }
        }
        socket.close();
        System.exit(0);
    }
}