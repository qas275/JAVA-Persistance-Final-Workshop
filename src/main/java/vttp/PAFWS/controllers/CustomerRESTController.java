package vttp.PAFWS.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp.PAFWS.models.Customer;
import vttp.PAFWS.models.Order;
import vttp.PAFWS.services.CustomerService;
import vttp.PAFWS.services.OrderException;

@RestController
@EnableWebMvc
@RequestMapping(path = "/api")
public class CustomerRESTController {
    
    @Autowired
    private CustomerService cSvc;

    @GetMapping(path="/customers")
    public ResponseEntity<String> getAllCustomers(@RequestParam Integer offset, @RequestParam Integer limit){
        List<String> allCust = cSvc.getAllCust(limit, offset);
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (String  customer : allCust) {
            jab.add(customer);
        }
        JsonArray res = jab.build();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(res.toString());

    }

    @GetMapping(path="/customer/{cust_id}")
    public ResponseEntity<String> getCustomerID(@PathVariable Integer cust_id){
        Optional<Customer> custOpt = cSvc.getCustomerByID(cust_id);
        if(custOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body("INVALID CUST ID :" + String.valueOf(cust_id));
        }
        Customer cust = custOpt.get();
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("ID", cust.getId()).add("name", cust.getName()).add("city", cust.getCity());
        JsonObject jo = job.build();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(jo.toString());
    }

    @GetMapping(path = "/customer/{cust_id}/orders")
    public ResponseEntity<String> getCustomerOrders(@PathVariable Integer cust_id){
        List<Order> orders = cSvc.getCustomerOrdersByID(cust_id);
        if(orders.size()<1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body("INVALID CUST ID OR NO ORDERS MADE WITH CUST ID");
        }
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Order order : orders) {
            jab.add(order.toJSON());
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(jab.build().toString());
    }

    @PostMapping(path = "/addOrder")
    public ResponseEntity<String> addOrder(@RequestBody MultiValueMap<String, String> body){
        Integer id = Integer.parseInt(body.getFirst("id"));
        String name = body.getFirst("name");
        String city = body.getFirst("city");
        if(!cSvc.addOrder(id, name, city)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body("INVALID CUST ID OR NO ORDERS MADE WITH CUST ID");
        }
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body("ORDER ADDED");
    }

    @PutMapping(path = "/updateOrder")
    public ResponseEntity<String> updateOrder(@RequestBody MultiValueMap<String, String> body){
        String name = body.getFirst("name");
        String city = body.getFirst("city");
        Integer id = Integer.parseInt(body.getFirst("id"));
        if(!cSvc.updateOrder(name, city, id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body("INVALID CUST ID OR NO ORDERS MADE WITH CUST ID");
        }
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body("ORDER UPDATED");
    }

    @GetMapping(path = "/countOrders")
    public ResponseEntity<String> countOrders(){
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(Integer.toString(cSvc.countOrders()));
    }

    @PostMapping(path="/addOrderDetails")
    public ResponseEntity<String> addOrderDetails(@RequestBody MultiValueMap<String, String> body){
        String name = body.getFirst("name");
        String city = body.getFirst("city");
        Float quantity = Float.parseFloat(body.getFirst("quantity"));
        Float price = Float.parseFloat(body.getFirst("price"));
        try {
            if(cSvc.updateOrderDetails(name, city, quantity, price)){
                return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body("ADDED ORDER DETAILS");
            }
        } catch (OrderException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body("INVALID CUST ID OR NO ORDERS MADE WITH CUST ID");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body("INVALID CUST ID OR NO ORDERS MADE WITH CUST ID");
    }

}
