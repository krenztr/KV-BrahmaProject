
public class PluginManager {
	private CoreApp coreApp;
	
	public PluginManager(CoreApp coreApp)
	{
		this.coreApp = coreApp;
	}
	
	public int startPlugin(IBrahmaPlugin plugin)
	{
		plugin.load(coreApp);
		plugin.start();	
		return 0;
	}
	
	public void stopPlugin(IBrahmaPlugin plugin)
	{
		plugin.stop();
	}
}
