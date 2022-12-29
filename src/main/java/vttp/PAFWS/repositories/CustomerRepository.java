package vttp.PAFWS.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import static vttp.PAFWS.repositories.Queries.*;//NEED TO IMPORT THIS SO CAN USE THE QUERY STATEMENT FROM QUERIES

@Repository
public class CustomerRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getAllCust(Integer limit, Integer offset){
        List<String> res = new LinkedList<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(ALL_CUSTOMERS, limit, offset);
        while(rs.next()){
            res.add(rs.getString("name"));
        }
        return res;
    }

    public SqlRowSet getCustomerByID(Integer id){
        return jdbcTemplate.queryForRowSet(CUSTOMER_BY_ID, id);
    }

    public SqlRowSet getCustomerOrders(Integer id){
        return jdbcTemplate.queryForRowSet(CUSTOMER_ORDERS_BY_ID, id);
    }

    public int addOrder(Integer id, String name, String city){
        return jdbcTemplate.update(ADD_ORDER, id, name, city, name, city);
    }

    public int updateOrder(String name, String city, Integer id){
        return jdbcTemplate.update(UPDATE_ORDER, name, city, id);
    }

    public SqlRowSet countOrders(){
        return jdbcTemplate.queryForRowSet(SQL_COUNT_ORDERS);
    }

    public SqlRowSet lastOrder(){
        return jdbcTemplate.queryForRowSet(SQL_LAST_ORDER);
    }

    public int addOrderDetails(int id, float quantity, float price){
        return jdbcTemplate.update(SQL_ADD_ORDERDETAILS, id, quantity, price);
    }

}
