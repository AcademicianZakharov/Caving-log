package simple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.nfis.db.ConnectionManager;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
class DaoFileTest {
    @Mock
    private ConnectionManager connectionManager;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    private DaoFile daoFile;
    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        //setup common mock behavior
        when(connectionManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        daoFile = new DaoFile(connectionManager);
    }
    @Test
    void testAddCaver() throws SQLException {
        String name = "John Doe";
        String status = "Active";
        String phone = "123-456-7890";
        daoFile.addCaver(name, status, phone);
        verify(preparedStatement).setString(1, name);
        verify(preparedStatement).setString(2, status);
        verify(preparedStatement).setString(3, phone);
        verify(preparedStatement).executeUpdate();
    }
    @Test
    void testGetCavers() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("caver_id")).thenReturn(1, 2);
        when(resultSet.getString("name")).thenReturn("John", "Jane");
        when(resultSet.getString("status")).thenReturn("Active", "Active");
        when(resultSet.getString("phone")).thenReturn("123", "456");
        List<Caver> cavers = daoFile.getCavers();
        assertEquals(2, cavers.size());
        assertEquals("John", cavers.get(0).getName());
        assertEquals("Jane", cavers.get(1).getName());
    }
    @Test
    void testAddTrip() throws SQLException {
        int caverId = 1;
        String caveName = "Test Cave";
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        Timestamp endTime = new Timestamp(System.currentTimeMillis() + 3600000);
        int groupSize = 5;
        double maxTripLength = 2.5;
        daoFile.addTrip(caverId, caveName, startTime, endTime, groupSize, maxTripLength);
        verify(preparedStatement).setInt(1, caverId);
        verify(preparedStatement).setString(2, caveName);
        verify(preparedStatement).setTimestamp(3, startTime);
        verify(preparedStatement).setTimestamp(4, endTime);
        verify(preparedStatement).setInt(5, groupSize);
        verify(preparedStatement).setDouble(6, maxTripLength);
        verify(preparedStatement).executeUpdate();
    }
    @Test
    void testGetTrips() throws SQLException {
        //arrange
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("trip_id")).thenReturn(1);
        when(resultSet.getInt("caver_id")).thenReturn(1);
        when(resultSet.getString("cave_name")).thenReturn("Test Cave");
        when(resultSet.getTimestamp("start_time")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(resultSet.getTimestamp("end_time")).thenReturn(new Timestamp(System.currentTimeMillis() + 3600000));
        when(resultSet.getInt("group_size")).thenReturn(5);
        when(resultSet.getDouble("max_trip_length")).thenReturn(2.5);
        List<Trip> trips = daoFile.getTrips();
        assertEquals(1, trips.size());
        assertEquals("Test Cave", trips.get(0).getCave_name());
    }
    @Test
    void testUpdateCaver() throws SQLException {
        int caverId = 1;
        String newName = "John Updated";
        String status = "Active";
        String phone = "123-456-7890";
        daoFile.updateCaver(caverId, newName, status, phone);
        verify(preparedStatement).setString(1, newName);
        verify(preparedStatement).setString(2, status);
        verify(preparedStatement).setString(3, phone);
        verify(preparedStatement).setInt(4, caverId);
        verify(preparedStatement).executeUpdate();
    }
    @Test
    void testDeleteCaver() throws SQLException {
        int caverId = 1;
        daoFile.deleteCaver(caverId);
        verify(preparedStatement).setInt(1, caverId);
        verify(preparedStatement).executeUpdate();
    }
}