package vttp.PAFWS.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.PAFWS.models.Customer;
import vttp.PAFWS.models.Order;
import vttp.PAFWS.repositories.CustomerRepository;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository cRepo;

    public List<String> getAllCust(Integer limit, Integer offset) {
        return cRepo.getAllCust(limit, offset);
    }

    public Optional<Customer> getCustomerByID(Integer id){
        SqlRowSet rs = cRepo.getCustomerByID(id);
        Customer c = new Customer();
        while(rs.next()){// move cursor in rowset from headers to results in table
            c.setCity(rs.getString("city"));
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
        }
        if(null==c.getName()){
            return Optional.empty();
        }
        return Optional.of(c);
    }

    public List<Order> getCustomerOrdersByID(Integer id){
        SqlRowSet rs = cRepo.getCustomerOrders(id);
        List<Order> res = new LinkedList<>();
        while(rs.next()){
            try {
                res.add(Order.createOrder(rs));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public boolean addOrder(Integer id, String name, String city){
        return cRepo.addOrder(id, name, city)>0;
    }

    public boolean updateOrder(String name, String city, Integer id){
        return cRepo.updateOrder(name, city, id)>0;
    }

    public Integer countOrders(){
        SqlRowSet rs = cRepo.countOrders();
        Integer orders = 0;
        while(rs.next()){
            orders = rs.getInt("Orders");
        }
        return orders;
    }

    @Transactional (rollbackFor = OrderException.class)
    public boolean updateOrderDetails(String name, String city, Float quantity, Float price) throws OrderException{
        if(cRepo.addOrder(84, name, city)<1){
            throw new OrderException("cannot update!!!");
        }
        SqlRowSet rs = cRepo.lastOrder();
        int id=0;
        while(rs.next()){
            id = rs.getInt("id");
        }
        if(cRepo.addOrderDetails(id, quantity, price)<1){
            throw new OrderException("cannot add details!!!");
        }
        return true;
    }
}
