package kr.hongik.mnms;

import org.springframework.stereotype.Repository;

@Repository
public class Participate {
	
	private String memID;
	private int GID;

	public String getMemID() {
		return memID;
	}

	public void setMemID(String memID) {
		this.memID = memID;
	}

	public int getGID() {
		return GID;
	}

	public void setGID(int gID) {
		GID = gID;
	}

}