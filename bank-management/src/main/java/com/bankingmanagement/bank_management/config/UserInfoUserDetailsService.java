package com.bankingmanagement.bank_management.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.bankingmanagement.bank_management.Model.Account;
import com.bankingmanagement.bank_management.Repository.AccountRepository;


@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {
        Optional<Account> userInfo = repository.findByAccountNumber(accountNumber);
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found with account number: " + accountNumber));
    }
}
