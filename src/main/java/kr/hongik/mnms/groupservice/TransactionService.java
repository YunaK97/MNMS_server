package kr.hongik.mnms.groupservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.hongik.mnms.Transaction;
import kr.hongik.mnms.mapper.TransactionMapper;



@Service
public class TransactionService implements ITransactionService {
	
	@Autowired
	private TransactionMapper transMap;

	@Override
	public List<Transaction> dailyTransact(String GID) {
		List<Transaction> list = transMap.dailyTransact(GID);
		return list;
	}

	@Override
	public List<Transaction> membershipTransact(String GID) {
		List<Transaction> list = transMap.membershipTransact(GID);
		return list;
	} 

	@Override
	public List<Transaction> listTransaction(String accountNum) {
		List<Transaction> list = transMap.listTransaction(accountNum);
		return list;
	}

	public String accountBalance(String accountNum) {
		String accountBalance = transMap.accountBalance(accountNum)+"";
		return accountBalance;
	}
	

}
