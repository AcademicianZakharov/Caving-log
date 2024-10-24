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
import java.lang.String;


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

		DaoFile dao = new DaoFile();
		dao.testConnection();
		response.getWriter().write("Connection Test Completed");        

		List<Caver> cavers = new ArrayList<>();
		cavers = dao.getCavers();
		// build HTML code
		String htmlRespone = "<html>";
		for (Caver caver: cavers) {

			htmlRespone += "<h3>name: " + caver.getName() + "<br/>";
			htmlRespone += "id: " + caver.getCaver_id() + "</br>";
			htmlRespone += "phone: " + caver.getPhone() + "<br/>";
			htmlRespone += "status: " + caver.getStatus() + "</h3>";

		}

		htmlRespone += "</html>";
		response.getWriter().write(htmlRespone);
	}

	/**
	 * @see HttpServlet#doPost (HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger.info("doPost Method for the ControllerServlet");
	}

}

