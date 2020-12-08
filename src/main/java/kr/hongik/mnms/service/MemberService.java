package kr.hongik.mnms.service;


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.hongik.mnms.Account;
import kr.hongik.mnms.Friend;
import kr.hongik.mnms.Group;
import kr.hongik.mnms.Member;
import kr.hongik.mnms.Transaction;
import kr.hongik.mnms.mapper.MemberMapper;


@Service
public class MemberService implements IMemberService {

	@Autowired
	private MemberMapper memMap;

	@Override
	public Member memberLogin(String memID, String memPW) {
		
		Member mem = new Member();
		
		mem.setMemID(memID);
		mem.setMemPW(memPW);
		
		Member member = memMap.memberLogin(mem);

		return member;
	}

	@Override
	public void memberJoin(String memID, String memPW, String memName, String memEmail, String memSsn, String accountNum,
			String accountBank, String accountBalance, String accountPassword,String phoneNumber) {
		
		Member member = new Member();
		member.setMemID(memID);
		member.setMemPW(memPW);
		member.setMemName(memName);
		member.setMemEmail(memEmail);
		member.setAccountNum(accountNum);
		member.setMemSsn(memSsn);
		member.setPhoneNumber(phoneNumber);
		
		int balance = Integer.parseInt(accountBalance);
		
		Account account = new Account();
		account.setAccountNum(accountNum);
		account.setAccountBank(accountBank);
		account.setAccountBalance(balance);
		account.setAccountPassword(accountPassword);

		memMap.memberJoinA(account);
		memMap.memberJoin(member);
		
	}

	@Override
	public Member checkID(String memID) {
		Member member = memMap.checkID(memID);
		return member;
	}
	public String checkPW(Member mem) {
		String pw = memMap.checkPW(mem);
		return pw;
	}
	
	public void changePW(String memID, String memPW) {
		Member member = new Member();
		member.setMemID(memID);
		member.setMemPW(memPW);
		memMap.changePW(member);
	}
	@Override
	public Member checkEmail(String memEmail) {
		Member member = memMap.checkEmail(memEmail);
		return member;
	}
	
	@Override
	public List<Group> dailyGroupList(String memID) {
		List<Group> list = memMap.dailyGroupList(memID);
		return list;
	}
	
	@Override
	public List<Group> membershipGroupList(String memID) {
		List<Group> list = memMap.membershipGroupList(memID);
		return list;
	}

	@Override
	public List<Member> requestedFriend(String memID) {
		List<Member> list = memMap.requestedFriend(memID);
		return list;
	}

	@Override
	public List<Member> showFriend(String memID) {
		List<Member> list = memMap.showFriend(memID);
		return list;
	}

	@Override
	public void deleteFriend(String memID,String friendID) {
		Friend friend=new Friend();
		friend.setMemID(memID);
		friend.setFriendID(friendID);
		Friend friend2=new Friend();
		friend2.setMemID(friendID);
		friend2.setFriendID(memID);
		memMap.deleteFriend(friend);
		memMap.deleteFriend(friend2);
	}

	@Override
	public String newFriend(String memID) {
		String name=memMap.newFriend(memID);
		return name;	
	}

	public List<Friend> friendcheck(String memID, String friendID) {
		Friend friend=new Friend();
		friend.setMemID(memID);
		friend.setFriendID(friendID);
		List<Friend> list=memMap.friendcheck(friend);
		return list;
	}

	public void friendAdd(String memID, String friendID) {
		Friend friend=new Friend();
		friend.setMemID(memID);
		friend.setFriendID(friendID);
		memMap.friendAdd(friend);
	}

	public void resultfriend(String myID, String memID) {
		Friend friend=new Friend();
		friend.setMemID(memID);
		friend.setFriendID(myID);
		memMap.updatefriend(friend);
		memMap.insertfriend(friend);
	}

	public void resultreject(String myID, String memID) {
		Friend friend=new Friend();
		friend.setMemID(memID);
		friend.setFriendID(myID);
		memMap.resultreject(friend);	
	}

	public List<Transaction> showTransaction(String accountNum) {
		List<Transaction> trans=memMap.showTransaction(accountNum);
		return trans;
	}
	@Override
	public void updateAccount(String accountNum, int mon) {
		HashMap<String,String> h=new HashMap<String,String>();
		h.put("accountNum",accountNum);
		String money=Integer.toString(mon);
		h.put("money",money);
		memMap.updateAccount(h);
	
	}
	@Override
	public void updateSend(String accountNum, String history1, int mon) {
		Transaction trans=new Transaction();
		trans.setAccountNum(accountNum);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long systemTime=System.currentTimeMillis();
		String time1=format.format(systemTime);
		trans.setSince(time1);
		trans.setTransactHistory(history1);
		trans.setTransactMoney(mon);
		memMap.updateSend(trans);

	}


//	@Override
//	public Member friendResult(String[] friend, String memID, String TAG) {
//		HashMap<String,String> friend=new HashMap<String, String>();
//		friend.put("friend",)
//		Member member=memMap.friendResult();
//		return null;
//	}
//	

}