import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Observable;

public class ThreadWriter_old extends Observable {
    int strlen;
    BufferedWriter out;

    public ThreadWriter_old(Socket s) {
        try {
            out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyObservers() {
        setChanged();
        notifyObservers(this);
    }

    public void write(String str) {
        new Thread(() -> {
            try {
                //BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.write(str);
                out.flush();
                strlen = str.length();
                notifyObservers();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
