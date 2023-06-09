package com.example.simplebankapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {
    String firstName;
    String lastName;
    String accountPassword;
    Double initialDeposit;
}
