package plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;

public class PutServlet implements IServlet {

	public HttpResponse service(HttpRequest request, String serverRootDirectory) {
		HttpResponse response = null;
		
		if (request.getMethod().equals("PUT"))
			response = doPut(request, serverRootDirectory);
			
		// TODO add more than just GET to this Servlet
		else
			response = HttpResponseFactory.create400BadRequest(Protocol.CLOSE);

		return response;
		
	}

	private HttpResponse doPut(HttpRequest request, String serverRootDirectory) {
		
		HttpResponse response = null;
		String uri = request.getUri();
		String[] parts = uri.split("/");

		String fileName = "";
		String content = "";
		String charBody = new String(request.getBody());
		fileName = charBody.substring(0, charBody.indexOf(":"));
		content = charBody.substring(charBody.indexOf(":")+1);
		
		if(content.length()>10000){
			response = HttpResponseFactory
					.create413EntityTooLarge(Protocol.CLOSE);
			return response;
		}
		
		// Get root directory path from server
		// Combine them together to form absolute file path
		File file = new File(serverRootDirectory + "/" + fileName);
		// Check if the file exists
		if (file.isDirectory()) {
			// Look for default index.html file in a directory
			String location = serverRootDirectory + fileName
					+ System.getProperty("file.separator")
					+ Protocol.DEFAULT_FILE;
			file = new File(location);

			try {

				FileWriter fw = new FileWriter(file);

				if (file.exists()) {
					// Lets create 200 OK response
					CharSequence seq = java.nio.CharBuffer.wrap(request
							.getBody());
					fw.append(seq);
					response = HttpResponseFactory.create200OK(null,
							Protocol.CLOSE);
				} else {
					// File does not exist so lets create a new one!
					fw.write(content);

					response = HttpResponseFactory.create201OK(null,
							Protocol.CLOSE);
				}
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { // Its a file
					// Lets create 200 OK response
			try {

				FileWriter fw = new FileWriter(file);

				if (file.exists()) {
					// Lets create 200 OK response
					fw.write(content);
					response = HttpResponseFactory.create200OK(null,
							Protocol.CLOSE);
				} else {
					// File does not exist so lets create a new one!
					fw.write(content);

					response = HttpResponseFactory.create201OK(null,
							Protocol.CLOSE);
				}

				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

}