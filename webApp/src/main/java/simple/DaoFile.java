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

	//sql strings
	String sql_select_all = "SELECT * FROM cavers";
	String sql_insert_caver = "INSERT INTO cavers (caver_id, name, status, phone) VALUES (?, ?, ?, ?)";
	
	int largest_key;
	//Logger.info("largest_key:"+ largest_key);
	
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
        } catch (SQLException e) {
        	Logger.info("db connection error");
        } 
    }
	
	  //Create new caver record in the db
	  public void addCaver(String name, String status, String phone) {
	      try (Connection connection = connectionManager.getConnection();
	           PreparedStatement ps = connection.prepareStatement(sql_insert_caver)) {
	          ps.setInt(1, largest_key);
	          ps.setString(2, name);
	          ps.setString(3, status);
	          ps.setString(4, phone);
	          ps.executeUpdate();
	      } catch (SQLException e) {
	          e.printStackTrace();
	      }
	  }
	
	//Read caver records from the db
	public List<Caver> getCavers() {	
		List<Caver> cavers = new ArrayList<>();
		try (Connection connection = connectionManager.getConnection();
		     PreparedStatement ps = connection.prepareStatement(sql_select_all);
		     ResultSet rs = ps.executeQuery()) {
		    while (rs.next()) {
		        Caver caver = new Caver(rs.getInt("caver_id"), rs.getString("name"),
		                                  rs.getString("status"), rs.getString("phone"));
		        cavers.add(caver);
		        if(caver.getCaver_id() >=largest_key) {
		        	largest_key = caver.getCaver_id();
		        	Logger.info("largest_key:"+ largest_key);
		        }
		    }
		    
		} catch (SQLException e) {
			Logger.info("db connection error");
			}
	  for (Caver caver: cavers) {
		  	Logger.info(caver.getName()+ " " + caver.getCaver_id() + " " + caver.getPhone() + " " + caver.getStatus());
			}
	  return cavers;
	}
	
	

	

}