package simple;
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
public class DaoFile {


	//sql and prepared statements
	String sql_select_all_cavers = "SELECT * FROM cavers";
	String sql_insert_caver = "INSERT INTO cavers (name, status, phone) VALUES (?, ?, ?)";
	String sql_delete_caver = "DELETE FROM cavers WHERE caver_id = ?";
	String sql_update_caver = "UPDATE cavers SET name = ?, status = ?, phone = ? WHERE caver_id = ?";

	//remember to change it for a given caver_id
	String SQL_SELECT_TRIPS = "SELECT * FROM trips";
	String SQL_INSERT_TRIP = "INSERT INTO trips (caver_id, cave_name, start_time, end_time, group_size, max_trip_length) VALUES (?, ?, ?, ?, ?, ?)";
	String SQL_DELETE_TRIP = "DELETE FROM trips WHERE trip_id = ?";
	String SQL_UPDATE_TRIP = "UPDATE trips SET cave_name = ?, start_time = ?, end_time = ?, group_size = ?, max_trip_length = ? WHERE trip_id = ?";

	//establish connection to the db
	private ConnectionManager connectionManager;
	public DaoFile() {
		//Logger.info("DaoFile!");
		try {
			connectionManager = new TomcatConnectionManager("java:/comp/env", "jdbc/dbcp");
			//Logger.info("Created new ConnectionManager");


		} catch(NamingException e) {
			connectionManager = null;
			Logger.error(e);
		}
	}
	//checking for connection to the db
	public DaoFile(ConnectionManager connectionManager) {

		this.connectionManager = connectionManager;
		Logger.info("existing connectionManager");

	}
	public void testConnection() {
		Connection connection = null;
		try {
			connection = connectionManager.getConnection();
			if (connection != null) {
				Logger.info("----------------db connection-------------");
			}
			connection.close();
		} catch (SQLException e) {
			Logger.info("db connection error");
		} 
	}

	//add new caver record in the db
	public void addCaver(String name, String status, String phone) throws SQLException{
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = connectionManager.getConnection();
			ps = connection.prepareStatement(sql_insert_caver);
			ps.setString(1, name);
			ps.setString(2, status);
			ps.setString(3, phone);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null) ps.close();
				if (connection != null) connection.close();
			} catch (SQLException e){
			}
		}
	}
	//add new trip
	public void addTrip(int caverId, String caveName, Timestamp startTime, Timestamp endTime, int groupSize, double maxTripLength) {
		try (Connection connection = connectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL_INSERT_TRIP)) {
			ps.setInt(1, caverId);
			ps.setString(2, caveName);
			ps.setTimestamp(3, startTime);
			ps.setTimestamp(4, endTime);
			ps.setInt(5, groupSize);
			ps.setDouble(6, maxTripLength);
			ps.executeUpdate();
			Logger.info("Trip added successfully");
		} catch (SQLException e) {
			Logger.error("Error adding trip", e);
		} 
	}

	//Read caver records from the db
	public List<Caver> getCavers() {	
		List<Caver> cavers = new ArrayList<>();
		try (Connection connection = connectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql_select_all_cavers);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Caver caver = new Caver(rs.getInt("caver_id"), rs.getString("name"),
						rs.getString("status"), rs.getString("phone"));
				cavers.add(caver);

			}
			connection.close();
		} catch (SQLException e) {
			Logger.info("db connection error");
		}
		return cavers;
	}

	//read all trips
	public List<Trip> getTrips() {
		List<Trip> trips = new ArrayList<>();
		try (Connection connection = connectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL_SELECT_TRIPS);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Trip trip = new Trip(rs.getInt("trip_id"), rs.getInt("caver_id"), rs.getString("cave_name"),
						rs.getTimestamp("start_time"), rs.getTimestamp("end_time"),
						rs.getInt("group_size"), rs.getDouble("max_trip_length"));
				trips.add(trip);
			}
		} catch (SQLException e) {
			Logger.error("Error retrieving trips", e);
			e.printStackTrace();
		}
		return trips;
	}


	//update caver name
	public void updateCaver(int caverId, String newName, String status, String phone) {
		try (Connection connection = connectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql_update_caver)) {
			ps.setString(1, newName);
			ps.setString(2, status);
			ps.setString(3, phone);
			ps.setInt(4, caverId);
			ps.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//update a trip
	public void updateTrip(int tripId, String caveName, Timestamp startTime, Timestamp endTime, int groupSize, double maxTripLength) {
		try (Connection connection = connectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_TRIP)) {
			ps.setString(1, caveName);
			ps.setTimestamp(2, startTime);
			ps.setTimestamp(3, endTime);
			ps.setInt(4, groupSize);
			ps.setDouble(5, maxTripLength);
			ps.setInt(6, tripId);
			ps.executeUpdate();
			Logger.info("Trip with ID " + tripId + " updated successfully");
		} catch (SQLException e) {
			Logger.error("Error updating trip", e);
		}
	}

	// delete a caver by caver_id
	public void deleteCaver(int caver_id) {
		try (Connection connection = connectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql_delete_caver)) {
			ps.setInt(1, caver_id);
			ps.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//delete a trip
	public void deleteTrip(int tripId) {
		try (Connection connection = connectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL_DELETE_TRIP)) {
			ps.setInt(1, tripId);
			ps.executeUpdate();
			Logger.info("Trip with ID " + tripId + " deleted successfully");
		} catch (SQLException e) {
			Logger.error("Error deleting trip", e);
		}
	}


}