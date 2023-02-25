package com.example.AccountMS.repository;


import com.example.AccountMS.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    List<Account> findByCustomerId(int customerId);
    //void findByCustomerId(account.getCustomerId());

}
