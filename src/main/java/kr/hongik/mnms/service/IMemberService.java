package kr.hongik.mnms.service;

import java.util.List;

import kr.hongik.mnms.Friend;
import kr.hongik.mnms.Group;
import kr.hongik.mnms.Member;
import kr.hongik.mnms.Transaction;

public interface IMemberService {
		Member memberLogin(String memID, String memPW);
		void memberJoin(String memID, String memPW, String memName, String memEmail, String memSsn, 
				String accountNum, String accountBank, String accountBalance, String accountPassword, String phoneNumber);
		Member checkID(String memID);
		String checkPW(Member mem);
		void changePW(String memID, String memPW);
		Member checkEmail(String memEmail);
		List<Group> dailyGroupList(String memID);
		List<Group> membershipGroupList(String memID);
		List<Member> requestedFriend(String memID);
		List<Member> showFriend(String memID);
		void deleteFriend(String memID,String friendID);
		String newFriend(String memID);
		List<Friend> friendcheck(String memID, String friendID);
		void friendAdd(String memID, String friendID);
		void resultfriend(String myID, String memID);
		void resultreject(String myID,String memID);
		List<Transaction> showTransaction(String accountNum);
//		Member friendResult(String[] friend,String memID,String TAG);
		void updateAccount(String accountNum, int mon);
		void updateSend(String accountNum, String history1, int mon);
}
