
public interface IBrahmaPlugin {
	/*
	 * When implementing this interface, you must include a constructor which accepts a JFrame as the only argument.
	 */
	
	/*
	 * Called when the program is loaded into the host application.
	 */
	public void start();
	
	/*
	 * Called when the program is removed from the host application. Responsible for removal and disposal of all assets created or modified by the plugin.
	 */
	public void stop();
}
