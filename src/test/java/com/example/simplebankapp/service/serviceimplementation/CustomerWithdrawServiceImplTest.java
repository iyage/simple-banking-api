package com.example.simplebankapp.service.serviceimplementation;

import com.example.simplebankapp.enums.TransactionType;
import com.example.simplebankapp.models.DataBase;
import com.example.simplebankapp.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerWithdrawServiceImplTest {
    CustomerWithdrawServiceImpl customerWithdrawService;
    DataBase dataBase ;
    @BeforeEach
    void setUp() {
        customerWithdrawService = new CustomerWithdrawServiceImpl();
        dataBase =new DataBase();
    }

    @Test
    void withdraw() {

        User user =  new User();
        user.setAccountBalance(1000.00);
        user.setAccountName("yage");
        user.setAccountNumber("1234567890");
        user.setAccountPassword("1234");
        dataBase.addUser(user);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.
                withUsername("1234567890").password("1234").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
       customerWithdrawService.withdraw(200.00);
        User newUser= dataBase.findUserByAccountNumber("1234567890");
        assertEquals(user.getAccountBalance(),800);
        assertEquals(newUser.getAccountStatementList().get(0).getTransactionType(), TransactionType.WITHDRAWAL);
        assertEquals(newUser.getAccountStatementList().get(0).getAccountBalance(), 200);
    }
}