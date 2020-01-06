package KEA;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

//class implements runnable to use Thread
public class ConnectToServer implements Runnable {

    private Socket socket;
    private String username;
    private final LinkedList<String> messagesToSend;
    private boolean hasMessages = false;

    public ConnectToServer(Socket socket, String username) throws IOException {
        this.socket = socket;
        this.username = username;
        messagesToSend = new LinkedList<String>();
    }

    //uses "synchronized" keyword to block other threads. so only one may go at a time
    //is used in client class when client sends input to server
    public void addNextMessage(String message){
        synchronized (messagesToSend){
            hasMessages = true;
            messagesToSend.push(message);
        }
    }

    //obligatory run method that handles input to the server
    @Override
    public void run() {

        System.out.println("Welcome: " + username);
        System.out.println("Local Port: " + socket.getLocalPort());
        System.out.println("Server = " + socket.getRemoteSocketAddress() + ":" + socket.getPort());

        try{
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), false);
            InputStream serverInStream = socket.getInputStream();
            Scanner serverIn = new Scanner(serverInStream);

            while(!socket.isClosed()){
                if(serverInStream.available() > 0){
                    if(serverIn.hasNextLine()){
                        System.out.println(serverIn.nextLine());
                    }
                }
                if(hasMessages){
                    String nextSend = "";

                    synchronized(messagesToSend){
                        nextSend = messagesToSend.pop();
                        hasMessages = !messagesToSend.isEmpty();
                    }
                    serverOut.println(username + " > " + nextSend);
                    serverOut.flush();
                }
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
