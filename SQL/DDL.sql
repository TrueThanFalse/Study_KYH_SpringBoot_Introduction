< 24-01-28 >
● member 테이블 생성 쿼리 구문.
drop table if exists member CASCADE;
create table member
(
id bigint generated by default as identity,
name varchar(255),
primary key (id)
);