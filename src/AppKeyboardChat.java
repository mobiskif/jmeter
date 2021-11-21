import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AppKeyboardChat {

    public AppKeyboardChat(Socket s) {
        try {
            BufferedReader net_in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter std_out = new PrintWriter(System.out, true);
            SocketHelper.transferThread(net_in, std_out, "").start();

            BufferedReader std_in = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter net_out = new PrintWriter(s.getOutputStream(), true);
            SocketHelper.transferThread(std_in, net_out, "").start();
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

}
