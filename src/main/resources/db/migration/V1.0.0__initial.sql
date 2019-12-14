create table todo
(
  id int auto_increment primary key,
  title varchar(225) not null,
  completed bit
);

insert into todo (title, completed) values ('todo 1', 0);
insert into todo (title, completed) values ('todo 2', 0);
insert into todo (title, completed) values ('todo 3', 0);