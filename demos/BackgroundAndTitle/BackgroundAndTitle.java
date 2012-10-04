import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class BackgroundAndTitle implements IBrahmaPlugin
{
	private JFrame frame;
	
	public BackgroundAndTitle(JFrame frame)
	{
		this.frame = frame;
	}
	
	@Override
	public void start() {
		JPanel panel = (JPanel) frame.getContentPane();
		frame.setTitle("Test App");
		panel.setBackground(Color.BLACK);
		//panel.repaint();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
