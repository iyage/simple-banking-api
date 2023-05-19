package com.example.simplebankapp.service.serviceimplementation;
import com.example.simplebankapp.MinimumInitialDepositException;
import com.example.simplebankapp.dto.RegistrationDto;
import com.example.simplebankapp.enums.Roles;
import com.example.simplebankapp.models.DataBase;
import com.example.simplebankapp.models.User;
import com.example.simplebankapp.service.CreateNewUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
@Service
public class CreateNewUserServiceimpl implements CreateNewUserService {
    @Autowired
    PasswordEncoder passwordEncoder;



    @Override
    public User createUser(RegistrationDto registrationDto) {
       DataBase dataBase = new DataBase();
        String userName = registrationDto.getAccountName();
        String userPassWord = registrationDto.getAccountPassword();
        Double initialDeposit = registrationDto.getInitialDeposit();
        User user = new User();
        System.out.println();
       UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        id = id.substring(26,id.length());
        id = "0x"+id;
        id = (Long.decode(id)).toString();
         user.setAccountNumber(id.substring(0,10));
        System.out.println(id);
        user.setAccountNumber(id);
        user.setAccountName(userName);
        user.setRoles(Roles.USER);
        user.setIsEnabled(true);
        user.setAccountPassword(passwordEncoder.encode(userPassWord));
        if(registrationDto.getInitialDeposit()>=500){
            user.setAccountBalance(registrationDto.getInitialDeposit());

        }
        else throw  new MinimumInitialDepositException("initial deposit must be 500 naira and above");
        dataBase.addUser(user);
        return user;
    }
}
