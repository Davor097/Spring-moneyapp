create table if not exists user (
username varchar(50) not null,
password varchar(100) not null,
enabled bit not null
);

create table if not exists authority (
username varchar(50) not null,
authority varchar(20) not null
);

create table if not exists wallet (
id long primary key auto_increment,
currentAmount int not null,
name varchar(50) not null,
owner varchar(50) not null
);

create table if not exists expense (
id long not null primary key auto_increment,
name varchar(50) not null,
amount int not null,
type varchar(10) not null,
date timestamp not null,
walletId int not null,
foreign key (walletId) references wallet(id)
);

