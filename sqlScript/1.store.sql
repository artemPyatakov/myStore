CREATE DATABASE `mystore`;

use mystore;
SET SQL_SAFE_UPDATES = 0;

#Статусы заказа
create table order_status(
id_order_status int AUTO_INCREMENT primary key,
status varchar(50)
);

#Категории товаров
create table category(
id_category int AUTO_INCREMENT primary key,
category varchar(255),
version int default 0
);

#Товары
create table product (
id_product int AUTO_INCREMENT primary key,
id_category int,
product varchar(500),
price decimal(15,2),
info varchar(500),
version int default 0,
constraint cat_fk foreign key (id_category) references category (id_category)
);

#заказы
create table orders (
id_order int AUTO_INCREMENT primary key,
id_order_status int,
first_name varchar(50),
last_name varchar(50),
phone varchar(50),
version int default 0,
created_date datetime default now(),
last_modified_date datetime default now(),
constraint ost_fk foreign key (id_order_status) references order_status (id_order_status)
);

#Продукты в заказе
create table order_detail (
id_order_detail int AUTO_INCREMENT primary key,
id_order int,
id_product int,
amount int,
summa decimal(15,2),
version int default 0,
constraint ord_fk foreign key (id_order) references orders (id_order) on delete cascade,
constraint prd_fk foreign key (id_product) references product (id_product),
unique(id_order, id_product)
);

CREATE TRIGGER orders_lastmodified
BEFORE update ON `orders`
FOR EACH ROW
SET new.last_modified_date = now();























