
use mystore;

create table users(
	username varchar(50) not null primary key,
	password varchar(100) not null,
	enabled boolean not null
);
create table authorities (
	username varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);

insert into users(username,password,enabled) values('admin','$2a$10$Ay5A9lF7fOCca4knhsQb9uy8v7uIxpiVcybjgQnYk45gsSx9xALAy',true);
insert into users(username,password,enabled) values('user_1','$2a$10$XrtbTlBsXkNIB3zuSGB11eSTdjbl3aKMFkE9qzLwecYiX61hGpPwG',true);

insert into authorities(username,authority) values('user_1','ROLE_USER');
insert into authorities(username,authority) values('admin','ROLE_ADMIN');
insert into authorities(username,authority) values('admin','ROLE_USER');