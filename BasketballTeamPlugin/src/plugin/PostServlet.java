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
		String teamName = temp.substring(temp.indexOf("=") + 1, temp.length());
		boolean added = postNewTeam(teamName);
		if (added) {
			response = HttpResponseFactory.create200OKPOST(Protocol.CLOSE,
					teamName);
		} else
			response = HttpResponseFactory.create400BadRequest(Protocol.CLOSE);
		return response;
	}

	private static boolean postNewTeam(String teamName) {
		teamName = teamName.replace('+', ' ');
		ArrayList<String> teams = new ArrayList<String>();
		System.out
				.println("-------- MySQL JDBC Connection Testing ------------");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return false;
		}

		System.out.println("MySQL JDBC Driver Registered!");
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://mysql.allisonandwilliams.com/test_for_addie",
					"adalyn", "godisgood");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return false;
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			Statement stmt = null;
			ResultSet rs = null;

			try {
				stmt = connection.createStatement();
				stmt.executeUpdate("INSERT INTO `test_for_addie`.`Teams` (`TeamName`,`numMembers`) VALUES ('"
						+ teamName + "', '" + 0 + "');");

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