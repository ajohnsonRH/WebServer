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

		System.out.println("URI:" + uri);

		// String[] parts = uri.split("/");

		String fileName = "", content = "";
		String charBody = new String(request.getBody());
		System.out.println("body:" + charBody);
		System.out.println(serverRootDirectory);
		fileName = charBody.substring(0, charBody.indexOf(":"));
		content = charBody.substring(charBody.indexOf(":") + 1);
		System.out.println("File:" + fileName + " content:" + content);

		// Get root directory path from server
		// Combine them together to form absolute file path
		File file = new File(serverRootDirectory + "/" + fileName);

		// Check if the file exists
		if (file.isDirectory()) {
			if (file.exists()) {
				response = HttpResponseFactory
						.create304NotModified(Protocol.CLOSE);
			} else {
				// create the directory
				file.mkdirs();
				response = HttpResponseFactory
						.create200OK(null, Protocol.CLOSE);
			}
		} else { // Its a file
			try {
				// file will get created or overwritten either way
				FileWriter fw = new FileWriter(file);
				fw.write(content);

				// Lets create 200 OK response
				response = HttpResponseFactory
						.create200OK(null, Protocol.CLOSE);

				fw.close();
			} catch (IOException e) {
				response = HttpResponseFactory.create500InternalServerError(Protocol.CLOSE);
				e.printStackTrace();
			}
		}
		return response;
	}

}