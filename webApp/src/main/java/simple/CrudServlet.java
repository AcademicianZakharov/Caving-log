package simple;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
 * Servlet CrudServlet
 * Does CRUD operations for cavers in the database
 */
public class CrudServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoFile dao;

	/**
	 * Default constructor.
	 */
	public CrudServlet() {
		this(new DaoFile());
	}
	
	//for tests
	public CrudServlet(DaoFile dao) {
		super();
		this.dao = dao;
	}

	/**
	 * handles HTTP GET method
	 * Reads a list of cavers from the db and sends it to read_handler.jsp
	 *
	 * @param request  The HttpServletRequest object 
	 * @param response The HttpServletResponse object
	 * @throws ServletException if a servlet error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dao.testConnection();
		List<Caver> cavers = dao.getCavers(); 
		//store the list of cavers in the session
		HttpSession session = request.getSession();
		session.setAttribute("cavers", cavers);
		//forward to the JSP page
		RequestDispatcher dispatcher = request.getRequestDispatcher("read_handler.jsp");
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
		//regex
		String nameRegex = "^[A-Za-z\\s]{1,100}$";//
		String statusRegex = "^[A-Za-z\\s]{1,100}$";//
		String phoneRegex = "[0-9]{3}-[0-9]{3}-[0-9]{4}$";//

		String action = request.getParameter("action");
		DaoFile dao = this.dao;
		PrintWriter out = response.getWriter();
		dao.testConnection();
		if ("delete".equals(action)) {
			//delete caver
			int caverId = Integer.parseInt(request.getParameter("caver_id"));
			dao.deleteCaver(caverId);
			Logger.info("Caver with ID " + caverId + " deleted.");
			response.sendRedirect(request.getContextPath() + "/CrudServlet");
			action = null;

		} else if ("update".equals(action)) {
			//update caver
			int caverId = Integer.parseInt(request.getParameter("caver_id"));
			String name = request.getParameter("name");
			String status = request.getParameter("status");
			String phone = request.getParameter("phone");

			if (!isValid(name, nameRegex) || !isValid(status, statusRegex) || !isValid(phone, phoneRegex)) {
				out.println("Error: All fields (name, status, phone) are required and must be in the correct format.");
				return;
			}
			Logger.info("Updating Caver: Name = " + name + ", Status = " + status + ", Phone = " + phone);
			try {
			dao.updateCaver(caverId,name, status, phone);
			}
			catch (Exception e) {
				// TODO: handle exceptiont
				e.printStackTrace();
			}
			Logger.info("Caver with ID " + caverId + " updated.");
			response.sendRedirect(request.getContextPath() + "/CrudServlet");
			action = null;
		} else if ("insert".equals(action)) {
			//add caver
			String name = request.getParameter("name");
			String status = request.getParameter("status");
			String phone = request.getParameter("phone");
			//Check valid input
			if (!isValid(name, nameRegex) || !isValid(status, statusRegex) || !isValid(phone, phoneRegex)) {
				out.println("Error: All fields (name, status, phone) are required and must be in the correct format.");
				return;
			}
			try {
				dao.addCaver(name, status, phone);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Logger.info("New caver added: " + name);
			//show the caver in a jsp page
			List<Caver> cavers = dao.getCavers(); 
			//store the list of cavers in the session
			HttpSession session = request.getSession();
			session.setAttribute("cavers", cavers);
			//forward to the JSP page
			RequestDispatcher dispatcher = request.getRequestDispatcher("read_handler.jsp");
			dispatcher.forward(request, response);
			action = null;
		}
		else {		
			//Read cavers from db
			List<Caver> cavers = dao.getCavers(); 
			//store the list of cavers in the session
			HttpSession session = request.getSession();
			session.setAttribute("cavers", cavers);
			//forward to the JSP page
			RequestDispatcher dispatcher = request.getRequestDispatcher("read_handler.jsp");
			dispatcher.forward(request, response);
			action = null;
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
