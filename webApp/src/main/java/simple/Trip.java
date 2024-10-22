package simple;
import java.lang.String;
import java.sql.Timestamp;

public class Trip {

	private int trip_id;
	private int caver_id;
	private String cave_name;
	private Timestamp start_time;
	private Timestamp end_time;
	private int group_size;
	private int max_trip_length;

	public Trip(int a, int b, String c, Timestamp d, Timestamp e, int f, int g) {
		this.trip_id = a;
		this.caver_id = b;
		this.cave_name = c;
		this.start_time = d;
		this.end_time = e;
		this.group_size = f;
		this.max_trip_length = g;
	}

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
	
	public int getMax_trip_length() {
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
	
	public void setMax_trip_length(int newMax_trip_length) {
        this.max_trip_length = newMax_trip_length;
    }
}
