package simple;
import java.lang.String;

/**
 * Caver class
 * contains getter and setter methods for each field in Caver: caver_id, name, status and phone
 */
public class Caver{

	private int caver_id;
	private String name;
	private String status;
	private String phone;
	
	/**
	 * Caver constructor
	 * @param caver_id
	 * @param name
	 * @param status
	 * @param phone
	 */
	public Caver(int caver_id, String name, String status, String phone) {
		this.caver_id = caver_id;
		this.name = name;
		this.status = status;
		this.phone = phone;
	}
	/**
	 * Caver no-arg constructor
	 */
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
