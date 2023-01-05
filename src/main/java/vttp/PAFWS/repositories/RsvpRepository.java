package vttp.PAFWS.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import static vttp.PAFWS.repositories.Queries.*;

import java.util.Optional;

@Repository
public class RsvpRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public SqlRowSet getAllRsvps(){
        return jdbcTemplate.queryForRowSet(SQL_GETALL_RSVPS);
    }

    public Optional<String> findRSVPByEmail(String email){
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_RSVP, email);
        String name = "";
        rs.next();
        try {
            name = rs.getString("name");
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(name);
    }

    public String insertRsvp(String name, String email, String comment){
        jdbcTemplate.update(SQL_INSERT_RSVP, name, email, comment);
        return name;
    }

    public int updateRSVP(String name, String email, String comment){
        return jdbcTemplate.update(SQL_UPDATE_RSVP, name, comment, email);
    }

}
