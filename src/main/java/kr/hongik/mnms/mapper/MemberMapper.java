package kr.hongik.mnms.mapper;

import java.util.HashMap;
import java.util.List;

import kr.hongik.mnms.Account;
import kr.hongik.mnms.Friend;
import kr.hongik.mnms.Group;
import kr.hongik.mnms.Member;
import kr.hongik.mnms.Transaction;

public interface MemberMapper {
	Member memberLogin(Member member);
	void memberJoin(Member member);
	void memberJoinA(Account account);
	Member checkID(String memID);
	String checkPW(Member mem);
	void changePW(Member member);
	Member checkEmail(String memEmail);
	List<Group> dailyGroupList(String memID);
	List<Group> membershipGroupList(String memID);
	List<Member> requestedFriend(String memID);
	List<Member> showFriend(String memID);
//	Member friendResult(Map<String,String>);
	List<Transaction> listTransaction(String accountNum);
	void deleteFriend(Friend friend);
	String newFriend(String memID);
	List<Friend> friendcheck(Friend friend);
	void friendAdd(Friend friend);
	void updatefriend(Friend friend);
	void insertfriend(Friend friend);
	void resultreject(Friend friend);
	List<Transaction> showTransaction(String accountNum);
	void updateAccount(HashMap<String, String> h);
	void updateSend(Transaction trans);
}
