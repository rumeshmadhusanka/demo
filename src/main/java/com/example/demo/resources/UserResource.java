package com.example.demo.resources;


import com.example.demo.ExtractObject;
import com.example.demo.ResponseFormat;
import com.example.demo.dao.User;
import com.example.demo.repository.JDBCUserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RestController
@RequestMapping(path = "/user")
public class UserResource {

    private static JDBCUserRepository jdbcUserRepository = new JDBCUserRepository();

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseFormat getUser(@PathVariable("userId") int userId, HttpServletResponse response) {
        ArrayList<User> list = new ArrayList<>(); //array list to send data
        ResponseFormat responseFormat; //Response object
        User user; //user returned by the sql query
        try {
            user = jdbcUserRepository.getUser(userId);
            user.setPassword(null); //do not expose password to outside
            response.setStatus(HttpServletResponse.SC_OK);
            responseFormat = new ResponseFormat("success", "OK", list);
            list.add(user);
            ExtractObject.getAttributesAndData(user);
        } catch (EmptyResultDataAccessException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseFormat = new ResponseFormat("failed", "No user found", list);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            responseFormat = new ResponseFormat("failed", "Bad Request", list);
        }


        return responseFormat;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseFormat addUser(@RequestBody User user) {
        return null;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseFormat updateUser(@PathVariable String userId) {
        return null;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseFormat deleteUser(int userId) {
        return null;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseFormat getAllUsers() {
        return null;
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public ResponseFormat searchUserByName(@RequestParam String name,HttpServletResponse response){
        //  localhost/user/search?name=Amal
        ArrayList<User> list = new ArrayList<User>(); //array list to send data
        ResponseFormat responseFormat; //Response object
        try{
            list = jdbcUserRepository.searchUsersByName(name);
            response.setStatus(HttpServletResponse.SC_OK);
            responseFormat = new ResponseFormat("success", "OK", list);
        }catch (EmptyResultDataAccessException e){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseFormat = new ResponseFormat("failed", "No user found", list);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            responseFormat = new ResponseFormat("failed", "Bad Request", list);
            System.out.println(e);
        }
        return responseFormat;
    }

}
