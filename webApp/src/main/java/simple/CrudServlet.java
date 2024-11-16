package simple;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.tinylog.Logger;
/**
 * Servlet CrudServlet
 * Does CRUD operations for cavers in the database
 */
public class CrudServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoFile dao;
	
	//regex for input validation
	private static final String NAME_STATUS_REGEX = "^[A-Za-z\\s]{1,100}$";
	private static final String PHONE_REGEX = "[0-9]{3}-[0-9]{3}-[0-9]{4}$";
	/**
	 * Default constructor.
	 */
	public CrudServlet() {
		this(new DaoFile());
	}
	/**
	 * for running CrudServletTest
	 */
	public CrudServlet(DaoFile dao) {
		super();
		this.dao = dao;
	}
	/**
	 * handles HTTP GET method
	 * calls retireveCavers to get a list of cavers from the db and send it to read_handler.jsp
	 *
	 * @param request  The HttpServletRequest object 
	 * @param response The HttpServletResponse object
	 * @throws ServletException if a servlet error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			dao.testConnection();
			retrieveCavers(request, response);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
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
		try {
			String action = request.getParameter("action");
			dao.testConnection();
			if ("delete".equals(action)) {
				deleteCaver(request, response);
			} else if ("update".equals(action)) {
				updateCaver(request, response);
			} else if ("insert".equals(action)) {
				insertCaver(request, response);
			} else {
				retrieveCavers(request, response);
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
	/**
	 * deletes caver from db
	 *
	 * @param request  The HttpServletRequest object
	 * @param response The HttpServletResponse object
	 * @throws ServletException if a servlet error occurs
	 * @throws IOException if an I/O error occurs
	 * @throws SQLException if SQL error occurs
	 */	
	private void deleteCaver(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, SQLException {
		int caverId = Integer.parseInt(request.getParameter("caver_id"));
		dao.deleteCaver(caverId);
		Logger.info("Caver with ID " + caverId + " deleted.");
		response.sendRedirect(request.getContextPath() + "/CrudServlet");
	}
	/**
	 * validates attributes and updates caver in the db
	 *
	 * @param request  The HttpServletRequest object
	 * @param response The HttpServletResponse object
	 * @throws ServletException if a servlet error occurs
	 * @throws IOException if an I/O error occurs
	 * @throws SQLException if SQL error occurs
	 */	
	private void updateCaver(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
		String name = request.getParameter("name");
		String status = request.getParameter("status");
		String phone = request.getParameter("phone");
		if (!isValid(name, NAME_STATUS_REGEX) || !isValid(status, NAME_STATUS_REGEX) || !isValid(phone, PHONE_REGEX)) {
			response.getWriter().println("Error: All fields (name, status, phone) are required and must be in the correct format.");
			return;
		}
		int caverId = Integer.parseInt(request.getParameter("caver_id"));
		dao.updateCaver(caverId, name, status, phone);
		Logger.info("Caver with ID " + caverId + " updated.");
		response.sendRedirect(request.getContextPath() + "/CrudServlet");
	}
	/**
	 * validates attributes and inserts caver to the db
	 *
	 * @param request  The HttpServletRequest object
	 * @param response The HttpServletResponse object
	 * @throws ServletException if a servlet error occurs
	 * @throws IOException if an I/O error occurs
	 * @throws SQLException if SQL error occurs
	 */	
	private void insertCaver(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
		String name = request.getParameter("name");
		String status = request.getParameter("status");
		String phone = request.getParameter("phone");
		if (!isValid(name, NAME_STATUS_REGEX) || !isValid(status, NAME_STATUS_REGEX) || !isValid(phone, PHONE_REGEX)) {
			response.getWriter().println("Error: All fields (name, status, phone) are required and must be in the correct format.");
			return;
		}
		dao.addCaver(name, status, phone);
		Logger.info("New caver added: " + name);
		retrieveCavers(request, response);
	}
	/**
	 * gets cavers from the db, forwards to read_handler.jsp to display it
	 *
	 * @param request  The HttpServletRequest object
	 * @param response The HttpServletResponse object
	 * @throws ServletException if a servlet error occurs
	 * @throws IOException if an I/O error occurs
	 * @throws SQLException if SQL error occurs
	 */	
	private void retrieveCavers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		try {
			List<Caver> cavers = dao.getCavers();
			HttpSession session = request.getSession();
			session.setAttribute("cavers", cavers);
			RequestDispatcher dispatcher = request.getRequestDispatcher("read_handler.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException e) {
			Logger.error("Error loading cavers from database", e);
			throw new ServletException(e);
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