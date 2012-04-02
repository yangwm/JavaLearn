
/*
drop table book;
*/

create table book (
	id                      INT IDENTITY not null,
	title                   VARCHAR(32) not null,
	authors                 VARCHAR(32),
	--description				VARCHAR(32),
	--create_by				VARCHAR(32),
	--create_date				DATETIME,
	--update_by				VARCHAR(32),
	--update_date				DATETIME,
	CONSTRAINT book_id_pk 	PRIMARY KEY (id)
);


insert into book (title, authors) values ('C++ Primer Plus', 'duck');
insert into book (title, authors) values ('Thinking In Java', 'bruce eckel');
insert into book (title, authors) values ('a', 'a of authors');
insert into book (title, authors) values ('b', 'b of authors');
insert into book (title, authors) values ('c', 'c of authors');
insert into book (title, authors) values ('d', 'd of authors');
insert into book (title, authors) values ('e', 'e of authors');
insert into book (title, authors) values ('f', 'f of authors');
insert into book (title, authors) values ('g', 'g of authors');
insert into book (title, authors) values ('h', 'h of authors');
insert into book (title, authors) values ('i', 'i of authors');
insert into book (title, authors) values ('j', 'j of authors');
insert into book (title, authors) values ('k', 'k of authors');
insert into book (title, authors) values ('l', 'l of authors');
insert into book (title, authors) values ('m', 'm of authors');
insert into book (title, authors) values ('n', 'n of authors');
insert into book (title, authors) values ('o', 'o of authors');
insert into book (title, authors) values ('p', 'p of authors');
insert into book (title, authors) values ('q', 'q of authors');
insert into book (title, authors) values ('r', 'r of authors');
insert into book (title, authors) values ('s', 's of authors');
insert into book (title, authors) values ('t', 't of authors');
insert into book (title, authors) values ('u', 'u of authors');
insert into book (title, authors) values ('v', 'v of authors');
insert into book (title, authors) values ('w', 'w of authors');
insert into book (title, authors) values ('x', 'x of authors');
insert into book (title, authors) values ('y', 'y of authors');
insert into book (title, authors) values ('z', 'z of authors');
commit;
