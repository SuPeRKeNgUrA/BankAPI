DROP TABLE IF EXISTS PERSONS;

CREATE TABLE PERSONS (
                         id INT AUTO_INCREMENT NOT NULL,
                         name VARCHAR(250) NOT NULL,
                         surname VARCHAR(250) NOT NULL,
                         phone VARCHAR(11) DEFAULT NULL,
                         passport VARCHAR(10) NOT NULL,
                         PRIMARY KEY (id)
);

INSERT INTO PERSONS (name, surname, phone, passport) VALUES
                                                                     ('Denis', 'Makarov', '89150675376', '5673673567'),
                                                                     ('Ivan', 'Panin', '89104568765', '4518978634'),
                                                                     ('Maks', 'Zolotov', '89804624531', '4519745213');

DROP TABLE IF EXISTS ACCOUNTS;

CREATE TABLE ACCOUNTS (
                         id INT AUTO_INCREMENT NOT NULL,
                         account VARCHAR(16),
                         balance DOUBLE,
                         person_id INT,
                         PRIMARY KEY (id),
                         FOREIGN KEY (person_id) REFERENCES PERSONS(id)
);

INSERT INTO ACCOUNTS (account, balance, person_id) VALUES
                                              ('4058354637462435', 2000.45, 2),
                                              ('7356454734625343', 5500.00, 1),
                                              ('8765374652343847', 8000.75, 2);

DROP TABLE IF EXISTS CARDS;

CREATE TABLE CARDS (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          number VARCHAR (16),
                          state VARCHAR,
                          month_until INT,
                          day_until INT,
                          account_id INT,
                          FOREIGN KEY (account_id) REFERENCES ACCOUNTS(id),
                          CHECK (month_until > 0 AND month_until < 13),
                          CHECK (day_until > 0 AND day_until < 31)
);

INSERT INTO CARDS (number, state, month_until, day_until, account_id) VALUES
                                               ('2200283645372635', 'Active', 6, 12, 1),
                                               ('2300475634367493', 'Active', 7, 28, 2),
                                               ('2400353784634236', 'Frozen', 12, 5, 3);

