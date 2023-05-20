package com.example.simplebankapp.controllers;
import com.example.simplebankapp.AuthenticationResponse;
import com.example.simplebankapp.MyUserDetailsService;
import com.example.simplebankapp.dto.LoginDto;
import com.example.simplebankapp.dto.RegistrationDto;
import com.example.simplebankapp.dto.ResponseDto;
import com.example.simplebankapp.dto.TransactionDto;
import com.example.simplebankapp.models.DataBase;
import com.example.simplebankapp.models.User;
import com.example.simplebankapp.service.CreateNewUserService;
import com.example.simplebankapp.service.serviceimplementation.CustomerWithdrawServiceImpl;
import com.example.simplebankapp.service.serviceimplementation.DepositServiceImpl;
import com.example.simplebankapp.service.serviceimplementation.GetAccountStatementService;
import com.example.simplebankapp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Controller {
    @Autowired
    AuthenticationManager authenticationManager;

    DataBase dataBase= new DataBase();
    @Autowired
    CreateNewUserService createNewUserService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private GetAccountStatementService getAccountStatementService;
    @Autowired
    private DepositServiceImpl depositService;

  CustomerWithdrawServiceImpl customerWithdrawService = new CustomerWithdrawServiceImpl();

    @PostMapping("/create_account")
    public ResponseEntity<?> createUser(@RequestBody RegistrationDto registrationDto){
     ResponseDto responseDto =  createNewUserService.createUser(registrationDto);
        if(responseDto.getStatus().equalsIgnoreCase("200"))return new  ResponseEntity<ResponseDto>(responseDto,HttpStatus.CREATED);
        else return new  ResponseEntity<ResponseDto>(responseDto,HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/account_info")
    public ResponseEntity<ResponseDto> getUserInfo(){
        ResponseDto responseDto = getAccountStatementService.getAcctInfo();
        if(responseDto.getStatus().equalsIgnoreCase("200"))return new  ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
        else return new  ResponseEntity<ResponseDto>(responseDto,HttpStatus.BAD_REQUEST);

    }
    @GetMapping("/account_statement")
    public ResponseEntity<ResponseDto> accountStatementList() {
         ResponseDto responseDto = getAccountStatementService.getAccountStatementList();
         if(responseDto.getStatus().equalsIgnoreCase("200"))return new  ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
         else return new  ResponseEntity<ResponseDto>(responseDto,HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ResponseDto> withdraw(@RequestBody TransactionDto transactionDto){
      ResponseDto responseDto =  customerWithdrawService.withdraw(transactionDto);
        if(responseDto.getStatus().equalsIgnoreCase("200"))   return  new ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
        else return new  ResponseEntity<ResponseDto>(responseDto,HttpStatus.BAD_REQUEST);
        }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto> deposit(@RequestBody TransactionDto transactionDto, HttpServletRequest req){
        ResponseDto responseDto = depositService.deposit(transactionDto);
        if(responseDto.getStatus().equalsIgnoreCase("200"))   return  new ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
        else return new  ResponseEntity<ResponseDto>(responseDto,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto> createAuthenticationToken(@RequestBody LoginDto loginDto){

        try {
            Authentication auth  = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUserAccountNumber(), loginDto.getPassword())
            );
            System.out.println(auth);
            SecurityContextHolder.getContext().setAuthentication(auth);
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(loginDto.getUserAccountNumber());

            final String jwt = jwtTokenUtil.generateToken(userDetails);
            ResponseDto responseDto = ResponseDto.builder()
                    .status("200")
                    .message("Successfully login")
                    .data(new AuthenticationResponse(jwt))
                    .build();
            return new  ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            ResponseDto responseDto = ResponseDto.builder()
                    .status("400")
                    .message("Incorrect username or password")
                    .build();
            return new   ResponseEntity<ResponseDto>(responseDto,HttpStatus.BAD_REQUEST);
        }


    }

}
