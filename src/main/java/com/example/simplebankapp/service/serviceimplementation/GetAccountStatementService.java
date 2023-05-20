package com.example.simplebankapp.service.serviceimplementation;

import com.example.simplebankapp.dto.ResponseDto;
import com.example.simplebankapp.models.AccountStatement;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface GetAccountStatementService {
   ResponseDto getAccountStatementList();
   ResponseDto getAcctInfo();
}
