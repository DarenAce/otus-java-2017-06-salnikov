set global time_zone = '+3:00';
drop database if exists otus;
create database otus;
create user if not exists 'daren'@'localhost' identified by 'daren';
grant all privileges on otus.* to 'daren'@'localhost';