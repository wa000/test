package test;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class FrameTest extends JFrame implements ActionListener
{
    /**
     * 
     */
 private static final long serialVersionUID = -6024214868642328628L;
    
private JButton btn = new JButton("Start");
 
    private JProgressBar bar = new JProgressBar(){
        
        private static final long serialVersionUID = 5155233799977383252L;

        public void paint(Graphics g)
        {super.paint(g);
            
            System.out.println("paint");
        }
    };

    public FrameTest()
    {
        init();
    }
    
    private void init()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        add(btn);
        add(bar);
        btn.addActionListener(this);
        setVisible(true);
    }
    
    private int i = 0;
    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        
        new Thread(new Runnable() {
            public void run()
            {
                for(i = 0; i < 100; i++)
                {
                    
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run()
                        {
                            bar.setValue(i);
                        }
                    });
                    
                    
                    try
                    {
                        Thread.sleep(20);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    
    public static void main(String[] args)
    {
        new FrameTest();
    }
    
}
