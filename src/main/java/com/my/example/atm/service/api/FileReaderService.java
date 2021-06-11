package com.my.example.atm.service.api;

import com.my.example.atm.dao.entity.Account;

import java.io.IOException;
import java.util.List;

public interface FileReaderService {

    List<Account> readAccount(String path) throws IOException;
}
