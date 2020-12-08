package kr.hongik.mnms;

import org.springframework.stereotype.Repository;

@Repository
public class DailyGroup extends Group {
	
	private int DID;

	public int getDID() {
		return DID;
	}

	public void setDID(int dID) {
		DID = dID;
	}

	
}