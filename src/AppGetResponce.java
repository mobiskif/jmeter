import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;

public class AppGetResponce {

    public AppGetResponce(Socket s, Log infos) {
        StringWriter out = new StringWriter();
        try {
            BufferedReader net_in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String readLine = "";
            while ((readLine = net_in.readLine()) != null) out.write(readLine + "\n");
            net_in.close();
            out.flush();
            infos.add(out.toString());
            System.out.println(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
