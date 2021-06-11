package com.my.example.atm.service.impl;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.service.api.FileReaderService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileReaderServiceImpl implements FileReaderService {

    private static final String DELIMITER = ",";

    @Override
    public List<Account> readAccount(String path) throws IOException {
        return Files.readAllLines(Paths.get(path)).stream()
                .map(row -> {
                    String[] acc = row.split(DELIMITER);
                    Account account = new Account(acc[1], acc[3], acc[0], Long.parseLong(acc[2]));
                    return account;
                }).collect(Collectors.toList());
    }
}
