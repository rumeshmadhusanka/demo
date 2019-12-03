package com.example.demo.repository;

import com.example.demo.MysqlDataSource;
import com.example.demo.dao.Passenger;
import com.example.demo.dao.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JDBCPassengerRepository{
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JDBCPassengerRepository() {
        DataSource dataSource = MysqlDataSource.getInstance();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

    }

    static final class PassengerMapper implements RowMapper<Passenger> {
        @Override
        public Passenger mapRow(ResultSet rs, int rowNum) throws SQLException {
            Passenger passenger = new Passenger();
            passenger.setUserId(rs.getInt("passenger_id"));
            passenger.setFirstName(rs.getString("first_name"));
            passenger.setLastName(rs.getString("last_name"));
            passenger.setLastLogin(rs.getTimestamp("last_login"));
            passenger.setPassword(rs.getString("password"));
            passenger.setEmail(rs.getString("email"));
            passenger.setNic(rs.getString("nic"));
            passenger.setPassport_id(rs.getString("passport_id"));
            return passenger;
        }
    }

    public Passenger getPassenger(int userId) {
        String query = "select * from passenger where user_id =:id";
        return  this.namedParameterJdbcTemplate.queryForObject(query,
                new MapSqlParameterSource("id", userId),
                new PassengerMapper());
    }

    public boolean addUser(User user) {
        String query = "insert into user(first_name,last_name,email,password) " +
                "values(:first_name,:last_name,:email,:password)";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("first_name", user.getFirstName());
        paramMap.put("last_name", user.getLastName());
        paramMap.put("email", user.getEmail());
        paramMap.put("password", user.getPassword());
        int status = namedParameterJdbcTemplate.update(query, paramMap);
        return status != 0;
    }
}
