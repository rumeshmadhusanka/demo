package com.example.demo.repository;

import com.example.demo.MysqlDataSource;
import com.example.demo.dao.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@Repository
public class JDBCUserRepository implements UserService {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate ;
    public JDBCUserRepository(){
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
            return user;
        }
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUser(int userId) {
        String query = "select * from user where user_id =:id";
        return this.namedParameterJdbcTemplate.queryForObject(query,
                new MapSqlParameterSource("id", userId),
                new UserMapper());
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }

    @Override
    public boolean deleteUser(int userId) {
        return false;
    }
}
