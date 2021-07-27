package com.my.example.atm.service.impl;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.dao.entity.AccountUserDetails;
import com.my.example.atm.dao.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String accountNo) throws UsernameNotFoundException {
        Account user = Optional.ofNullable(accountRepository.findByAccountNumber(accountNo))
                .orElseThrow(() -> new UsernameNotFoundException("Account not found"));
        return new AccountUserDetails(user);
    }
}
