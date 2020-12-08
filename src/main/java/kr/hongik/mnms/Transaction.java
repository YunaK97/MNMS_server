package kr.hongik.mnms;

import org.springframework.stereotype.Repository;

@Repository
public class Transaction {

	private int transactID;
	private String transactHistory;
	private int transactMoney;
	private String since;
	private String accountNum;
	private int DID;
	private int MID;

	public int getTransactID() {
		return transactID;
	}

	public void setTransactID(int transactID) {
		this.transactID = transactID;
	}

	public String getTransactHistory() {
		return transactHistory;
	}

	public void setTransactHistory(String transactHistory) {
		this.transactHistory = transactHistory;
	}

	public int getTransactMoney() {
		return transactMoney;
	}

	public void setTransactMoney(int transactMoney) {
		this.transactMoney = transactMoney;
	}


	public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public int getDID() {
		return DID;
	}

	public void setDID(int dID) {
		DID = dID;
	}

	public int getMID() {
		return MID;
	}

	public void setMID(int mID) {
		MID = mID;
	}

}
