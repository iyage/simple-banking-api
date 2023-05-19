package com.example.simplebankapp.models;
import com.example.simplebankapp.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String accountName;
    private String accountPassword;
    private String accountNumber;
   private List<AccountStatement>accountStatementList = new ArrayList<>();
   private  Double accountBalance;
   private Roles roles;
    private Boolean isEnabled;


}
