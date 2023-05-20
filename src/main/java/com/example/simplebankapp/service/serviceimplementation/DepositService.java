package com.example.simplebankapp.service.serviceimplementation;

import com.example.simplebankapp.dto.ResponseDto;
import com.example.simplebankapp.dto.TransactionDto;

public interface DepositService {
    ResponseDto deposit (TransactionDto transactionDto);

}
