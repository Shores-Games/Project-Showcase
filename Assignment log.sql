/*
SELECT product_code, product_name, list_price, discount_percent
FROM products;
*/

/*
SELECT concat(last_name, ', ', first_name) AS full_name
FROM customers
WHERE last_name BETWEEN 'M' AND 'Z'
ORDER BY last_name ASC;
*/

/*
SELECT product_name, list_price, date_added
FROM products
WHERE list_price > 500 AND list_price < 2000
ORDER BY date_added DESC;
*/
/*
SELECT category_name AS CATEGORY, product_name AS PRODUCT, list_price AS PRICE
FROM categories
INNER JOIN products ON categories.category_id = products.category_id
ORDER BY categories.category_name, products.product_name;
*/

/*
SELECT concat(customers.last_name, ', ', customers.first_name) AS CUSTOMER, 
concat(addresses.line1, ', ', addresses.city, 
', ', addresses.state, ' ', addresses.zip_code) AS ADDRESS
FROM customers
INNER JOIN addresses ON customers.customer_id = addresses.customer_id
WHERE customers.email_address = 'allan.sherwood@yahoo.com';
*/
/*
SELECT concat(customers.last_name, ', ', customers.first_name) AS CUSTOMER, 
orders.order_date AS 'ORDER DATE',
products.product_name AS 'PRODUCT',
order_items.item_price AS 'PRICE',
order_items.discount_amount AS 'DISCOUNT',
order_items.quantity AS 'QUANTITY'
FROM customers
INNER JOIN orders ON customers.customer_id = orders.customer_id
INNER JOIN order_items ON orders.order_id = order_items.order_id
INNER JOIN products ON order_items.product_id = products.product_id
ORDER BY customers.last_name, orders.order_date, products.product_name;
*/

/*
INSERT INTO categories (category_name) VALUES ('Brass');
SELECT category_id, category_name 
FROM categories;
*/

/*
UPDATE categories SET category_name = 'Woodwinds' WHERE category_id = 5;
SELECT category_id, category_name 
FROM categories;
*/

/*
DELETE FROM categories WHERE category_id = 5;
SELECT category_id, category_name 
FROM categories;
*/

/*
INSERT INTO products (category_id, product_code, product_name, description, 
list_price, discount_percent, date_added)
VAlUES (4, 'dgx_640', 'Yamaha DGX 640 88-Key Digital Piano', 'Long description to come.',
799.99, 0, NOW());
SELECT category_id, product_code, product_name, description, 
list_price, discount_percent, date_added
FROM products
WHERE category_id = 4;
*/

/*
SELECT COUNT(order_id) AS 'Count of Orders', SUM(tax_amount) AS 'Sum of Tax'
FROM orders;
*/

/*
SELECT categories.category_name AS 'Category', COUNT(products.product_id) AS 'Count',
MAX(products.list_price) AS 'MAX Price'
FROM categories
INNER JOIN products ON categories.category_id = products.category_id
GROUP BY category_name
ORDER BY Count DESC; 
*/
/*
SELECT customers.email_address AS 'Email', 
SUM(order_items.item_price) * COUNT(order_items.item_id) AS 'Total Spent X Total Items'
FROM customers
INNER JOIN orders ON customers.customer_id = orders.customer_id
INNER JOIN order_items ON orders.order_id = order_items.order_id
GROUP BY customers.customer_id
ORDER BY SUM(order_items.item_price) DESC;
*/
/*
SELECT customers.email_address, 
COUNT(order_items.order_id) AS 'Count',
SUM(order_items.item_price - order_items.discount_amount) 
* COUNT(order_items.order_id) AS 'Total'
FROM customers
INNER JOIN orders ON customers.customer_id = orders.customer_id
INNER JOIN order_items ON orders.order_id = order_items.order_id
GROUP BY customers.customer_id
HAVING Count > 1
ORDER BY Total DESC;
*/
/*
SELECT order_id AS 'Order_Number', COUNT(product_id) AS 'Product_Amount'
FROM order_items
GROUP BY order_id
HAVING COUNT(product_id) > 1;
*/

/*
DELIMITER $$
CREATE PROCEDURE insert_category (
IN category_name VARCHAR(25)
)
BEGIN
INSERT INTO categories (category_name)
VALUES (category_name);
END $$ DELIMITER ;

CALL insert_category ('WOODWIND');
CALL insert_category ('BRASS');

SELECT category_name
FROM categories;
*/

/*
DELIMITER $$
CREATE FUNCTION discount_price(id INT) 
RETURNS DECIMAL(10,2)
READS SQL DATA
DETERMINISTIC
BEGIN
DECLARE price DECIMAL(10,2);
DECLARE discount DECIMAL(10,2);
SELECT item_price INTO price FROM order_items WHERE id = item_id;
SELECT discount_amount INTO discount FROM order_items WHERE id = item_id;
RETURN price-discount;
END $$ DELIMITER ;


SELECT item_id, item_price, discount_amount,
discount_price(item_id) AS 'Price After Discounts'
FROM order_items;
*/

/*
DELIMITER $$
CREATE FUNCTION item_total(id INT) 
RETURNS DECIMAL(10,2)
READS SQL DATA
DETERMINISTIC
BEGIN
DECLARE discountedPrice DECIMAL(10,2);
DECLARE myQuantity INT;
SET discountedPrice = discount_price(id);
SELECT quantity INTO myQuantity 
FROM order_items WHERE item_id = id;
RETURN discountedPrice * myQuantity;
END $$ DELIMITER ;

SELECT item_id, item_price, discount_amount,
discount_price(item_id) AS 'Price After Discounts', 
quantity, item_total(item_id) AS "Total Possible Revenue"
FROM order_items;
*/

/*
SELECT order_id AS 'Order_Number', product_id
FROM order_items;
*/

/*
SELECT product_name, list_price, discount_percent,
ROUND((list_price * discount_percent / 100), 2) AS discount_amount,
ROUND((list_price - (list_price * discount_percent / 100)), 2) AS discount_price
FROM products
ORDER BY discount_price desc
LIMIT 5;
*/

/*
SELECT item_id, item_price, discount_amount, quantity,
(item_price * quantity) AS price_total,
(discount_amount * quantity) AS discount_total,
((item_price - discount_amount) * quantity) AS item_total
FROM order_items
WHERE ((item_price - discount_amount) * quantity) > 500
ORDER BY item_total DESC;
*/

/*
SELECT prod1.product_name, prod1.list_price
FROM products AS prod1
JOIN products AS prod2 ON prod1.list_price = prod2.list_price
AND prod1.product_id != prod2.product_id
ORDER BY prod1.product_name;
*/

/*
SELECT c.category_name, p.product_id
FROM categories AS c
LEFT JOIN products AS p ON c.category_id = p.category_id
WHERE p.product_id IS NULL;
*/

/*
INSERT INTO customers (email_address, password, first_name, last_name)
VALUES ('rick@raven.com', '', 'Rick', 'Raven');

SELECT first_name, last_name, email_address, password
FROM customers;
*/

/*
SELECT email_address,
COUNT(DISTINCT product_id) AS distinct_product_count
FROM customers AS c
JOIN orders AS o ON c.customer_id = o.customer_id
JOIN order_items AS oi ON o.order_id = oi.order_id
GROUP BY c.email_address
HAVING distinct_product_count > 1
ORDER BY email_address ASC;
*/

/*
SELECT IF(GROUPING(category_name) = 1, 'GRAND TOTAL', category_name) AS category_name,
IF(GROUPING(product_name) = 1, 'CATEGORY TOTAL ROW', product_name) AS product_name,
SUM(quantity) AS total_quantity
FROM order_items AS oi
JOIN products AS p ON oi.product_id = p.product_id
JOIN categories AS c ON p.category_id = c.category_id
GROUP BY category_name, product_name WITH ROLLUP;
*/
/*
DROP USER 'BShores';
CREATE USER 'BShores'@'%' IDENTIFIED BY 'MySQLPassword1!';
GRANT SELECT, INSERT, UPDATE, DELETE ON my_guitar_shop.customers TO 'BShores'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON my_guitar_shop.addresses TO 'BShores'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON my_guitar_shop.orders TO 'BShores'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON my_guitar_shop.order_items TO 'BShores'@'%';
GRANT SELECT ON my_guitar_shop.products TO 'BShores'@'%';
GRANT SELECT ON my_guitar_shop.categories TO 'BShores'@'%';
REVOKE GRANT OPTION ON *.* FROM 'BShores'@'%';
SHOW GRANTS FOR 'BShores'@'%';
*/
