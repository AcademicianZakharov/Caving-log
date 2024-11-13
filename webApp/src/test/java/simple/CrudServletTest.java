package simple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
/**
 * Unit tests for CrudServlet
 */
class CrudServletTest {
	private DaoFile mockDao;
	private List<Caver> mockCavers;
	
	/**
	 * mock objects and initial data
	 * @throws SQLException 
	 */
	@BeforeEach void setUp() throws SQLException {
		mockDao = mock(DaoFile.class); 
		mockCavers = new ArrayList<>();
		mockCavers.add(new Caver(1, "John", "Active", "123-456-7890"));
		when(mockDao.getCavers()).thenReturn(mockCavers);
		}
	/**
	 * tests doGet and checks that it forwards correctly
	 * @throws ServletException
	 * @throws IOException
	 */
    @Test
    void testDoGet() throws ServletException, IOException {
    	//setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        CrudServlet servlet = new CrudServlet(mockDao);
        //call doGet
        servlet.doGet(request, response);
        //verify
        verify(request).getRequestDispatcher("read_handler.jsp");
        verify(dispatcher).forward(request, response);
    }
	/**
	 * tests insertion of a caver and that it forwards correctly
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
    @Test
    void testInsertValidCaver() throws ServletException, IOException, SQLException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getParameter("action")).thenReturn("insert");
        when(request.getParameter("trip_id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("John");
        when(request.getParameter("status")).thenReturn("Active");
        when(request.getParameter("phone")).thenReturn("123-456-7890");
        
        CrudServlet servlet = new CrudServlet(mockDao);
        
        servlet.doPost(request, response);
        
        verify(request).getRequestDispatcher("read_handler.jsp");
        verify(dispatcher).forward(request, response);
    }
	/**
	 * tests invalid insertion of a caver
	 * @throws ServletException
	 * @throws IOException
	 */
    @Test
    void testInsertInvalidCaver() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(request.getParameter("action")).thenReturn("insert");
        when(request.getParameter("name")).thenReturn("John1");
        when(request.getParameter("phone")).thenReturn("invalid");
        when(response.getWriter()).thenReturn(writer);
        CrudServlet servlet = new CrudServlet(mockDao);
        
        servlet.doPost(request, response);
        
        assertEquals("Error: All fields (name, status, phone) are required and must be in the correct format.",
            stringWriter.toString().trim());
    }
	/**
	 * tests deletion of a caver and that it redirects correctly
	 * @throws ServletException
	 * @throws IOException
	 */
    @Test
    void testDeleteCaver() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("caver_id")).thenReturn("1");
        CrudServlet servlet = new CrudServlet(mockDao);
        
        servlet.doPost(request, response);
        
        verify(response).sendRedirect(anyString());
    }
	/**
	 * tests updating a caver and that it redirects correctly
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
    @Test
    void testUpdateCaver() throws ServletException, IOException, SQLException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("action")).thenReturn("update");
        when(request.getParameter("caver_id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("John");
        when(request.getParameter("status")).thenReturn("Active");
        when(request.getParameter("phone")).thenReturn("123-456-7890");
        
        CrudServlet servlet = new CrudServlet(mockDao);
        
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
    }
	/**
	 * tests invalid updating of a caver and that it redirects correctly
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
    @Test
    void testUpdateInvalidCaver() throws ServletException, IOException, SQLException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(request.getParameter("caver_id")).thenReturn("1");
        when(request.getParameter("action")).thenReturn("update");
        when(request.getParameter("name")).thenReturn("John1");
        when(request.getParameter("phone")).thenReturn("invalid");
        when(response.getWriter()).thenReturn(writer);
        CrudServlet servlet = new CrudServlet(mockDao);
        
        servlet.doPost(request, response);
        
        assertEquals("Error: All fields (name, status, phone) are required and must be in the correct format.",
            stringWriter.toString().trim());
    }
}