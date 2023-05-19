package com.example.simplebankapp.service.serviceimplementation;

import com.example.simplebankapp.enums.TransactionType;
import com.example.simplebankapp.models.AccountStatement;
import com.example.simplebankapp.models.DataBase;
import com.example.simplebankapp.models.User;
import com.example.simplebankapp.service.CustWithdrawalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Slf4j
public class CustomerWithdrawServiceImpl implements CustWithdrawalService {
   private DataBase dataBase = new DataBase();
    @Override
    public void withdraw(Double withdrawAmount) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        String userAccount = userDetails.getUsername();
            Optional<User> userOptional = Optional.ofNullable(dataBase.findUserByAccountNumber( userAccount));
            if(userOptional.isEmpty()){
                System.out.println("no user found");
            }
            else {
                if(withdrawAmount>1){
                    log.info(userOptional.get().getAccountBalance().toString());
                    User user = userOptional.get();
                    List<AccountStatement> accountStatementList= user.getAccountStatementList();
                    AccountStatement acoountstatement = new AccountStatement();
                    acoountstatement.setTransactionType(TransactionType.WITHDRAWAL);
                    acoountstatement.setTransactionDate(new Date());
                    Double newBalance  = user.getAccountBalance()-withdrawAmount;

                    if( newBalance>500) {
                        user.setAccountBalance(newBalance);
                        acoountstatement.setAccountBalance(withdrawAmount);
                       accountStatementList.add(acoountstatement);
                       user.setAccountStatementList(accountStatementList);
                    }
                    else{
                        System.out.println("your minimum balance must 500");
                    }


                }else{
                    System.out.println("cant withdraw less than 1 naira");
                }
            }

        }
}
