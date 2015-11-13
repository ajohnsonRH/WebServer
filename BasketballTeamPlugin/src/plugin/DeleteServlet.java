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

@WebServlet("/BasketballTeamPlugin/DeleteServlet/*")
public class DeleteServlet implements IServlet {

	public HttpResponse service(HttpRequest request, String serverRootDirectory) {
		HttpResponse response = null;

		if (request.getMethod().equals("DELETE"))
			response = doPut(request, serverRootDirectory);
		else
			response = HttpResponseFactory.create400BadRequest(Protocol.CLOSE);

		return response;

	}

	private HttpResponse doPut(HttpRequest request, String serverRootDirectory) {
		HttpResponse response = null;
		String temp = String.valueOf(request.getBody());
		
		String[] nameAndNum = temp.split(" ");
		String[] name = nameAndNum[0].split("=");

		boolean updated = deleteTeam(name[1]);
		if (updated) {
			response = HttpResponseFactory.create200OKPOSTPUTDELETE(Protocol.CLOSE,
					name[1]);

		} else {
			response = HttpResponseFactory.create400BadRequest(Protocol.CLOSE);

		}
		return response;
	}

	private static boolean deleteTeam(String teamName) {
		teamName = teamName.replace('+', ' ');
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
				// TODO fix why PUT works on the server and does not work here
				stmt = connection.createStatement();
				stmt.executeUpdate("DELETE FROM test_for_addie.Teams WHERE TeamName='"
						+ teamName + "';");
				
				System.out.println("Successfully deleted "+teamName);

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
