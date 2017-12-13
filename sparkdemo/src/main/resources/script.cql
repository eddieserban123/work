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
