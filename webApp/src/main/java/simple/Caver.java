package simple;
import java.lang.String;

public class Caver{

	private int caver_id;
	private String name;
	private String status;
	private String phone;

	public Caver(int a, String b, String c, String d) {
		this.caver_id = a;
		this.name = b;
		this.status = c;
		this.phone = d;
	}

	public Caver() {
		this.caver_id = -1;
		this.name = "";
		this.status = "";
		this.phone = "";
	}
	//getters
	public int getCaver_id() {
        return this.caver_id;
    }
	
	public String getName() {
	        return this.name;
	    }
	
	public String getStatus() {
        return this.status;
    }
	
	public String getPhone() {
        return this.phone;
    }
	//setters
	public void setCaver_id(int newCaver_id) {
		this.caver_id = newCaver_id;
	}
	public void setName(String newName) {
		this.name = newName;
	}
	
	public void setStatus(String newStatus) {
		this.status = newStatus;
	}
	
	public void setPhone(String newPhone) {
		this.phone = newPhone;
	}
	
	
	
}
