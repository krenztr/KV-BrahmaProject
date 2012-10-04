package ButtonAdder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class ButtonAdder implements IBrahmaPlugin
{
	private JFrame frame;
	private JButton button;
	
	public ButtonAdder(JFrame jframe)
	{
		this.frame = jframe;
		this.button = new JButton("Button");
		this.button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame,
					    "The button has been clicked.");
			}
			
		});
	}
	
	@Override
	public void start() {
		frame.add(button);
		
	}

	@Override
	public void stop() {
		frame.remove(button);
		
	}

	@Override
	public void setFrame(JFrame frame) {
		final JFrame frameF = frame;
		this.frame = frame;
		this.button = new JButton("Button");
		this.button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frameF,
					    "The button has been clicked.");
			}
			
		});
	}

}
