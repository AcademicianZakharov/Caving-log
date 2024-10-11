package simple;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.tinylog.Logger;

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
        //send response with register result
        response.getWriter().write("operation = " + operation);
        response.getWriter().write("<br>filter = " + filter);
	}

	/**
	 * @see HttpServlet#doPost (HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger.info("doPost Method for the ControllerServlet");
	}

}
//random
//When I uncommented lines 60 to 71, I was not able access the nfis website but 