create table todo
(
  id int auto_increment primary key,
  version int,
  title varchar(225) not null,
  completed bit
);

insert into todo (title, version, completed) values ('todo 1', 0, 0);
insert into todo (title, version, completed) values ('todo 2', 0, 0);
insert into todo (title, version, completed) values ('todo 3', 0, 0);