/*
 * GetServlet.java
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import plugin.IServlet;
import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;

/**
 * 
 */
public class PostServlet implements IServlet {
	String requestType = "POST";

	public HttpResponse service(HttpRequest request, String serverRootDirectory) {
		HttpResponse response = null;
		if (request.getMethod().equals("POST"))
			response = doPost(request, serverRootDirectory);
		// TODO add more than just GET to this Servlet
		else
			response = HttpResponseFactory.create400BadRequest(Protocol.CLOSE);

		return response;
	}

	private HttpResponse doPost(HttpRequest request, String serverRootDirectory) {
		HttpResponse response = null;
		String uri = request.getUri();
		System.out.println("URI:" + uri);
		String charBody = new String(request.getBody());
		String fileName = "", content = "";
		// TODO something with the body
		System.out.println("body:"+charBody);
		System.out.println(serverRootDirectory);
		fileName = charBody.substring(0, charBody.indexOf(":"));
		content = charBody.substring(charBody.indexOf(":")+1);
		System.out.println("File:"+fileName+" content:"+content);
		
//		if (charBody.contains(".html")) { // TODO alter this to allow other file
//			String[] body = charBody.split(":");
//			fileName = body[0];
//			content = body[2];
//			System.out.println("File:"+fileName+" content:"+content);
//		}

		// Get root directory path from server
		// Combine them together to form absolute file path
		File file = new File(serverRootDirectory +"/"+ fileName);
		// Check if the file exists
		if (file.isDirectory()) {
			// Look for default index.html file in a directory
			String location = serverRootDirectory + fileName
					+ System.getProperty("file.separator")
					+ Protocol.DEFAULT_FILE;
			file = new File(location);

			try {
				System.out.println("got here 2");
				if (file.exists()) {
					// Lets create 200 OK response
					Files.write(Paths.get(location),
							new String(content).getBytes("UTF-8"),
							StandardOpenOption.APPEND);
					response = HttpResponseFactory.create200OK(null,
							Protocol.CLOSE);
				} else {
					FileWriter fw = new FileWriter(file);
					// File does not exist so lets create a new one!
					fw.write(content);
					response = HttpResponseFactory.create201OK(null,
							Protocol.CLOSE);
					fw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { // Its a file
					// Lets create 200 OK response
			try {
				System.out.println("got here 3");
				System.out.println("Dir: "+serverRootDirectory);
				if (file.exists()) {
					// Lets create 200 OK response
					try {
						System.out.println("got here 4");
						Files.write(Paths.get(file.getPath()), new String(
								content).getBytes("UTF-8"),
								StandardOpenOption.APPEND);
						// more code
					} catch (IOException e) {
						// exception handling left as an exercise for the reader
					}
					response = HttpResponseFactory.create200OK(null,
							Protocol.CLOSE);
				} else {
					FileWriter fw = new FileWriter(file);
					// File does not exist so lets create a new one!
					fw.write(content);
					response = HttpResponseFactory.create201OK(null,
							Protocol.CLOSE);
					fw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

}
