insert into order_status (status) values ('NEW');
insert into order_status (status) values ('IN_PROGRESS');
insert into order_status (status) values ('COMPLETE');

insert into category (category) values ('Смартфоны и гаджеты');
insert into category (category) values ('Компьютеры и ноутбуки');
insert into category (category) values ('Техника для дома');
insert into category (category) values ('Техника для кухни');

insert into product (id_category, product, price, info) 
values (1, 'Samsung Galaxy S10', 60990.95, 'Не просто особенный смартфон. Это смартфон будущего.');
insert into product (id_category, product, price, info) 
values (1, 'Планшет Prestigio Wize PMT3161 3G Black', 5000.00, 'Доступная модель с большим экраном в лаконичном эргономичном корпусе.');

insert into product (id_category, product, price, info) 
values (2, 'Ноутбук Apple MacBook Pro 13', 90000.30, 'Компактный и мобильный, но при этом мощный компьютер с 13,3-дюймовым дисплеем высокого разрешения.');
insert into product (id_category, product, price, info) 
values (2, 'Компьютер HP Pavilion 590-p0025ur', 35000.00, 'Стационарный компьютер способен работать в многозадачном режиме и имеет высокую производительность.');

insert into product (id_category, product, price, info) 
values (3, 'Пылесос с пылесборником Tefal City Space TW2641EA', 3000.00, 'Мощный и компактный пылесос TEFAL City Space TW2641EA создан для комфортной уборки в небольших помещениях.');
insert into product (id_category, product, price, info) 
values (3, 'Утюг Tefal Maestro 2 FV1846E0', 2000.00, 'Быстрое и эффективное глажение.'); 

insert into product (id_category, product, price, info) 
values (4, 'Блендер Scarlett SC-HB42F47', 2050.00, 'Погружной блендер Scarlett SC-HB42F47 снабжен насадкой из прочной нержавеющей стали. Она позволяет измельчать любые продукты — твердые и мягкие, плотные, вязкие, горячие и холодные.');
insert into product (id_category, product, price, info) 
values (4, 'Кофемашина DeLonghi ECAM 22.110 B Magnifica S', 25000.00, 'Кофемашина DeLonghi ECAM 22.110 B Magnifica S максимально упрощает приготовление тонизирующего напитка.');


INSERT INTO `mystore`.`orders` (`id_order_status`, `first_name`, `last_name`, `phone`, `version`) 
VALUES ('1', 'Art', 'Pyat', '33-55-88', '0');

INSERT INTO `mystore`.`order_detail` (`id_order`, `id_product`, `amount`, `summa`, `version`) 
VALUES ('1', '1', '2', '120000.54', '0');

INSERT INTO `mystore`.`order_detail` (`id_order`, `id_product`, `amount`, `summa`, `version`) 
VALUES ('1', '2', '3', '60000.45', '0');
