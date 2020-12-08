package kr.hongik.mnms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.hongik.mnms.Friend;
import kr.hongik.mnms.Group;
import kr.hongik.mnms.Member;
import kr.hongik.mnms.Transaction;
import kr.hongik.mnms.groupservice.SHAPasswordEncoder;
import kr.hongik.mnms.groupservice.TransactionService;
import kr.hongik.mnms.service.MemberService;

@RestController
@RequestMapping(value = "/member", method = { RequestMethod.GET, RequestMethod.POST })
public class MemberController {

	@Autowired
	private MemberService memServ;

	@Autowired
	private TransactionService transServ;

	@ResponseBody
	@RequestMapping(value = "/login", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> login(@RequestParam("memID") String memID, @RequestParam("memPW") String memPW) {

		System.out.println("memID:" + memID);
		System.out.println("memPW:" + memPW);

		String encryptPW = SHAPasswordEncoder.encrypt(memPW);
		System.out.println("암호화 후 비밀번호:" + encryptPW);

		Member member = memServ.memberLogin(memID, encryptPW);

		Map<String, String> result = new HashMap<String, String>();
		if (member != null) {
			result.put("memName", member.getMemName());
			result.put("memID", member.getMemID());
			result.put("memEmail", member.getMemEmail());
			result.put("accountNum", member.getAccountNum());
			result.put("memSsn", member.getMemSsn());
			result.put("phoneNumber", member.getPhoneNumber());
			result.put("success", "true");
		} else {
			result.put("success", "false");
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/join", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> join(@RequestParam("memID") String memID, @RequestParam("memPW") String memPW,
			@RequestParam("memName") String memName, @RequestParam("memEmail") String memEmail,
			@RequestParam("memSsn") String memSsn, @RequestParam("accountNum") String accountNum,
			@RequestParam("accountBank") String accountBank, @RequestParam("accountBalance") String accountBalance,
			@RequestParam("accountPassword") String accountPassword, @RequestParam("phoneNumber") String phoneNumber) {

		String encryptPW = SHAPasswordEncoder.encrypt(memPW);
		System.out.println("암호화 후 비밀번호:" + encryptPW);
		String encryptAP=SHAPasswordEncoder.encrypt(accountPassword);
		memServ.memberJoin(memID, encryptPW, memName, memEmail, memSsn, accountNum, accountBank, accountBalance,
				encryptAP, phoneNumber);

		Map<String, String> result = new HashMap<String, String>();
		result.put("success", "true");
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/pay", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> pay(@RequestParam("history1") String history1, @RequestParam("accountNum") String accountNum,@RequestParam("history2") String history2,
			@RequestParam("friendAccount") String friendAccount, @RequestParam("money") String money) {
		System.out.println("++++pay++++");
	      
		Map<String, String> result = new HashMap<String, String>();
		int mon=Integer.parseInt(money);
		memServ.updateAccount(accountNum,-mon);
		memServ.updateAccount(friendAccount, mon);
		memServ.updateSend(accountNum,history1,-mon);
		memServ.updateSend(friendAccount,history2, mon);
		result.put("success", "true");
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/checkID", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> checkID(@RequestParam("memID") String memID) {

		Map<String, String> result = new HashMap<String, String>();
		System.out.println("체크 ID: " + memID);

		Member member = memServ.checkID(memID);
		if (member != null) {
			result.put("success", "false");

		} else {
			result.put("success", "true");
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/checkPW", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> checkPW(@RequestParam("memPW") String memPW, @RequestParam("memID") String memID) {

		System.out.println("입력한 비밀번호:" + memPW);
		String encryptPW = SHAPasswordEncoder.encrypt(memPW);
		Member mem = new Member();
		mem.setMemID(memID);
		mem.setMemPW(encryptPW);
		String pw = memServ.checkPW(mem);

		Map<String, String> result = new HashMap<String, String>();

		if (pw != null) {
			result.put("success", "true");
		} else {
			result.put("success", "false");
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/changePW", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> changePW(@RequestParam("memID") String memID, @RequestParam("memPW") String memPW) {

		System.out.println("=======changePW=======");
		System.out.println("입력한 비밀번호:" + memPW);
		System.out.println("입력한 ID:" + memID);
		String encryptPW = SHAPasswordEncoder.encrypt(memPW);
		memServ.changePW(memID, encryptPW);

		Map<String, String> result = new HashMap<String, String>();
		result.put("success", "true");

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/checkEmail", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> checkEmail(@RequestParam("memEmail") String memEmail) {

		Map<String, String> result = new HashMap<String, String>();

		Member member = memServ.checkEmail(memEmail);
		if (member != null) {
			result.put("success", "false");
		} else {
			result.put("success", "true");
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/dailyGroupList", produces = "application/json; charset=UTF-8", method = {
			RequestMethod.GET, RequestMethod.POST })
	public Map<String, String> dailyGroupList(@RequestParam("memID") String memID) {

		Map<String, String> result = new HashMap<String, String>();

		List<Group> list = memServ.dailyGroupList(memID);

		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i).getGID());
			System.out.print(list.get(i).getGroupName());
			result.put("GID" + i, String.valueOf((list.get(i).getGID())));
			result.put("groupName" + i, list.get(i).getGroupName());
		}

		result.put("dailyGroupSize", String.valueOf(list.size()));
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/membershipGroupList", produces = "application/json; charset=UTF-8", method = {
			RequestMethod.GET, RequestMethod.POST })
	public Map<String, String> membershipGroupList(@RequestParam("memID") String memID) {

		Map<String, String> result = new HashMap<String, String>();

		List<Group> list = memServ.membershipGroupList(memID);

		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i).getGID());
			System.out.print(list.get(i).getGroupName());
			result.put("GID" + i, String.valueOf((list.get(i).getGID())));
			result.put("groupName" + i, list.get(i).getGroupName());
		}

		result.put("membershipGroupSize", String.valueOf(list.size()));
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/requestedFriend", produces = "application/json; charset=UTF-8", method = {
			RequestMethod.GET, RequestMethod.POST })
	public Map<String, String> requestedFriend(@RequestParam("memID") String memID) {
		Map<String, String> result = new HashMap<String, String>();
		List<Member> member = memServ.requestedFriend(memID);
		if (!member.isEmpty()) {
			for (int i = 0; i < member.size(); i++) {
				System.out.print(member.get(i).getMemID());
				System.out.print(member.get(i).getMemName());
				result.put("memID" + i, String.valueOf((member.get(i).getMemID())));
				result.put("memName" + i, member.get(i).getMemName());
			}
			result.put("showFriendSize", String.valueOf(member.size()));
			result.put("success", "true");
		} else {
			result.put("success", "false");
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/friendResult", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> friendResult(@RequestParam("memID") String myID, @RequestParam("TAG") String TAG,
			HttpServletRequest request) {

		Map<String, String> result = new HashMap<String, String>();
		int size = Integer.parseInt(request.getParameter("friendSize"));
		System.out.println("내아이디 : " + myID);
		for (int i = 0; i < size; i++) {
			System.out.println("친구id: " + request.getParameter("memID" + i));
			String memID = request.getParameter("memID" + i);
			if (TAG.equals("friend")) {
				memServ.resultfriend(myID, memID);
			} else if (TAG.equals("reject")) {
				memServ.resultreject(myID, memID);
			}
		}
		result.put("success", "true");
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/showFriend", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> showFriend(@RequestParam("memID") String memID) {

		Map<String, String> result = new HashMap<String, String>();

		List<Member> list = memServ.showFriend(memID);

		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i).getMemID());
			System.out.print(list.get(i).getMemName());
			result.put("memID" + i, String.valueOf((list.get(i).getMemID())));
			result.put("memName" + i, list.get(i).getMemName());
		}

		result.put("showFriendSize", String.valueOf(list.size()));
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteFriend", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> deleteFriend(@RequestParam("memID") String memID,
			@RequestParam("friendID") String friendID) {

		System.out.println("삭제 할 친구:" + friendID);

		memServ.deleteFriend(memID, friendID);

		Map<String, String> result = new HashMap<String, String>();
		result.put("success", "true");
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/newFriend", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> newFriend(@RequestParam("memID") String memID) {

		System.out.println("검색 ID:" + memID);
		String s = memServ.newFriend(memID);
		Map<String, String> result = new HashMap<String, String>();
		if (s.isEmpty()) {
			result.put("success", "false");
		} else {
			result.put("memName", s);
			result.put("success", "true");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/friendAdd", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> friendAdd(@RequestParam("memID") String memID,
			@RequestParam("friendID") String friendID) {

		System.out.println("친구 ID:" + friendID);
		List<Friend> list = memServ.friendcheck(memID, friendID);
		Map<String, String> result = new HashMap<String, String>();
		if (list.isEmpty()) {
			memServ.friendAdd(memID, friendID);
			result.put("dup", "false");
			result.put("success", "true");
		} else {
			result.put("dup", "true");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/listTransaction", produces = "application/json; charset=UTF-8", method = {
			RequestMethod.GET, RequestMethod.POST })
	public Map<String, String> listTransaction(@RequestParam("accountNum") String accountNum) {

		List<Transaction> list = transServ.listTransaction(accountNum);
		String accountBalance = transServ.accountBalance(accountNum);

		Map<String, String> result = new HashMap<String, String>();

		for (int i = 0; i < list.size(); i++) {
			result.put("accountNum" + i, list.get(i).getAccountNum());
			result.put("transactID" + i, String.valueOf(list.get(i).getTransactID()));
			result.put("transactHistory" + i, list.get(i).getTransactHistory());
			result.put("transactMoney" + i, String.valueOf(list.get(i).getTransactMoney()));
			result.put("since" + i, list.get(i).getSince());
			result.put("MID" + i, String.valueOf(list.get(i).getMID()));
			result.put("DID" + i, String.valueOf(list.get(i).getDID()));
		}

		result.put("listTransactionSize", String.valueOf(list.size()));
		result.put("accountBalance", accountBalance);
		return result;
	}

	@Autowired
	private JavaMailSender mailSender;

	@RequestMapping(value = "/mail", produces = "application/json; charset=UTF-8", method = {
			RequestMethod.GET, RequestMethod.POST })
	public Map<String, String> mailSend(HttpServletRequest request) {

		Random rand = new Random();
		String numStr = "";

		for (int i = 0; i < 6; i++) {
			// 0~9 까지 난수 생성
			String ran = Integer.toString(rand.nextInt(10));

			if (!numStr.contains(ran)) {
				// 중복된 값이 없으면 numStr에 append
				numStr += ran;
			} else {
				// 생성된 난수가 중복되면 루틴을 다시 실행한다
				i -= 1;
			}
		}

		String setfrom = "mnmsauth@gmail.com";
		String tomail = request.getParameter("memEmail"); // 받는 사람 이메일
		System.out.println("=====Email=====");
		System.out.println(tomail);
		String title = "[MNMS]인증번호"; // 제목
		String content = "[MNMS]인증번호는 " + numStr + "입니다."; // 내용

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

			messageHelper.setFrom(setfrom); // 보내는사람 생략하면 정상작동을 안함
			messageHelper.setTo(tomail); // 받는사람 이메일
			messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
			messageHelper.setText(content); // 메일 내용

			mailSender.send(message);
			} catch (MessagingException me) { 
				me.printStackTrace();
			}


		Map<String, String> result = new HashMap<String, String>();
		result.put("number", numStr);
		return result;
	
	}
	@ResponseBody
	@RequestMapping(value = "/showTransaction", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> showTransaction(@RequestParam("accountNum") String accountNum) {

		Map<String, String> result = new HashMap<String, String>();

		List<Transaction> trans=memServ.showTransaction(accountNum);
		for(int i=0;i<trans.size();i++) {
			result.put("transactHistory"+i,trans.get(i).getTransactHistory());
			result.put("transactMoney"+i,String.valueOf(trans.get(i).getTransactMoney()));
			result.put("since"+i,trans.get(i).getSince());
		}
		
		return result;
	}

}
