DROP TABLE IF EXISTS CAR;

CREATE TABLE CAR (
    id int AUTO_INCREMENT NOT NULL,
    fin varchar(50) NOT NULL,
    brand varchar(25) NOT NULL,
    model varchar(50) NOT NULL,
    engine varchar(25) NOT NULL,
    PRIMARY KEY(id)
);