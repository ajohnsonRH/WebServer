/*
 * MyPlugin.java
 * Oct 25, 2015
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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;

public class MyPlugin implements IPlugin {
	public String contextRoot = "/GetPlugin";
	public String configPath = "config.txt";
	public HashMap<String, IServlet> servletMap = new HashMap<String, IServlet>();

	public MyPlugin() {
		initServletMap();
	}

	private void initServletMap() {

		InputStream input = getClass().getResourceAsStream("/config.txt");

		java.util.Scanner s = new java.util.Scanner(input).useDelimiter("\\A");

		while (s.hasNextLine()) {

			String line = s.nextLine();

			String[] splited = line.split("\\s+");

			Class<?> servletClass;
			try {
				servletClass = Class.forName(splited[2]);

				Constructor<?> constructor = servletClass.getConstructor();
				Object servletInstance = constructor.newInstance();
				
				System.out.println("Getting put into servlet map:"+splited[1]);

				servletMap.put(splited[1], (IServlet) servletInstance);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			input.close();
			s.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	// find servlet and forward request
	public HttpResponse handleRequest(HttpRequest request,
			String serverRootDirectory) {
		String requestUri = request.getUri();
		System.out.println("REQUEST URI:" + requestUri);
		
		String[] parts = requestUri.split("/");
		String servletUri = parts[2];

		IServlet servlet = findServlet(servletUri);
		
		System.out.println("Getting searched in servlet map:"+servletUri);

		if (servlet == null)
			return HttpResponseFactory.create404NotFound(Protocol.BAD_REQUEST_TEXT);

		return servlet.service(request, serverRootDirectory);
	}

	private IServlet findServlet(String servletURI) {
		return servletMap.get(servletURI);
	}

	public String getContextRoot() {
		return contextRoot;
	}

	public String getConfigPath() {
		return configPath;
	}

}
