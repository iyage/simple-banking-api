package com.example.simplebankapp.service.serviceimplementation;

import com.example.simplebankapp.enums.TransactionType;
import com.example.simplebankapp.models.AccountStatement;
import com.example.simplebankapp.models.DataBase;
import com.example.simplebankapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DepositServiceImpl implements  DepositService{

    DataBase dataBase = new DataBase();
    @Override
    public void deposit( Double depositAmount) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String accountNumber = userDetails.getUsername();
    Optional<User> optionalUser= Optional.of(dataBase.findUserByAccountNumber(accountNumber));
    if(optionalUser.isPresent()){
        User user = optionalUser.get();
        if(depositAmount>1 && depositAmount<1000000){
         Double   newBalance =  depositAmount+user.getAccountBalance();
            AccountStatement accountStatement = new AccountStatement();
            accountStatement.setAccountBalance(depositAmount);
           accountStatement.setTransactionDate(new Date());
           accountStatement.setTransactionType(TransactionType.DEPOSIT);
             user.setAccountBalance(newBalance);
            List<AccountStatement>accountStatements = new ArrayList<>();
            accountStatements = user.getAccountStatementList();
            accountStatements.add(accountStatement);
            user.setAccountStatementList(accountStatements);

        }
    }
    }
}
