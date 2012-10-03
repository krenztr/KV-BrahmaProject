import java.io.File;
import java.util.ArrayList;


public class PluginManager {
	ArrayList<String> pluginNames;
	
	public PluginManager(String filepath)
	{
		//TODO: Create XML buffer document and load/or create document based on filepath
	}
	
	public void loadXML()
	{
		//TODO: Add all plugins from the XML
	}
	
	public void createXML(){
		//TODO: Create an empty XML document based on the DTD
	}
	
	public void addPluginToXML(File jarFile){
		//TODO
	}
	
	public void addDependent(String parentName, String childName){
		//TODO
	}
	
	public void removeDependent(String parentName, String childName){
		//TODO
	}
	
	public void startPlugin(String pluginName){
		//TODO
	}
	
	public void stopPlugin(String pluginName){
		//TODO: Stop dependents first
	}
	
	public void addPlugin(){
		//TODO: Check plugin dependicies, then add to list and update XML. Finally, start plugin.
	}
	
	public void removePlugin(){
		//TODO: CHeck dependents first. Then stop plugin, then remove plugin from list and XML.
	}
}
