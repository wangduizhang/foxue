package cn.fodizi.res;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class JPBar extends JFrame {

    //JProgressBar的使用  
    private JProgressBar progress;
    private Component component;
    private volatile int indexstart = 0;
    private static String paString = System.getProperty("user.dir") + File.separator + "img" + File.separator + "1.jpg";

    public JPBar(Component component, String title) {
        this.component = component;
        //component.setEnabled(false);
        setTitle("稍等");
        setBounds(component.getX() + 300, component.getY() + 230, 300, 78);
        progress = new JProgressBar();
        //显示进度文本  
        progress.setStringPainted(true);
        setUndecorated(false);
        setAlwaysOnTop(true);
        getContentPane().add(progress, BorderLayout.CENTER);
        final JLabel jl = new JLabel(title);
        getContentPane().add(jl, BorderLayout.NORTH);
        setResizable(false);
        Toolkit tool = this.getToolkit(); //得到一个Toolkit对象
        Image image = tool.createImage(paString);
        this.setIconImage(image);
        this.setVisible(true);
    }

    public void play(int index, int min) {

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JPBar jp = (JPBar) e.getSource();
                jp.dispose();
            }
        });
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        progress.setIndeterminate(true);
        progress.setIndeterminate(false);
        indexstart++;
        for (int i = indexstart - 1; i <= index; i++) {
            try {
                Thread.sleep(min);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
            progress.setValue(i);
        }
        indexstart = index + 1;
    }

    public void close() {
        this.play(100, 5);
        this.dispose();
        //this.component.setEnabled(true);
    }

}
