package com.example.simplebankapp.service.serviceimplementation;

import com.example.simplebankapp.dto.ResponseDto;
import com.example.simplebankapp.dto.TransactionDto;
import com.example.simplebankapp.enums.TransactionType;
import com.example.simplebankapp.models.AccountStatement;
import com.example.simplebankapp.models.DataBase;
import com.example.simplebankapp.models.User;
import com.example.simplebankapp.service.CustWithdrawalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;

@Slf4j
public class CustomerWithdrawServiceImpl implements CustWithdrawalService {
   private DataBase dataBase = new DataBase();
    @Override
    public ResponseDto withdraw(TransactionDto transactionDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken))
        {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
                    getAuthentication().getPrincipal();
            String userAccount = userDetails.getUsername();
            User user = dataBase.findUserByAccountNumber( userAccount);
            if(transactionDto.getAmount()>1){
                AccountStatement  acctStatement = new AccountStatement();
                acctStatement.setTransactionType(TransactionType.WITHDRAWAL);
                acctStatement.setTransactionDate(new Date());
                acctStatement.setNarration(transactionDto.getReason());
                acctStatement.setAmount(transactionDto.getAmount());
                Double newBalance  = user.getAccountBalance()-transactionDto.getAmount();
                if( newBalance>=500) {
                    user.setAccountBalance(newBalance);
                    acctStatement.setAccountBalance(newBalance);

                    user.setAccountBalance(newBalance);
                    user.getTransactionHistory().add(acctStatement);
                    return  ResponseDto.builder()
                            .data(
                                    "Withdraw Amount "+transactionDto.getAmount()+"\n"+
                                            "New Balance "+ user.getAccountBalance()
                            )
                            .message("Success")
                            .status("200")
                            .build();
                }
                else{
                    return  ResponseDto.builder()
                            .data("minimum balance must be 500")
                            .message("Fail")
                            .status("400")
                            .build();
                }


            }else{
                System.out.println("cant withdraw less than 1 naira");
                return  ResponseDto.builder()
                        .data("cant withdraw less than 1 naira")
                        .message("Fail")
                        .status("400")
                        .build();

            }
        }
        else{
            return  ResponseDto.builder()
                    .data("Token expired or user not logged in")
                    .message("Fail")
                    .status("400")
                    .build();
        }
    }
}
