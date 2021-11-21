import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class AppEcho_old implements Observer {

    private final ThreadWriter_old writer;

    public AppEcho_old(Socket socket) {
        System.out.println(getClass().getSimpleName() + " started with " + socket.getInetAddress().getHostName());

        ThreadReader reader = new ThreadReader(socket);
        reader.addObserver(this);
        new Thread(reader).start();

        writer = new ThreadWriter_old(socket);
        writer.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o.getClass().equals(ThreadReader.class)) {
            ThreadReader r = (ThreadReader) o;
            writer.write("-> " + r.getStr() + "\n");
        } else if (o.getClass().equals(ThreadWriter_old.class)) {
            System.out.println("Send bytes " + ((ThreadWriter_old) o).strlen);
        }
    }
}
