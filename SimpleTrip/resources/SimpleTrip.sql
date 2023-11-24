drop database if exists trip;
create database trip;
use trip;

drop table if exists user;
drop table if exists article;
drop table if exists spot;
drop table if exists favorite;

drop table if exists users;
drop table if exists qna;
drop table if exists comments;
drop table if exists articles;
drop table if exists spots;
drop table if exists favorites;

create table users (
	user_id varchar(20) not null primary key,
    user_pw varchar(20) not null,
    user_name varchar(20),
    user_sex enum('M', 'F'),
    user_age int,
    user_role enum('admin', 'user'),
    user_oauth varchar(20),
    user_exit boolean default false
);

insert into users (user_id, user_pw, user_name, user_sex, user_age, user_role, user_oauth, user_exit)
values
('ssafy', '1234', 'kim', 'F', 24, 'admin', 'KAKAO', false),
('ssafy2', '5678', 'lee', 'M', 25, 'user', 'GOOGLE', false),
('ssafy3', '789789', 'choi', 'M', 26, 'user', 'FB', false)
;

create table articles (
	article_id int auto_increment primary key,
    article_title varchar(50),
    article_content text,
	user_id varchar(20) not null,
	foreign key (user_id) references users (user_id)
);

insert into articles (article_title, article_content, user_id)
values ('aaaaaaaaa1', 'aaaaaaaaaaaaaaa1', 'ssafy'), ('bbbbbbbbbbbbbb2', 'bbbbbbbbbbbbbbbbbb2', 'ssafy'), ('ccccccccccccccccc3', 'ccccccccccccccccccc3', 'ssafy'), ('ddddddddddddddd4', 'ddddddddddddddddd4', 'ssafy'), ('eeeeeeeeeeeeeeeee5', 'eeeeeeeeeeeeeeeeeee5', 'ssafy')
     ,('aaaaaaaaa1', 'aaaaaaaaaaaaaaa1', 'ssafy'), ('bbbbbbbbbbbbbb2', 'bbbbbbbbbbbbbbbbbb2', 'ssafy'), ('ccccccccccccccccc3', 'ccccccccccccccccccc3', 'ssafy'), ('ddddddddddddddd4', 'ddddddddddddddddd4', 'ssafy'), ('eeeeeeeeeeeeeeeee5', 'eeeeeeeeeeeeeeeeeee5', 'ssafy')
     ,('aaaaaaaaa1', 'aaaaaaaaaaaaaaa1', 'ssafy'), ('bbbbbbbbbbbbbb2', 'bbbbbbbbbbbbbbbbbb2', 'ssafy'), ('ccccccccccccccccc3', 'ccccccccccccccccccc3', 'ssafy'), ('ddddddddddddddd4', 'ddddddddddddddddd4', 'ssafy'), ('eeeeeeeeeeeeeeeee5', 'eeeeeeeeeeeeeeeeeee5', 'ssafy');


create table spots (
	spot_id int primary key,
    spot_name varchar(50),
    spot_addr varchar(100)
);

create table favorites (
	favorite_id int auto_increment primary key,
    user_id varchar(20),
    spot_id int,
    foreign key (user_id) references users (user_id),
	foreign key (spot_id) references spots (spot_id)
);

create table qna (
                     qna_no int auto_increment primary key,
                     question varchar(100),
                     answer varchar(100),
                     question_user_id varchar(20),
                     answer_admin_id varchar(20),
                     foreign key (question_user_id) references users(user_id),
                     foreign key (answer_admin_id) references users(user_id)
);

insert into qna (question, question_user_id)
values ('test1', 'ssafy'), ('test2', 'ssafy'), ('test3', 'ssafy'), ('test4', 'ssafy'), ('test5', 'ssafy'),
       ('test6', 'ssafy'), ('test7', 'ssafy'), ('test8', 'ssafy'), ('test9', 'ssafy'), ('test10', 'ssafy')
        , ('test11', 'ssafy'), ('test12', 'ssafy'), ('test13', 'ssafy'), ('test14', 'ssafy'), ('test15', 'ssafy'),
       ('test16', 'ssafy'), ('test17', 'ssafy'), ('test18', 'ssafy'), ('test19', 'ssafy'), ('test20', 'ssafy')
        , ('test21', 'ssafy'), ('test22', 'ssafy'), ('test23', 'ssafy'), ('test24', 'ssafy'), ('test25', 'ssafy'),
       ('test26', 'ssafy'), ('test27', 'ssafy'), ('test28', 'ssafy'), ('test29', 'ssafy'), ('test30', 'ssafy');

create table `comments` (
                            comment_no int auto_increment primary key,
                            article_id int not null,
                            comment_content varchar(200),
                            comment_user_id varchar(20),
                            foreign key (article_id) references articles(article_id),
                            foreign key (comment_user_id) references users(user_id)
);

insert into `comments` (comment_content, comment_user_id, article_id)
values ('test comment insert1', 'ssafy', 1), ('test comment insert2', 'ssafy', 1), ('test comment insert3', 'ssafy', 1);
insert into `comments` (comment_content, comment_user_id, article_id)
values ('test comment insert1', 'ssafy', 2), ('test comment insert2', 'ssafy', 2), ('test comment insert3', 'ssafy', 2);


