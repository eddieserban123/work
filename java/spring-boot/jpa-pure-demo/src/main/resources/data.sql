
insert into course(id, name, created, updated, is_deleted)
values(10001,'JPA in 50 Steps', sysdate(), sysdate(), false);
insert into course(id, name, created, updated, is_deleted)
values(10002,'Spring in 50 Steps', sysdate(), sysdate(),false);
insert into course(id, name, created, updated, is_deleted)
values(10003,'Spring Boot in 100 Steps', sysdate(), sysdate(),false);

insert into course(id, name, created, updated, is_deleted)
values(10004,'JPA in 54 Steps', sysdate(), sysdate(), false);
insert into course(id, name, created, updated,is_deleted)
values(10005,'Spring in 55 Steps', sysdate(), sysdate(), false);
insert into course(id, name, created, updated,is_deleted)
values(10006,'Spring Boot in 106 Steps', sysdate(), sysdate(), false);


insert into course(id, name, created, updated, is_deleted)
values(10007,'JPA in 57 Steps', sysdate(), sysdate(), false);
insert into course(id, name, created, updated, is_deleted)
values(10008,'Spring in 58 Steps', sysdate(), sysdate(), false);
insert into course(id, name, created, updated, is_deleted)
values(10009,'Spring Boot in 109 Steps', sysdate(), sysdate(), false);


insert into passport(id,number)
values(40001,'E123456');
insert into passport(id,number)
values(40002,'N123457');
insert into passport(id,number)
values(40003,'L123890');

insert into student(id,name,passport_id)
values(20001,'Ranga',40001);
insert into student(id,name,passport_id)
values(20002,'Adam',40002);
insert into student(id,name,passport_id)
values(20003,'Jane',40003);

insert into review(id,rating,description,course_id)
values(50001,'FIVE', 'Great Course',10001);
insert into review(id,rating,description, course_id)
values(50002,'FOUR', 'Wonderful Course',10001);
insert into review(id,rating,description,course_id)
values(50003,'FIVE', 'Awesome Course',10003);



insert into student_course(student_id,course_id)
values(20001,10001);
insert into student_course(student_id,course_id)
values(20002,10001);
insert into student_course(student_id,course_id)
values(20003,10001);
insert into student_course(student_id,course_id)
values(20001,10003);
