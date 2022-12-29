package vttp.PAFWS.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.PAFWS.models.Task;
import vttp.PAFWS.models.User;

import static vttp.PAFWS.repositories.Queries.*;

import java.util.Optional;


@Repository
public class TaskRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Optional<User> findUserByUserId(String username){
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_USER, username);
        User user = new User();
        rs.next();
        try {
            user.setName(rs.getString("name"));
            user.setUser_id(rs.getString("user_id"));
            user.setUsername(rs.getString("username"));
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    public String insertUser(User user){
        jdbcTemplate.update(SQL_INSERT_USER, user.getUser_id(), user.getUsername(), user.getName());
        return user.getUser_id();
    }

    public boolean insertTask(Task task){
        int res = jdbcTemplate.update(SQL_INSERT_TASK, task.getDescription(), task.getPriority(), task.getDate());
        return res >0;
    }

}
