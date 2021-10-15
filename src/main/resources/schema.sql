DROP TABLE IF EXISTS PERSONS;

CREATE TABLE PERSONS (
                         id INT AUTO_INCREMENT,
                         name VARCHAR(250) NOT NULL,
                         surname VARCHAR(250) NOT NULL,
                         phone VARCHAR(11) DEFAULT NULL,
                         passport VARCHAR(10) PRIMARY KEY,
                         requestAccount INT DEFAULT 0,
                         confirmedRequest INT DEFAULT 0
);

INSERT INTO PERSONS (name, surname, phone, passport) VALUES
                                                                     ('Denis', 'Makarov', '89150675376', '5673673567'),
                                                                     ('Ivan', 'Panin', '89104568765', '4518978634'),
                                                                     ('Maks', 'Zolotov', '89804624531', '4519745213'),
                                                                     ('Andrey', 'Morozov', '89765436765', '4519745218'),
                                                                     ('Dmitriy', 'Manoshin', '89104568432', '4563789854');

DROP TABLE IF EXISTS ACCOUNTS;

CREATE TABLE ACCOUNTS (
                         id INT AUTO_INCREMENT,
                         account VARCHAR(16) PRIMARY KEY,
                         balance DOUBLE,
                         person_id INT,
                         requestCard INT DEFAULT 0,
                         confirmedRequest INT DEFAULT 0,
                         FOREIGN KEY (person_id) REFERENCES PERSONS(id)
);

CREATE INDEX indexedAccount ON ACCOUNTS (account);

INSERT INTO ACCOUNTS (account, balance, person_id) VALUES
                                              ('4058354637462435', 2000.45, 1),
                                              ('7356454734625343', 5500.00, 1),
                                              ('6574635243567833', 6000.00, 2),
                                              ('9876567865432321', 7000.00, 2),
                                              ('2277556677443344', 9500.00, 3),
                                              ('7788665544332211', 9500.00, 3),
                                              ('8765374652343847', 8000.75, 4),
                                              ('8765374652343848', 8000.75, 5),
                                              ('8765374652343849', 8000.75, 5),
                                              ('7788654324567845', 10000.75, 5);

DROP TABLE IF EXISTS CARDS;

CREATE TABLE CARDS (
                          id INT AUTO_INCREMENT,
                          number VARCHAR (16) PRIMARY KEY,
                          state VARCHAR,
                          month_until INT,
                          year_until INT,
                          account_id INT,
                          FOREIGN KEY (account_id) REFERENCES ACCOUNTS(id),
                          CHECK (month_until > 0 AND month_until < 13),
                          CHECK (year_until > 20 AND year_until < 30)
);

CREATE INDEX indexedNumber ON CARDS (number);

INSERT INTO CARDS (number, state, month_until, year_until, account_id) VALUES
                                               ('2200283645372635', 'Active', 6, 25, 1),
                                               ('2300475634367493', 'Active', 7, 28, 2),
                                               ('4400354676542233', 'Frozen', 7, 27, 3),
                                               ('9876342625342628', 'Active', 7, 28, 4),
                                               ('2288776655667744', 'Active', 7, 28, 5),
                                               ('8899009988776655', 'Active', 7, 28, 6),
                                               ('2400353784634236', 'Frozen', 12, 22, 7),
                                               ('8599009988776657', 'Active', 7, 28, 8);

