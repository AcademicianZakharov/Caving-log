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
public class daoFile {

	
	String sql_select_all = "SELECT * FROM cavers INNER JOIN trips ON trips.caver_id = cavers.caver_id";
	
	private ConnectionManager connectionManager;
	public daoFile() {
		Logger.info("daoFile!");
		try {
			connectionManager = new TomcatConnectionManager("java:/comp/env", "jdbc/dbcp");
			Logger.info("Created new ConnectionManager");


		} catch(NamingException e) {
			connectionManager = null;
			Logger.error(e);
		}
	}

	public daoFile(ConnectionManager connectionManager) {

		this.connectionManager = connectionManager;
		Logger.info("existing connectionManager");

	}
	public void testConnection() {
        Connection connection = null;
        try {
            connection = connectionManager.getConnection();
            if (connection != null) {
            	Logger.info("db connection");
            }
        } catch (SQLException e) {
        	Logger.info("db connection error");
        } 
    }
	
	
	
    // General Read method
    public List<Object[]> readRecords(String table, String[] columns, String joinCondition) {
        List<Object[]> records = new ArrayList<>();
        String columnList = String.join(", ", columns);
        String sql = "SELECT " + columnList + " FROM " + table;
        if (joinCondition != null && !joinCondition.isEmpty()) {
            sql += " " + joinCondition;
        }
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] record = new Object[columns.length];
                for (int i = 0; i < columns.length; i++) {
                    record[i] = rs.getObject(columns[i]);
                }
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
	

}