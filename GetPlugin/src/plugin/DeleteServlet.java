package plugin;

import java.io.File;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;

public class DeleteServlet implements IServlet {
	String requestType = "DELETE";

	@Override
	public HttpResponse service(HttpRequest request, String serverRootDirectory) {
		
		HttpResponse response = null;
		String uri = request.getUri();
		
		String[] parts = uri.split("/");
		String fileName = "/"+parts[3];
		
		
		// Get root directory path from server
		// Combine them together to form absolute file path
		File file = new File(serverRootDirectory + fileName);
		
		// Check if the file exists
		if (file.isDirectory()) {
			// Look for default index.html file in a directory
			String location = serverRootDirectory + fileName
					+ System.getProperty("file.separator")
					+ Protocol.DEFAULT_FILE;
			file = new File(location);

			if (file.exists()) {
				// Lets create 200 OK response
				file.delete();
				response = HttpResponseFactory
						.create200OK(null, Protocol.CLOSE);
			} else {
				// File does not exist so lets return

				response = HttpResponseFactory
						.create404NotFound(Protocol.CLOSE);
			}
		} else { // Its a file
			if (file.exists()) {
				// Lets create 200 OK response
				file.delete();
				response = HttpResponseFactory
						.create200OK(null, Protocol.CLOSE);
			} else {
				// File does not exist so lets return

				System.out.println("body = " + new String(request.getBody()));

				response = HttpResponseFactory
						.create404NotFound(Protocol.CLOSE);
			}
		}
		return response;
	}
}
