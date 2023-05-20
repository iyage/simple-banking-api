package com.example.simplebankapp.service;
import com.example.simplebankapp.dto.RegistrationDto;
import com.example.simplebankapp.dto.ResponseDto;
import com.example.simplebankapp.models.User;

public interface CreateNewUserService {
    public ResponseDto createUser(RegistrationDto registrationDto);

}
