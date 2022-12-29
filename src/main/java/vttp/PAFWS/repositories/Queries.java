package vttp.PAFWS.repositories;

public class Queries {
    public static final String ALL_CUSTOMERS= "select concat(first_name,' ', last_name) name from northwind.customers limit ? offset ?";

    public static final String CUSTOMER_BY_ID = "select id, concat(last_name,' ', first_name) name, city from northwind.customers where id = ?";

    public static final String CUSTOMER_ORDERS_BY_ID = "select c.id, concat(c.first_name,' ', c.last_name) name, o.shipped_date, o.ship_city from northwind.customers c join northwind.orders o on c.id=o.customer_id where c.id=?";

    //on dup key required if wanna check if existing key already then update it, if not existing then create new
    public static final String ADD_ORDER = "insert into northwind.orders (id, ship_name, ship_city) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE ship_name=?, ship_city=?";

    public static final String UPDATE_ORDER = "update northwind.orders set ship_name= ?, ship_city=? where id=?";

    public static final String SQL_COUNT_ORDERS = "select count(orders.id) as Orders from northwind.orders";
    
    public static final String SQL_LAST_ORDER = "select last_insert_id(id) id from northwind.orders order by id desc limit 1";

    public static final String SQL_ADD_ORDERDETAILS = "insert into northwind.order_details (order_id, quantity, unit_price) values (?, ?, ?);";

    public static final String SQL_FIND_USER = "select * from pafws.user where username=?";

    public static final String SQL_INSERT_USER = "insert into pafws.user(user_id, username, name) values(?,?,?)";

    public static final String SQL_INSERT_TASK = "insert into pafws.task(description, priority, due_date) values(?,?,?)";
}
