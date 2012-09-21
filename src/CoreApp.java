
public class CoreApp {
	private PluginManager pluginManager;
	
	public CoreApp()
	{
		this.pluginManager = new PluginManager(this);
	}
	void stopPlugin(IBrahmaPlugin plugin)
	{
		pluginManager.stopPlugin(plugin);
	}
}
