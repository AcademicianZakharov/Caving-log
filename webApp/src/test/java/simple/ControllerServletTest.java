//package simple;
//
//
//import org.tinylog.Logger;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.atLeast;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//
//import org.junit.jupiter.api.Test;
//
//public class ControllerServletTest {
//
//	/**
//	 * An example of testing the Servlet
//	 * @throws ServletException
//	 * @throws IOException
//	 */
//	@Test
//    void exampleDoGetTest() throws ServletException, IOException {
//        
//		HttpServletRequest request = mock(HttpServletRequest.class);       
//        HttpServletResponse response = mock(HttpServletResponse.class);    
//        final String OPERATION_VALUE = "query";
//        final String FILTER_VALUE= "query";
//        when(request.getParameter(ControllerServlet.OPERATION_PARAM)).thenReturn(OPERATION_VALUE);
//        when(request.getParameter(ControllerServlet.FILTER_PARAM)).thenReturn(FILTER_VALUE);
//
//        StringWriter stringWriter = new StringWriter();
//        PrintWriter writer = new PrintWriter(stringWriter);
//        when(response.getWriter()).thenReturn(writer);
//
//        new ControllerServlet().doGet(request, response);
//
//        verify(request, atLeast(1)).getParameter(ControllerServlet.OPERATION_PARAM); 
//        writer.flush(); // it may not have been flushed yet...
//        assertTrue(stringWriter.toString().contains(FILTER_VALUE));
//        Logger.info("ControllerServlet test passed.");
//    }
//
//}
//
