package com.example.simplebankapp.controllers;
import com.example.simplebankapp.AuthenticationResponse;
import com.example.simplebankapp.MyUserDetailsService;
import com.example.simplebankapp.dto.LoginDto;
import com.example.simplebankapp.dto.RegistrationDto;
import com.example.simplebankapp.dto.TransactionDto;
import com.example.simplebankapp.models.AccountStatement;
import com.example.simplebankapp.models.DataBase;
import com.example.simplebankapp.models.User;
import com.example.simplebankapp.service.CreateNewUserService;
import com.example.simplebankapp.service.serviceimplementation.CustomerDepositServiceImpl;
import com.example.simplebankapp.service.serviceimplementation.CustomerWithdrawServiceImpl;
import com.example.simplebankapp.service.serviceimplementation.DepositServiceImpl;
import com.example.simplebankapp.service.serviceimplementation.GetAccounStatementService;
import com.example.simplebankapp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class Controller {
    @Autowired
    AuthenticationManager authenticationManager;

    DataBase dataBase= new DataBase();
    @Autowired
    CreateNewUserService createNewUserService;
    @Autowired
    CustomerDepositServiceImpl  customerDepositService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private GetAccounStatementService getAccounStatementService;
    @Autowired
    private DepositServiceImpl depositService;

  CustomerWithdrawServiceImpl customerWithdrawService = new CustomerWithdrawServiceImpl();

    @PostMapping("/create_account")
    public ResponseEntity<?> createUser(@RequestBody RegistrationDto registrationDto){
     User user =  createNewUserService.createUser(registrationDto);
        return  new ResponseEntity<Object>(user,HttpStatus.CREATED);
    }
    @GetMapping("/account_info/{accountNumber}")
    public ResponseEntity<?> getuserInfo(@PathVariable String accountNumber ){
Optional<User> optionalUser = Optional.ofNullable( dataBase.findUserByAccountNumber(accountNumber));
if(optionalUser.isEmpty()) throw  new RuntimeException();
else{
    User user = optionalUser.get();
    return  new ResponseEntity<Object>(user,HttpStatus.OK);
}

    }
    @GetMapping("/account_statement/{accountNumber}")
    public ResponseEntity<?> accountStatementList(@PathVariable String accountNumber ) throws Throwable {
         List<AccountStatement> accounts = getAccounStatementService.getAccountStatementList(accountNumber);
           return ResponseEntity.ok(accounts);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody TransactionDto transactionDto){
        customerWithdrawService.withdraw(transactionDto.getAmount());
            return  new ResponseEntity<Object>("Success",HttpStatus.OK);
        }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public ResponseEntity<?> deposit(@RequestBody TransactionDto transactionDto){
        depositService.deposit(transactionDto.getAmount());
        return  new ResponseEntity<Object>("Success",HttpStatus.OK);
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDto loginDto) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUserAccountNumber(), loginDto.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginDto.getUserAccountNumber());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
