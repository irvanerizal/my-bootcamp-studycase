package com.my.example.atm.service.impl;

import com.my.example.atm.exception.DataNotValidException;
import com.my.example.atm.service.TestingProperties;
import com.my.example.atm.service.api.AccountService;
import com.my.example.atm.service.api.FileReaderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
@Disabled
@ExtendWith(MockitoExtension.class)
class DataLoaderServiceTest extends TestingProperties {

    @InjectMocks
    private DataLoaderServiceImpl dataLoaderService;

    @Mock
    private AccountService accountService;

    @Mock
    private FileReaderService fileReaderService;

    @Test
    void whenLoadAccountData_givenEmptyPath_isStillSuccess() throws Exception {

        when(accountService.getStaticAccounts())
                .thenReturn(accounts);
        doNothing().when(accountService).setAccount(accountSet);

        dataLoaderService.loadAccountData("");

        verify(accountService, times(1)).getStaticAccounts();
        verify(accountService, times(1)).setAccount(accountSet);
    }

    @Test
    void whenLoadAccountData_givenPath_isSuccess() throws Exception {

        String path = "DIR:/EXAMPLE";

        when(fileReaderService.readAccount(path))
                .thenReturn(bulkAccounts);
        doNothing().when(accountService).setAccount(bulkAccountSet);

        dataLoaderService.loadAccountData(path);

        verify(fileReaderService, times(1)).readAccount(path);
        verify(accountService, times(1)).setAccount(bulkAccountSet);
    }

    @Test
    void whenLoadAccountData_givenPathAndDataNotValid_thenReturnExpectedError() {

        String path = "DIR:/EXAMPLE";

        Assertions.assertThrows(DataNotValidException.class, () -> {
            when(fileReaderService.readAccount(path))
                    .thenReturn(accounts);
            dataLoaderService.loadAccountData(path);
        });
    }

}