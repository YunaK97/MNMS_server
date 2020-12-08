package kr.hongik.mnms.groupservice;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import kr.hongik.mnms.Account;
import kr.hongik.mnms.CALCULATE;
import kr.hongik.mnms.DailyGroup;
import kr.hongik.mnms.Group;
import kr.hongik.mnms.Member;
import kr.hongik.mnms.Participate;
import kr.hongik.mnms.Transaction;
import kr.hongik.mnms.mapper.DailyGroupMapper;



@Service
public class DailyGroupService implements IDailyGroupService {
	
	@Autowired
	private DailyGroupMapper dailyMap;
	

	@Override
	public DailyGroup dutch(String DID) {
		DailyGroup daily = dailyMap.dutch(DID);
		return daily;
	}


	@Override
	public DailyGroup info(String GID) {
		DailyGroup daily = dailyMap.info(GID);
		Group group = dailyMap.infoG(GID);
		daily.setGID(group.getGID());
		daily.setGroupName(group.getGroupName());
		return daily;
	}
	
	@Override
	public List<Member> memList(String GID) {
		List<Member> list = dailyMap.memList(GID);
		return list;
		
	}

	@Override
	public void deleteDailygroup(String memID, String GID) {
		Participate p=new Participate();
		int gid=Integer.parseInt(GID);
		p.setGID(gid);
		p.setMemID(memID);	
		dailyMap.deleteParticipate(p);
	}


	public void newDailygroup(String memID, String dailyName) {
		dailyMap.newgroup(dailyName);
		dailyMap.newdgroup(dailyName);
		int gid=dailyMap.getgid(dailyName);
	}
	

	@Override
	public void add(Participate part) {
		dailyMap.add(part);
		
	}

	public int getgid(String dailyName) {
		int gid=dailyMap.getgid(dailyName);
		return gid;
	}


	@Override
	public void deleteMember(Participate part) {
		dailyMap.deleteMember(part);
	}

	@Override
	public void updateAccount(String accountNum, String money) {
		HashMap<String,String> h=new HashMap<String,String>();
		h.put("accountNum",accountNum);
		h.put("money",money);
		dailyMap.updateAccount(h);
	}

	@Override
	public void updateSend(String accountNum, String money, String history,String DID) {
		Transaction trans=new Transaction();
		trans.setAccountNum(accountNum);
		trans.setDID(Integer.parseInt(DID));
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long systemTime=System.currentTimeMillis();
		String time1=format.format(systemTime);
		trans.setSince(time1);
		trans.setTransactHistory(history);
		trans.setTransactMoney(-(Integer.parseInt(money)));
		dailyMap.updateSend(trans);
	}

	@Override
	public void updateSend(String accountNum, String money, String nick) {
		Transaction trans=new Transaction();
		trans.setAccountNum(accountNum);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long systemTime=System.currentTimeMillis();
		String time1=format.format(systemTime);
		trans.setSince(time1);
		trans.setTransactHistory(nick+"송금");
		trans.setTransactMoney(-(Integer.parseInt(money)));
		dailyMap.updateSend2(trans);
		
	}

	@Override
	public List<HashMap<String, String>> dutchpay(String dID) {
		
		List<HashMap<String,String>> l=dailyMap.dutchpay(dID);
		return l;
	}

	@Override
	public boolean checkPW(String accountNum, String encryptPW) {
		boolean check=false;
		Account ac=dailyMap.checkPW(accountNum);
		if(encryptPW.equals(ac.getAccountPassword())) {
			check=true;
		}
		else check=false;
		return check;

	}

	@Override
	public void updateGet(String accountNum, String money, String mynick, String DID) {
		Transaction trans=new Transaction();
		trans.setAccountNum(accountNum);
		trans.setDID(Integer.parseInt(DID));
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long systemTime=System.currentTimeMillis();
		String time1=format.format(systemTime);
		trans.setSince(time1);
		trans.setTransactHistory(mynick);
		trans.setTransactMoney(Integer.parseInt(money));
		dailyMap.updateSend(trans);
	}

	@Override
	public void updatefriendAccount(String accountNum, String money) {
		HashMap<String,String> h=new HashMap<String,String>();
		h.put("accountNum",accountNum);
		h.put("money",money);
		dailyMap.updatefriendAccount(h);
	}

	@Override
	public void updateGet2(String accountNum, String money, String mynick) {
		Transaction trans=new Transaction();
		trans.setAccountNum(accountNum);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long systemTime=System.currentTimeMillis();
		String time1=format.format(systemTime);
		trans.setSince(time1);
		trans.setTransactHistory(mynick);
		trans.setTransactMoney(Integer.parseInt(money));
		dailyMap.updateSend2(trans);
	}

	@Override
	public boolean iscalculated(int DID) {
		boolean result=dailyMap.iscalculated(DID);
		return result;
	}

	@Override
	public void calculate(CALCULATE cal) {
		dailyMap.calculate(cal);
	}

	@Override
	public void updateCalculate(int did, String memID) {
		CALCULATE cal=new CALCULATE();
		cal.setDID(did);
		cal.setmemID(memID);
		dailyMap.updateCalculate(cal);
	}

	@Override
	public boolean istransacted(int did, String memID) {
		CALCULATE cal=new CALCULATE();
		cal.setDID(did);
		cal.setmemID(memID);
		boolean result=dailyMap.istransacted(cal);
		return result;
	}

	@Override
	public boolean checkdelete(int did) {
		boolean result=dailyMap.checkdelete(did);
		return result;
	}

	@Override
	public void deleteDaily(int gid) {
		dailyMap.deleteDaily(gid);
	}

	@Override
	public void deleteGroup(int gid) {
		dailyMap.deleteGroup(gid);
	}

	@Override
	public void deleteDailygroup(int gid) {
		dailyMap.deleteDailygroup(gid);

	}

	@Override
	public int getDID(String gID) {
		int did=dailyMap.getDID(gID);
		return did;
	}

	@Override
	public boolean checkfriend(String friendID, String memID) {
		HashMap<String,String> h=new HashMap<String,String>();
		h.put("friendID",friendID);
		h.put("memID",memID);
		boolean result=dailyMap.checkfriend(h);
		return result;
	}

	@Override
	public boolean isvalidName(String dailyName) {
		boolean result=dailyMap.isvalidName(dailyName);
		return result;
	}

}
