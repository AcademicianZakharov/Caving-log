package simple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.lang.String;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import org.nfis.db.ConnectionManager;
import org.nfis.db.TomcatConnectionManager;
import org.tinylog.Logger;
import java.util.ArrayList;
import java.util.List;

class DaoFileTest {
 
    @Test
    void testAddCaver() throws SQLException {
    	//mock objects
		ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);
		//ResultSet result = mock(ResultSet.class);
		//mock behaviour
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement("INSERT INTO cavers (name, status, phone) VALUES (?, ?, ?)"))
        .thenReturn(ps);
		//when(mockConnection.prepareStatement(any(String.class))).thenReturn(ps);
		//when(ps.executeQuery()).thenReturn(result);
		//when(result.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        //dao object
		DaoFile dao = new DaoFile(mockConnectionManager);
		//test data
        String name = "jack";
        String status = "safe";
        String phone = "123-456-7890";
        //method with data
        dao.addCaver(name, status, phone);
        //verify setstring, execute and close
        verify(ps).setString(1, name);
        verify(ps).setString(2, status);
        verify(ps).setString(3, phone);
        verify(ps).executeUpdate();
        verify(ps).close();
        verify(mockConnection).close();
    }
    
    @Test
    void testAddCaver_SQLException() throws SQLException {
		ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);
		//ResultSet result = mock(ResultSet.class);
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
        when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement("INSERT INTO cavers (name, status, phone) VALUES (?, ?, ?)"))
            .thenThrow(new SQLException());
        
        DaoFile dao = new DaoFile(mockConnectionManager);
        assertThrows(SQLException.class, () -> dao.addCaver("John Doe", "Active", "123-456-7890"));
    }

    @Test
    void testGetCavers() throws SQLException {
		ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);
		
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement("SELECT * FROM cavers"))
        .thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("caver_id")).thenReturn(1, 2);
        when(rs.getString("name")).thenReturn("John", "Jane");
        when(rs.getString("status")).thenReturn("Active", "Active");
        when(rs.getString("phone")).thenReturn("123", "456");
        
        DaoFile dao = new DaoFile(mockConnectionManager);
        List<Caver> cavers = dao.getCavers();
        assertEquals(2, cavers.size());
        assertEquals("John", cavers.get(0).getName());
        assertEquals("Jane", cavers.get(1).getName());
    }
    
    @Test
    void testUpdateCaver() throws SQLException { 	
		ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);
		
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement("UPDATE cavers SET name = ?, status = ?, phone = ? WHERE caver_id = ?")).thenReturn(ps);
        
        int caverId = 1;
        String newName = "John Updated";
        String status = "Active";
        String phone = "123-456-7890";
        
        DaoFile dao = new DaoFile(mockConnectionManager);

        dao.updateCaver(caverId, newName, status, phone);
        verify(ps).setString(1, newName);
        verify(ps).setString(2, status);
        verify(ps).setString(3, phone);
        verify(ps).setInt(4, caverId);
        verify(ps).executeUpdate();
    }
    
    @Test
    void testDeleteCaver() throws SQLException {
		ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);
		
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement("DELETE FROM cavers WHERE caver_id = ?")).thenReturn(ps);
        int caverId = 1;
        
        DaoFile dao = new DaoFile(mockConnectionManager);

        dao.deleteCaver(caverId);
        verify(ps).setInt(1, caverId);
        verify(ps).executeUpdate();
    }
    
    @Test
    void testAddTrip() throws SQLException {
		ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);
		
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement("INSERT INTO trips (caver_id, cave_name, start_time, end_time, group_size, max_trip_length) VALUES (?, ?, ?, ?, ?, ?)")).thenReturn(ps);
		
        int caverId = 1;
        String caveName = "Test Cave";
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        Timestamp endTime = new Timestamp(System.currentTimeMillis() + 3600000);
        int groupSize = 5;
        double maxTripLength = 2.5;
        
        DaoFile dao = new DaoFile(mockConnectionManager);

        
        dao.addTrip(caverId, caveName, startTime, endTime, groupSize, maxTripLength);
        verify(ps).setInt(1, caverId);
        verify(ps).setString(2, caveName);
        verify(ps).setTimestamp(3, startTime);
        verify(ps).setTimestamp(4, endTime);
        verify(ps).setInt(5, groupSize);
        verify(ps).setDouble(6, maxTripLength);
        verify(ps).executeUpdate();
    }
    
}