import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PluginManager {
	private PluginCore pluginCore;
	private ArrayList<String> pluginNames;
	private String filepath;
	private ArrayList<File> pluginBundles;
	private HashMap<String, IBrahmaPlugin> plugins;

	public PluginManager(String filepath, PluginCore pluginCore) {
		this.filepath = filepath;
		File xmlDoc = new File(this.filepath);
		this.pluginCore = pluginCore;
		this.pluginNames = new ArrayList<String>();
		this.pluginBundles = new ArrayList<File>();
		this.plugins = new HashMap<String, IBrahmaPlugin>();

		if (xmlDoc.exists()) {
			loadXML(filepath);
		} else {
			createXML(filepath);
		}
	}

	public void loadXML(String filepath) {
		try {
			Document doc = buildDoc(filepath);

			NodeList pluginNodes = doc.getElementsByTagName("Plugin");
			for (int i = 0; i < pluginNodes.getLength(); i++) {
				Node currentNode = pluginNodes.item(i);
				NamedNodeMap attr = currentNode.getAttributes();
				this.pluginNames
						.add(attr.getNamedItem("name").getTextContent());
				String pluginPath = attr.getNamedItem("path").getTextContent();
				File pluginBundle = new File(pluginPath);
				if (pluginBundle.exists()) {
					this.pluginBundles.add(pluginBundle);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			for (File f : this.pluginBundles) {
				addPlugin(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Document buildDoc(String filepath)
			throws ParserConfigurationException, SAXException, IOException {
		// build the XML doc from memory
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(filepath);
		return doc;
	}

	public void createXML(String filepath) {
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("BrahmaPlugins");
			doc.appendChild(rootElement);

			writeToXML(filepath, doc);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void writeToXML(String filepath, Document doc)
			throws TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException {
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filepath));
		transformer.transform(source, result);
	}

	public void addPluginToXML(String className, String bundlePath,
			String[] dependencies) {
		try {
			Document doc = buildDoc(this.filepath);

			Node rootNode = doc.getFirstChild();
			NodeList pluginNodes = rootNode.getChildNodes();
			// Check for pre-existing node
			Boolean added = false;
			for (int i = 0; i < pluginNodes.getLength(); i++) {
				Node currentNode = pluginNodes.item(i);
				if (currentNode.getAttributes().getNamedItem("name")
						.getTextContent() == className) {
					added = true;
				}
			}

			if (!added) {
				Element pluginNode = doc.createElement("Plugin");
				pluginNode.setAttribute("name", className);
				pluginNode.setAttribute("path", bundlePath);
				rootNode.appendChild(pluginNode);
				Element dependentNode = doc.createElement("Dependent");
				dependentNode.setNodeValue(className);
				for (String parent : dependencies) {
					addDependentNodes(pluginNodes, dependentNode, parent);
				}

			}

			writeToXML(this.filepath, doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addDependentNodes(NodeList pluginNodes, Element dependentNode,
			String parentName) {
		for (int j = 0; j < pluginNodes.getLength(); j++) {
			Node possiblePar = pluginNodes.item(j);
			if (possiblePar.getAttributes().getNamedItem("name")
					.getTextContent() == parentName) {
				possiblePar.appendChild(dependentNode);
				break;
			}
		}
	}

	public void removeDependentNodes (NodeList pluginNodes, String dependentName) {
		
		for (int j = 0; j < pluginNodes.getLength(); j++) {
			Node pluginNode = pluginNodes.item(j);
			NodeList dependents = pluginNode.getChildNodes();
			for (int i = 0; i < dependents.getLength(); i++) {
				if(dependents.item(i).getNodeValue() == dependentName){
					pluginNode.removeChild(dependents.item(i));
					break;
				}
			}
		}
	
	}

	public void startPlugin(IBrahmaPlugin pluginClass) {
		pluginClass.start();
	}

	public void stopPlugin(IBrahmaPlugin pluginClass) {
		pluginClass.stop();
	}

	public void addPlugin(File jarBundle) throws Exception {
		// Get the manifest file in the jar file
		JarFile jarFile = new JarFile(jarBundle);
		String bundlePath = jarBundle.getPath();

		Manifest mf = jarFile.getManifest();
		Attributes mainAttribs = mf.getMainAttributes();
		// Get hold of the Plugin-Class attribute and load the class
		String className = mainAttribs.getValue("Plugin-Class");
		String[] dependencies = mainAttribs.getValue("Plugin-Dependencies")
				.split(",");
		Boolean supported = true;
		for (String s : dependencies) {
			if (!this.pluginCore.hasPlugin(s)) {
				supported = false;
				break;
			}
		}
		if (supported) {
			URL[] urls = new URL[] { new URL(bundlePath) };
			@SuppressWarnings("resource")
			ClassLoader classLoader = new URLClassLoader(urls);
			Class<?> pluginClass = classLoader.loadClass(className);
			IBrahmaPlugin plugin = (IBrahmaPlugin) pluginClass.newInstance();
			plugins.put(className, plugin);
			if (verifyImplementation(pluginClass)) {
				pluginCore.addPlugin(className);
				addPluginToXML(className, bundlePath, dependencies);
				startPlugin(plugin);
			}
			
		}
		jarFile.close();

	}

	private boolean verifyImplementation(Class<?> pluginClass) {
		// Make sure their class implements our interface.
		Class<?>[] interfaces = pluginClass.getInterfaces();
		Boolean implemented = false;
		for (Class<?> i : interfaces) {
			if (IBrahmaPlugin.class == i) {
				implemented = true;
			}
		}

		return implemented;
	}

	public void removePlugin(String name) {
		IBrahmaPlugin plugin = plugins.get(name);
		stopPlugin(plugin);
		plugins.remove(name);
		plugin = null;
		try {
			Document doc = buildDoc(this.filepath);

			Node rootNode = doc.getFirstChild();
			NodeList pluginNodes = rootNode.getChildNodes();
			Node pluginNode = null;
			for(int i = 0; i < pluginNodes.getLength(); i++){
				if(pluginNodes.item(i).getAttributes().getNamedItem("name").getTextContent() == name){
					pluginNode = pluginNodes.item(i);
				}
			}
			NodeList dependentNodes = pluginNode.getChildNodes();
			for(int i = 0; i < dependentNodes.getLength(); i++){
				removePlugin(dependentNodes.item(i).getNodeValue());
			}
			
			removeDependentNodes(pluginNodes, name);
			
			rootNode.removeChild(pluginNode);
			
			writeToXML(this.filepath, doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
