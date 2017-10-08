import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by stenpaaro on 06/10/2017.
 */
public class ServerThread extends Thread {

    private Socket socket;
    private String userName;
    private final LinkedList<String>messages;
    private boolean hasMessages = false;
    String DATA = "DATA";
    String JOIN = "JOIN";


    public ServerThread(Socket socket, String userName) {
        this.socket = socket;
        this.userName = userName;
        messages = new LinkedList<String>();
    }

    public void nextMessage(String message){
        synchronized (messages){
            hasMessages = true;
            messages.push(message);
        }
    }

    @Override
    public void run() {
        String slashRemove = socket.getInetAddress().toString().split("/")[1];
        System.out.println(JOIN + " " + userName + ", " + slashRemove + ":" + socket.getPort() );

        try {
            PrintWriter outputStream = new PrintWriter(socket.getOutputStream(), false);
            InputStream inputStream = socket.getInputStream();
            Scanner scanner = new Scanner(inputStream);

                while (!socket.isClosed()) {
                    if (inputStream.available() > 0) {
                        if (scanner.hasNextLine()) {
                            System.out.println(scanner.nextLine());
                        }
                    }
                    if (hasMessages) {
                        String nextSend = "";
                        synchronized (messages) {
                            nextSend = messages.pop();
                            hasMessages = !messages.isEmpty();

                            outputStream.println(DATA + " " +  userName + ":  " + nextSend);
                            outputStream.flush();
                        }

                    }

                }
            System.out.println(messages);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
