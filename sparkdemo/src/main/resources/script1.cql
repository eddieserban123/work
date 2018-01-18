DROP KEYSPACE IF EXISTS test1;
CREATE KEYSPACE test1 WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 2};
use test1;
DROP TABLE if EXISTS trades_by_time1;

CREATE TABLE trades_by_time1(day date , offset int, market_event varchar, aqrid int, tradetime_millis bigint, Primary Key(day, offset));
