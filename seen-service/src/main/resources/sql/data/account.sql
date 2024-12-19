use seen;
insert into coin_account (id, account_type, description, create_time)
values (1,1,'系统账户',CURRENT_TIMESTAMP);
insert into coin_account (id, account_type, description, create_time)
values (2,2,'系统账户',CURRENT_TIMESTAMP);
insert into coin_account (id, account_type, description, create_time)
values (3,3,'系统账户',CURRENT_TIMESTAMP);
insert into coin_account (id, account_type, description, create_time)
values (4,4,'系统账户',CURRENT_TIMESTAMP);


insert into coin_sys_account_balance (id, account_id, coin_amount, change_time, update_time, create_time)
values (1,1,0,now(),now(),now());

insert into coin_sys_account_balance (id, account_id, coin_amount, change_time, update_time, create_time)
values (2,2,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

insert into coin_sys_account_balance (id, account_id, coin_amount, change_time, update_time, create_time)
values (3,3,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

insert into coin_sys_account_balance (id, account_id, coin_amount, change_time, update_time, create_time)
values (4,4,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);