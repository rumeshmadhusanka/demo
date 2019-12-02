package com.example.demo.repository;



import com.example.demo.dao.User;

import java.util.ArrayList;
import java.util.List;

public interface UserService{
    ArrayList<User> getAllUsers();
    User getUser(int userId);
    boolean addUser(User user);
    int updateUser(User user);
    boolean deleteUser(int userId);
}
