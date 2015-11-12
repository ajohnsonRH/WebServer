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

@WebServlet("/BasketballTeamPlugin/GetServlet/*")
public class GetServlet extends HttpServlet implements IServlet {
	private static final long serialVersionUID = 1L;
	String requestType = "GET";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// super.doGet(req, resp);
		System.out.println(this.getServletInfo());
		resp.setContentType("text/plain"); 
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write("");
	}

	public HttpResponse service(HttpRequest request, String serverRootDirectory) {
		System.out.println("service in getServlet");
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