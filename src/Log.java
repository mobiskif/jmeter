import java.util.Observable;

public class Log extends Observable {
    private String log;
    private String status;

    void add(String str) {
        log += str + "\n";
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        setChanged();
        notifyObservers(this);
    }

    public String getLog() {
        return log;
    }

    public void set(String s) {
        status = s;
        notifyObservers();
    }

    public String getStatus() {
        return status;
    }
}
