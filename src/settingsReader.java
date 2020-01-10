import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class settingsReader {
    public  String set(int str) throws IOException{
        FileReader fReader = new FileReader("settingsDumpSer");
        BufferedReader br = new BufferedReader(fReader);
        String ret;// = br.readLine();
        int count = 0;
        while ((ret = br.readLine()) != null){
            count++;
            if (count == str){
                return ret;
            }
        }
        return null;
    }
}
