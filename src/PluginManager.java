import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class PluginManager {
	private PluginCore pluginCore;
	private ArrayList<String> pluginNames;

	public PluginManager(String filepath, PluginCore pluginCore) {
		// TODO: Create XML buffer document and load/or create document based on
		// filepath
		this.pluginCore = pluginCore;
	}

	public void loadXML() {
		// TODO: Add all plugins from the XML
	}

	public void createXML() {
		// TODO: Create an empty XML document based on the DTD
	}

	public void addPluginToXML(JarFile jarFile) {
		// TODO
	}

	public void addDependent(String parentName, String childName) {
		// TODO
	}

	public void removeDependent(String parentName, String childName) {
		// TODO
	}

	public void startPlugin(IBrahmaPlugin pluginName) {
		pluginName.start();
	}

	public void stopPlugin(IBrahmaPlugin pluginName) {
		pluginName.stop();
	}

	public void addPlugin(File jarBundle) throws Exception {
		// TODO: Check plugin dependicies, then add to list and update XML.
		// Finally, start plugin.
		
		// Get the manifest file in the jar file
		JarFile jarFile = new JarFile(jarBundle);
		String bundlePath = jarBundle.getPath();
		Manifest mf = jarFile.getManifest();
		Attributes mainAttribs = mf.getMainAttributes();
		// Get hold of the Plugin-Class attribute and load the class
		String className = mainAttribs.getValue("Plugin-Class");
//		URL[] urls = new URL[] { new URL(bundlePath) };
//		ClassLoader classLoader = new URLClassLoader(urls);
//		Class<?> pluginClass = classLoader.loadClass(className);

		pluginCore.addPlugin(className);
		addPluginToXML(jarFile);

	}

	public void removePlugin() {
		// TODO: CHeck dependents first. Then stop plugin, then remove plugin
		// from list and XML.
	}
}
