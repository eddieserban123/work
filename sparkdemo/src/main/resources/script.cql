DROP KEYSPACE IF EXISTS test;
CREATE KEYSPACE test WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
use test;
DROP TABLE if EXISTS school;
CREATE TABLE school(id int  PRIMARY KEY, classroom int, name varchar, mark1 int, mark2 int);

INSERT INTO  school(id,classroom,name,mark1,mark2) VALUES(1,1,'Obamma', 9,8);
INSERT INTO  school(id,classroom,name,mark1,mark2) VALUES(2,1,'Peter', 6,8);
INSERT INTO  school(id,classroom,name,mark1,mark2) VALUES(3,1,'Paul', 9,7);
INSERT INTO  school(id,classroom,name,mark1,mark2) VALUES(4,2,'Marry', 10,9);
INSERT INTO  school(id,classroom,name,mark1,mark2) VALUES(5,3,'Owen', 10,10);
INSERT INTO  school(id,classroom,name,mark1,mark2) VALUES(6,3,'Peter', 6,8);
INSERT INTO  school(id,classroom,name,mark1,mark2) VALUES(7,1,'George', 5,6);
INSERT INTO  school(id,classroom,name,mark1,mark2) VALUES(8,1,'Steve', 2,4);

CREATE TABLE school2(id int , classroom int, name varchar, mark1 int, mark2 int, Primary key(classroom,id));

INSERT INTO  school2(id,classroom,name,mark1,mark2) VALUES(1,1,'Obamma', 9,8);
INSERT INTO  school2(id,classroom,name,mark1,mark2) VALUES(2,1,'Peter', 6,8);
INSERT INTO  school2(id,classroom,name,mark1,mark2) VALUES(3,1,'Paul', 9,7);
INSERT INTO  school2(id,classroom,name,mark1,mark2) VALUES(4,2,'Marry', 10,9);
INSERT INTO  school2(id,classroom,name,mark1,mark2) VALUES(5,3,'Owen', 10,10);
INSERT INTO  school2(id,classroom,name,mark1,mark2) VALUES(6,3,'Peter', 6,8);
INSERT INTO  school2(id,classroom,name,mark1,mark2) VALUES(7,1,'George', 5,6);
INSERT INTO  school2(id,classroom,name,mark1,mark2) VALUES(8,1,'Steve', 2,4);

DROP TABLE if EXISTS trades_by_time;
CREATE TABLE trades_by_time(day date , offset int, market_event varchar, aqrid int, tradetime_millis bigint, Primary Key(day, offset));

INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-01',123,'Future', 9,823);
INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-01',127,'Equity', 9,220);
INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-01',12,'Equity', 10,63);

INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-02',200,'Share', 4,100);
INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-02',99,'Future', 5,1120);
INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-02',5,'Equity', 4,60);


INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-03',100,'Share', 24,204);
INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-03',89,'Future', 25,25);
INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-03',5,'Quotes', 14,260);
