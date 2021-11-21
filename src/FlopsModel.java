import java.util.ArrayList;

public class FlopsModel implements Runnable {
    Boolean isRun = false;
    final Log infos = new Log();
    final ArrayList<FlopsButton> buttons = new ArrayList<>();
    Thread currentThread;
    final ThreadGroup threadGroup = new ThreadGroup("buttons");

    public FlopsButton startRunnableButton() {
        if (!isRun) {
            buttons.clear();
            new Thread(this).start();
        }
        FlopsButton rb = new FlopsButton();
        buttons.add(rb);
        new Thread(threadGroup, rb).start();
        return rb;
    }

    void calcValues() {
        final Float[] ff = {0f};
        buttons.forEach((it) -> ff[0] += it.value);
        infos.set(String.format("%.2f", ff[0] ));
        //infos.set(String.format("%.2f", ff[0] / buttons.size() ));
        //infos.add(String.format("%.0f", buttons.size()*1.f ));
    }

    public void stopAll() {
        isRun = false;
        buttons.forEach((it) -> it.setVisible(false));
        threadGroup.interrupt();
    }

    @Override
    public void run() {
        isRun = true;
        currentThread = Thread.currentThread();
        currentThread.setName("model");
        ThreadGroup threadGroup = currentThread.getThreadGroup();
        
        System.out.println(threadGroup.getParent().getName() + "::" + threadGroup.getName() + "::" + currentThread.getName());
        while (isRun) {
            calcValues();
            //infos.notifyObservers();
            try { Thread.sleep(1000); } catch (InterruptedException e) { }
        }
    }

    public void stopRunnableButton() {
        FlopsButton btn = buttons.get(0);
        btn.setVisible(false);
        buttons.remove(0);
    }
}
