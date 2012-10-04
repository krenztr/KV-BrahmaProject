import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileFilter;


public class PluginCore {
	JFrame frame;
	private JList<String> sideList;
	private DefaultListModel<String> listModel;
	private JButton addButton;
	private JButton removeButton;
	private JPanel buttonPanel;
	private JFileChooser fileChooser;
	private PluginManager pluginManager;
	
	private final String XMLVAL = "brahma.xml";
	
	public PluginCore()
	{
		
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileFilter(){		
			@Override
			public String getDescription() {
				return ".jar files";
			}

			@Override
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".jar");		
			}
			
		});
		
		this.frame = new JFrame("Brahma Plugin Handler");
		frame.setSize(new Dimension(500, 300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pluginManager = new PluginManager(XMLVAL,this);
		
		//JPanel contentPane = (JPanel)frame.getContentPane();
		//contentPane.setPreferredSize(new Dimension(700, 500));
		//
		//JLabel bottomLabel = new JLabel("No plugins registered yet!");
		
		listModel = new DefaultListModel<String>();
		sideList = new JList<String>(listModel);
		sideList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sideList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollPane = new JScrollPane(sideList);
		scrollPane.setPreferredSize(new Dimension(100, 50));
				
		// Lets lay them out, contentPane by default has BorderLayout as its layout manager
		frame.add(scrollPane, BorderLayout.EAST);
		//frame.add(bottomLabel, BorderLayout.SOUTH);
		
		addButton = new JButton("Add");
		removeButton = new JButton("Remove");
		
		//Add action listeners
		addButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fileChooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					try {
						pluginManager.addPlugin(fileChooser.getSelectedFile());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		});
		removeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = sideList.getSelectedValue();
				if(sideList.getSelectedIndex() != -1)
				{
					pluginManager.removePlugin(name);
					listModel.removeElement(name);
					sideList.repaint();
				}
			}
			
		});
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		frame.add(buttonPanel);
		frame.setVisible(true);
	}
	
	public void addPlugin(String className) {
		this.listModel.addElement(className);
		this.sideList.repaint();
	}

	public boolean hasPlugin(String s) {
		return this.listModel.contains(s);
	}
}
