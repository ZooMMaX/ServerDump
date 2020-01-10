import org.zeroturnaround.exec.ProcessExecutor;

import java.io.*;
import java.util.concurrent.TimeoutException;

public class Main {
    private static File f;
    private static FileWriter fWriter;
    private static int port;
        public static void main(String[] args) throws InterruptedException, TimeoutException, IOException {
            settingsReader sre = new settingsReader();

            //search settings
            f = new File("settingsDumpSer");
            if (!f.exists()){
                if (f.createNewFile()){
                    fWriter = new FileWriter(f, false);
                    fWriter.write("443");
                    fWriter.close();
                    System.out.println("created settings file");
                    System.out.println("Read settings from 'settingsDumpSer' file");
                    port = Integer.parseInt(sre.set(1));
                    System.out.println("Port Work: " + Integer.toString(port));
                }
            }else {
                System.out.println("Read settings from 'settingsDumpSer' file");
                port = Integer.parseInt(sre.set(1));
                System.out.println("Port: " + Integer.toString(port));
            }
            //search file database
            f = new File("dumpBase");
            if (f.exists()) {
                System.out.println("file database exists\nStarting server");
                MultiThreadedServer server = new MultiThreadedServer(port);
                new Thread(server).start();
            }else{
                //create file database
                if (f.mkdir()) {
                    System.out.println("create file database\nStarting server");
                    MultiThreadedServer server = new MultiThreadedServer(port);
                    new Thread(server).start();
                }else {
                    System.out.println("Error! file database not create. Please create 'dumpBase' dir from work directory");
                }
            }
        }
}