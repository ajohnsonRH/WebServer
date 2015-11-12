package plugin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;

@WebServlet("/BasketballTeamPlugin/ExtraServlet/*")
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
	
	private static ArrayList<String> doGetTeams() {
		ArrayList<String> teams = new ArrayList<String>();
		System.out
				.println("-------- MySQL JDBC Connection Testing ------------");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return teams;
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
			return teams;
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			Statement stmt = null;
			ResultSet rs = null;

			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT * FROM Teams");

				if (stmt.execute("SELECT * FROM Teams")) {
					rs = stmt.getResultSet();
					while (rs.next()) {
						System.out.println(rs.getString(1));
						teams.add(rs.getString(1));
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
		return teams;
	}

	
	
	//TODO DELETE THIS
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
		ArrayList<String> teams = doGetTeams();
		System.out.println("TEAMS: " +teams.toString());
		response = HttpResponseFactory.create200OKGET(Protocol.CLOSE,teams.toString());
		return response;
	}

}
