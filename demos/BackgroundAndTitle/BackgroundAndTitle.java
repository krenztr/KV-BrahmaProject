package BackgroundAndTitle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import PanelAdder.IBrahmaPlugin;


public class BackgroundAndTitle implements IBrahmaPlugin
{
	private JFrame frame;
	
	public BackgroundAndTitle()
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

	@Override
	public void setFrame(JFrame frame) {
		 this.frame = frame;
	}

}
