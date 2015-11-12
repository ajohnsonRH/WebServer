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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;

@WebServlet("/BasketballTeamPlugin/PostServlet/*")
public class PostServlet implements IServlet {
	String requestType = "POST";

	public HttpResponse service(HttpRequest request, String serverRootDirectory) {
		HttpResponse response = null;
		if (request.getMethod().equals("POST"))
			response = doPost(request, serverRootDirectory);
		else
			response = HttpResponseFactory.create400BadRequest(Protocol.CLOSE);
		return response;
	}

	private HttpResponse doPost(HttpRequest request, String serverRootDirectory) {
		HttpResponse response;
		String temp = String.valueOf(request.getBody());
		
		String[] nameAndNum = temp.split(" ");
		String[] name = nameAndNum[0].split("=");
		
		boolean added = postNewTeam(name[1]);
		if (added) {
			response = HttpResponseFactory.create200OKPOSTPUTDELETE(Protocol.CLOSE,
					name[1]);
		} else
			response = HttpResponseFactory.create400BadRequest(Protocol.CLOSE);
		return response;
	}

	private static boolean postNewTeam(String teamName) {
		teamName = teamName.replace('+', ' ');
		ArrayList<String> teams = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		Connection connection = null;

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://mysql.allisonandwilliams.com/test_for_addie",
					"adalyn", "godisgood");

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		if (connection != null) {
			Statement stmt = null;
			ResultSet rs = null;

			try {
				stmt = connection.createStatement();
				stmt.executeUpdate("INSERT INTO test_for_addie.Teams (TeamName, numMembers) VALUES ('"
						+ teamName + "', '0');");
				
				System.out.println("Successfully added the team "+teamName);

				// Now do something with the ResultSet ....
			} catch (SQLException ex) {
				// handle any errors
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			} finally {
				// it is a good idea to release
				// resources in a finally{} block
				// in reverse-order of their creation
				// if they are no-longer needed

				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException sqlEx) {
					} // ignore

					stmt = null;
				}
			}
		} else {
			System.out.println("Failed to make connection!");
		}
		return true;
	}

}