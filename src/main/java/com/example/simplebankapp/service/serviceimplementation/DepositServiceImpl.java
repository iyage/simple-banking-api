package com.example.simplebankapp.service.serviceimplementation;

import com.example.simplebankapp.dto.ResponseDto;
import com.example.simplebankapp.dto.TransactionDto;
import com.example.simplebankapp.enums.TransactionType;
import com.example.simplebankapp.models.AccountStatement;
import com.example.simplebankapp.models.DataBase;
import com.example.simplebankapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DepositServiceImpl implements  DepositService{

    DataBase dataBase = new DataBase();

    @Override
    public ResponseDto deposit(TransactionDto transactionDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(auth);
     if(!(auth instanceof AnonymousAuthenticationToken))
     {
         UserDetails userDetails = (UserDetails) auth.getPrincipal();
         System.out.println(userDetails.getUsername());
                 String accountNumber = userDetails.getUsername();
    User user= dataBase.findUserByAccountNumber(accountNumber);
     if(transactionDto.getAmount()>1 &&transactionDto.getAmount()<1000000)
     {
         Double   newBalance = transactionDto.getAmount()+user.getAccountBalance();
            AccountStatement accountStatement = new AccountStatement();
            accountStatement.setAccountBalance(newBalance);
            accountStatement.setAmount(transactionDto.getAmount());
           accountStatement.setTransactionDate(new Date());
           accountStatement.setTransactionType(TransactionType.DEPOSIT);
           accountStatement.setNarration(transactionDto.getReason());
             user.setAccountBalance(newBalance);
           user.getTransactionHistory().add(accountStatement);
          return  ResponseDto.builder()
                  .data(
                          "Amount Deposited "+transactionDto.getAmount()+"\n"+
                                  "New Balance "+ user.getAccountBalance()
                  )
                  .message("Success")
                  .status("200")
                  .build();
        }
     else
         {
             if(transactionDto.getAmount() <= 1)
             {
                 return  ResponseDto.builder()
                         .data("Minimum deposit not met")
                         .message("Fail")
                         .status("400")
                         .build();
             }
             else{
                 return  ResponseDto.builder()
                         .data("Maximum deposit exceeded")
                         .message("Fail")
                         .status("400")
                         .build();
             }

     }

     }
     else{
         return  ResponseDto.builder()
                 .data("Account does not exist")
                 .message("Fail")
                 .status("400")
                 .build();
     }

    }
}
