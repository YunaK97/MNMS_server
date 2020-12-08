 package kr.hongik.mnms.groupservice;

import java.util.Date;
import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.hongik.mnms.Account;
import kr.hongik.mnms.Friend;
import kr.hongik.mnms.Group;
import kr.hongik.mnms.Member;
import kr.hongik.mnms.MemberFee;
import kr.hongik.mnms.MembershipGroup;
import kr.hongik.mnms.Participate;
import kr.hongik.mnms.Transaction;
import kr.hongik.mnms.mapper.MembershipGroupMapper;


@Service
public class MembershipGroupService implements IMembershipGroupService {
	
	@Autowired
	private MembershipGroupMapper memshipMap;

	@Override
	public MembershipGroup info(String GID) {
		MembershipGroup memship = memshipMap.info(GID);
		Group group = memshipMap.infoG(GID);
		memship.setGroupName(group.getGroupName());
		return memship;
	}
	
	

	@Override
	public List<Member> memList(String GID) {
		List<Member> list = memshipMap.memList(GID);
		return list;
		
	}

	@Override
	public void deleteMembershipgroup(String memID, String GID) {
		Participate p=new Participate();
		int gid=Integer.parseInt(GID);
		p.setGID(gid);
		p.setMemID(memID);
		memshipMap.deleteParticipate(p);
		
	}


	@Override
	public void add(List<Participate> list) {
		memshipMap.add(list);
		
	}

	public void newMembergroup(String memID, String name,int money,int cnt,String account) {
		memshipMap.newgroup(name);
		int gid=memshipMap.getgid(name);
		MembershipGroup ms=new MembershipGroup();
		ms.setPresident(memID);
		ms.setGroupName(name);
		ms.setFee(money);
		ms.setNotSubmit(cnt);
		ms.setGID(gid);
		ms.setAccountNum(account);
		String mTime=DateTime.now().toString("yyyy-MM-dd");
		ms.setPayDay(mTime);
		ms.setPayDuration("month");
		memshipMap.newmgroup(ms);
	}

	public int getgid(String name) {
		int gid=memshipMap.getgid(name);
		return gid;
	}

	public void add(String memID, int gid) {
		Participate p=new Participate();
		p.setGID(gid);
		p.setMemID(memID);
		memshipMap.add(p);
	}

	public void newAccount(String passWD, String account) {
		Account ac=new Account();
		ac.setAccountBalance(0);
		ac.setAccountBank("AAAH");
		ac.setAccountNum(account);
		ac.setAccountPassword(passWD);
		memshipMap.newAccount(ac);
	}



	public int getmid(int gid) {
		String GID=Integer.toString(gid);
		int Mid=memshipMap.getmid(GID);
		return Mid;
	}

	@Override
	public void addfee(String memberID, int mid) {
		MemberFee mf=new MemberFee();
		mf.setMemID(memberID);
		mf.setMID(mid);
		memshipMap.addfee(mf);
	}


	@Override
	public Integer getMembership(String gID) {
		Integer mid=memshipMap.getMembership(gID);
		return mid;
	}

	@Override
	public boolean checkPresident(String memID, String gID) {
		boolean check=false;
		MembershipGroup mem=new MembershipGroup();
		mem.setPresident(memID);
		int gid=Integer.parseInt(gID);
		mem.setGID(gid);
		if(memshipMap.checkPresident(mem)==0) {
			check=true;
		}
		else
			check=false;
		return check;
	}

	@Override
	public boolean getMemberfee(String memID, int mid) {
		MemberFee mf=new MemberFee();
		mf.setMemID(memID);
		mf.setMID(mid);
		MemberFee r=memshipMap.getMemberfee(mf);
		if(r.isCheck()==false) 
			return true;
		else 
			return false;
	}

	@Override
	public void updateAccount(String accountNum, int money) {
		Account ac=new Account();
		ac.setAccountNum(accountNum);
		ac.setAccountBalance(money);
		memshipMap.updateAccount(ac);
	}
	@Override
	public int getTotal(String accountNum) {
		int total=memshipMap.getTotal(accountNum);
		return total;
	}

	@Override
	public void updateTrans1(String groupName, int mon, String accountNum) {
		Transaction trans=new Transaction();
		trans.setAccountNum(accountNum);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long systemTime=System.currentTimeMillis();
		String time1=format.format(systemTime);
		trans.setSince(time1);
		trans.setTransactHistory(groupName+"정기결제");
		trans.setTransactMoney(mon);
		memshipMap.updateTrans1(trans);
	}
	@Override
	public void updateTrans2(String groupName, int mon, String accountNum,Integer mid) {
		Transaction trans=new Transaction();
		trans.setAccountNum(accountNum);
		trans.setMID(mid);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long systemTime=System.currentTimeMillis();
		String time1=format.format(systemTime);
		trans.setSince(time1);
		trans.setTransactHistory(groupName+"정기결제");
		trans.setTransactMoney(mon);
		memshipMap.updateTrans2(trans);
	}
	@Override
	public void updateSend(String groupName, int mon, String accountNum,Integer mid) {
		Transaction trans=new Transaction();
		trans.setAccountNum(accountNum);
		trans.setMID(mid);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long systemTime=System.currentTimeMillis();
		String time1=format.format(systemTime);
		trans.setSince(time1);
		trans.setTransactHistory(groupName);
		trans.setTransactMoney(-mon);
		memshipMap.updateTrans2(trans);
	}
	@Override
	public void deleteMember(Participate part) {
		memshipMap.deleteMember(part);
	}


	@Override
	public Account getmTotal(int mID) {
		Account ac=memshipMap.getmTotal(mID);
		return ac;
	}


	@Override
	public void updateMembership(String accountNum, int total) {
		Account ac=new Account();
		ac.setAccountNum(accountNum);
		ac.setAccountBalance(total);
		memshipMap.updateMembership(ac);
		
	}


	@Override
	public void updateMemberfee(int mid, String memID) {
		MemberFee mf=new MemberFee();
		mf.setMID(mid);
		mf.setMemID(memID);
		memshipMap.updateMemberfee(mf);
	}


	@Override
	public boolean checkPW(String accountNum, String pw) {
		boolean check=false;
		Account ac=memshipMap.checkPW(accountNum);
		if(pw.equals(ac.getAccountPassword())) {
			check=true;
		}
		else check=false;
		return check;

	}


	@Override
	public List<Participate> getParticipate(String memID) {
		List<Participate> part=memshipMap.getParticipate(memID);
		return part;
	}


	@Override
	public String getMname(int gID) {
		String name=memshipMap.getMname(gID);
		return name;
	}


	@Override
	public boolean getPayday(Integer mid) {
		MembershipGroup mg=memshipMap.getPayday(mid);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		Date now=new Date();
		String current=dateFormat.format(now);
		Date nowday=null;Date end=null;
		try {
			nowday = dateFormat.parse(current);
			String payday=mg.getPayDay();
			end = dateFormat.parse(payday);	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("오늘날짜: "+nowday);
		System.out.println("동아리날짜:"+end);
		int result=nowday.compareTo(end);
		if(result>=0)
			return true;
		else
			return false;
	}
	@Override
	public void checkUnpay(String mID) {
		memshipMap.checkUnpay(mID);
		
	}


	@Override
	public void getOut(String mID, String notSubmit,String GID) {
		HashMap<String,String> h=new HashMap<String,String>();
		h.put("a",mID);
		h.put("b",notSubmit);
		h.put("c",GID);
		memshipMap.getOut(h);
		
	}


	@Override
	public int notSubmit(String memID, String mID) {
		HashMap<String,String> h=new HashMap<String,String>();
		h.put("memID",memID);
		h.put("MID",mID);
		int n=memshipMap.notSubmit(h);
		return n;
	}
	@Override
	public List<MemberFee> notSubmitP(String mID) {
		List<MemberFee> list = memshipMap.notSubmitP(mID);
		return list;
	}


	@Override
	public void setdate(String date, String mID,String duration) {
		MembershipGroup m=new MembershipGroup();
		m.setPayDay(date);
		m.setMID(Integer.parseInt(mID));
		m.setPayDuration(duration);
		memshipMap.setdate(m);
	}


	@Override
	public void updatedate(String duration, String mID) {
		HashMap<String,String> h=new HashMap<String,String>();
		h.put("duration",duration);
		h.put("MID",mID);
		memshipMap.updatedate(h);
	}


	@Override
	public void updatepay(String pay, String mID) {
		MembershipGroup m=new MembershipGroup();
		m.setFee(Integer.parseInt(pay)*10000);
		m.setMID(Integer.parseInt(mID));
		memshipMap.updatepay(m);
		
	}

	@Override
	public void updatename(String name, String gID) {
		Group g=new Group();
		g.setGID(Integer.parseInt(gID));
		g.setGroupName(name);
		memshipMap.updatename(g);
		
	}
	@Override
	public void updateNS(String ns, String mID) {
		
	MembershipGroup m=new MembershipGroup();
	m.setNotSubmit(Integer.parseInt(ns));
	m.setMID(Integer.parseInt(mID));
	memshipMap.updateNS(m);

	}


	@Override
	public boolean isvalidName(String name) {
		boolean result=memshipMap.isvalidName(name);
		return result;

	}


	@Override
	public boolean isValidGID(String gID) {
		boolean result=memshipMap.isValidGID(gID);
		return result;

	}


	@Override
	public String getAccount(String memID) {
		String accountNum=memshipMap.getAccount(memID);
		return accountNum;
	}


	@Override
	public String getMemName(String memID) {
		String name=memshipMap.getMemName(memID);
		return name;
	}


	@Override
	public boolean isMembership(String gid) {
		boolean result=memshipMap.isMembership(gid);
		return result;
	}
	
}
