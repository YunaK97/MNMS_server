package kr.hongik.mnms;

import java.sql.Date;

import org.springframework.stereotype.Repository;

@Repository
public class MembershipGroup extends Group {
	
	private int MID;
	private String president;
	private String payDay;
	private String payDuration;
	private int fee;
	private int notSubmit;
	private String accountNum;
	private int GID;
	
	public void setGID(int GID) {
		this.GID=GID;
	}
	public int getGID() {
		return GID;
	}
	public int getMID() {
		return MID;
	}
	public void setMID(int mID) {
		MID = mID;
	}
	public String getPresident() {
		return president;
	}
	public void setPresident(String president) {
		this.president = president;
	}
	public String getPayDay() {
		return payDay;
	}
	public void setPayDay(String mTime) {
		this.payDay = mTime;
	}
	public String getPayDuration() {
		return payDuration;
	}
	public void setPayDuration(String payDuration) {
		this.payDuration = payDuration;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public int getNotSubmit() {
		return notSubmit;
	}
	public void setNotSubmit(int notSubmit) {
		this.notSubmit = notSubmit;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	
}