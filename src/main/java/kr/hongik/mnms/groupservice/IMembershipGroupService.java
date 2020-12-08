package kr.hongik.mnms.groupservice;

import java.util.List;

import kr.hongik.mnms.Account;
import kr.hongik.mnms.Member;
import kr.hongik.mnms.MemberFee;
import kr.hongik.mnms.MembershipGroup;
import kr.hongik.mnms.Participate;

public interface IMembershipGroupService {
	MembershipGroup info(String GID);
	List<Member> memList(String GID);
	void deleteMembershipgroup(String memID,String GID);
	void add(List<Participate> list);
	void newMembergroup(String memID, String name,int money,int cnt,String account);
	int getgid(String name);
	void add(String memID, int gid);
	void newAccount(String passWD, String account);
	int getmid(int gid);
	void addfee(String memberID, int mid);
	Integer getMembership(String gID);
	boolean checkPresident(String memID, String gID);
	boolean getMemberfee(String memID, int mid);
	void updateAccount(String accountNum, int money);
	int getTotal(String accountNum);
	void updateTrans1(String groupName, int mon, String accountNum);
	void updateTrans2(String groupName, int mon, String accountNum,Integer mid);
	void deleteMember(Participate part);
	Account getmTotal(int mID);
	void updateMembership(String string, int total);
	void updateMemberfee(int mid, String memID);
	boolean checkPW(String accountNum, String pw);
	List<Participate> getParticipate(String memID);
	String getMname(int gID);
	boolean getPayday(Integer mid);
	void checkUnpay(String mID);
	void getOut(String mID, String notSubmit,String GID);
	int notSubmit(String memID, String mID);
	List<MemberFee> notSubmitP(String mID);
	void setdate(String date, String mID,String duration);
	void updatedate(String duration, String mID);
	void updatepay(String pay, String mID);
	void updatename(String name, String gID);
	void updateNS(String ns, String mID);
	void updateSend(String groupName, int mon, String accountNum, Integer mid);
	boolean isvalidName(String name);
	boolean isValidGID(String gID);
	String getAccount(String memID);
	String getMemName(String memID);
	boolean isMembership(String gid);
}
