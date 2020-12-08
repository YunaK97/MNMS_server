package kr.hongik.mnms.mapper;

import java.util.List;

import kr.hongik.mnms.Account;
import kr.hongik.mnms.Transaction;

public interface TransactionMapper {
	List<Transaction> dailyTransact(String GID);
	List<Transaction> membershipTransact(String GID);
	List<Transaction> listTransaction(String accountNum);
	int accountBalance(String accountNum);
}
