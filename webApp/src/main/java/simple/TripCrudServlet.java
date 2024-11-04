package simple;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

import org.tinylog.Logger;

/**
 * Servlet implementation class TripCrudServelet
 */
public class TripCrudServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TripCrudServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoFile dao = new DaoFile();
        dao.testConnection();
        List<Trip> trips = dao.getTrips();
        HttpSession session = request.getSession();
        session.setAttribute("trips", trips);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("view_trips.jsp");
        dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		String action = request.getParameter("action");
		DaoFile dao = new DaoFile();
		dao.testConnection();


		dao.testConnection();
		if ("delete".equals(action)) {
            int tripId = Integer.parseInt(request.getParameter("trip_id"));
            dao.deleteTrip(tripId);
            
            Logger.info("Trip with ID " + tripId + " deleted.");
            response.sendRedirect(request.getContextPath() + "/TripCrudServlet");
            action = null;
			
		} else if ("update".equals(action)) {
            int tripId = Integer.parseInt(request.getParameter("trip_id"));
            String caveName = request.getParameter("cave_name");
            Timestamp startTime = Timestamp.valueOf(request.getParameter("start_time"));
            Timestamp endTime = Timestamp.valueOf(request.getParameter("end_time"));
            int groupSize = Integer.parseInt(request.getParameter("group_size"));
            double maxTripLength = Double.parseDouble(request.getParameter("max_trip_length"));
            
			//Check valid input
			String tripIdRegex = "^[A-Za-z\\s]{1,100}$";
			String caveNameRegex = "^[A-Za-z\\s]{1,100}$";
			String startTimeRegex = "[0-9]{3}-[0-9]{3}-[0-9]{4}";
			String endTimeRegex = "^[A-Za-z\\s]{1,100}$";
			String groupSizeRegex = "^[A-Za-z\\s]{1,100}$";
			String maxTripLengthRegex = "[0-9]{3}-[0-9]{3}-[0-9]{4}";
			
//			if (!isValid(tripId, tripIdRegex) || !isValid(caveName, caveNameRegex) || !isValid(startTime, startTimeRegex)
//					|| !isValid(endTime, endTimeRegex)|| !isValid(groupSize, groupSizeRegex)|| !isValid(maxTripLength, maxTripLengthRegex)) {
//				out.println("Error: All fields (name, status, phone) are required and must be in the correct format.");
//				return;
//			}
//            
            dao.updateTrip(tripId, caveName, startTime, endTime, groupSize, maxTripLength);
            
            Logger.info("Trip with ID " + tripId + " updated.");
            response.sendRedirect(request.getContextPath() + "/TripCrudServlet");
            action = null;
		} else if ("insert".equals(action)) {
			Logger.info("got caver_id: " + request.getParameter("caver_id"));
			int caverId = Integer.parseInt(request.getParameter("caver_id"));
            String caveName = request.getParameter("cave_name");
            Timestamp startTime = Timestamp.valueOf(request.getParameter("start_time"));
            Timestamp endTime = Timestamp.valueOf(request.getParameter("end_time"));
            int groupSize = Integer.parseInt(request.getParameter("group_size"));
            double maxTripLength = Double.parseDouble(request.getParameter("max_trip_length"));
            
            dao.addTrip(caverId, caveName, startTime, endTime, groupSize, maxTripLength);
            
            Logger.info("New trip added: Cave = " + caveName);
            response.sendRedirect(request.getContextPath() + "/TripCrudServlet");
            action = null;
		}
		else {		
			//Read trips from db
            List<Trip> trips = dao.getTrips();
            HttpSession session = request.getSession();
            session.setAttribute("trips", trips);
            
            int caverId = Integer.parseInt(request.getParameter("caver_id"));
            session.setAttribute("caver_id", caverId);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("view_trips.jsp");
            dispatcher.forward(request, response);
            action = null;
		}
		
	}
	
//	private boolean isValid(String input, String regex) {
//		Pattern pattern = Pattern.compile(regex);
//		Matcher matcher = pattern.matcher(input);
//		return matcher.matches();
//	}

}
