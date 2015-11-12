package plugin;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;

@WebServlet("/GetPlugin/ExtraServlet/*")
public class ExtraServlet extends HttpServlet implements IServlet {

	private static final long serialVersionUID = 1L;

	public HttpResponse service(HttpRequest request, String serverRootDirectory) {
		HttpResponse response;
		if (request.getMethod().equals("GET"))
			response = doGet(request, serverRootDirectory);
		else
			response = HttpResponseFactory.create400BadRequest(Protocol.CLOSE);

		return response;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//super.doGet(req, resp);
		String text = "some text";
		System.out.println(this.getServletInfo());

		resp.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
		resp.setCharacterEncoding("UTF-8"); // You want world domination, huh?
		resp.getWriter().write(text);
	}

	private HttpResponse doGet(HttpRequest request, String serverRootDirectory) {
		HttpResponse response;
		String uri = request.getUri();
		String[] parts = uri.split("/");
		String fileName = parts[3];

		// Get root directory path from server
		// Combine them together to form absolute file path
		File file = new File(serverRootDirectory + "/" + fileName);
		// Check if the file exists
		if (file.exists()) {
			if (file.isDirectory()) {
				// Look for default index.html file in a directory
				String location = serverRootDirectory + uri
						+ System.getProperty("file.separator")
						+ Protocol.DEFAULT_FILE;
				file = new File(location);
				if (file.exists()) {
					
					System.out.println("length: "+file.length());

					if (file.length() > 10000000) {
						response = HttpResponseFactory
								.create413EntityTooLarge(Protocol.CLOSE);
					}

					else {
						// Lets create 200 OK response
						response = HttpResponseFactory.create200OK(file,
								Protocol.CLOSE);
					}
				} else {
					// File does not exist so lets create 404 file not found
					response = HttpResponseFactory
							.create404NotFound(Protocol.CLOSE);
				}
			} else {

				System.out.println("length: "+file.length());
				
				if (file.length() > 10000000) {
					response = HttpResponseFactory
							.create413EntityTooLarge(Protocol.CLOSE);
				}

				else {
					// Its a file
					// Lets create 200 OK response
					response = HttpResponseFactory
							.create200OK(file, Protocol.CLOSE);
				}
			}
		} else {
			// File does not exist so lets create 404 file not found code
			response = HttpResponseFactory.create404NotFound(Protocol.CLOSE);
		}
		return response;
	}

}
