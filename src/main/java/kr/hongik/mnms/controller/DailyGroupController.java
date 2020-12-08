package kr.hongik.mnms.controller;

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

import kr.hongik.mnms.CALCULATE;
import kr.hongik.mnms.DailyGroup;
import kr.hongik.mnms.Member;
import kr.hongik.mnms.Participate;
import kr.hongik.mnms.Transaction;
import kr.hongik.mnms.groupservice.DailyGroupService;
import kr.hongik.mnms.groupservice.SHAPasswordEncoder;
import kr.hongik.mnms.groupservice.TransactionService;


@RestController
@RequestMapping(value="/daily", method= {RequestMethod.GET,RequestMethod.POST})
public class DailyGroupController {
	
	@Autowired
	private TransactionService transServ;
	@Autowired
	private DailyGroupService dailyServ;
	
	@ResponseBody
	@RequestMapping(value="/", produces="application/json; charset=UTF-8", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String, String> home(@RequestParam("GID") String GID) {	
		
		List<Transaction> list = transServ.dailyTransact(GID);

        Map<String, String> result = new HashMap<String, String>();
        
        for(int i=0; i<list.size(); i++) {
	        result.put("accountNum"+i, list.get(i).getAccountNum());
	        result.put("transactID"+i, String.valueOf(list.get(i).getTransactID()));
	        result.put("transactHistory"+i, list.get(i).getTransactHistory());
	        result.put("transactMoney"+i, String.valueOf(list.get(i).getTransactMoney()));
	        result.put("since"+i, list.get(i).getSince());
	        result.put("DID"+i, String.valueOf(list.get(i).getDID()));
        }
        
        result.put("dailyTransactionSize", String.valueOf(list.size()));
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/info", produces="application/json; charset=UTF-8", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String, String> info(@RequestParam("GID") String GID) {
		DailyGroup daily = dailyServ.info(GID);
		
	    Map<String, String> result = new HashMap<String, String>();
	    result.put("DID", String.valueOf(daily.getDID()));
	    result.put("GID", String.valueOf(daily.getGID()));
	    result.put("groupName", daily.getGroupName());
	    
	    return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/dutch", produces="application/json; charset=UTF-8", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String, String> dutch(@RequestParam("DID") String DID) {	
		
		DailyGroup daily = dailyServ.dutch(DID);

        Map<String, String> result = new HashMap<String, String>();
        result.put("DID", String.valueOf(daily.getDID()));
        result.put("GID", String.valueOf(daily.getGID()));
        
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/member", produces="application/json; charset=UTF-8", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String, String> member(@RequestParam("GID") String GID) {	
		
		Map<String, String> result = new HashMap<String, String>();
        
		List<Member> list = dailyServ.memList(GID);

		for(int i=0; i<list.size(); i++) {
			System.out.print(list.get(i).getMemID());
			System.out.print(list.get(i).getMemName());
			result.put("memID"+i, String.valueOf((list.get(i).getMemID())));
			result.put("memName"+i, list.get(i).getMemName());
		}

		result.put("dailyMemberSize", String.valueOf(list.size()));
		
     	return result;
	}
	@ResponseBody
	@RequestMapping(value = "/deleteDailygroup", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> deleteDailygroup(@RequestParam("memID") String memID,@RequestParam("GID") String GID) {
		Map<String, String> result = new HashMap<String, String>();
	//	int did=dailyServ.getDID(GID);
		int gid=Integer.parseInt(GID);
		System.out.println("삭제 할 그룹:" + GID);
	//	if(dailyServ.checkdelete(did)) {
	//		result.put("success", "false");
	//	}else {
			dailyServ.deleteDaily(gid);
			System.out.println("deleteDaily성공");
			
			dailyServ.deleteGroup(gid);
			System.out.println("deleteGroup성공");
			
			dailyServ.deleteDailygroup(gid);
			System.out.println("deleteDailyGroup성공");
		
			result.put("success", "true");
	//	}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/new", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> newDailygroup(@RequestParam("memID") String memID,@RequestParam("dailyName") String dailyName, HttpServletRequest request) {
		Map<String, String> result = new HashMap<String, String>();
		if(dailyServ.isvalidName(dailyName)) {
			result.put("success", "false");
			return result;
		}
		System.out.println("그룹 이름:" + dailyName);
		dailyServ.newDailygroup(memID,dailyName);
		int gid=dailyServ.getgid(dailyName);
		int size=Integer.parseInt(request.getParameter("memberSize"));
		
		Participate part = new Participate();

		part.setMemID(memID);
		part.setGID(gid);
	
		dailyServ.add(part);
		for(int i=0;i<size;i++) {
			System.out.println(request.getParameter("memID"+i));	
			String memberID = request.getParameter("memID"+i);
			part.setMemID(memberID);
			dailyServ.add(part);
		}
		result.put("success", "true");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/add", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> add(@RequestParam("GID") String GID,@RequestParam("friendSize") String friendSize,HttpServletRequest request) {	
		
        System.out.println("add함수 시작");
		int size=Integer.parseInt(friendSize);
		Participate part = new Participate();
		for(int i=0;i<size;i++) {
			System.out.println(request.getParameter("memID"+i));	
			String memberID = request.getParameter("memID"+i);
			part.setGID(Integer.parseInt(GID));
			part.setMemID(memberID);
			dailyServ.add(part);
		}
		
		Map<String, String> result = new HashMap<String, String>();
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
		
		dailyServ.deleteMember(part);
	
		result.put("success", "true");
		return result;
		
	}
	@ResponseBody
	@RequestMapping(value = "/result", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> result(@RequestParam("DID") String DID) {	
		
        System.out.println("++++dutchpay++++");
		Map<String, String> result = new HashMap<String, String>();
		
		List<HashMap<String,String>> list=dailyServ.dutchpay(DID);
		int total=0;
		for(int i=0;i<list.size();i++) {
			int mon=Integer.parseInt(String.valueOf(list.get(i).get("money")));
			mon=-mon;
			result.put("money"+i,String.valueOf(mon));
			 System.out.println("money:"+mon);
			result.put("accountNum"+i,String.valueOf(list.get(i).get("accountNum")));
			 System.out.println("accountNum:"+list.get(i).get("accountNum"));
			result.put("memID"+i,list.get(i).get("memID"));
			total+=mon;
		}
		result.put("total",String.valueOf(total));
		result.put("memSize",String.valueOf(list.size()));
		result.put("success", "true");
		return result;
		
	}
	@ResponseBody
	@RequestMapping(value = "/calculate", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> calculate(@RequestParam("DID") String DID,HttpServletRequest request) {
		   System.out.println("++++dutchpay++++");
			Map<String, String> result = new HashMap<String, String>();
			int did=Integer.parseInt(DID);
			if(dailyServ.iscalculated(did)) {
				
			}else {
				int size=Integer.parseInt(request.getParameter("friendSize"));
				CALCULATE cal = new CALCULATE();
				for(int i=0;i<size;i++) {
					System.out.println(request.getParameter("memID"+i));	
					String memberID = request.getParameter("memID"+i);
					cal.setDID(Integer.parseInt(DID));
					cal.setmemID(memberID);
					int count=Integer.parseInt(request.getParameter("count"+i));
					cal.setcount(count);
					dailyServ.calculate(cal);
				}
			}
		result.put("success", "true");
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/checkPW", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> checkPW(@RequestParam("accountNum") String accountNum,@RequestParam("accountPassword") String pw) {
		Map<String, String> result = new HashMap<String, String>();
			String encryptPW = SHAPasswordEncoder.encrypt(pw);
			boolean check=dailyServ.checkPW(accountNum,encryptPW);
			if(check==true) {
				result.put("success","true");
			}
			else {result.put("success","false");}
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/pay", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> pay(@RequestParam("accountNum") String accountNum,@RequestParam("history") String history,
			@RequestParam("money") String money,@RequestParam("DID") String DID) {	 
		
        System.out.println("++++pay++++!!!!!!!!!!!!!!!!!!!!!!지금 여기 고치는중 여기보세요!!!!!!!!!!!!!!");
      
		Map<String, String> result = new HashMap<String, String>();
		 System.out.println(history);
		dailyServ.updateAccount(accountNum,money);
		dailyServ.updateSend(accountNum,money,history,DID);
		result.put("success", "true");
		return result;
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/transact", produces = "application/json; charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, String> transact(@RequestParam("accountNum") String accountNum,@RequestParam("myAccountNum") String me,@RequestParam("money") String money,
			@RequestParam("nick") String nick,@RequestParam("Mynick") String mynick,@RequestParam("DID") String DID,@RequestParam("memID") String memID
			,@RequestParam("friendID") String friendID) {	
		
        System.out.println("++++transaction++++");
      int did=Integer.parseInt(DID);
		Map<String, String> result = new HashMap<String, String>();
		if(dailyServ.checkfriend(friendID,memID)) {
			if(dailyServ.istransacted(did,memID)) {
				result.put("success", "false");
			}else {
				dailyServ.updateAccount(me,money);
				dailyServ.updateSend(me,money,nick);
				dailyServ.updateGet2(accountNum,money,mynick);
				dailyServ.updatefriendAccount(accountNum,money);
				dailyServ.updateCalculate(did,memID);
				result.put("success", "true");
				result.put("myAccountNum",me);
			}
		}
		else {
			result.put("success", "notfriend");
		}
		return result;
		
	}
}
