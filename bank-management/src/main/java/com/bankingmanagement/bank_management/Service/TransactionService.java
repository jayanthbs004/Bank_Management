package com.bankingmanagement.bank_management.Service;

import com.bankingmanagement.bank_management.Exception.InsufficientBalanceException;
import com.bankingmanagement.bank_management.Exception.ResourceNotFoundException;
import com.bankingmanagement.bank_management.Model.Account;
import com.bankingmanagement.bank_management.Model.Transaction;
import com.bankingmanagement.bank_management.Model.TransactionType;
import com.bankingmanagement.bank_management.Repository.AccountRepository;
import com.bankingmanagement.bank_management.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id " + id));
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction createTransaction(Transaction transaction) {
        Account account = accountRepository.findById(transaction.getAccount().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + transaction.getAccount().getId()));

        if (transaction.getTransactionType() == TransactionType.WITHDRAWAL && account.getBalance() < transaction.getAmount()) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal");
        }

        if (transaction.getTransactionType() == TransactionType.WITHDRAWAL) {
            account.setBalance(account.getBalance() - transaction.getAmount());
        } else if (transaction.getTransactionType() == TransactionType.DEPOSIT) {
            account.setBalance(account.getBalance() + transaction.getAmount());
        }

        accountRepository.save(account);
        transaction.setTransactionDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
