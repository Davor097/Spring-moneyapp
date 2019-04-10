create table if not exists users (
username varchar(50) not null,
password varchar(100) not null,
enabled bit not null
);

create table if not exists authorities (
username varchar(50) not null,
authority varchar(20) not null
);

create table if not exists wallets (
id long primary key auto_increment,
currentAmount int,
name varchar(50) not null,
owner varchar(50) not null,
foreign key (owner) references users(username)
);

create table if not exists expenses (
id long not null primary key auto_increment,
name varchar(50) not null,
amount int not null,
type varchar(10) not null,
date timestamp not null,
walletId int not null,
foreign key (walletId) references wallets(id)
);

