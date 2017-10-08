import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by stenpaaro on 0/10/2017.
 */
public class ClientThread extends Thread {

    private Socket socket;
    private PrintWriter output;
    private Server server;
    private BufferedReader input;

    public ClientThread(Server server, Socket socket){
        this.server = server;
        this.socket = socket;
    }

    private PrintWriter getOutput() {
        return output;
    }

    @Override
    public void run() {
        try {
            output = new PrintWriter(socket.getOutputStream(), false);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (!socket.isClosed()){

                String message = input.readLine();
                   System.out.println(message);
                        for(ClientThread clientThread: server.getNames()){
                            PrintWriter clientOut = clientThread.getOutput();
                            if (clientOut != null){
                                clientOut.write(message + "\r\n");
                                clientOut.flush();
                            }
                        }
                }

        }catch (IOException e){

        }
    }
}
