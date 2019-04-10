insert into users (username, password, enabled) values ('admin', '$2a$10$foozkfl2WKMLr8TJqP72JuU3KgxhM/Z9VcWQ6BQySh1rLmHcYSRJC', 1);
insert into users (username, password, enabled) values ('student', '$2a$10$HXL3kE1nHaf0htZniTNSUO22pdhVg5OKFVurp7kF9AVSD4o6R8O0i', 1);
insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');
insert into authorities (username, authority) values ('admin', 'ROLE_USER');
insert into authorities (username, authority) values ('student', 'ROLE_USER');
