package com.example.AccountMS.service;

import com.example.AccountMS.entity.Account;
import com.example.AccountMS.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServices {
    @Autowired
    private AccountRepository repository;
    public List<Account> saveAccounts(List<Account> accounts) {
        return repository.saveAll(accounts);
    }
    public Account getAccountById(int accountNumber) {
        return repository.findById(accountNumber).orElse(null);
    }
    public List<Account> getAccounts() {
        return repository.findAll();
    }

    public String deleteAccount(int accountNumber){
        repository.deleteById(accountNumber);
        return("Account removed   "+accountNumber);
    }
    @Transactional
    public void deleteAccountsByCustomerId(int customerId) {
        List<Account> accounts = repository.findByCustomerId(customerId);
        repository.deleteAll(accounts);
    }
    public String getBalanceById(int accountNumber)
    {
        Account account=repository.findById(accountNumber).orElse(null);
        return("Balance Amount is :"+ account.getAccountBalance());
    }
    public Account updateAccount(Account account)
    {
        Account existingAccount=repository.findById(account.getAccountNumber()).orElse(null);
        existingAccount.setAccountType(account.getAccountType());
        existingAccount.setCustomerId(account.getCustomerId());
        existingAccount.setCustomerName(account.getCustomerName());
        existingAccount.setAccountBalance(account.getAccountBalance());
        existingAccount.setStatus(account.getStatus());
        existingAccount.setInterest(account.getInterest());
        return repository.save(existingAccount);
    }
}
