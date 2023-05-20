package com.example.simplebankapp.service.serviceimplementation;

import com.example.simplebankapp.dto.ResponseDto;
import com.example.simplebankapp.models.AccountStatement;
import com.example.simplebankapp.models.DataBase;
import com.example.simplebankapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class GetAccountStatementServiceImpl implements GetAccountStatementService {
    @Autowired
    DataBase dataBase;
    @Override
    public ResponseDto getAccountStatementList() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken))
        {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String accountNumber = userDetails.getUsername();
            User user = dataBase.findUserByAccountNumber(accountNumber);
            return   ResponseDto.builder()
                    .status("200")
                    .message("success")
                    .data(user.getTransactionHistory())
                    .build();
        }

        else
            {
                return   ResponseDto.builder()
                        .status("400")
                        .message("Fail")
                        .data("token has expired or user not logged in")
                        .build();

        }

    }

    @Override
    public ResponseDto getAcctInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken))
        {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String accountNumber = userDetails.getUsername();
            User user = dataBase.findUserByAccountNumber(accountNumber);
            return   ResponseDto.builder()
                    .status("200")
                    .message("success")
                    .data(user)
                    .build();
        }

        else
        {
            return   ResponseDto.builder()
                    .status("400")
                    .message("Fail")
                    .data("token has expired or user not logged in")
                    .build();

        }
    }
}
