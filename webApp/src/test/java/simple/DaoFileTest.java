package simple;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.lang.String;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.nfis.db.ConnectionManager;
import java.util.List;

/**
 * Unit tests for DaoFie
 */
class DaoFileTest {
 
	/**
	 * tests adding a caver to the db
	 * verifies if the parameters were added correctly to the preparedstatement and that it gets executed
	 * @throws SQLException
	 */
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

	/**
	 * tests getting cavers from the db
	 * asserts that the retrieved data is added to a list of cavers correctly
	 * @throws SQLException
	 */
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
        when(rs.getString("phone")).thenReturn("123-457-7890", "123-457-7891");
        
        DaoFile dao = new DaoFile(mockConnectionManager);
        List<Caver> cavers = dao.getCavers();
        assertEquals(2, cavers.size());
        assertEquals("John", cavers.get(0).getName());
        assertEquals("Jane", cavers.get(1).getName());
        assertEquals("Active", cavers.get(0).getStatus());
        assertEquals("Active", cavers.get(1).getStatus());
        assertEquals("123-457-7890", cavers.get(0).getPhone());
        assertEquals("123-457-7891", cavers.get(1).getPhone());
        verify(ps).close();
        verify(rs).close();
        verify(mockConnection).close();
    }
	/**
	 * tests update caver 
	 * verifies if the parameters were added correctly to the preparedstatement and that it gets executed
	 * @throws SQLException
	 */
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
    
	/**
	 * tests delete caver 
	 * verifies if the parameters were added correctly to the preparedstatement and that it gets executed
	 * @throws SQLException
	 */
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
    
	/**
	 * tests adding a trip 
	 * verifies if the parameters were added correctly to the preparedstatement and that it gets executed
	 * @throws SQLException
	 */
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
    /**
	 * tests getting trips from the db
	 * asserts that the retrieved data is added to a list of trips correctly
	 * @throws SQLException
	 */
    @Test
    void testGetTrips() throws SQLException {
    	ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);
		
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement("SELECT * FROM trips")).thenReturn(ps);
    	
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("trip_id")).thenReturn(1);
        when(rs.getInt("caver_id")).thenReturn(1);
        when(rs.getString("cave_name")).thenReturn("Othello tunnels");
        when(rs.getTimestamp("start_time")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(rs.getTimestamp("end_time")).thenReturn(new Timestamp(System.currentTimeMillis() + 3600000));
        when(rs.getInt("group_size")).thenReturn(5);
        when(rs.getDouble("max_trip_length")).thenReturn(2.5);
        
        DaoFile dao = new DaoFile(mockConnectionManager);
        
        List<Trip> trips = dao.getTrips();
        assertEquals(1, trips.size());
        assertEquals("Othello tunnels", trips.get(0).getCave_name());

        verify(ps).close();
        verify(rs).close();
        verify(mockConnection).close();
    }
    
	/**
	 * tests updating a trip 
	 * verifies if the parameters were added correctly to the preparedstatement and that it gets executed
	 * @throws SQLException
	 */
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
	/**
	 * tests deleting trip 
	 * verifies if the parameters were added correctly to the preparedstatement and that it gets executed
	 * @throws SQLException
	 */
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
    
	/**
	 * tests adding a caver to the db
	 * asserts that an SQLException is thrown
	 * @throws SQLException
	 */
    @Test
    void testAddCaver_SQLException() throws SQLException {
		ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
		//throws an exception
        when(mockConnection.prepareStatement("INSERT INTO cavers (name, status, phone) VALUES (?, ?, ?)")).thenThrow(new SQLException());
        
        DaoFile dao = new DaoFile(mockConnectionManager);
        assertThrows(SQLException.class, () -> dao.addCaver("John1", "Active1", "123-456-78"));
    }
    
	/**
	 * tests adding a trip to the db
	 * asserts that an SQLException is thrown
	 * @throws SQLException
	 */
    @Test
    void testAddTrip_SQLException() throws SQLException {
		ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
		//throws an exception
        when(mockConnection.prepareStatement("INSERT INTO trips (caver_id, cave_name, start_time, end_time, group_size, max_trip_length) VALUES (?, ?, ?, ?, ?, ?)")).thenThrow(new SQLException());
        
        DaoFile dao = new DaoFile(mockConnectionManager);
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        Timestamp endTime = new Timestamp(System.currentTimeMillis() + 3600000);
        assertThrows(SQLException.class, () -> dao.addTrip(1, "othello tunnels1", startTime, endTime, 4, 12.0));
    }
	/**
	 * tests update caver
	 * asserts that an SQLException is thrown
	 * @throws SQLException
	 */
    @Test
    void testUpdateCaver_SQLException() throws SQLException {
		ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
		//throws an exception
        when(mockConnection.prepareStatement("UPDATE cavers SET name = ?, status = ?, phone = ? WHERE caver_id = ?")).thenThrow(new SQLException());
        
        DaoFile dao = new DaoFile(mockConnectionManager);
        assertThrows(SQLException.class, () -> dao.updateCaver(1, "John1", "Active1", "123-456-78"));
    }
	/**
	 * tests updating a trip 
	 * asserts that an SQLException is thrown
	 * @throws SQLException
	 */
    @Test
    void testUpdateTrip_SQLException() throws SQLException {
		ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
		//throws an exception
        when(mockConnection.prepareStatement("UPDATE trips SET cave_name = ?, start_time = ?, end_time = ?, group_size = ?, max_trip_length = ? WHERE trip_id = ?")).thenThrow(new SQLException());
        
        DaoFile dao = new DaoFile(mockConnectionManager);
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        Timestamp endTime = new Timestamp(System.currentTimeMillis() + 3600000);
        assertThrows(SQLException.class, () -> dao.updateTrip(1, "othello tunnels1", startTime, endTime, 4, 12.0));
    }
	/**
	 * tests deleting a caver
	 * asserts that an SQLException is thrown
	 * @throws SQLException
	 */
    @Test
    void testDeleteCaver_SQLException() throws SQLException {
		ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
		//throws an exception
        when(mockConnection.prepareStatement("DELETE FROM cavers WHERE caver_id = ?")).thenThrow(new SQLException());
        
        DaoFile dao = new DaoFile(mockConnectionManager);
        assertThrows(SQLException.class, () -> dao.deleteCaver(1));
    }
	/**
	 * tests deleting a trip
	 * asserts that an SQLException is thrown
	 * @throws SQLException
	 */
    @Test
    void testDeleteTrip_SQLException() throws SQLException {
		ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
		//throws an exception
        when(mockConnection.prepareStatement("DELETE FROM trips WHERE trip_id = ?")).thenThrow(new SQLException());
        
        DaoFile dao = new DaoFile(mockConnectionManager);
        assertThrows(SQLException.class, () -> dao.deleteTrip(1));
    }
	/**
	 * tests getting cavers from db
	 * asserts that an SQLException is thrown
	 * @throws SQLException
	 */
    @Test
    void testGetCavers_SQLException() throws SQLException {
		ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
		//throws an exception
        when(mockConnection.prepareStatement("SELECT * FROM cavers")).thenThrow(new SQLException());
        
        DaoFile dao = new DaoFile(mockConnectionManager);
        assertThrows(SQLException.class, () -> dao.getCavers());
    }
	/**
	 * tests getting trips from db
	 * asserts that an SQLException is thrown
	 * @throws SQLException
	 */
    @Test
    void testGetTrips_SQLException() throws SQLException {
		ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
		Connection mockConnection = mock(Connection.class);
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
		//throws an exception
        when(mockConnection.prepareStatement("SELECT * FROM trips")).thenThrow(new SQLException());
        
        DaoFile dao = new DaoFile(mockConnectionManager);
        assertThrows(SQLException.class, () -> dao.getTrips());
    }

    
    
}