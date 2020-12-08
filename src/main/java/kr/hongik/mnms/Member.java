package kr.hongik.mnms;

import org.springframework.stereotype.Repository;

@Repository
public class Member {
	
	private String memID;
	private String memPW;
	private String memName;
	private String memEmail;
	private String accountNum;
	private String memSsn;
	private String phoneNumber;
	
	public String getMemID() {
		return memID;
	}
	public void setMemID(String memID) {
		this.memID = memID;
	}
	public String getMemPW() {
		return memPW;
	}
	public void setMemPW(String memPW) {
		this.memPW = memPW;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getMemEmail() {
		return memEmail;
	}
	public void setMemEmail(String memEmail) {
		this.memEmail = memEmail;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getMemSsn() {
		return memSsn;
	}
	public void setMemSsn(String memSsn) {
		this.memSsn = memSsn;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}