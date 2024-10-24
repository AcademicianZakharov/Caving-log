package simple;

import java.io.IOException;
import java.io.PrintWriter;
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
		String formName = request.getParameter("name");
		DaoFile dao = new DaoFile();
		PrintWriter out = response.getWriter();
        dao.testConnection();
		if ("delete".equals(action)) {
			// Delete action
			int caverId = Integer.parseInt(request.getParameter("caver_id"));
			dao.deleteCaver(caverId);
			Logger.info("Caver with ID " + caverId + " deleted.");
			response.sendRedirect(request.getContextPath() + "/CrudServlet");
		} else if ("update".equals(action)) {
			//update action
			int caverId = Integer.parseInt(request.getParameter("caver_id"));
			String name = request.getParameter("name");
//			String status = request.getParameter("status");
//			String phone = request.getParameter("phone");
//			//Check for empty parameters
//			if (name == null || name.isEmpty() || status == null || status.isEmpty() || phone == null || phone.isEmpty()) {
//				out.println("Error: All fields (name, status, phone) are required.");
//				return;
//			}
//			Logger.info("Adding a new Caver: Name = " + name + ", Status = " + status + ", Phone = " + phone);
			dao.updateCaverName(caverId,name);
			Logger.info("Caver with ID " + caverId + " updated.");
			response.sendRedirect("read_handler.jsp");  
		} else if ("insert".equals(formName)) {
			//default action (could be adding a new caver, etc.)
			String name = request.getParameter("name");
			String status = request.getParameter("status");
			String phone = request.getParameter("phone");
			//Check for empty parameters
			if (name == null || name.isEmpty() || status == null || status.isEmpty() || phone == null || phone.isEmpty()) {
				out.println("Error: All fields (name, status, phone) are required.");
				return;
			}
			Logger.info("Adding a new Caver: Name = " + name + ", Status = " + status + ", Phone = " + phone);
			dao.addCaver(name, status, phone);
			Logger.info("New caver added: " + name);
			List<Caver> cavers = dao.getCavers(); 
			//store the list of cavers in the session
			HttpSession session = request.getSession();
			session.setAttribute("cavers", cavers);
			//forward to the JSP page
			RequestDispatcher dispatcher = request.getRequestDispatcher("read_handler.jsp");
			dispatcher.forward(request, response);
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
		}
		

	}

}
