package plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;

@WebServlet("/BasketballTeamPlugin/GetServlet/*")
public class GetServlet extends HttpServlet implements IServlet {

	private static final long serialVersionUID = 1L;

	public HttpResponse service(HttpRequest request, String serverRootDirectory) {
		HttpResponse response;
		if (request.getMethod().equals("GET"))
			response = doGet(request, serverRootDirectory);
		else
			response = HttpResponseFactory.create400BadRequest(Protocol.CLOSE);

		return response;
	}

	private HttpResponse doGet(HttpRequest request, String serverRootDirectory) {
		HttpResponse response;
		String teams = doGetTeams();
		response = HttpResponseFactory.create200OKGET(Protocol.CLOSE, teams.toString());
		return response;
	}
	
	private static String doGetTeams() {
		StringBuilder teams = new StringBuilder();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return teams.toString();
		}

		Connection connection = null;

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://mysql.allisonandwilliams.com/test_for_addie",
					"adalyn", "godisgood");

		} catch (SQLException e) {
			e.printStackTrace();
			return teams.toString();
		}

		if (connection != null) {
			Statement stmt = null;
			ResultSet rs = null;

			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT * FROM Teams");

				if (stmt.execute("SELECT * FROM Teams")) {
					rs = stmt.getResultSet();
					teams.append("Team Name                Number of Players\n");
					int g= 25;
					while (rs.next()) {
						teams.append(rs.getString(1));
						for(int i=0; i< 25-rs.getString(1).length(); i++){
							teams.append(" ");
						}
						teams.append(rs.getString(2));
						teams.append("\n");
					}
				}

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

				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException sqlEx) {
					} // ignore

					rs = null;
				}

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
		return teams.toString();
	}
}
