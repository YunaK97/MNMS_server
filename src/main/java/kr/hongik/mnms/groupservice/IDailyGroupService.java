package kr.hongik.mnms.groupservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.hongik.mnms.CALCULATE;
import kr.hongik.mnms.DailyGroup;
import kr.hongik.mnms.Member;
import kr.hongik.mnms.Participate;


public interface IDailyGroupService {
	DailyGroup dutch(String DID);
	DailyGroup info(String GID);
	List<Member> memList(String GID);
	void deleteDailygroup(String memID,String GID);
	void newDailygroup(String memID, String dailyName);
	void add(Participate part);
	int getgid(String dailyName);
	void deleteMember(Participate part);
	void updateAccount(String accountNum, String money);
	void updateSend(String accountNum, String money, String history, String DID);
	void updateSend(String accountNum, String money, String nick);
	List<HashMap<String, String>> dutchpay(String dID);
	boolean checkPW(String accountNum, String encryptPW);
	void updateGet(String accountNum, String money, String mynick, String DID);
	void updatefriendAccount(String accountNum, String money);
	void updateGet2(String accountNum, String money, String mynick);
	boolean iscalculated(int DID);
	void calculate(CALCULATE cal);
	void updateCalculate(int did, String memID);
	boolean istransacted(int did, String memID);
	boolean checkdelete(int did);
	void deleteDaily(int gid);
	void deleteGroup(int gid);
	void deleteDailygroup(int gid);
	int getDID(String gID);
	boolean checkfriend(String friendID, String memID);
	boolean isvalidName(String dailyName);
}
