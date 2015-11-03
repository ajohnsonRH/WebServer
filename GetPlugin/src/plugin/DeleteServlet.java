package plugin;

import java.io.File;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;

public class DeleteServlet implements IServlet {

	public HttpResponse service(HttpRequest request, String serverRootDirectory) {
		
		HttpResponse response = null;
		if(request.getMethod().equals("DELETE"))
			response = doDelete(request, serverRootDirectory);
		else
			response = HttpResponseFactory.create400BadRequest(Protocol.CLOSE);
		return response;
	}

	private HttpResponse doDelete(HttpRequest request, String serverRootDirectory) {
		HttpResponse response;
		String fileName = new String(request.getBody());
		
		// Get root directory path from server
		// Combine them together to form absolute file path
		File file = new File(serverRootDirectory + "/" + fileName);
		
		// Check if the file exists
		if (file.exists()) {

			if (file.isDirectory()) {
				//TODO This won't work if directory isn't empty
				file.delete();
				response = HttpResponseFactory
						.create200OK(null, Protocol.CLOSE);
			} else { 
				// It's a file
				file.delete();
				response = HttpResponseFactory
						.create200OK(null, Protocol.CLOSE);
			}
		} else {
			// File does not exist so lets return
			System.out.println("body = " + new String(request.getBody()));
			response = HttpResponseFactory.create404NotFound(Protocol.CLOSE);
		}
		return response;
	}
}
