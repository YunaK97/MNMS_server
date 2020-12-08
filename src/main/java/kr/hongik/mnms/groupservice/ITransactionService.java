package kr.hongik.mnms.groupservice;

import java.util.List;

import kr.hongik.mnms.Account;
import kr.hongik.mnms.Transaction;

public interface ITransactionService {
	List<Transaction> dailyTransact(String GID);
	List<Transaction> membershipTransact(String GID);
	List<Transaction> listTransaction(String accountNum);
	String accountBalance(String accountNum);
}
