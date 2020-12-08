package kr.hongik.mnms.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.hongik.mnms.Account;
import kr.hongik.mnms.CALCULATE;
import kr.hongik.mnms.DailyGroup;
import kr.hongik.mnms.Group;
import kr.hongik.mnms.Member;
import kr.hongik.mnms.Participate;
import kr.hongik.mnms.Transaction;

public interface DailyGroupMapper {
	DailyGroup dutch(String DID);
	DailyGroup info(String gID);
	Group infoG(String gID);
	List<Member> memList(String GID);

	void deleteParticipate(Participate p);
	void newgroup(String dailyName);
	void newdgroup(String dailyName);
	void add(Participate part);
	int getgid(String dailyName);
	//void deleteMember
	void deleteMember(Participate part);
	void updateAccount(HashMap<String, String> h);
	void updateSend(Transaction trans);
	List<HashMap<String, String>> dutchpay(String dID);
	Account checkPW(String accountNum);
	void updatefriendAccount(HashMap<String, String> h);
	void updateSend2(Transaction trans);
	boolean iscalculated(int dID);
	void calculate(CALCULATE cal);
	void updateCalculate(CALCULATE cal);
	boolean istransacted(CALCULATE cal);
	boolean checkdelete(int did);
	void deleteDaily(int gID);
	void deleteGroup(int gID);
	void deleteDailygroup(int gID);
	int getDID(String gID);
	boolean checkfriend(HashMap<String, String> h);
	boolean isvalidName(String dailyName);
}
