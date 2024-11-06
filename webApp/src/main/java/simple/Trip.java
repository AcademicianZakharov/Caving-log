package simple;
import java.lang.String;
import java.sql.Timestamp;
/**
 * Trip class
 * contains getter and setter methods for each field in Trip: trip_id, caver_id, cave_name, start_time, end_time, group_size, max_trip_length
 */
public class Trip {

	private int trip_id;
	private int caver_id;
	private String cave_name;
	private Timestamp start_time;
	private Timestamp end_time;
	private int group_size;
	private double max_trip_length;

	/**
	 * Trip constructor
	 * @param trip_id
	 * @param caver_id
	 * @param cave_name
	 * @param start_time
	 * @param end_time
	 * @param group_size
	 * @param max_trip_length
	 */
	public Trip(int trip_id, int caver_id, String cave_name, Timestamp start_time, Timestamp end_time, int group_size, double max_trip_length) {
		this.trip_id = trip_id;
		this.caver_id = caver_id;
		this.cave_name = cave_name;
		this.start_time = start_time;
		this.end_time = end_time;
		this.group_size = group_size;
		this.max_trip_length = max_trip_length;
	}
	/**
	 * Trip no-arg constructor
	 */
	public Trip() {
		this.trip_id = -1;
		this.caver_id = -1;
		this.cave_name = "";
		this.start_time = null;
		this.end_time = null;
		this.group_size = 0;
		this.max_trip_length = 0;
	}

	//getters
	public int getTrip_id() {
		return this.trip_id;
	}
	public int getCaver_id() {
		return this.caver_id;
	}

	public String getCave_name() {
		return this.cave_name;
	}

	public Timestamp getStart_time() {
		return this.start_time;
	}

	public Timestamp getEnd_time() {
		return this.end_time;
	}

	public int getGroup_size() {
		return this.group_size;
	}

	public double getMax_trip_length() {
		return this.max_trip_length;
	}


	//setters
	public void setTrip_id(int newTrip_id) {
		this.trip_id = newTrip_id;
	}

	public void setCaver_id(int newCaver_id) {
		this.caver_id = newCaver_id;
	}
	public void setCave_name(String newCave_name) {
		this.cave_name = newCave_name;
	}

	public void setStart_time(Timestamp newStart_time) {
		this.start_time = newStart_time;
	}

	public void  setEnd_time(Timestamp newEnd_time) {
		this.end_time = newEnd_time;
	}

	public void setGroup_size(int newGroup_size) {
		this.group_size = newGroup_size;
	}

	public void setMax_trip_length(double newMax_trip_length) {
		this.max_trip_length = newMax_trip_length;
	}
}
