create database TPK charset utf8;

#档案
create table record(
	rid int primary key auto_increment,
	idCard char(20),
	number char(20) unique,
	password varchar(255),
	salt varchar(255),
	level int(2),
	isdelete int(1) default 0,
	created_time datetime,
	created_user char(16),
	modified_time datetime,
	modified_user char(16),
	remark varchar(255)	
);

#组表
create table groups(
	gid int primary key auto_increment,
	name char(16),
	created_time datetime,
	created_user char(16),
	modified_time datetime,
	modified_user char(16),
	remark varchar(255)	
);


#用户
create table user(
	uid int primary key auto_increment,
	phone varchar(32),
	password varchar(255),
	salt varchar(255),
	name char(27),
	gender int(2),
	age int(3),
	place varchar(128),
	isdelete int(1) default 0,
	recordNumber char(20),
	created_time datetime,
	created_user char(16),
	modified_time datetime,
	modified_user char(16),
	remark varchar(255),
	constraint fk_user_recordNumber foreign key(recordNumber) references record(number)
);

#员工表
create table emp(
	eid int primary key auto_increment,
	number varchar(30) unique,
	name char(27),
	idCard char(20),
	age int(2),
	gender int(1),
	salary double(10,2),
	gid int,
	created_time datetime,
	created_user char(16),
	modified_time datetime,
	modified_user char(16),
	remark varchar(255),
	constraint fk_emp_gid foreign key(gid) references groups(gid)
);

#管理员用户
create table admin(
	aid int primary key auto_increment,
	number varchar(30) unique,
	password varchar(225),
	salt varchar(225),
	authority int(2),
	eid int(20),
	created_time datetime,
	created_user char(16),
	modified_time datetime,
	modified_user char(16),
	login_time datetime,
	login_user char(16),
	remark varchar(255),
	constraint fk_admin_id foreign key(eid) references emp(eid)
);

#业务价格表
create table btprice(
	pid int primary key auto_increment,
	name varchar(125),
	price double(8,2),
	describes varchar(255),
	created_time datetime,
	created_user char(16),
	modified_time datetime,
	modified_user char(16),
	remark varchar(255)
);

#业务表
create table business(
	bid int primary key auto_increment,
	recordNumber char(20),
	eid int,
	number varchar(20) unique,
	type int,
	way int(2),
	progress int(2) default 0,
	isdelete int(1) default 0,
	execute_time  datetime,
	created_time datetime,
	created_user char(16),
	modified_time datetime,
	modified_user char(16),
	remark varchar(255),
	constraint fk_bussiness_recordNumber foreign key(recordNumber) references record(number),
	constraint fk_business_eid foreign key(eid) references emp(eid),
	constraint fk_business_pid foreign key(type) references btprice(pid)
);	





