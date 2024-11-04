package simple;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
 * Servlet implementation class CrudServelet
 */
//@WebServlet("/CrudServlet")
public class CrudServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CrudServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DaoFile dao = new DaoFile();
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		String action = request.getParameter("action");
		DaoFile dao = new DaoFile();
		PrintWriter out = response.getWriter();
		dao.testConnection();
		if ("delete".equals(action)) {
			// Delete action
			int caverId = Integer.parseInt(request.getParameter("caver_id"));
			dao.deleteCaver(caverId);
			Logger.info("Caver with ID " + caverId + " deleted.");
			response.sendRedirect(request.getContextPath() + "/CrudServlet");
			action = null;
			
		} else if ("update".equals(action)) {
			int caverId = Integer.parseInt(request.getParameter("caver_id"));
			String name = request.getParameter("name");
			String status = request.getParameter("status");
			String phone = request.getParameter("phone");
			//Check valid input
			String nameRegex = "^[A-Za-z\\s]{1,100}$";
			String statusRegex = "^[A-Za-z\\s]{1,100}$";
			String phoneRegex = "[0-9]{3}-[0-9]{3}-[0-9]{4}";
			
			if (!isValid(name, nameRegex) || !isValid(status, statusRegex) || !isValid(phone, phoneRegex)) {
				out.println("Error: All fields (name, status, phone) are required and must be in the correct format.");
				return;
			}
			Logger.info("Updating Caver: Name = " + name + ", Status = " + status + ", Phone = " + phone);
			dao.updateCaver(caverId,name, status, phone);
			Logger.info("Caver with ID " + caverId + " updated.");
			response.sendRedirect(request.getContextPath() + "/CrudServlet");
			action = null;
		} else if ("insert".equals(action)) {
			String name = request.getParameter("name");
			String status = request.getParameter("status");
			String phone = request.getParameter("phone");
			//Check valid input
			String nameRegex = "^[A-Za-z\\s]{1,100}$";
			String statusRegex = "^[A-Za-z\\s]{1,100}$";
			String phoneRegex = "[0-9]{3}-[0-9]{3}-[0-9]{4}";
			
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

	private boolean isValid(String input, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
}
