drop table if exists member;
create table member(id bigint generated by default as identity, email varchar(255) unique, profile_image varchar(255), nickname varchar(255), identify_token varchar(255), state_message varchar(255), created_at timestamp, updated_at timestamp,  primary key (id));