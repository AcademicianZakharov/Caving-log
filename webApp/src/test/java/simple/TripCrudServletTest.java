package simple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nfis.db.ConnectionManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
class TripCrudServletTest {
	private DaoFile mockDao;
	private List<Trip> mockTrips;
	@BeforeEach void setUp() {
		mockDao = mock(DaoFile.class); 
		mockTrips = new ArrayList<>();
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        Timestamp endTime = new Timestamp(System.currentTimeMillis() + 3600000);
		mockTrips.add(new Trip(1, 1, "Othello Tunnels", startTime, endTime, 4, 20.5));
		when(mockDao.getTrips()).thenReturn(mockTrips); }
	
    @Test
    void testDoGet() throws ServletException, IOException {
    	
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        CrudServlet servlet = new CrudServlet(mockDao);
        // Execute
        servlet.doGet(request, response);
        // Verify
        verify(request).getRequestDispatcher("read_handler.jsp");
        verify(dispatcher).forward(request, response);
    }
    @Test
    void testInsertValidTrip() throws ServletException, IOException, SQLException {
        // change it
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
//        int caverId = 1;
//        String caveName = "Test Cave";
//        Timestamp startTime = new Timestamp(System.currentTimeMillis());
//        Timestamp endTime = new Timestamp(System.currentTimeMillis() + 3600000);
//        int groupSize = 5;
//        double maxTripLength = 2.5;
        
        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
        CrudServlet servlet = new CrudServlet(mockDao);
        // Execute
        servlet.doPost(request, response);
        // Verify
        verify(request).getRequestDispatcher("read_handler.jsp");
        verify(dispatcher).forward(request, response);
    }
    @Test
    void testInsertInvalidTrip() throws ServletException, IOException {
        // Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(request.getParameter("action")).thenReturn("insert");
        when(request.getParameter("name")).thenReturn("John1");
        when(request.getParameter("phone")).thenReturn("invalid");
        when(response.getWriter()).thenReturn(writer);
        CrudServlet servlet = new CrudServlet(mockDao);
        // Execute
        servlet.doPost(request, response);
        // Verify
        assertEquals("Error: All fields (name, status, phone) are required and must be in the correct format.",
            stringWriter.toString().trim());
    }
    @Test
    void testDeleteTrip() throws ServletException, IOException {
        // Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("trip_id")).thenReturn("1");
        CrudServlet servlet = new CrudServlet(mockDao);
        // Execute
        servlet.doPost(request, response);
        // Verify
        verify(response).sendRedirect(anyString());
    }
    @Test
    void testUpdateTrip() throws ServletException, IOException, SQLException {
        // Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("action")).thenReturn("update");
        when(request.getParameter("trip_id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("John");
        when(request.getParameter("status")).thenReturn("Active");
        when(request.getParameter("phone")).thenReturn("123-456-7890");
        
        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
		//DaoFile dao = new DaoFile(mockConnectionManager);
        CrudServlet servlet = new CrudServlet(mockDao);
        // Execute
        servlet.doPost(request, response);
        // Verify
        verify(response).sendRedirect(anyString());
    }
    @Test
    void testUpdateInvalidTrip() throws ServletException, IOException, SQLException {
        // Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(request.getParameter("trip_id")).thenReturn("1");
        when(request.getParameter("action")).thenReturn("update");
        when(request.getParameter("name")).thenReturn("John1");
        when(request.getParameter("phone")).thenReturn("invalid");
        when(response.getWriter()).thenReturn(writer);
        CrudServlet servlet = new CrudServlet(mockDao);
        

        // Execute
        servlet.doPost(request, response);
        // Verify
        assertEquals("Error: All fields (name, status, phone) are required and must be in the correct format.",
            stringWriter.toString().trim());
    }
}