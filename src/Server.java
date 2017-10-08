import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by stenpaaro on 06/10/2017.
 */
public class Server {
    private static final int PORT = 4567;
    private static final String IP = "10.10.17.235";
    private int PORT_NUMBER;
    private static List<ClientThread> names;
    String J_OK = "J_OK";


    public static void main(String[] args) {
        System.out.println("Server is ready");
        Server server = new Server(PORT);
        server.startServer();
    }

    public Server(int PORT){
        this.PORT_NUMBER = PORT;
    }

    public List<ClientThread> getNames(){
        return names;
    }

    public void startServer(){
        names = new ArrayList<>();
        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
            takeClients(serverSocket);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void takeClients(ServerSocket serverSocket){
        while (true){
            try {
                Socket socket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(this, socket);
                Thread thread = new Thread(clientThread);
                thread.start();
                    names.add(clientThread);
                    System.out.println(J_OK);
                    String slashRemove = socket.getInetAddress().toString().split("/")[1];
                    System.out.println("Connected to : " + slashRemove + ": " + socket.getLocalPort());

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
