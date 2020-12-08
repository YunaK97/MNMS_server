package kr.hongik.mnms.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.hongik.mnms.Account;
import kr.hongik.mnms.Group;
import kr.hongik.mnms.Member;
import kr.hongik.mnms.MemberFee;
import kr.hongik.mnms.MembershipGroup;
import kr.hongik.mnms.Participate;
import kr.hongik.mnms.Transaction;

public interface MembershipGroupMapper {
	MembershipGroup info(String GID);
	Group infoG(String GID);
	List<Member> memList(String GID);

	void deleteGroup(String GID);
	void deleteMembershipgroup(String GID);
	void deleteParticipate(Participate p);

	List<Member> search(String memID);
	void add(List<Participate> list);
	void newmgroup(MembershipGroup membership);
	void newgroup(String name);
	int getgid(String name);
	void add(Participate p);
	void newAccount(Account account);
	int getmid(String GID);
	void addfee(MemberFee mf);
	Integer getMembership(String GID);
	int checkPresident(MembershipGroup mem);
	MemberFee getMemberfee(MemberFee mf);
	int getTotal(String accountNum);
	void updateAccount(Account ac);
	void updateTrans1(Transaction trans);
	void updateTrans2(Transaction trans);
	void deleteMember(Participate part);
	Account getmTotal(int mID);
	void updateMembership(Account ac);
	void updateMemberfee(MemberFee mf);
	Account checkPW(String accountNum);
	List<Participate> getParticipate(String memID);
	String getMname(int gID);
	MembershipGroup getPayday(Integer mid);
	void checkUnpay(String mID);
	void getOut(HashMap<String,String> h);
	int notSubmit(HashMap<String,String> h);
	List<MemberFee> notSubmitP(String mID);
	void setdate(MembershipGroup m);
	void updatedate(HashMap<String, String> h);
	void updatepay(MembershipGroup m);
	void updatename(Group g);
	void updateNS(MembershipGroup m);
	boolean isvalidName(String name);
	boolean isValidGID(String gID);
	String getAccount(String memID);
	String getMemName(String memID);
	boolean isMembership(String GID);
}
