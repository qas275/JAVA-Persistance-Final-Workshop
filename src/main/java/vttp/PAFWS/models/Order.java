package vttp.PAFWS.models;

import java.text.SimpleDateFormat;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Order {
    private Integer id;
    private String name;
    private String city;
    private String date;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public static Order createOrder(SqlRowSet rs) throws Exception{
        Order order = new Order();
        SimpleDateFormat dateFormatter=new SimpleDateFormat("yyyy-mm-dd");
        order.setCity(rs.getString("ship_city"));
        if(null!=rs.getString("shipped_date")){
            order.setDate((dateFormatter.parse((rs.getString("shipped_date")))).toString());
        }else{
            order.setDate("NO DATE FOUND");
        }
        order.setId(rs.getInt("id"));
        order.setName(rs.getString("name"));
        return order;
    }

    public JsonObject toJSON(){
        JsonObjectBuilder job = Json.createObjectBuilder();
        return job.add("id", getId()).add("name", getName()).add("city", getCity()).add("date", getDate().toString()).build();
    }

}
