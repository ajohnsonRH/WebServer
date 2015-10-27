/*
 * GetResponse.java
 * Oct 18, 2015
 * Jessica Kassal, Adalyn Johnson, Graham Fuller
 */

package server;

import java.io.File;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;

/**
 * 
 * @author Chandan R. Rupakheti (rupakhcr@clarkson.edu)
 */
public class GetResponse implements IResponse {

	private Server server;

	/**
	 * @param server
	 */
	public GetResponse(Server server) {
		this.server = server;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.IResponse#getResponse()
	 */
	@Override
	public HttpResponse getResponse(HttpRequest request) {
		HttpResponse response;
		String uri = request.getUri();
		// Get root directory path from server
		String rootDirectory = server.getRootDirectory();
		// Combine them together to form absolute file path
		File file = new File(rootDirectory + uri);
		// Check if the file exists
		if (file.exists()) {
			if (file.isDirectory()) {
				// Look for default index.html file in a directory
				String location = rootDirectory + uri
						+ System.getProperty("file.separator")
						+ Protocol.DEFAULT_FILE;
				file = new File(location);
				if (file.exists()) {
					// Lets create 200 OK response
					response = HttpResponseFactory.create200OK(file,
							Protocol.CLOSE);
				} else {
					// File does not exist so lets create 404 file not found
					// code
					response = HttpResponseFactory
							.create404NotFound(Protocol.CLOSE);
				}
			} else { // Its a file
						// Lets create 200 OK response
				response = HttpResponseFactory
						.create200OK(file, Protocol.CLOSE);
			}
		} else {
			// File does not exist so lets create 404 file not found code
			response = HttpResponseFactory.create404NotFound(Protocol.CLOSE);
		}
		return response;
	}

}
