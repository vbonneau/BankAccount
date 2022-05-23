CREATE TABLE IF NOT EXISTS USER (
	USER_ID integer not null auto_increment, 
	PASSWORD varchar(255), 
	USERNAME varchar(255), 
	primary key (USER_ID)) ;

INSERT INTO USER( USER_ID, USERNAME, PASSWORD) VALUES
	(1, 'test', 'test'),
	(2, 'test2', 'test2');
