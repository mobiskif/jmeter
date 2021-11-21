import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class FlopsView extends JPanel implements ActionListener, Observer {
    final JLabel label0 = new JLabel("");
    final JLabel label1 = new JLabel("");
    final JLabel label2 = new JLabel("");
    final JPanel runPanel = new JPanel();
    final FlopsModel flopsModel = new FlopsModel();

    public FlopsView() {
        label0.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        this.setLayout(new BorderLayout(0, 0));

        JButton plusBtn = new JButton("+");
        plusBtn.addActionListener(this);
        JButton minusBtn = new JButton("-");
        minusBtn.addActionListener(this);

        JButton stopBtn = new JButton("Stop");
        stopBtn.addActionListener(this);

        JPanel toolBar = new JPanel(new GridLayout(0, 1, 0, 0));
        toolBar.add(plusBtn);
        toolBar.add(minusBtn);
        toolBar.add(stopBtn);
        toolBar.add(label0);
        toolBar.add(label1);
        toolBar.add(label2);

        this.add(toolBar, BorderLayout.WEST);
        this.add(runPanel, BorderLayout.CENTER);

        flopsModel.infos.addObserver(this);
        new Thread(flopsModel).start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("+")) {
            runPanel.add(flopsModel.startRunnableButton());
        }
        else if (e.getActionCommand().equalsIgnoreCase("-")) {
            flopsModel.stopRunnableButton();
        }
        else if (e.getActionCommand().equalsIgnoreCase("Stop")) {
            flopsModel.stopAll();
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        Log  oi = (Log) arg;
        label0.setText(oi.getStatus());
        label1.setText(oi.getStatus());
        label2.setText(oi.getLog());
    }
}
