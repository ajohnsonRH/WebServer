/*
 * PostResponse.java
 * Oct 18, 2015
 * Graham Fuller, Adalyn Johnson, Jessica Kassal
 */

package server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;

/**
 * 
 * @author Graham Fuller, Adalyn Johnson, Jessica Kassal
 */
public class PostResponse implements IResponse {
	private Server server;

	/**
	 * @param server
	 */
	public PostResponse(Server server) {
		this.server = server;
	}

	@Override
	public HttpResponse getResponse(HttpRequest request) {
		HttpResponse response = null;
		String uri = request.getUri();
		// Get root directory path from server
		String rootDirectory = server.getRootDirectory();
		// Combine them together to form absolute file path
		File file = new File(rootDirectory + uri);
		// Check if the file exists
		if (file.isDirectory()) {
			// Look for default index.html file in a directory
			String location = rootDirectory + uri
					+ System.getProperty("file.separator")
					+ Protocol.DEFAULT_FILE;
			file = new File(location);

			try {

				if (file.exists()) {
					// Lets create 200 OK response
					CharSequence seq = java.nio.CharBuffer.wrap(request
							.getBody());
					Files.write(Paths.get(location),
							new String(request.getBody()).getBytes("UTF-8"),
							StandardOpenOption.APPEND);
					response = HttpResponseFactory.create200OK(null,
							Protocol.CLOSE);
				} else {
					FileWriter fw = new FileWriter(file);
					// File does not exist so lets create a new one!
					fw.write(request.getBody());
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
				if (file.exists()) {
					// Lets create 200 OK response
					CharSequence seq = java.nio.CharBuffer.wrap(request
							.getBody());
					try  {
						Files.write(Paths.get(file.getPath()),
								new String(request.getBody()).getBytes("UTF-8"),
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
					fw.write(request.getBody());
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
