import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by stenpaaro on 06/10/2017.
 */
public class Client{
    private static final String IP = "10.10.17.235";
    private static final int PORT = 4567;

    private String userName;
    private int PORT_NUMBER;
    private String IP_ADDRESS;

    public static void main(String[] args) {

        String name = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome, please enter your username: ");

            while (name == null) {
                name = scanner.nextLine();
            }

        Client client = new Client(name, IP, PORT );
        client.startClient(scanner);
    }
    private Client(String userName, String IP, int PORT){
        this.userName = userName;
        this.IP_ADDRESS = IP;
        this.PORT_NUMBER = PORT;
    }

    public void startClient(Scanner scanner){
        try {
            Socket socket = new Socket(IP_ADDRESS, PORT_NUMBER);

            ServerThread serverThread = new ServerThread(socket, userName);
            Thread thread = new Thread(serverThread);
            thread.start();
                while (thread.isAlive()){
                    if (scanner.hasNextLine()){
                        serverThread.nextMessage(scanner.nextLine());
                    }
                }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
