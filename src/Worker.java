import java.io.*;
import java.net.Socket;


public class Worker implements Runnable {

    protected Socket clientSocket = null;

    public Worker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        //Socket socket;
        try {
            InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            PrintWriter out = new PrintWriter(output, true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            while (reader.readLine().equals("quit") == false) {
                String com1 = reader.readLine();
                if (com1.equals("a")) {
                    String com2 = reader.readLine();
                    if (com2.equals("1")) {
                        out.println("command a1");
                    }
                    if (com2.equals("2")) {
                        out.println("command a2");
                    }
                }
            }
            out.println("Disconnected");
            System.out.println("stop signal received, stopping connect");
            clientSocket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}