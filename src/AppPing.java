import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class AppPing {

    public AppPing(Socket s) {
        String msg = s.getInetAddress().toString() + " " + Utils.time() + " =>  ";
        try {
            BufferedReader net_in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter net_out = new PrintWriter(s.getOutputStream(), true);
            SocketHelper.transferThread(net_in, net_out, msg).start();
        } catch (IOException e) {
            //log.add(e.toString());
            System.err.println(e.toString());
        }
    }

}
