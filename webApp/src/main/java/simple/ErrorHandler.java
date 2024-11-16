package simple;

import java.io.IOException;
import org.tinylog.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ErrorHandler
 */
@WebServlet("/ErrorHandler")
public class ErrorHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processError(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processError(request, response);
	}
	
	private void processError(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		//customize error message
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
		if (servletName == null) {
			servletName = "Unknown";
		}
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null) {
			requestUri = "Unknown";
		}     
		
		HttpSession session = request.getSession();
		session.setAttribute("error",  statusCode + "&nbsp;Server Error <br> Oops, something went wrong.");   
		request.getRequestDispatcher("/error_page.jsp").forward(request, response);
		Logger.info("Servlet " + servletName + " has thrown an exception " + throwable.getClass().getName() +
				" : " + throwable.getMessage() + " ,status code: " + statusCode);
	}
}
