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
            //ByteArrayInputStream in = new ByteArrayInputStream(clientSocket.getInputStream().readAllBytes());
            /*while (reader.readLine().equals("quit")) {
                //byte in[] = new byte[reader.read()];
                // if (in[0] == 0xFF) {
                out.write("close connect\n".getBytes());
                out.flush();
                clientSocket.close();
            }*/
                //}else
            //out.write("connected\n".getBytes());
            //out.flush();
            while (reader.readLine().equals("quit") == false) {
                String com1 = reader.readLine();
                if (com1.equals("a")){
                    String com2 = reader.readLine();
                    if (com2.equals("1")){
                        out.println("command a1");
                    }
                    if (com2.equals("2")){
                        out.println("command a2");
                    }
                }
            }
                //}
            //}
            out.println("Disconnected");
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