package com.example.simplebankapp.service.serviceimplementation;

import com.example.simplebankapp.dto.RegistrationDto;
import com.example.simplebankapp.models.DataBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CreateNewUserServiceimplTest {
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    CreateNewUserServiceimpl createNewUserServiceimpl = new CreateNewUserServiceimpl();

    DataBase dataBase = new DataBase();


    @BeforeEach
    void setUp() {

    }
  @Test
    public void checkIfUserIsCreated() {
      Mockito.when(passwordEncoder.encode(any())).thenReturn("zxcvbhnvbnj");
        RegistrationDto registrationD;
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setAccountName("foo");
        registrationDto.setAccountPassword("1234");
        registrationDto.setInitialDeposit(500.00);
        createNewUserServiceimpl.createUser(registrationDto);
        assertEquals(dataBase.getTotalUser(), 1);
    }
}