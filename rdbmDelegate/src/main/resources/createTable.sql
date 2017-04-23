
create Table PERF_TEST(
	id number(12) primary key,
	user_name varchar2(512) not null,
	user_memo varchar2(2000), 
	create_dt date default (sysdate)			
)

create or replace SEQUENCE PERF_TEST_SEQ INCREMENT BY 1 NOCACHE NOCYCLE; 