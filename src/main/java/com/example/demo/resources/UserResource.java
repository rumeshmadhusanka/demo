package com.example.demo.resources;

import com.example.demo.ResponseFormat;
import com.example.demo.dao.User;
import com.example.demo.repository.JDBCUserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
            response.setStatus(HttpServletResponse.SC_OK);
            responseFormat = new ResponseFormat("success", "OK", list);
            list.add(user);
        } catch (EmptyResultDataAccessException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseFormat = new ResponseFormat("failed", "No user found", list);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            responseFormat = new ResponseFormat("failed", "Bad Request", list);
        }
        return responseFormat;
    }

    public boolean addUser(User user) {
        return false;
    }


    public int updateUser(User user) {
        return 0;
    }

    public boolean deleteUser(int userId) {
        return false;
    }

    public List<User> getAllUsers() {
        return null;
    }

}
