create table users (

                       user_id serial unique primary key,
                       firstname text not null,
                       lastname text not null,
                       user_name text unique not null,
                       pssword text not null,
                       security_level int check (security_level >= 0 and  security_level < 3)
);

create table account (

                         accountid serial unique primary key,
                         userid int unique references users (user_id),
                         ballence double precision not null,
                         status int check(status >= 0 and status <3)

);

ALTER SEQUENCE account_accountid_seq RESTART WITH 1000 ;


create table transfer (

                          transaction_id serial unique primary key,
                          from_account_acid int references account (account_id) not null,
                          to_account_acid int not null,
                          amount double precision not null,
                          approval int check(approval >=0 and approval <3)


);



ALTER SEQUENCE users_user_id_seq RESTART WITH 1000 ;

insert into users ( first_name, last_name, user_name, pssword, level)
values('Chunkit', 'Yip', '510599', '33831023', 0);

select user_id from users where user_name = '510599' and pssword = '33831023';

select firstname, lastname, a.account_id, t.amount
from users u
         inner join
     account a on a.user_id = a.user_id
         inner join
     transfer t on a.account_id = t.from_account_acid
where t.approval =0 ;