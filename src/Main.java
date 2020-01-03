public class Main {
    public static final int PORT_WORK = 9000;
    public static final int PORT_STOP = 9001;

    public static void main(String[] args) {
        MultiThreadedServer server = new MultiThreadedServer(PORT_WORK);
        new Thread(server).start();
        try {
            Thread monitor = new StopMonitor(PORT_STOP);
            monitor.start();
            monitor.join();
            System.out.println("Right after join.....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping Server");
        server.stop();
    }
}