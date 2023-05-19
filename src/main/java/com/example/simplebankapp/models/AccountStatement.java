package com.example.simplebankapp.models;
import com.example.simplebankapp.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatement {
    Date transactionDate;
  TransactionType transactionType;
    Double amount;
    String narration;
    Double accountBalance;
}
