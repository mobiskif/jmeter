import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JButton;

public class FlopsButton extends JButton implements Runnable {
    public float value = 0f;

    public FlopsButton() {
        super();
        this.addActionListener(e -> setVisible(false));
    }

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        ThreadGroup threadGroup = currentThread.getThreadGroup();
        System.out.println(threadGroup.getParent().getName() + "::" + threadGroup.getName() + "::" + currentThread.getName());
        while (isVisible()) {
            value = calcFlops();
            //value = calcResponceTime();
            //value = calcRPS();
            try { Thread.sleep(900); } catch (InterruptedException e) { }
        }
    }

    Float calcFlops() {
        float flops= 0.0f;
        long t1 = System.currentTimeMillis();
        for (long i = 0; i <= 1000e6; i++) flops = i * 1.0f;
        long t2 = System.currentTimeMillis();
        float time = (t2 - t1) / 1000f;
        flops = (float) 1e-9 * flops / time;
        setText(String.format("%.1f", flops) + " GF  " + String.format("%.1f", time) + " S");
        return flops;
    }


}
