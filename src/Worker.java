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
            output.write("Connected\n".getBytes());
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            while (!reader.readLine().equals("quit")) {

            }
            output.write("Disconnected\n".getBytes());
            System.out.println("stop signal received, stopping connect");
            clientSocket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        /*try {
            InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            long time = System.currentTimeMillis();
            output.write("Connected\n".getBytes());
            int in[] = new int[input.readAllBytes().length];
            while (true) {
                if (in[0] == 0) {
                    output.close();
                    input.close();
                } else {
                    System.out.println("Request processed: " + time);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}