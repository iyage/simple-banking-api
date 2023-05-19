package com.example.simplebankapp.service.serviceimplementation;
import com.example.simplebankapp.enums.TransactionType;
import com.example.simplebankapp.models.AccountStatement;
import com.example.simplebankapp.models.DataBase;
import com.example.simplebankapp.models.User;
import com.example.simplebankapp.service.CustomerDepositService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class CustomerDepositServiceImpl implements CustomerDepositService {
   DataBase dataBase = new DataBase();
    @Override
    public void customerDeposit(Double amount, String accountNumber) {
   Optional<User> userOptional = Optional.ofNullable(dataBase.findUserByAccountNumber(accountNumber));
   if(userOptional.isEmpty()){

   }
   else {
       if(amount>1 && amount<1000000){
          User user = userOptional.get();
       List<AccountStatement>accountStatementList= user.getAccountStatementList();
       AccountStatement acoountstatement = new AccountStatement();
       acoountstatement.setTransactionType(TransactionType.DEPOSIT);
       acoountstatement.setTransactionDate(new Date());
       Double newBalance = amount+ user.getAccountBalance();
               user.setAccountBalance(newBalance);
       acoountstatement.setAccountBalance(newBalance);
       }else{

       }
   }

    }
}
