
import java.io.*;
import java.net.Socket;


public class Worker implements Runnable {

    protected Socket clientSocket = null;
    public static File f;

    public Worker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    public void oup(String o){
        System.out.println(o);
    }

    @Override
    public void run() {
        try {
            InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            PrintWriter out = new PrintWriter(output, true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            while (!reader.readLine().equals("quit")) {
                if (reader.readLine().equals("apiv1")) {
                    oup("not quit\napi v1");
                    String com1 = reader.readLine();
                    if (com1.equals("newUser")) {
                        oup("new user");
                        String com2 = reader.readLine();
                        f = new File("dumpBase/" + com2);
                        if (f.exists()) {
                            out.println("user exists");
                            oup("user exists");
                        } else {
                            if (f.mkdir()) {
                                out.println("user create");
                                oup("user create");
                            }
                        }
                    }
                    if (com1.equals("Login")) {
                        oup("login");
                        String com2 = reader.readLine();
                        f = new File("dumpBase/" + com2);
                        if (f.exists()) {
                            out.println("LoginSuccess");
                            oup("login success");
                        } else {
                            out.println("LoginError");
                            oup("login error");
                            clientSocket.close();
                        }
                    }
                    if (com1.equals("LSuc")) {
                        oup("lsuc");
                        String com2 = reader.readLine();
                        if (com2.equals("Read")) {
                            oup("read");
                            String login = reader.readLine();
                            f = new File("dumpBase/" + login);
                            String[] lst = f.list();
                            StringBuilder o = new StringBuilder();
                            assert lst != null;
                            int ii = 0;
                            for (String s : lst) {
                                ii++;
                                o.append(s).append(";");
                                if (ii == lst.length) {
                                    out.println(Integer.toString(ii) + ";" + o);
                                }
                            }
                        }
                        if (com2.equals("Ncard")) {
                            oup("ncard");
                            String login = reader.readLine();
                            String numcard = reader.readLine();
                            f = new File("dumpBase/" + login + "/" + numcard);
                            String[] lst = f.list();
                            assert lst != null;
                            int ii = 0;
                            StringBuilder o = new StringBuilder();
                            for (String s : lst) {
                                ii++;
                                o.append(s).append(";");
                                if (ii == lst.length) {
                                    out.println(Integer.toString(ii) + ";" + o);
                                }
                            }
                        }
                        if (com2.equals("Dload")) {
                            oup("dload");
                            String login = reader.readLine();
                            String numcard = reader.readLine();
                            String dumpname = reader.readLine();
                            FileReader fReader = new FileReader("dumpBase/" + login + "/" + numcard + "/" + dumpname);
                            BufferedReader br = new BufferedReader(fReader);
                            String ret = br.readLine();
                            String retu = "";
                            while (ret != null) {
                                retu = retu + ret + ";";
                                ret = br.readLine();
                                if (ret == null) {
                                    out.println(retu);
                                }
                            }
                        }
                        if (com2.equals("Uload")) {
                            oup("uload");
                            String login = reader.readLine();
                            oup("login " + login);
                            String numcard = reader.readLine();
                            oup("numcard " + numcard);
                            File a = new File("dumpBase/" + login + "/" + numcard);
                            if (!a.exists()) {
                                if (a.mkdir()) {
                                    out.println("dir create");
                                } else {
                                    out.println("dir ncreate");
                                    clientSocket.close();
                                }
                            } else {
                                out.println("dir exists");
                            }
                            String dumpname = reader.readLine();
                            oup("dname " + dumpname);
                            File aa = new File("dumpBase/" + login + "/" + numcard + "/" + dumpname);
                            if (!aa.exists()) {
                                if (aa.createNewFile()) {
                                    out.println("dumpfile create");
                                } else {
                                    out.println("dumpfile ncreate");
                                    clientSocket.close();
                                }
                            } else {
                                out.println("dumpfile exists");
                            }
                            String dump = reader.readLine();
                            oup("dump " + dump);
                            FileWriter fWriter = new FileWriter(aa);
                            fWriter.write(dump);
                            fWriter.close();
                        }
                        if (com2.equals("Del")){
                            oup("del");
                            String login = reader.readLine();
                            oup("login: " + login);
                            String numcard = reader.readLine();
                            oup("ncard: " + numcard);
                            String dumpname = reader.readLine();
                            oup("dname :" + dumpname);
                            File a = new File("dumpBase/" + login + "/" + numcard);
                            if (!a.exists()){
                                out.println("error");
                                clientSocket.close();
                            }
                            File aa = new File("dumpBase/" + login + "/" + numcard + "/" + dumpname);
                            if (!aa.exists()){
                                out.println("error");
                                clientSocket.close();
                            }else {
                                if (aa.delete()){
                                    String[] lst = a.list();
                                    assert lst != null;
                                    if (lst.length == 0){
                                        if (a.delete()){
                                            f = new File("dumpBase/" + login);
                                            String[] lst2 = f.list();
                                            StringBuilder o = new StringBuilder();
                                            assert lst2 != null;
                                            int ii = 0;
                                            for (String s : lst2) {
                                                ii++;
                                                o.append(s).append(";");
                                                if (ii == lst2.length) {
                                                    out.println(Integer.toString(ii) + ";" + o);
                                                }
                                            }
                                        }
                                    }
                                    int ii = 0;
                                    StringBuilder o = new StringBuilder();
                                    for (String s : lst) {
                                        ii++;
                                        o.append(s).append(";");
                                        if (ii == lst.length) {
                                            out.println(Integer.toString(ii) + ";" + o);
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
            out.println("Disconnected");
            System.out.println("stop signal received, stopping connect");
            clientSocket.close();
        } catch (Exception e) {
            oup(e.toString());
            throw new RuntimeException(e);
        }
    }
}