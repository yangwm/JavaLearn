--Oracle :
create table coffees (
	cof_name 	varchar2(32),
	sup_id 		number, 
	price 		number, 
	sales 		number, 
	total 		number
);
create table suppliers (
	sup_id		number,
	sup_name	varchar2(32),
	street		varchar2(32),
	city		varchar2(32),
	state		varchar2(32),
	zip			number
);

insert into coffees (cof_name, sup_id, price, sales,total)
values ('Colombian',101,7.99,0,0) ; 
insert into coffees (cof_name, sup_id, price, sales,total)
values ('French_Roast',49,8.99,0,0) ; 
insert into coffees (cof_name, sup_id, price, sales,total)
values ('Espresso',150,9.99,0,0) ;
insert into coffees (cof_name, sup_id, price, sales,total)
values ('Colombian_Decaf',101,8.99,0,0) ;
insert into coffees (cof_name, sup_id, price, sales,total)
values ('French_Roast_Decaf',49,9.99,0,0) ;
commit;

insert into suppliers (sup_id,sup_name,street,city,state,zip)
values (101,'Acme, Inc.','99 Market Street','Groundsville','CA',95199) ;
insert into suppliers (sup_id,sup_name,street,city,state,zip)
values (49,'Superior Coffee','1 Party Place','Mendocino','CA',95460) ;
insert into suppliers (sup_id,sup_name,street,city,state,zip)
values (150,'The High Ground','100 Coffee Lane','Meadows','CA',93966) ;
commit;

select * from coffees ;
select * from suppliers ;

-- drop table coffees ;
-- drop table suppliers;
