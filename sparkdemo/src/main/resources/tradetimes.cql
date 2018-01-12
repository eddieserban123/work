DROP KEYSPACE IF EXISTS test;
CREATE KEYSPACE test WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
use test;
DROP TABLE if EXISTS trades_by_time;
CREATE TABLE trades_by_time(day date , offset int, market_event varchar, aqrid int, tradetime_millis bigint, Primary Key(day, offset));

INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-01',123,'Future', 9,823);
INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-01',127,'Equity', 9,220);
INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-01',12,'Equity', 10,63);

INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-02',200,'Share', 4,100);
INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-02',99,'Future', 5,1120);
INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-02',5,'Equity', 4,60);


INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-04',100,'Share', 24,204);
INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-04',89,'Future', 25,25);
INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-04',5,'Quotes', 14,260);

INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-07',10000,'Share', 124,2004);
INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-07',8500,'Future', 125,725);
INSERT INTO  trades_by_time(day, offset, market_event , aqrid , tradetime_millis ) VALUES('2017-01-07',22300,'Quotes', 124,160);
