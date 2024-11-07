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

/**
 * Data Access Object
 * methods for CRUD operations for cavers and trips in the database
 */
public class DaoFile {

	private ConnectionManager connectionManager;
	/**
	 * default constructor
	 */
	DaoFile() {
		try {
			connectionManager = new TomcatConnectionManager("java:/comp/env", "jdbc/dbcp");
			Logger.info("Created new ConnectionManager");

		} catch(NamingException e) {
			connectionManager = null;
			Logger.error(e);
		}
	}
	/**
	 * constructor with existing connectionManager
	 */
	DaoFile(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
		Logger.info("existing connectionManager");
	}
	/**
	 * attempts to get a connection with connectionManager
	 */
	void testConnection() {
		if(connectionManager != null) {
			Connection connection = null;
			try {
				connection = connectionManager.getConnection();
				if (connection != null) {
					Logger.info("----------------db connection---------------");
				}
				connection.close();
			} catch (SQLException e) {
				Logger.info("db connection error");
			} 
		}
		else {
			Logger.error("Connection manager is null");
		}
	}

	/**
	 * adds a caver to cavers table in the db
	 *
	 * @param name to be put in the preparedstament 
	 * @param status to be put in the preparedstament 
	 * @param phone to be put in the preparedstament 
	 * @throws SQLexception if caver could not be added
	 */
	void addCaver(String name, String status, String phone) throws SQLException{
		String INSERT_CAVER = "INSERT INTO cavers (name, status, phone) VALUES (?, ?, ?)";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = connectionManager.getConnection();
			ps = connection.prepareStatement(INSERT_CAVER);
			ps.setString(1, name);
			ps.setString(2, status);
			ps.setString(3, phone);
			ps.executeUpdate();
		} catch (SQLException e) {
			Logger.error("Error adding caver", e);
			throw e;
		} finally {
			try {
				if (ps != null) ps.close();
				if (connection != null) connection.close();
			} catch (SQLException e){
			}
		}
	}
	
	/**
	 * adds a trip to trips table in the db
	 *
	 * @param caverID to be put in the preparedstament 
	 * @param caveName to be put in the preparedstament 
	 * @param startTime to be put in the preparedstament
	 * @param endTime to be put in the preparedstament 
	 * @param groupSize to be put in the preparedstament 
	 * @exception maxTripLength to be put in the preparedstament  
	 */
	void addTrip(int caverId, String caveName, Timestamp startTime, Timestamp endTime, int groupSize, double maxTripLength) {
		String INSERT_TRIP = "INSERT INTO trips (caver_id, cave_name, start_time, end_time, group_size, max_trip_length) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection connection = connectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(INSERT_TRIP)) {
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

	/**
	 * reads cavers from cavers table in the db
	 * @return List<Caver> 
	 */
	List<Caver> getCavers() {
		String SELECT_CAVERS = "SELECT * FROM cavers";
		List<Caver> cavers = new ArrayList<>();
		try (Connection connection = connectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(SELECT_CAVERS);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Caver caver = new Caver(rs.getInt("caver_id"), rs.getString("name"),
						rs.getString("status"), rs.getString("phone"));
				cavers.add(caver);
			}
			
		} catch (SQLException e) {
			Logger.error("Error reading cavers", e);
		}
		return cavers;
	}

	/**
	 * reads trips from the trips table in the db
	 * @return List<Trip> 
	 */
	List<Trip> getTrips() {
		String SELECT_TRIPS = "SELECT * FROM trips";
		List<Trip> trips = new ArrayList<>();
		try (Connection connection = connectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(SELECT_TRIPS);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Trip trip = new Trip(rs.getInt("trip_id"), rs.getInt("caver_id"), rs.getString("cave_name"),
						rs.getTimestamp("start_time"), rs.getTimestamp("end_time"),
						rs.getInt("group_size"), rs.getDouble("max_trip_length"));
				trips.add(trip);
			}
		} catch (SQLException e) {
			Logger.error("Error reading trips", e);
		}
		return trips;
	}

	/**
	 * updates a caver in the cavers table in the db
	 *
	 * @param caverId to be put in the preparedstament 
	 * @param name to be put in the preparedstament 
	 * @param status to be put in the preparedstament 
	 * @param phone to be put in the preparedstament 
	 * @exception SQLexception if caver could not be updated
	 */
	void updateCaver(int caverId, String newName, String status, String phone) throws SQLException{
		String UPDATE_CAVER = "UPDATE cavers SET name = ?, status = ?, phone = ? WHERE caver_id = ?";
		try (Connection connection = connectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE_CAVER)) {
			ps.setString(1, newName);
			ps.setString(2, status);
			ps.setString(3, phone);
			ps.setInt(4, caverId);
			ps.executeUpdate();
		} catch (SQLException e) {
			Logger.error("Error updating caver", e);
		}
	}

	/**
	 * updates a trip in the trips table in the db
	 *
	 * @param tripID to be put in the preparedstament 
	 * @param caveName to be put in the preparedstament 
	 * @param startTime to be put in the preparedstament
	 * @param endTime to be put in the preparedstament 
	 * @param groupSize to be put in the preparedstament 
	 * @param maxTripLength to be put in the preparedstament  
	 * @exception SQLexception if trip could not be updated
	 */
	void updateTrip(int tripId, String caveName, Timestamp startTime, Timestamp endTime, int groupSize, double maxTripLength) {
		String UPDATE_TRIP = "UPDATE trips SET cave_name = ?, start_time = ?, end_time = ?, group_size = ?, max_trip_length = ? WHERE trip_id = ?";
		try (Connection connection = connectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE_TRIP)) {
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

	/**
	 * deletes a caver from the cavers table in the db
	 *
	 * @param caverId to be put in the preparedstament 
	 * @exception SQLexception if caver could not be deleted
	 */
	void deleteCaver(int caverId) {
		String DELETE_CAVER = "DELETE FROM cavers WHERE caver_id = ?";
		try (Connection connection = connectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(DELETE_CAVER)) {
			ps.setInt(1, caverId);
			ps.executeUpdate();
		} catch (SQLException e) {
			Logger.error("Error deleting caver", e);
		}
	}

	/**
	 * deletes a trip from the trips table in the db
	 *
	 * @param tripId to be put in the preparedstament 
	 * @exception SQLexception if trip could not be deleted
	 */
	void deleteTrip(int tripId) {
		String DELETE_TRIP = "DELETE FROM trips WHERE trip_id = ?";
		try (Connection connection = connectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(DELETE_TRIP)) {
			ps.setInt(1, tripId);
			ps.executeUpdate();
			Logger.info("Trip with ID " + tripId + " deleted successfully");
		} catch (SQLException e) {
			Logger.error("Error deleting trip", e);
		}
	}
}