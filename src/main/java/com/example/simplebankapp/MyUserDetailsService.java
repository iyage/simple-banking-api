package com.example.simplebankapp;

import com.example.simplebankapp.models.DataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
   @Autowired
   DataBase dataBase;
    @Override
    public UserDetails loadUserByUsername(String userAccount) throws UsernameNotFoundException {
        Optional<com.example.simplebankapp.models.User> optionalUser =Optional.ofNullable( dataBase.findUserByAccountNumber(userAccount));
            return new User(optionalUser.get().getAccountNumber(),optionalUser.get().getAccountPassword(),
                    new ArrayList<>());
    }
}