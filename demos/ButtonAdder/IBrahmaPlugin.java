package ButtonAdder;

import javax.swing.JFrame;


public interface IBrahmaPlugin {
/*
* Called when the program is loaded into the host application.
*/
public void start();

/*
* Called when the program is removed from the host application. Responsible for removal and disposal of all assets created or modified by the plugin.
*/
public void stop();

/*
* Used to pass the JFrame created by the PluginCore.
*/
public void setFrame(JFrame frame);
}