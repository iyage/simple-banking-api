package com.example.simplebankapp.models;
import com.example.simplebankapp.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String firstName;
    private String lastName;
    @JsonIgnore
    private String accountPassword;
    private String accountNumber;
    @JsonIgnore
   private List<AccountStatement>transactionHistory = new ArrayList<>();
   private  Double accountBalance;
   @JsonIgnore
   private Roles roles;
   @JsonIgnore
    private Boolean isEnabled;




}
