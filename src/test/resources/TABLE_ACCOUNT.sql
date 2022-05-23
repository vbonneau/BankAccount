CREATE TABLE IF NOT EXISTS ACCOUNT (
	ACCOUNT_ID integer not null auto_increment, 
	BALANCE float,
	USER_ID integer, 
	primary key (ACCOUNT_ID)) ;
	
alter table ACCOUNT add constraint foreign key (USER_ID) references USER (USER_ID);

INSERT INTO USER( ACCOUNT_ID, BALANCE, USER_ID) VALUES
	(1, 100, 1),
	(2, 500, 2);
