CREATE TABLE users (

                       user_id  SERIAL UNIQUE PRIMARY KEY ,
                       firstname TEXT NOT NULL,
                       lastname TEXT NOT NULL,
                       user_name TEXT UNIQUE NOT NULL,
                       pssword TEXT NOT NULL,
                       security_level int check (security_level >= 0 and  security_level < 3)
);

CREATE TABLE account (

                         accountid SERIAL UNIQUE PRIMARY KEY,
                         userid INT UNIQUE REFERENCES users (user_id),
                         balance  DOUBLE PRECISION NOT NULL,
                         status INT CHECK(status >= 0 and status <3)

);

ALTER SEQUENCE account_accountid_seq RESTART WITH 1000 ;


create table transfer (

                          transaction_id SERIAL UNIQUE PRIMARY KEY,
                          from_account_acid INT REFERENCES account (account_id) not null,
                          to_account_acid INT NOT NULL,
                          amount DOUBLE PRECISION NOT NULL,
                          approval INT CHECK(approval >=0 and approval <3)


);



ALTER SEQUENCE users_user_id_seq RESTART WITH 1000 ;

INSERT INTO users ( first_name, last_name, user_name, pssword, level)
VALUES('Chunkit', 'Yip', '510599', '33831023', 0);

SELECT user_id FROM users WHERE user_name = '510599' AND pssword = '33831023';

SELECT firstname, lastname, a.account_id, t.amount
FROM users u
         INNER JOIN
     account a ON user_id = a.user_id
         INNER JOIN
     transfer t ON a.account_id = t.from_account_acid
WHERE t.approval =0 ;

select * from transfer;