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
		//mock behaviour
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement("INSERT INTO cavers (name, status, phone) VALUES (?, ?, ?)")).thenReturn(ps);
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
        verify(ps).close();
        verify(rs).close();
        verify(mockConnection).close();
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
        verify(ps).close();
        verify(mockConnection).close();
        
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
        verify(ps).close();
        verify(mockConnection).close();
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
        verify(ps).close();
        verify(mockConnection).close();
    }
    
    @Test
    void testGetTrips() throws SQLException {
    	ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);
		
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement("SELECT * FROM trips")).thenReturn(ps);
    	
        //arrange
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("trip_id")).thenReturn(1);
        when(rs.getInt("caver_id")).thenReturn(1);
        when(rs.getString("cave_name")).thenReturn("Test Cave");
        when(rs.getTimestamp("start_time")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(rs.getTimestamp("end_time")).thenReturn(new Timestamp(System.currentTimeMillis() + 3600000));
        when(rs.getInt("group_size")).thenReturn(5);
        when(rs.getDouble("max_trip_length")).thenReturn(2.5);
        
        DaoFile dao = new DaoFile(mockConnectionManager);
        
        List<Trip> trips = dao.getTrips();
        assertEquals(1, trips.size());
        assertEquals("Test Cave", trips.get(0).getCave_name());
        verify(ps).close();
        verify(rs).close();
        verify(mockConnection).close();
    }
    
    @Test
    void testUpdateTrip() throws SQLException {
    	ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
    	Connection mockConnection = mock(Connection.class);
    	PreparedStatement ps = mock(PreparedStatement.class);
    	
    	when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
    	when(mockConnection.prepareStatement("UPDATE trips SET cave_name = ?, start_time = ?, end_time = ?, group_size = ?, max_trip_length = ? WHERE trip_id = ?")).thenReturn(ps);
    	
    	int tripID = 1;
    	String caveName = "Othello Tunnels";
    	Timestamp startTime = new Timestamp(System.currentTimeMillis());
    	Timestamp endTime = new Timestamp(System.currentTimeMillis() + 3600000);
        int groupSize = 5;
        double maxTripLength = 2.5;
        
        DaoFile dao = new DaoFile(mockConnectionManager);
        
        dao.updateTrip(tripID, caveName, startTime, endTime, groupSize, maxTripLength);
        verify(ps).setString(1, caveName);
        verify(ps).setTimestamp(2, startTime);
        verify(ps).setTimestamp(3, endTime);
        verify(ps).setInt(4, groupSize);
        verify(ps).setDouble(5, maxTripLength);
        verify(ps).setInt(6, tripID);
        verify(ps).executeUpdate();
        verify(ps).close();
    	
    }
    
    @Test
    void testDeleteTrip() throws SQLException {
		ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);
		
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement("DELETE FROM trips WHERE trip_id = ?")).thenReturn(ps);
        int tripId = 1;
        
        DaoFile dao = new DaoFile(mockConnectionManager);

        dao.deleteTrip(tripId);
        verify(ps).setInt(1, tripId);
        verify(ps).executeUpdate();
        verify(ps).close();
        verify(mockConnection).close();
    }
    
}