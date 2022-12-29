desc customers;
desc orders;
desc order_details;

select last_insert_id(id) id from orders order by id desc limit 1;

select * from northwind.order_details ;
select count(orders.id) from orders;

select id, concat(last_name,' ', first_name) name, city from customers where id = 19;

insert into orders (id, ship_name, ship_city) values (30, 'Karen Toh', 'Las Vegas') ON DUPLICATE KEY UPDATE    
ship_name="KK", ship_city='vegas';

insert into order_details (order_id, quantity, unit_price) values (83, 93.0, 2.3);

update orders set id=30, ship_name= "LL", ship_city="Las Vegas" where id=19;

select c.id, concat(c.first_name,' ', c.last_name) name, o.shipped_date, o.ship_city 
	from northwind.customers c join northwind.orders o on c.id=o.customer_id 
    where c.id=2;

select o.id, customer_id, order_date,quantity*unit_price*(1-discount) as total_price, p.quantity_per_unit*p.standard_cost as cost_price from orders o 
	join order_details od on o.id = od.order_id
    join products p on p.id = od.product_id
    where o.id = 45;

-- usage of aliases within the query can only before operations after aggregation, such as group and order and having
select ship_state_province state, sum(shipping_fee) fees, payment_type cards from orders
group by ship_state_province, payment_type
order by ship_state_province
;

-- temporary table example, must alias all inner query
select tmp.state, tmp.fees as cost, tmp.cards from (
	select ship_state_province state, sum(shipping_fee) fees, payment_type cards from orders
		group by ship_state_province, payment_type
		order by ship_state_province
	) as tmp
    where tmp.fees>100
;
-- need to specify the database if want to use another database's data
select * from task;
select * from user;
select * from northwind.customers;

insert into user(user_id, username, name) values('c4ec9cc8','user1','karen goh');
insert into task(description, priority, due_date) values('c4ec9cc8','user1','karen goh');
