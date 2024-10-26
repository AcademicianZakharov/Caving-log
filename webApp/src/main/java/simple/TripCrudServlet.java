package simple;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


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
@WebServlet("/TripCrudServlet")
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
		PrintWriter out = response.getWriter();
		dao.testConnection();
		if ("delete".equals(action)) {
            int tripId = Integer.parseInt(request.getParameter("trip_id"));
            dao.deleteTrip(tripId);
            
            Logger.info("Trip with ID " + tripId + " deleted.");
            response.sendRedirect(request.getContextPath() + "/TripCrudServlet");
			
		} else if ("update".equals(action)) {
            int tripId = Integer.parseInt(request.getParameter("trip_id"));
            String caveName = request.getParameter("cave_name");
            Timestamp startTime = Timestamp.valueOf(request.getParameter("start_time"));
            Timestamp endTime = Timestamp.valueOf(request.getParameter("end_time"));
            int groupSize = Integer.parseInt(request.getParameter("group_size"));
            double maxTripLength = Double.parseDouble(request.getParameter("max_trip_length"));
            
            dao.updateTrip(tripId, caveName, startTime, endTime, groupSize, maxTripLength);
            
            Logger.info("Trip with ID " + tripId + " updated.");
            response.sendRedirect(request.getContextPath() + "/TripCrudServlet");
		} else if ("insert".equals(action)) {
            String caveName = request.getParameter("cave_name");
            Timestamp startTime = Timestamp.valueOf(request.getParameter("start_time"));
            Timestamp endTime = Timestamp.valueOf(request.getParameter("end_time"));
            int groupSize = Integer.parseInt(request.getParameter("group_size"));
            double maxTripLength = Double.parseDouble(request.getParameter("max_trip_length"));
            
            dao.addTrip(caveName, startTime, endTime, groupSize, maxTripLength);
            
            Logger.info("New trip added: Cave = " + caveName);
            response.sendRedirect(request.getContextPath() + "/TripCrudServlet");
		}
		else {		
			//Read trips from db
            List<Trip> trips = dao.getTrips();
            HttpSession session = request.getSession();
            session.setAttribute("trips", trips);
            RequestDispatcher dispatcher = request.getRequestDispatcher("view_trips.jsp");
            dispatcher.forward(request, response);
		}
		
	}

}
