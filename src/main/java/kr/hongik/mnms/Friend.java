package kr.hongik.mnms;

import org.springframework.stereotype.Repository;

@Repository
public class Friend {

	private String memID;
	private String friendID;
	private String state;
	
	public String getMemID() {
		return memID;
	}
	public void setMemID(String memID) {
		this.memID = memID;
	}
	public String getFriendID() {
		return friendID;
	}
	public void setFriendID(String friendID) {
		this.friendID = friendID;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}

