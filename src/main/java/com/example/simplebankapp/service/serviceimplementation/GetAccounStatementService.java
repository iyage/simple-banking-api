package com.example.simplebankapp.service.serviceimplementation;

import com.example.simplebankapp.models.AccountStatement;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface GetAccounStatementService {
    List<AccountStatement>getAccountStatementList(String accountNumber) throws Throwable;
}
