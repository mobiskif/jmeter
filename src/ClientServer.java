import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ClientServer implements Runnable {
    Boolean isRun = false;
    Log log;
    Boolean isServer = false;
    ServerSocket serverSocket;
    Socket socket;
    int typeApp;

    public ClientServer(Log log, Boolean isserv, int typeapp) {
        this.log = log;
        this.isServer = isserv;
        this.typeApp = typeapp;
    }

    void doWork(Socket socket) {
        switch (typeApp) {
            case (0):
                new Thread(new SocketProcessor(socket, this.log)).start();
                break;
            case (1):
                new AppGetFromURL(socket, "index/style.css");
                break;
            case (2):
                new AppGetFromURL(socket, "http://www.columbia.edu/~fdc/sample.html");
                break;
            case (3):
                new AppKeyboardChat(socket);
                break;
            case (4):
                new AppGetResponce(socket, this.log);
                break;
            default:
                new AppPing(socket);
                break;
        }
    }

    @Override
    public void run() {
        Utils.printThreadInfo(this);
        isRun = true;
        try {
            if (isServer) serverSocket = new ServerSocket(Utils.getPort());
            while (isRun) {
                if (isServer) socket = serverSocket.accept();
                else socket = new Socket(Utils.getHost(), Utils.getPort());
                log.add("Connection with " + socket.getRemoteSocketAddress() + " at " + Utils.getDf().format(new Date().getTime()));
                System.out.println("Connection with " + socket.getRemoteSocketAddress() + " at " + Utils.getDf().format(new Date().getTime()));
                doWork(socket);
                isRun = isServer;
            }
        } catch (IOException e) {
            log.add(e.toString());
            System.err.println(e.toString());
        } catch (Throwable t) {
            System.err.println(t.toString());
        }
    }

    public void stop() {
        isRun = false;
        try {
            if (isServer) serverSocket.close();
            else socket.close();
            finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        log.add("finalize() " + getClass().getSimpleName());
    }
}
