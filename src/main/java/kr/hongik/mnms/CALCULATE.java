package kr.hongik.mnms;
import org.springframework.stereotype.Repository;

@Repository
public class CALCULATE {
	private int DID;
	private String memID;
	private int count;
	
	public int getDID() {
		return DID;
	}
	public void setDID(int DID) {
		this.DID = DID;
	}
	public String getmemID() {
		return memID;
	}
	public void setmemID(String memID) {
		this.memID = memID;
	}
	public int getcount() {
		return count;
	}
	public void setcount(int count) {
		this.count = count;
	}
}
