package plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;

@WebServlet("/BasketballTeamPlugin/PutServlet/*")
public class PutServlet implements IServlet {

	public HttpResponse service(HttpRequest request, String serverRootDirectory) {
		HttpResponse response = null;

		if (request.getMethod().equals("PUT"))
			response = doPut(request, serverRootDirectory);
		else
			response = HttpResponseFactory.create400BadRequest(Protocol.CLOSE);

		return response;

	}

	private HttpResponse doPut(HttpRequest request, String serverRootDirectory) {
		HttpResponse response = null;
		String temp = String.valueOf(request.getBody());
		System.out.println(temp);
		String teamName = temp.substring(temp.indexOf("=") + 1, temp.length());
		String numMem = temp
				.substring(temp.lastIndexOf("=") + 1, temp.length());
		boolean updated = putUpdateTeam(teamName, numMem);
		if (updated) {
			response = HttpResponseFactory.create200OKPOST(Protocol.CLOSE,
					teamName);

		} else {
			response = HttpResponseFactory.create400BadRequest(Protocol.CLOSE);

		}
		return response;
	}

	private static boolean putUpdateTeam(String teamName,
			String numberOfTeamMembers) {
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
			System.out
					.println("You made it, take control of your database now!");
			Statement stmt = null;
			ResultSet rs = null;

			try {
				// TODO fix why PUT works on the server and does not work here
				stmt = connection.createStatement();
				stmt.executeUpdate("UPDATE `test_for_addie`.`Teams` SET `numMembers`='"
						+ numberOfTeamMembers
						+ "' WHERE `TeamName`='"
						+ teamName + "';");

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