create user larku password 'larku' CREATEDB CREATEROLE;

ALTER ROLE larku WITH LOGIN;
ALTER ROLE larku INHERIT;

set role larku;

create database AdopterDB;
