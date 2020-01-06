package KEA;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    //Local host address
    private static String SERVER_IP = "127.0.0.1";
    //port number the server uses
    private static int SERVER_PORT = 6000;

    //username
    private String userName;

    //constructor
    private Client(String userName, String host, int portNumber){
        this.userName = userName;
        this.SERVER_IP = host;
        this.SERVER_PORT = portNumber;
    }

    //Main that runs client
    public static void main(String[] args) throws IOException {
        //ask for username
        String readName = null;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please input username:");

        //only goes through if the if the username is not empty
        while (readName == null || readName.trim().equals("")) {
            readName = scan.nextLine();
            if (readName.trim().equals("")) {
                System.out.println("Invalid. Please enter again:");
            }
        }
        //create object of client
        Client client = new Client(readName, SERVER_IP, SERVER_PORT);
        //method that handles conn. to server with ServerHandler class
        client.runClient(scan);
    }

    public void runClient(Scanner scan){

        try{
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            Thread.sleep(1000);

            // creates object of clientThread
            ConnectToServer clientThread = new ConnectToServer(socket, userName);
            // creates thread of clientThread
            Thread serverAccessThread = new Thread(clientThread);
            // initialises thread
            serverAccessThread.start();

            while(serverAccessThread.isAlive()){
                String message = scan.nextLine();

                if (message.equalsIgnoreCase("quit")){
                    socket.close();
                    break;
                }
                else {
                    clientThread.addNextMessage(message);
                }
            }
            socket.close();
        }catch(IOException ex){
            System.err.println("Fatal Connection error!");

            ex.printStackTrace();
        }catch(InterruptedException ex){
            System.out.println("Interrupted");
        }
    }
}
