package simple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CrudServletTest {
    private CrudServlet servletUnderTest;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private DaoFile dao;

    @Test
    void testDoGet() throws ServletException, IOException, SQLException {
		ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
		PreparedStatement ps = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);
        when(mockConnection.prepareStatement("SELECT * FROM cavers"))
        .thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        servletUnderTest = new CrudServlet();
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        dispatcher = Mockito.mock(RequestDispatcher.class);
        
    	DaoFile dao = new DaoFile(mockConnectionManager);
        List<Caver> cavers = new ArrayList<>();
        cavers.add(new Caver(1, "John", "Active", "123-456-7890"));
        when(dao.getCavers()).thenReturn(cavers);
        when(request.getSession()).thenReturn(session);
        
        servletUnderTest.doGet(request, response);

        verify(session).setAttribute("cavers", cavers);
        verify(request).getRequestDispatcher("read_handler.jsp");
        verify(dispatcher).forward(request, response);
    }
//    @Test
//    void testDoPost_Delete() throws IOException, ServletException {
//        when(request.getParameter("action")).thenReturn("delete");
//        when(request.getParameter("caver_id")).thenReturn("1");
//        when(request.getContextPath()).thenReturn("/myapp");
//
//        servletUnderTest.doPost(request, response);
//
//        verify(dao).deleteCaver(1);
//        verify(response).sendRedirect("/myapp/CrudServlet");
//    }
//    @Test
//    void testDoPost_Update() throws IOException, ServletException {
//        // Arrange
//        when(request.getParameter("action")).thenReturn("update");
//        when(request.getParameter("caver_id")).thenReturn("1");
//        when(request.getParameter("name")).thenReturn("John");
//        when(request.getParameter("status")).thenReturn("Active");
//        when(request.getParameter("phone")).thenReturn("123-456-7890");
//        when(request.getContextPath()).thenReturn("/myapp");
//
//        servletUnderTest.doPost(request, response);
//
//        verify(dao).updateCaver(1, "John Doe", "Active", "123-456-7890");
//        verify(response).sendRedirect("/myapp/CrudServlet");
//    }
//    @Test
//    void testDoPost_Insert() throws IOException, ServletException, SQLException {
//        // Arrange
//        when(request.getParameter("action")).thenReturn("insert");
//        when(request.getParameter("name")).thenReturn("John");
//        when(request.getParameter("status")).thenReturn("Active");
//        when(request.getParameter("phone")).thenReturn("123-456-7890");
//        when(request.getSession()).thenReturn(session);
//        when(dao.getCavers()).thenReturn(new ArrayList<>());
//        when(request.getRequestDispatcher("read_handler.jsp")).thenReturn(dispatcher);
//        
//        servletUnderTest.doPost(request, response);
//        
//        verify(dao).addCaver("John", "Active", "123-456-7890");
//        verify(session).setAttribute("cavers", new ArrayList<>());
//        verify(request).getRequestDispatcher("read_handler.jsp");
//        verify(dispatcher).forward(request, response);
//    }
}