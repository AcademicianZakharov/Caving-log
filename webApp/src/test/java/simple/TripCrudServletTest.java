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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
/**
 * Unit tests for TripCrudServlet
 */
class TripCrudServletTest {
	private DaoFile mockDao;
	private List<Trip> mockTrips;
	/**
	 * mock objects and initial data
	 * @throws SQLException 
	 */
	@BeforeEach void setUp() throws SQLException {
		mockDao = mock(DaoFile.class); 
		mockTrips = new ArrayList<>();
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        Timestamp endTime = new Timestamp(System.currentTimeMillis() + 3600000);
		mockTrips.add(new Trip(1, 1, "Othello Tunnels", startTime, endTime, 4, 20.5));
		when(mockDao.getTrips()).thenReturn(mockTrips); 
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
        TripCrudServlet servlet = new TripCrudServlet(mockDao);
        //call doGet
        servlet.doGet(request, response);
        //verify
        verify(request).getRequestDispatcher("view_trips.jsp");
        verify(dispatcher).forward(request, response);
    }
	/**
	 * tests insertion of a trip and that it forwards correctly
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
    @Test
    void testInsertValidTrip() throws ServletException, IOException, SQLException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        
        when(request.getParameter("action")).thenReturn("insert");
        when(request.getParameter("trip_id")).thenReturn("1");
        when(request.getParameter("caver_id")).thenReturn("1");
        when(request.getParameter("cave_name")).thenReturn("Othello tunnels");
        when(request.getParameter("start_time")).thenReturn("2024-10-29 12:00:00");
        when(request.getParameter("end_time")).thenReturn("2024-10-30 12:00:00");
        when(request.getParameter("group_size")).thenReturn("4");
        when(request.getParameter("max_trip_length")).thenReturn("24");

        TripCrudServlet servlet = new TripCrudServlet(mockDao);
        
        servlet.doPost(request, response);
        
        verify(response).sendRedirect(anyString());
    }
	/**
	 * tests invalid insertion of a trip
	 * @throws ServletException
	 * @throws IOException
	 */
    @Test
    void testInsertInvalidTrip() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(request.getParameter("action")).thenReturn("insert");
        when(request.getParameter("caver_id")).thenReturn("1");
        when(request.getParameter("cave_name")).thenReturn("Othello tunnels1");
        when(request.getParameter("start_time")).thenReturn("2024-10-29 12:00:00a");
        when(request.getParameter("end_time")).thenReturn("2024-10-30 12:00:00b");
        when(request.getParameter("group_size")).thenReturn("4");
        when(request.getParameter("max_trip_length")).thenReturn("24");
        when(response.getWriter()).thenReturn(writer);
        
        TripCrudServlet servlet = new TripCrudServlet(mockDao);
        
        servlet.doPost(request, response);
        
        assertEquals("Error: Invalid input format.",
            stringWriter.toString().trim());
    }
	/**
	 * tests deletion of a trip and that it redirects correctly
	 * @throws ServletException
	 * @throws IOException
	 */
    @Test
    void testDeleteTrip() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("trip_id")).thenReturn("1");
        TripCrudServlet servlet = new TripCrudServlet(mockDao);
        
        servlet.doPost(request, response);
        
        verify(response).sendRedirect(anyString());
    }
	/**
	 * tests updating a trip and that it redirects correctly
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
    @Test
    void testUpdateTrip() throws ServletException, IOException, SQLException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        
        when(request.getParameter("action")).thenReturn("update");
        when(request.getParameter("trip_id")).thenReturn("1");
        when(request.getParameter("caver_id")).thenReturn("1");
        when(request.getParameter("cave_name")).thenReturn("Othello tunnels");
        when(request.getParameter("start_time")).thenReturn("2024-10-29 12:00:00");
        when(request.getParameter("end_time")).thenReturn("2024-10-30 12:00:00");
        when(request.getParameter("group_size")).thenReturn("4");
        when(request.getParameter("max_trip_length")).thenReturn("24");

        TripCrudServlet servlet = new TripCrudServlet(mockDao);
        
        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }
	/**
	 * tests invalid updating of a trip and that it redirects correctly
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
    @Test
    void testUpdateInvalidTrip() throws ServletException, IOException, SQLException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        //testing bad input cave_name with a number
        when(request.getParameter("action")).thenReturn("update");
        when(request.getParameter("trip_id")).thenReturn("1%");
        when(request.getParameter("caver_id")).thenReturn("1#");
        when(request.getParameter("cave_name")).thenReturn("Othello tunnels1");
        when(request.getParameter("start_time")).thenReturn("2024-10-29 12:00:00a");
        when(request.getParameter("end_time")).thenReturn("2024-10-29 16:00:00b");
        when(request.getParameter("group_size")).thenReturn("4");
        when(request.getParameter("max_trip_length")).thenReturn("24");
        when(response.getWriter()).thenReturn(writer);
        TripCrudServlet servlet = new TripCrudServlet(mockDao);
        
        servlet.doPost(request, response);

        assertEquals("Error: Invalid input format.",
            stringWriter.toString().trim());
    }
}