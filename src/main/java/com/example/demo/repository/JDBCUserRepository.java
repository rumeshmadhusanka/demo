package com.example.demo.repository;

import com.example.demo.ExtractObject;
import com.example.demo.MysqlDataSource;
import com.example.demo.dao.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class JDBCUserRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JDBCUserRepository() {
        DataSource dataSource = MysqlDataSource.getInstance();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    static final class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setLastLogin(rs.getTimestamp("last_login"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    }



    public User getUser(int userId) {
        String query = "select * from user where user_id =:id";
        return this.namedParameterJdbcTemplate.queryForObject(query,
                new MapSqlParameterSource("id", userId),
                new UserMapper());
    }


    public boolean addUser(User user) {
        String query = "insert into user(first_name,last_name,email,password) " +
                "values(:first_name,:last_name,:email,:password)";
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("first_name", user.getFirstName());
//        paramMap.put("last_name", user.getLastName());
//        paramMap.put("email", user.getEmail());
//        paramMap.put("password", user.getPassword());
        paramMap = ExtractObject.getAttributesAndData(user);//experimental
        int status = namedParameterJdbcTemplate.update(query, paramMap);
        return status != 0;
    }


    public boolean updateUser(User user) {
        String query = "update user set first_name=:first_name,last_name=:last_name,email=:email,password=:password where user_id=:user_id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("user_id", user.getUserId());
        paramMap.put("first_name", user.getFirstName());
        paramMap.put("last_name", user.getLastName());
        paramMap.put("email", user.getEmail());
        paramMap.put("password", user.getPassword());
        int status = namedParameterJdbcTemplate.update(query, paramMap);
        return status != 0;
    }


    public boolean deleteUser(int userId) {
        String query = "delete from user where user_id=:id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("user_id", userId);
        int status = namedParameterJdbcTemplate.update(query, paramMap);
        return status != 0;
    }

    public ArrayList<User> searchUsersByName(String keyword) {
        String query = "select user_id, first_name, last_name,email from user where first_name like :first_name or last_name like :last_name ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("first_name", keyword);
        paramMap.put("last_name", keyword);
        List<Map<String, Object>> list = this.namedParameterJdbcTemplate.queryForList(query, paramMap);
        ArrayList<User> array = new ArrayList<User>();
        for (Map<String, Object> mapItem : list) {
            User u = new User();
            System.out.println((String) mapItem.get("first_name"));
            u.setFirstName((String) mapItem.get("first_name"));
            u.setLastName((String) mapItem.get("last_name"));
            u.setEmail((String) mapItem.get("email"));
            //u.setLastLogin(Timestamp.valueOf((String) mapItem.get("last_name")));
            u.setUserId((Integer) mapItem.get("user_id"));
            array.add(u);
        }
        return array;
    }


}
