package com.example.simplebankapp.service.serviceimplementation;

import com.example.simplebankapp.models.AccountStatement;
import com.example.simplebankapp.models.DataBase;
import com.example.simplebankapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class GetAccountStatementServiceImpl implements GetAccounStatementService {
    @Autowired
    DataBase dataBase;
    @Override
    public List<AccountStatement> getAccountStatementList(String accountNumber) throws Throwable {
        Optional<User> optionalUser = Optional.ofNullable(dataBase.findUserByAccountNumber(accountNumber));
        if(optionalUser.isPresent()){
            User user =  optionalUser.get();
         List<AccountStatement>userAccountStatements = user.getAccountStatementList();
         return userAccountStatements;
        }

        return null;
    }
}
