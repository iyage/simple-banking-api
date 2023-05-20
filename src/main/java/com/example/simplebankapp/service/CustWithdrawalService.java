package com.example.simplebankapp.service;

import com.example.simplebankapp.dto.ResponseDto;
import com.example.simplebankapp.dto.TransactionDto;

public interface CustWithdrawalService {
    public ResponseDto withdraw(TransactionDto transactionDto);
}
