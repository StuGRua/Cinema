SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
create table movie
(
    Movie_id   int          not null
        primary key,
    Movie_name varchar(20)  not null,
    Base_price float(11, 0) null,
    Start_time datetime     null,
    End_time   datetime     null,
    Last_time  int          null
)
    charset = utf8mb4;

INSERT INTO cinema.movie (Movie_id, Movie_name, Base_price, Start_time, End_time, Last_time) VALUES (1, '辉夜大小姐', 20, '2020-08-25 00:00:00', '2020-10-20 00:00:00', 120);
INSERT INTO cinema.movie (Movie_id, Movie_name, Base_price, Start_time, End_time, Last_time) VALUES (2, '小妇人', 30, '2020-08-25 00:00:00', '2020-09-25 00:00:00', 110);
INSERT INTO cinema.movie (Movie_id, Movie_name, Base_price, Start_time, End_time, Last_time) VALUES (3, '千与千寻', 35, '2020-06-21 00:00:00', '2019-07-10 00:00:00', 100);
INSERT INTO cinema.movie (Movie_id, Movie_name, Base_price, Start_time, End_time, Last_time) VALUES (4, '复仇者联盟', 30, '2012-05-04 00:00:00', '2012-05-31 00:00:00', 120);
INSERT INTO cinema.movie (Movie_id, Movie_name, Base_price, Start_time, End_time, Last_time) VALUES (5, '命运之夜', 30, '2020-08-25 00:00:00', '2008-05-31 00:00:00', 80);
INSERT INTO cinema.movie (Movie_id, Movie_name, Base_price, Start_time, End_time, Last_time) VALUES (6, '名侦探柯南', 30, '2020-08-25 00:00:00', '2020-08-31 00:00:00', 90);

create table moviehall
(
    Hall_id  int         not null
        primary key,
    Type     varchar(30) null,
    Row_sum  int         null,
    Line_sum int         null
)
    charset = utf8mb4;

INSERT INTO cinema.moviehall (Hall_id, Type, Row_sum, Line_sum) VALUES (1, '激光巨幕', 20, 10);
INSERT INTO cinema.moviehall (Hall_id, Type, Row_sum, Line_sum) VALUES (2, '杜比', 20, 10);
INSERT INTO cinema.moviehall (Hall_id, Type, Row_sum, Line_sum) VALUES (3, '3D 杜比', 20, 10);
INSERT INTO cinema.moviehall (Hall_id, Type, Row_sum, Line_sum) VALUES (4, 'IMAX 激光巨幕', 30, 15);
INSERT INTO cinema.moviehall (Hall_id, Type, Row_sum, Line_sum) VALUES (5, '激光', 20, 10);
INSERT INTO cinema.moviehall (Hall_id, Type, Row_sum, Line_sum) VALUES (6, 'VIP', 10, 5);
INSERT INTO cinema.moviehall (Hall_id, Type, Row_sum, Line_sum) VALUES (7, '激光', 20, 10);
INSERT INTO cinema.moviehall (Hall_id, Type, Row_sum, Line_sum) VALUES (8, 'VIP', 10, 5);
INSERT INTO cinema.moviehall (Hall_id, Type, Row_sum, Line_sum) VALUES (9, '巨幕 杜比', 20, 10);
INSERT INTO cinema.moviehall (Hall_id, Type, Row_sum, Line_sum) VALUES (10, '激光', 20, 10);

create table audience
(
    Aud_id   int auto_increment,
    Name     varchar(20)  not null,
    Password varchar(255) not null,
    Tel      varchar(30)  null,
    Type     varchar(20)  null,
    primary key (Aud_id, Name)
)
    charset = utf8mb4;

create index Aud_id
    on audience (Aud_id);

INSERT INTO cinema.audience (Aud_id, Name, Password, Tel, Type) VALUES (1, 'StuG', 'x74rtw05', '18687839701', 'Manager');
INSERT INTO cinema.audience (Aud_id, Name, Password, Tel, Type) VALUES (2, '朝喜', '2', '2', 'Manager');
INSERT INTO cinema.audience (Aud_id, Name, Password, Tel, Type) VALUES (4, '1', '1', '1', 'Manager');
INSERT INTO cinema.audience (Aud_id, Name, Password, Tel, Type) VALUES (5, 'hhh', 'x74rtw05', '199999', 'Authority');

create table `show`
(
    Hall_id   int      not null,
    Movie_id  int      not null,
    Show_time datetime not null,
    primary key (Hall_id, Movie_id, Show_time),
    constraint Hall_id
        foreign key (Hall_id) references moviehall (Hall_id),
    constraint Movie_id
        foreign key (Movie_id) references movie (Movie_id)
            on update cascade on delete cascade
)
    charset = utf8mb4;

create index Show_time
    on `show` (Show_time);

INSERT INTO cinema.`show` (Hall_id, Movie_id, Show_time) VALUES (1, 1, '2020-08-31 09:00:00');

create table arrange
(
    Aud_id       int          not null,
    Hall_id      int          not null,
    Movie_id     int          not null,
    Line         varchar(255) not null,
    Row          varchar(255) not null,
    Arrange_time datetime     not null,
    primary key (Aud_id, Hall_id, Arrange_time, Movie_id, Line, Row),
    constraint arrange_ibfk_1
        foreign key (Aud_id) references audience (Aud_id),
    constraint arrange_ibfk_2
        foreign key (Hall_id) references `show` (Hall_id)
            on update cascade on delete cascade,
    constraint arrange_ibfk_3
        foreign key (Movie_id) references `show` (Movie_id)
            on update cascade on delete cascade,
    constraint arrange_ibfk_4
        foreign key (Arrange_time) references `show` (Show_time)
            on update cascade on delete cascade
)
    charset = utf8mb4;

create index Arrange_time
    on arrange (Arrange_time);

create index Hall_id
    on arrange (Hall_id);

create index Movie_id
    on arrange (Movie_id);

INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '1', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '10', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '11', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '12', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '13', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '14', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '15', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '16', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '17', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '18', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '19', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '2', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '20', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '3', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '4', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '5', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '6', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '7', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '8', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '1', '9', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '2', '1', '2020-08-31 09:00:00');
INSERT INTO cinema.arrange (Aud_id, Hall_id, Movie_id, Line, Row, Arrange_time) VALUES (5, 1, 1, '8', '14', '2020-08-31 09:00:00');

SET FOREIGN_KEY_CHECKS = 1;