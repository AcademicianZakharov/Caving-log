package simple;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.tinylog.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControllerServlet extends HttpServlet {
	public static final String OPERATION_PARAM = "operation";
	public static final String FILTER_PARAM = "filter";
	public ControllerServlet() {
		super();
	}
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger.info("doGet Method for the ControllerServlet");
		String operation = (String) request.getParameter(OPERATION_PARAM);
		String filter = (String) request.getParameter(FILTER_PARAM);
		response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");

        daoFile dao = new daoFile();
        dao.testConnection();
        response.getWriter().write("Connection Test Completed");
     // Read all cavers and trips
        List<Object[]> results = dao.readRecords("cavers JOIN trips ON cavers.caver_id = trips.caver_id",
            new String[] {"cavers.caver_id", "cavers.name", "trips.cave_name"}, null);
        for (Object[] row : results) {
            Logger.info(Arrays.toString(row));
        }

        
	}

	/**
	 * @see HttpServlet#doPost (HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger.info("doPost Method for the ControllerServlet");
	}

}

