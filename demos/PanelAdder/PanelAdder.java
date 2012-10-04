package PanelAdder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class PanelAdder implements IBrahmaPlugin{
	private JFrame frame;
	private JPanel panel;
//	private Thread p;
//	private int t = 0;
	
	public PanelAdder()
	{

	}
	
	@Override
	public void start() {
		frame.add(panel);
//		p.run();
	}

	@Override
	public void stop() {
//		p.stop();
		frame.remove(panel);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFrame(JFrame frame) {
		this.frame = frame;
		this.panel = new JPanel(){
			public void paint (Graphics g) {
			    Graphics2D g2 = (Graphics2D) g;
			    g.setColor(Color.red);
			    g.drawLine(0, 0, 100, 100);
			    panel.setBackground(Color.GREEN);
			}
		};
//		this.p = new Thread(){
//			public void run() {
//	             t++;
//	             try {
//					this.sleep((long) (1.0/60.0));
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	         }
//		};
		//panel.setBackground(Color.GREEN);
		panel.setPreferredSize(new Dimension(200, 200));
	}
	
}
