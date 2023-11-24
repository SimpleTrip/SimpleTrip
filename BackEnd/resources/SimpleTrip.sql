drop database if exists trip;
create database trip;
use trip;

-- 과거 테이블
drop table if exists user;
drop table if exists spot;
drop table if exists article;
drop table if exists favorite;

-- 게시판 관련 삭제
drop table if exists articles;
drop table if exists comments;

-- 찜기능 삭제
drop table if exists favorites;
drop table if exists spots;


-- qna 관련 삭제
drop table if exists qna;

-- planner 관련 삭제
drop table if exists plan_item;
drop table if exists plan;

-- places 관련 삭제
drop table if exists places;

-- user 관련 삭제
drop table if exists users;

-- user 테이블 생성
create table users
(
    user_id    varchar(20) not null primary key,
    user_pw    varchar(20) not null,
    user_sex   enum ('M', 'F'),
    user_age   int,
    user_role  enum ('ROLE_ADMIN', 'ROLE_USER'),
    user_oauth varchar(20),
    user_exit  boolean default false
);

-- 임시 user 생성
insert into users (user_id, user_pw, user_sex, user_age, user_role, user_oauth, user_exit)
values ('ssafy', '1234', 'F', 24, 'ROLE_USER', 'KAKAO', false);

insert into users (user_id, user_pw, user_sex, user_age, user_role, user_oauth, user_exit)
values ('test', '1234', 'F', 24, 'ROLE_ADMIN', 'KAKAO', false);

insert into users (user_id, user_pw, user_sex, user_age, user_role, user_oauth, user_exit)
values ('ssafy2', '1234', 'F', 24, 'ROLE_USER', 'KAKAO', false);

-- 게시글 테이블 생성
create table articles
(
    article_id      int auto_increment primary key,
    article_title   varchar(50),
    article_content text,
    user_id         varchar(20) not null,
    foreign key (user_id) references users (user_id)
);

-- 임시 게시글 생성
insert into articles (article_title, article_content, user_id)
values ('aaaaaaaaa1', 'aaaaaaaaaaaaaaa1', 'ssafy')
     , ('bbbbbbbbbbbbbb2', 'bbbbbbbbbbbbbbbbbb2', 'ssafy')
     , ('ccccccccccccccccc3', 'ccccccccccccccccccc3', 'ssafy')
     , ('ddddddddddddddd4', 'ddddddddddddddddd4', 'ssafy')
     , ('eeeeeeeeeeeeeeeee5', 'eeeeeeeeeeeeeeeeeee5', 'ssafy')
     , ('aaaaaaaaa1', 'aaaaaaaaaaaaaaa1', 'ssafy')
     , ('bbbbbbbbbbbbbb2', 'bbbbbbbbbbbbbbbbbb2', 'ssafy')
     , ('ccccccccccccccccc3', 'ccccccccccccccccccc3', 'ssafy')
     , ('ddddddddddddddd4', 'ddddddddddddddddd4', 'ssafy')
     , ('eeeeeeeeeeeeeeeee5', 'eeeeeeeeeeeeeeeeeee5', 'ssafy')
     , ('aaaaaaaaa1', 'aaaaaaaaaaaaaaa1', 'ssafy')
     , ('bbbbbbbbbbbbbb2', 'bbbbbbbbbbbbbbbbbb2', 'ssafy')
     , ('ccccccccccccccccc3', 'ccccccccccccccccccc3', 'ssafy')
     , ('ddddddddddddddd4', 'ddddddddddddddddd4', 'ssafy')
     , ('eeeeeeeeeeeeeeeee5', 'eeeeeeeeeeeeeeeeeee5', 'ssafy');

-- 게시글에 대한 댓글 테이블 생성
create table `comments`
(
    comment_no      int auto_increment primary key,
    article_id      int not null,
    comment_content varchar(200),
    comment_user_id varchar(20),
    foreign key (article_id) references articles (article_id),
    foreign key (comment_user_id) references users (user_id)
);

-- 게시글에 대한 임시 댓글 생성
insert into `comments` (comment_content, comment_user_id, article_id)
values ('test comment insert1', 'ssafy', 1),
       ('test comment insert2', 'ssafy', 1),
       ('test comment insert3', 'ssafy', 1);
insert into `comments` (comment_content, comment_user_id, article_id)
values ('test comment insert1', 'ssafy', 2),
       ('test comment insert2', 'ssafy', 2),
       ('test comment insert3', 'ssafy', 2);

-- 지점 테이블 생성
create table spots
(
    spot_id      int primary key,
    spot_name    varchar(50),
    spot_addr    varchar(100),
    spot_img_url varchar(100)
);

-- 찜 테이블 생성
create table favorites
(
    favorite_id int auto_increment primary key,
    user_id     varchar(20),
    spot_id     int,
    foreign key (user_id) references users (user_id),
    unique key unique_user_spot (user_id, spot_id)
);

create table places
(
    place_id      int auto_increment primary key,
    place_name    varchar(50),
    place_addr    varchar(100),
    place_type    varchar(20),
    place_content text,
    place_date    date,
    place_img_url varchar(100),
    place_lat     double,
    place_lng     double
);

create table qna
(
    qna_no           int auto_increment primary key,
    question         varchar(100),
    answer           varchar(100),
    question_user_id varchar(20),
    answer_admin_id  varchar(20),
    foreign key (question_user_id) references users (user_id),
    foreign key (answer_admin_id) references users (user_id)
);

-- qna 임의 생성(pagination 확인용으로 많이 생성)
insert into qna (question, question_user_id)
values ('test1', 'ssafy'),
       ('test2', 'ssafy'),
       ('test3', 'ssafy'),
       ('test4', 'ssafy'),
       ('test5', 'ssafy'),
       ('test6', 'ssafy'),
       ('test7', 'ssafy'),
       ('test8', 'ssafy'),
       ('test9', 'ssafy'),
       ('test10', 'ssafy')
        ,
       ('test11', 'ssafy'),
       ('test12', 'ssafy'),
       ('test13', 'ssafy'),
       ('test14', 'ssafy'),
       ('test15', 'ssafy'),
       ('test16', 'ssafy'),
       ('test17', 'ssafy'),
       ('test18', 'ssafy'),
       ('test19', 'ssafy'),
       ('test20', 'ssafy')
        ,
       ('test31', 'ssafy'),
       ('test32', 'ssafy'),
       ('test33', 'ssafy'),
       ('test34', 'ssafy'),
       ('test35', 'ssafy'),
       ('test36', 'ssafy'),
       ('test37', 'ssafy'),
       ('test38', 'ssafy'),
       ('test39', 'ssafy'),
       ('test40', 'ssafy')
        ,
       ('test41', 'ssafy'),
       ('test42', 'ssafy'),
       ('test43', 'ssafy'),
       ('test44', 'ssafy'),
       ('test45', 'ssafy'),
       ('test46', 'ssafy'),
       ('test47', 'ssafy'),
       ('test48', 'ssafy'),
       ('test49', 'ssafy'),
       ('test50', 'ssafy')
        ,
       ('test51', 'ssafy'),
       ('test52', 'ssafy'),
       ('test53', 'ssafy'),
       ('test54', 'ssafy'),
       ('test55', 'ssafy'),
       ('test56', 'ssafy'),
       ('test57', 'ssafy'),
       ('test58', 'ssafy'),
       ('test59', 'ssafy'),
       ('test60', 'ssafy')
        ,
       ('test61', 'ssafy'),
       ('test62', 'ssafy'),
       ('test63', 'ssafy'),
       ('test64', 'ssafy'),
       ('test65', 'ssafy'),
       ('test66', 'ssafy'),
       ('test67', 'ssafy'),
       ('test68', 'ssafy'),
       ('test69', 'ssafy'),
       ('test70', 'ssafy');

-- planner 중 plan 테이블 생성
create table `plan`
(
    plan_no      int auto_increment primary key,
    start_date   varchar(30),
    end_date     varchar(30),
    plan_title   varchar(100),
    plan_content varchar(500),
    plan_user_id varchar(20),
    regist_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foreign key (plan_user_id) references users (user_id)
);

-- planner중 plan_item 테이블 생성
create table plan_item
(
    id                  int not null,
    plan_no             int not null,
    place_name          varchar(30),
    address_name        varchar(100),
    road_address_name   varchar(100),
    category_group_code varchar(10),
    category_group_name varchar(50),
    category_name       varchar(80),
    place_url           varchar(100),
    x                   varchar(20),
    y                   varchar(20),
    memo                varchar(500),
    foreign key (plan_no) references plan (plan_no),
    primary key (id, plan_no)
);
