/*
 * PluginLoader.java
 * Oct 23, 2015
 *
 * Simple Web Server (SWS) for EE407/507 and CS455/555
 * 
 * Copyright (C) 2011 Chandan Raj Rupakheti, Clarkson University
 * 
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation, either 
 * version 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/lgpl.html>.
 * 
 * Contact Us:
 * Chandan Raj Rupakheti (rupakhcr@clarkson.edu)
 * Department of Electrical and Computer Engineering
 * Clarkson University
 * Potsdam
 * NY 13699-5722
 * http://clarkson.edu/~rupakhcr
 */
 
package plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import server.Server;

public class PluginLoader extends Thread {

	private static final String PLUGINS_FOLDER = "plugins";
	private static final String USER_DIR = "user.dir";
	private static final String CREATE_PLUGIN_METHOD_NAME = "createPlugin";
	private static final String JAR_PATH = "Jar path: ";
	private static final String FILE_SERVLETS = "file:plugins\\";
	private static final String PLUGIN_CREATOR = "Plugin-Creator";
	private Server server;
	

	public PluginLoader(Server server) {
		this.server = server;
	}

	public void initialLoad() throws Exception {

		File folder = new File("plugins/");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			if (file.isFile()) {
				loadPlugin(file.getName());
			}
		}
	}

	public void run() {
		while (true) {
			Path myDir = Paths.get(PLUGINS_FOLDER);

			try {
				WatchService watcher = myDir.getFileSystem().newWatchService();
				myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_MODIFY,
						StandardWatchEventKinds.ENTRY_DELETE);
				WatchKey watckKey = watcher.take();
				List<WatchEvent<?>> events = watckKey.pollEvents();
				updatePlugins(events);

			} catch (Exception e) {
				System.out.println("Error: " + e.toString());
			}
		}
	}

	private void updatePlugins(List<WatchEvent<?>> events) throws Exception {
		for (WatchEvent event : events) {
			if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE||event.kind()==StandardWatchEventKinds.ENTRY_MODIFY) {
				System.out.println("Created: "
						+ event.context().toString());
				loadPlugin(event.context().toString());				
			}
			if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
				System.out.println("Delete: "
						+ event.context().toString());
				server.plugins.remove( event.context().toString());
			}
		}
	}

	public void loadPlugin(String jarName) throws Exception {
		URL classUrl = new URL(FILE_SERVLETS + jarName);
		String pluginCreatorLocal = getPluginCreatorFolder(jarName);
		URL[] classUrls = { classUrl };
		URLClassLoader urlClassLoader = new URLClassLoader(classUrls);
		Class<?> beanClass = urlClassLoader.loadClass(pluginCreatorLocal);
		// Create a new instance from the loaded class
		Constructor<?> constructor = beanClass.getConstructor();
		Object beanObj = constructor.newInstance();
		Method method = beanClass.getMethod(CREATE_PLUGIN_METHOD_NAME);
		IPlugin newplugin = (IPlugin) method.invoke(beanObj);
		server.plugins.put(newplugin.getContextRoot(),newplugin);
	}

	private String getPluginCreatorFolder(String jarName) throws IOException {
		String jarPath = getJarPath(jarName);
		JarFile jarFile = new JarFile(jarPath);
		Manifest m = jarFile.getManifest();
		Attributes attr = m.getMainAttributes();
		String pluginCreatorLocal = attr.getValue(PLUGIN_CREATOR);
		return pluginCreatorLocal;
	}

	private String getJarPath(String jarName) {
		StringBuilder sb = new StringBuilder();
		sb.append(System.getProperty(USER_DIR));
		sb.append(File.separator);
		sb.append(PLUGINS_FOLDER);
		sb.append(File.separator);
		sb.append(jarName);
		return sb.toString();
	}
}
