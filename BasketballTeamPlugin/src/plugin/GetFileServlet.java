package plugin;

import java.io.File;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;

public class GetFileServlet implements IServlet {

	public HttpResponse service(HttpRequest request, String serverRootDirectory) {
		HttpResponse response = null;
		if(request.getMethod().equals("GET"))
			response = doGet(request,serverRootDirectory);
		else
			response = HttpResponseFactory.create400BadRequest(Protocol.CLOSE);

		return response;
	}
	
	private HttpResponse doGet(HttpRequest request, String serverRootDirectory) {
		HttpResponse response;
		String uri = request.getUri();
		String[] parts = uri.split("/");
		String fileName = parts[3];

		// Get root directory path from server
		// Combine them together to form absolute file path
		File file = new File(serverRootDirectory + "/" + fileName);
		
		if(file.isDirectory()){
			// Look for default index.html file in a directory
			String location = serverRootDirectory + uri
					+ System.getProperty("file.separator")
					+ Protocol.DEFAULT_FILE;
			file = new File(location);
		}
		
		if (file.exists()) {
			if (file.length() > 10000000) {
				response = HttpResponseFactory
						.create413EntityTooLarge(Protocol.CLOSE);
			}
			else {
				response = HttpResponseFactory.create200OK(file,
						Protocol.CLOSE);
			}
		}
		else{
			response = HttpResponseFactory.create404NotFound(Protocol.CLOSE);
		}
		
		return response;
	}
}
