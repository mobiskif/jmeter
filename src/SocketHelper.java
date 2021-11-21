import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.Socket;

public class SocketHelper {

    public SocketHelper(String host) {
        try {
            Socket s = new Socket(host, 9510);
            new AppKeyboardChat(s);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Thread transferThread(BufferedReader in, PrintWriter out, String msg) {
        return new Thread(() -> {
            String readLine = "";
            try {
                while ((readLine = in.readLine()) != null) {
                    out.println(msg + readLine);
                    //System.out.println(readLine);
                }
                in.close();
                out.flush();
                out.close();
            }
            catch (IOException e) {
                System.err.println(e.toString());
            }
        });
    }

    public static void transferHead(PrintWriter out, long contentLength) {
        String header = "" +
                "HTTP/1.1 200 OK\n" +
                //"Date: Mon, 27 Jul 2009 12:28:53 GMT\n" +
                "Server: Apache/2.2.14 (Win32)\n" +
                //"Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT\n" +
                "Content-Length: " + contentLength + "\n" +
                "Content-Type: text/html\n" +
                "Connection: keep-alive\n";
        out.println(header);
        out.flush();
    }

    public static void from_to(String path, OutputStream os) {
        String type = "text/html";
        String ext = path.substring(path.indexOf("."));
        switch (ext) {
            case ".html":
                type = "text/html";
                break;
            case ".css":
                type = "text/css";
                break;
            case ".ico":
                type = "image/x-icon";
                break;
            case ".jpg":
            case ".jpeg":
                type = "image/jpeg";
                break;
            case ".png":
                type = "image/png";
                break;
        }

        File file = new File(path);
        long contentLength = file.length();
        try {
            BufferedReader url_in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            BufferedWriter net_out = new BufferedWriter(new OutputStreamWriter(os));
            SocketHelper.transferHead2(net_out, contentLength, type);
            SocketHelper.transfer(url_in, net_out).start();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void transferHead2(BufferedWriter os, long contentLength, String type) {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2009-09-09\r\n" +
                "Content-Type: " + type + "\r\n" +
                "Content-Length: " + contentLength + "\r\n" +
                "Connection: keep-alive\r\n\r\n";
        String result = response;
        try {
            os.write(result);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Thread transfer(BufferedReader in, BufferedWriter out) {
        return new Thread(() -> {
            try {
                int a;
                while ((a = in.read()) != -1) {
                    System.out.print("" + a);
                    out.write(a);
                }
                out.flush();
                if (in != null) in.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        });
    }
}
