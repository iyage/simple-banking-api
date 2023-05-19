package com.example.simplebankapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
@Data
@NoArgsConstructor
public class DataBase {

 static Map<String, User> userList = new HashMap<>();
 public void addUser(User user){
  userList.put(user.getAccountNumber(),user);
 }
 public User findUserByAccountNumber(String accountNumber){
   User user = (userList.get(accountNumber));
return  user;
 }
 public List<User>getAllUser(){
  List<User>users = new ArrayList<>();
  userList.forEach((k,v)->{
      users.add(v);
  });
 return users;
 }
 public int getTotalUser(){
  return userList.size();
 }

}