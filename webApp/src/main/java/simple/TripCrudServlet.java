package simple;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import org.tinylog.Logger;
/**
 * Servlet TripCrudServlet
 * Does CRUD operations for trips in the database
 */
public class TripCrudServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoFile dao;
	/**
	 * Default constructor.
	 */
	public TripCrudServlet() {
		this(new DaoFile());
	}
	
	//for tests
	public TripCrudServlet(DaoFile dao) {
		super();
		this.dao = dao;
	}
	/**
	 * handles HTTP GET method
	 * Reads a list of trips from the db and sends it to  view_trips.jsp
	 *
	 * @param request  The HttpServletRequest object 
	 * @param response The HttpServletResponse object
	 * @throws ServletException if a servlet error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dao.testConnection();
		List<Trip> trips = null;
		try {
			trips = dao.getTrips();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		session.setAttribute("trips", trips);
		RequestDispatcher dispatcher = request.getRequestDispatcher("view_trips.jsp");
		dispatcher.forward(request, response);
	}
	/**
	 * handles the HTTP POST method for CRUD operations and validates input
	 *
	 * @param request  The HttpServletRequest object
	 * @param response The HttpServletResponse object
	 * @throws ServletException if a servlet error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//regex patterns
		String caveNameRegex = "^[A-Za-z\\s]{1,100}$";
		String timestampRegex = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$";
		String groupSizeRegex = "^\\d+$";
		String maxTripLengthRegex = "^\\d*[\\.]?[\\d]*$";

		String action = request.getParameter("action");
		DaoFile dao = this.dao;
		dao.testConnection();
		PrintWriter out = response.getWriter();
		if ("delete".equals(action)) {
			//delete trip
			int tripId = Integer.parseInt(request.getParameter("trip_id"));
			try {
				dao.deleteTrip(tripId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Logger.info("Trip with ID " + tripId + " deleted.");
			response.sendRedirect(request.getContextPath() + "/TripCrudServlet");
		} else if ("update".equals(action)) {
			// Validate input
			if (!isValid(request.getParameter("cave_name"), caveNameRegex) ||
					!isValid(request.getParameter("start_time"), timestampRegex) ||
					!isValid(request.getParameter("end_time"), timestampRegex) ||
					!isValid(request.getParameter("group_size"), groupSizeRegex) ||
					!isValid(request.getParameter("max_trip_length"), maxTripLengthRegex)) {
				out.println("Error: Invalid input format.");
				Logger.info("invalid input");
				return;
			}
			//update trip
			int tripId = Integer.parseInt(request.getParameter("trip_id"));
			String caveName = request.getParameter("cave_name");
			Timestamp startTime = Timestamp.valueOf(request.getParameter("start_time"));
			Timestamp endTime = Timestamp.valueOf(request.getParameter("end_time"));
			int groupSize = Integer.parseInt(request.getParameter("group_size"));
			double maxTripLength = Double.parseDouble(request.getParameter("max_trip_length"));
			if(startTime.after(endTime)) {
				out.println("Error: Invalid time.");
				Logger.info("invalid input");
				return;
			}

			try {
				dao.updateTrip(tripId, caveName, startTime, endTime, groupSize, maxTripLength);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Logger.info("Trip with ID " + tripId + " updated.");
			response.sendRedirect(request.getContextPath() + "/TripCrudServlet");
		} else if ("insert".equals(action)) {
			// Validate input
			if (!isValid(request.getParameter("cave_name"), caveNameRegex) ||
					!isValid(request.getParameter("start_time"), timestampRegex) ||
					!isValid(request.getParameter("end_time"), timestampRegex) ||
					!isValid(request.getParameter("group_size"), groupSizeRegex) ||
					!isValid(request.getParameter("max_trip_length"), maxTripLengthRegex)) {
				out.println("Error: Invalid input format.");
				Logger.info("invalid input");
				return;
			}
			//add trip
			int caverId = Integer.parseInt(request.getParameter("caver_id"));
			String caveName = request.getParameter("cave_name");
			Timestamp startTime = Timestamp.valueOf(request.getParameter("start_time"));
			Timestamp endTime = Timestamp.valueOf(request.getParameter("end_time"));
			int groupSize = Integer.parseInt(request.getParameter("group_size"));
			double maxTripLength = Double.parseDouble(request.getParameter("max_trip_length"));
			
			if(startTime.after(endTime)) {
				out.println("Error: Invalid time.");
				Logger.info("invalid input");
				return;
			}


			try {
				dao.addTrip(caverId, caveName, startTime, endTime, groupSize, maxTripLength);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Logger.info("New trip added: Cave = " + caveName);
			response.sendRedirect(request.getContextPath() + "/TripCrudServlet");
		} else {
			//Read from db
			List<Trip> trips = null;
			try {
				trips = dao.getTrips();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpSession session = request.getSession();
			session.setAttribute("trips", trips);
			int caverId = Integer.parseInt(request.getParameter("caver_id"));
			session.setAttribute("caver_id", caverId);
			RequestDispatcher dispatcher = request.getRequestDispatcher("view_trips.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * Validates an input string using regex
	 *
	 * @param String input to validate
	 * @param String regex to match
	 * @return true if input matches the regex, else false
	 */
	private boolean isValid(String input, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
}