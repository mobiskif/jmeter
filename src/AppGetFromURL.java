import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class AppGetFromURL {

    public AppGetFromURL(Socket s, String addr) {
        long contentLength;
        InputStream is;
        try {
            if (addr.contains("://")) {
                URL url = new URL(addr);
                URLConnection con = url.openConnection();
                contentLength = con.getContentLength();
                is = con.getInputStream();
            } else {
                File file = new File(addr);
                contentLength = file.length();
                is = new FileInputStream(file);
            }

            BufferedReader url_in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            PrintWriter net_out = new PrintWriter(s.getOutputStream(), true);

            SocketHelper.transferHead(net_out, contentLength);
            SocketHelper.transferThread(url_in, net_out, "from_").start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
