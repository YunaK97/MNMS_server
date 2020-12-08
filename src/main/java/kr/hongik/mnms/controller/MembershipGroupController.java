package kr.hongik.mnms.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.hongik.mnms.Account;
import kr.hongik.mnms.Member;
import kr.hongik.mnms.MemberFee;
import kr.hongik.mnms.MembershipGroup;
import kr.hongik.mnms.Participate;
import kr.hongik.mnms.Transaction;
import kr.hongik.mnms.groupservice.MembershipGroupService;
import kr.hongik.mnms.groupservice.SHAPasswordEncoder;
import kr.hongik.mnms.groupservice.TransactionService;



@RestController
@RequestMapping(value="/membership", method= {RequestMethod.GET,RequestMethod.POST})
public class MembershipGroupController {
	
	@Autowired
	private TransactionService transServ;
	@Autowired
	private MembershipGroupService memshipServ;

	
	@ResponseBody
	@RequestMapping(value="/", produces="application/json; charset=UTF-8", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String, String> home(@RequestParam("GID") String GID) {	
		
		List<Transaction> list = transServ.membershipTransact(GID);

        Map<String, String> result = new HashMap<String, String>();
        
        for(int i=0; i<list.size(); i++) {
	        result.put("accountNum"+i, list.get(i).getAccountNum());
	        result.put("transactID"+i, String.valueOf(list.get(i).getTransactID()));
	        result.put("transactHistory"+i, list.get(i).getTransactHistory());
	        result.put("transactMoney"+i, String.valueOf(list.get(i).getTransactMoney()));
	        result.put("since"+i, list.get(i).getSince());
	        result.put("MID"+i, String.valueOf(list.get(i).getMID()));
        }
        
        result.put("membershipTransactionSize", String.valueOf(list.size()));
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/info", produces="application/json; charset=UTF-8", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String, String> info(@RequestParam("GID") String GID) {	
		
		MembershipGroup memship = memshipServ.info(GID);

        Map<String, String> result = new HashMap<String, String>();
        result.put("MID", String.valueOf(memship.getMID()));
        result.put("president", memship.getPresident());
        result.put("payDay", String.valueOf(memship.getPayDay()));
        result.put("payDuration", memship.getPayDuration());
        result.put("fee", String.valueOf(memship.getFee()));
        result.put("notSubmit", String.valueOf(memship.getNotSubmit()));
        result.put("GID", String.valueOf(memship.getGID()));
        result.put("groupName", memship.getGroupName());
        result.put("accountNum", memship.getAccountNum());
        
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/member", produces="application/json; charset=UTF-8", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String, String> member(@RequestParam("GID") String GID) {	
		
		Map<String, String> result = new HashMap<String, String>();
        
		List<Member> list = memshipServ.memList(GID);

		for(int i=0; i<list.size(); i++) {
			System.out.print(list.get(i).getMemID());
			System.out.print(list.get(i).getMemName());
			result.put("memID"+i, String.valueOf((list.get(i).getMemID())));
			result.put("memName"+i, list.get(i).getMemName());
		}

		result.put("membershipMemberSize", String.valueOf(list.size()));
		
     	return result;
	}
	@ResponseBody
	@RequestMapping(value = "/deleteMembershipgroup", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> deleteMembershipgroup(@RequestParam("memID") String memID,@RequestParam("GID") String GID) {

		System.out.println("삭제 할 그룹:" + GID);

		memshipServ.deleteMembershipgroup(memID,GID);

		Map<String, String> result = new HashMap<String, String>();
		result.put("success", "true");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/new", produces="application/json; charset=UTF-8", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String, String> add(@RequestParam("memID") String memID,@RequestParam("membershipName") String Name,
			@RequestParam("membershipMoney") String money,@RequestParam("membershipNotSubmit") String NotSubmit,
			@RequestParam("accountNum") String account,@RequestParam("passWD") String passWD,@RequestParam("memberSize") String memSize,HttpServletRequest request) {	
		
		Map<String, String> result = new HashMap<String, String>();
        System.out.println("그룹이름: "+Name);
        System.out.println("내이름: "+memID);
        System.out.println("계좌번호: "+account);
        if(memshipServ.isvalidName(Name)) {
			result.put("success", "false");
			return result;
		}
        int mon=Integer.parseInt(money)*10000;
        int cnt=Integer.parseInt(NotSubmit);
        String encryptPW = SHAPasswordEncoder.encrypt(passWD);
        memshipServ.newAccount(encryptPW,account);
        memshipServ.newMembergroup(memID,Name,mon,cnt,account);
        int gid=memshipServ.getgid(Name);
        int mid=memshipServ.getmid(gid);
        int size=Integer.parseInt(memSize);
        memshipServ.add(memID,gid);
        memshipServ.addfee(memID,mid);
		for(int i=0;i<size;i++) {
			System.out.println(request.getParameter("memID"+i));
			String memberID = request.getParameter("memID"+i);
			memshipServ.add(memberID, gid);
			memshipServ.addfee(memberID,mid);
		}
	
		result.put("success", "true");
     	return result;
	}
	@ResponseBody
	@RequestMapping(value = "/checkPresident", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> checkPresident(@RequestParam("memID") String memID,@RequestParam("GID") String GID) {

		System.out.println("내 아이디:" + memID + "그룹 :"+ GID);

		boolean check=memshipServ.checkPresident(memID,GID);

		Map<String, String> result = new HashMap<String, String>();
		if(check==true)
			result.put("success", "true");
		else
			result.put("success","false");
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/unpay", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> checkUnpay(@RequestParam("MID") String MID,@RequestParam("NotSubmit") String NotSubmit,@RequestParam("GID") String GID,
			@RequestParam("Duration")String duration) {
		Map<String, String> result = new HashMap<String, String>();
		System.out.println("여기가 왜 오류지!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("GID:"+GID);
		if(memshipServ.isValidGID(GID)) {
		memshipServ.checkUnpay(MID);
		memshipServ.getOut(MID,NotSubmit,GID);
		//duration=duration.toUpperCase();
		System.out.println("Duration "+duration);
		memshipServ.updatedate(duration,MID);
		result.put("success","true");
		}
		else {
			result.put("success","false");
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/date", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> setdate(@RequestParam("date") String date,@RequestParam("MID") String MID,@RequestParam("Duration") String duration) {
		Map<String, String> result = new HashMap<String, String>();
		System.out.println("날짜: "+date);
		System.out.println("주기: "+duration);
		memshipServ.setdate(date,MID,duration);
		result.put("success","true");
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/updatename", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> updatename(@RequestParam("name") String name,@RequestParam("GID") String GID) {
		Map<String, String> result = new HashMap<String, String>();
		System.out.println("name"+ name);
		memshipServ.updatename(name,GID);
		result.put("success","true");
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/updateNotSubmit", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> updateNotSubmit(@RequestParam("NotSubmit") String ns,@RequestParam("MID") String MID) {
		Map<String, String> result = new HashMap<String, String>();
		memshipServ.updateNS(ns,MID);
		result.put("success","true");
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/updatepay", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> updatepay(@RequestParam("pay") String pay,@RequestParam("MID") String MID) {
		Map<String, String> result = new HashMap<String, String>();
		memshipServ.updatepay(pay,MID);
		result.put("success","true");
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/notSubmit", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> notSubmit(@RequestParam("memID") String memID,@RequestParam("MID") String MID,@RequestParam("GID") String GID) {
		Map<String, String> result = new HashMap<String, String>();
		boolean check=memshipServ.checkPresident(memID,GID);
		
		if(check==false) {
			List<MemberFee> list = memshipServ.notSubmitP(MID);
			for(int i=0; i<list.size(); i++) {
				System.out.println(list.get(i).getMemID());
				System.out.println(list.get(i).getCount());
				result.put("memID"+i, String.valueOf(list.get(i).getMemID()));
				result.put("count"+i, String.valueOf(list.get(i).getCount()));
			}
			result.put("MemberSize", String.valueOf(list.size()));
		}else {
			String n=Integer.toString(memshipServ.notSubmit(memID,MID));
			System.out.println(n);
			result.put("notSubmit",n);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/check", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> checktrans(@RequestParam("memID") String memID) {
		Map<String, String> result = new HashMap<String, String>();
		int count=0;
		List<Participate> part=memshipServ.getParticipate(memID);
		int Gsize=part.size();
		for(int i=0;i<Gsize;i++) {
			System.out.println("체크할 그룹"+part.get(i).getGID());
			int GID=part.get(i).getGID();
			String gid=Integer.toString(GID);
			if(memshipServ.isMembership(gid)) {
				Integer mid=memshipServ.getMembership(gid);
				boolean last=memshipServ.getPayday(mid);
				if(last==true) {
					boolean check=memshipServ.getMemberfee(memID,mid);
						if(check==true) {
							String name=memshipServ.getMname(GID);
							result.put("GID"+count,Integer.toString(GID));
							result.put("groupName"+count,name);
							count++;
						}
						else {}
				}else {
					
				}
			}
		}
		result.put("GIDsize",String.valueOf(count));
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/checkPW", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> checkPW(@RequestParam("accountNum") String accountNum,@RequestParam("accountPassword") String pw) {
		Map<String, String> result = new HashMap<String, String>();
		 System.out.println("++++CHECK PW!!!!!!!!!!!!!!!!!!!++++");
			String encryptPW = SHAPasswordEncoder.encrypt(pw); 
			System.out.println("암호화된 :"+encryptPW);
			boolean check=memshipServ.checkPW(accountNum,encryptPW);
			if(check==true) {
				result.put("success","true");
			}
			else {result.put("success","false");}
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/checkPW2", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> checkPW2(@RequestParam("accountNum") String accountNum,@RequestParam("accountPassword") String pw) {
		Map<String, String> result = new HashMap<String, String>();
			//String encryptPW = SHAPasswordEncoder.encrypt(pw);
			boolean check=memshipServ.checkPW(accountNum,pw);
			if(check==true) {
				result.put("success","true");
			}
			else {result.put("success","false");}
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/pay", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> pay(@RequestParam("memID") String memID,@RequestParam("memName")String memName,@RequestParam("accountNum") String accountNum,@RequestParam("money") String money,
			@RequestParam("groupName") String groupName,@RequestParam("MID") String MID) {
		
		System.out.println("내 아이디:" + memID + "계좌번호 :"+ accountNum);
		Map<String, String> result = new HashMap<String, String>();
		int total=memshipServ.getTotal(accountNum);
		System.out.println("내계좌 총액:"+total);
		int mon=Integer.parseInt(money);
		int mid=Integer.parseInt(MID);
		total=total-mon;
		System.out.println("돈 제외하고 남을 금액: "+total);
		
		memshipServ.updateAccount(accountNum,total);
		Account ac=memshipServ.getmTotal(mid);
		total=ac.getAccountBalance();
		total+=mon;
		memshipServ.updateTrans1(groupName,-mon,accountNum); //내가 그룹한테 보낸거
		memshipServ.updateTrans2(memName,mon, ac.getAccountNum(), mid); //그룹이 받은거 찍히기
		
		memshipServ.updateMembership(ac.getAccountNum(),total);
		memshipServ.updateMemberfee(mid,memID);
		result.put("success","true");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/add", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> add( @RequestParam("GID") String GID,@RequestParam("friendSize") String friendSize,
			HttpServletRequest request) {	
		
        System.out.println("add함수 시작");
        int size=Integer.parseInt(friendSize);
		Map<String, String> result = new HashMap<String, String>();
		for(int i=0;i<size;i++) {
			System.out.println(request.getParameter("memID"+i));
			String memberID = request.getParameter("memID"+i);
			memshipServ.add(memberID, Integer.parseInt(GID));
		}
	
		result.put("success", "true");
		return result;
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteMember", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> deleteMember( @RequestParam("GID") String GID,@RequestParam("memID") String memID) {	
		
        System.out.println("++++deleteMember++++");
      
		Map<String, String> result = new HashMap<String, String>();
		
		Participate part = new Participate();
		part.setGID(Integer.parseInt(GID));
		part.setMemID(memID);
		
		memshipServ.deleteMember(part);
	
		result.put("success", "true");
		return result;
		
	}
	@ResponseBody
	@RequestMapping(value = "/transaction", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> transact(@RequestParam("accountNum") String accountNum,@RequestParam("history") String history,
			@RequestParam("money") String money,@RequestParam("MID") String MID) {	 
		
        System.out.println("++++pay++++");
      
		Map<String, String> result = new HashMap<String, String>();
		int total=memshipServ.getTotal(accountNum);
		System.out.println("내계좌 총액:"+total);
		int mon=Integer.parseInt(money);
		int mid=Integer.parseInt(MID);
		total=total-mon;
		System.out.println("돈 제외하고 남을 금액: "+total);
		memshipServ.updateAccount(accountNum,total);
		memshipServ.updateSend(history,mon,accountNum,mid);
		result.put("success", "true");
		return result;
		
	}
	@ResponseBody
	@RequestMapping(value = "/notQR", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> notQR(@RequestParam("memID")String memID) {	 
		
        System.out.println("++++CHECK ACCOUNTNUM++++");
      
		Map<String, String> result = new HashMap<String, String>();
		String accountNum=memshipServ.getAccount(memID);
		String memName=memshipServ.getMemName(memID);
		result.put("memName",memName);
		result.put("accountNum",accountNum);
		result.put("success", "true");
		return result;
		
	}
}
