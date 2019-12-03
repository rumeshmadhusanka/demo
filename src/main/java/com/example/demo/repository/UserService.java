package com.example.demo.repository;



import com.example.demo.ResponseFormat;
import com.example.demo.dao.User;

import java.util.ArrayList;


public interface UserService{
    //ArrayList<User> getAllUsers();
    ResponseFormat getUser(int userId);
    ResponseFormat addUser(User user);
    ResponseFormat updateUser(User user);
    ResponseFormat deleteUser(int userId);
    ResponseFormat searchUser();
}
