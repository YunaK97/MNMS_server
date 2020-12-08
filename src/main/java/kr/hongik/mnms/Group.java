package kr.hongik.mnms;

import org.springframework.stereotype.Repository;

@Repository
public class Group {
	
	private int GID;
	private String groupName;
	
	public int getGID() {
		return GID;
	}
	public void setGID(int gID) {
		GID = gID;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	      
}