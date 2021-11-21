import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class View extends JPanel implements ActionListener, Observer {
    JTextArea infoText = new JTextArea();
    JLabel infoStatus = new JLabel();
    JLabel infoValue = new JLabel();
    Model model;

    public View(Boolean isserv, int typeapp) {
        this.setLayout(new BorderLayout());
        model = new Model(isserv, typeapp);

        JPanel btnBar = new JPanel(new GridLayout(0,1));
        JButton btn0 = new JButton("Start"); btn0.addActionListener(this); btnBar.add(btn0);
        JButton btn1 = new JButton("Stop"); btn1.addActionListener(this); btnBar.add(btn1);


        this.add(btnBar, BorderLayout.WEST);
        this.add(infoValue, BorderLayout.NORTH);
        final JScrollPane scrollPane = new JScrollPane(infoText);
        infoText.setLineWrap(true);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(infoStatus, BorderLayout.SOUTH);

        model.log.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Stop")) {
            model.stop();
        }
        else if (e.getActionCommand().equalsIgnoreCase("Start")) {
            model.start();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Log log = (Log) o;
        infoText.setText(log.getLog());
        infoStatus.setText(log.getStatus());
        infoText.setCaretPosition(infoText.getDocument().getLength());
    }

    public static void main(String[] args) {
        JFrame f = new JFrame(Runtime.getRuntime().availableProcessors() + " CPU Server");
        Utils.printThreadInfo(f);
        f.add(new View(true, 1));
        f.setLayout(new GridLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(200, 200, 640, 480);
        f.setVisible(true);

        JFrame f2 = new JFrame(Runtime.getRuntime().availableProcessors() + " CPU Client");
        Utils.printThreadInfo(f2);
        f2.add(new View(false, 99));
        //f2.add(new FlopsView());
        f2.setLayout(new GridLayout());
        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f2.setBounds(200, 800, 640, 480);
        f2.setVisible(true);

    }

}
