
alter session set nls_date_format = 'dd/MON/yyyy hh24:mi:ss';

ALTER TABLE HasAuthor
drop constraint fk_hasAuthor;
ALTER TABLE Fine
drop constraint fk_fine;


drop table hasSubject;
drop table holdRequest;
drop table Fine;
drop table Borrowing;
drop table bookCopy;
drop table Book;
drop table Borrower;
drop table BorrowerType;
drop table hasAuthor;


drop sequence bid_counter;
drop sequence hid_counter;
drop sequence fid_counter;
drop sequence borid_counter;

CREATE TABLE BorrowerType(
type            VARCHAR(15),
bookTimeLimit   DATE,
PRIMARY KEY (type)
);

insert into BorrowerType Values
('clerk',NULL);
insert into BorrowerType Values
('librarian',NULL);
insert into BorrowerType Values
('borrower',NULL);
insert into BorrowerType Values
('student',NULL);
insert into BorrowerType Values
('faculty',NULL);
insert into BorrowerType Values
('staff',NULL);



CREATE SEQUENCE bid_counter
START WITH    1
INCREMENT BY 1;

CREATE TABLE Borrower(
    bid        	INTEGER NOT NULL,
    password    VARCHAR(32)NOT NULL,
    name        VARCHAR(20)NOT NULL,
    address   	VARCHAR(20)NOT NULL,
    phone       INTEGER NOT NULL,
    emailAddress     CHAR(32)NOT NULL,
    sinOrStNo   INTEGER UNIQUE,
    expiryDate	DATE,
    type        VARCHAR(15) NOT NULL,
PRIMARY KEY (bid),
FOREIGN KEY(type) REFERENCES BorrowerType(type) ON DELETE CASCADE
);

insert into Borrower values
(bid_counter.nextVal, '1234Cats', 'SUPERCAT', '123 Cat st.',
 4794922, 'theGREATKITTY@gmail.com',5,'31/may/98', 'librarian');


CREATE TABLE HasAuthor(
    callNumber    VARCHAR(20),
    name    VARCHAR(32),
PRIMARY KEY(callNumber, name)
);

CREATE TABLE HasSubject(
    callNumber    VARCHAR(20),
    subject    VARCHAR(20),
PRIMARY KEY(callNumber, subject)
);

CREATE TABLE BookCopy(
    callNumber  VARCHAR(20),
    copyNo    	INTEGER,
    status      VARCHAR(10),
PRIMARY KEY(callNumber, copyNo)
);

CREATE TABLE Book(
    callNumber  VARCHAR(20) NOT NULL,
    isbn        INTEGER UNIQUE,
    title       VARCHAR(255),
    mainAuthor  VARCHAR(32),
    publisher   VARCHAR(32),
    year        INTEGER,
PRIMARY KEY(callNumber)
);



ALTER TABLE HasAuthor
ADD CONSTRAINT fk_hasAuthor
FOREIGN KEY(callNumber)
REFERENCES Book(callNumber)
;

ALTER TABLE HasSubject
ADD FOREIGN KEY(callNumber)
REFERENCES Book(callNumber)
;

ALTER TABLE BookCopy
ADD FOREIGN KEY(callNumber)
REFERENCES Book(callNumber)
;



CREATE SEQUENCE hid_counter
START WITH    1
INCREMENT BY 1;


CREATE TABLE HoldRequest(
    hid       	INTEGER NOT NULL,
    bid   	INTEGER,
    callNumber  VARCHAR(20),
    issueDate   DATE,
PRIMARY KEY(hid),
FOREIGN KEY(bid) REFERENCES Borrower,
FOREIGN KEY(callNumber) REFERENCES Book
    );



CREATE SEQUENCE fid_counter
START WITH   1
INCREMENT BY 1;

CREATE TABLE Fine(
    fid        	INTEGER NOT NULL,
    amount   	FLOAT,
    issueDate   DATE,
    paidDate    DATE,
    borid       INTEGER,
PRIMARY KEY(fid)
);


CREATE SEQUENCE borid_counter
START WITH   1
INCREMENT BY 1;

CREATE TABLE Borrowing(
    borid      	INTEGER NOT NULL,
    bid        	INTEGER,
    callNumber  VARCHAR(20),
    copyNo    	INTEGER,
outDate    	DATE,
inDate        	DATE,
PRIMARY KEY(borid),
FOREIGN KEY(bid) 
REFERENCES Borrower(bid),
FOREIGN KEY(callNumber) 
REFERENCES Book(callNumber),
FOREIGN KEY(callNumber, copyNo) 
REFERENCES BookCopy(callNumber, copyNo)
);


ALTER TABLE Fine
ADD Constraint fk_fine
FOREIGN KEY(borid)
REFERENCES Borrowing(borid)
;

//INSERT


INSERT INTO BOOK VALUES('A323.347',2114567,'Garfield','OMGSUPERCAT','Cats inc.',1976);
INSERT INTO BOOK VALUES('G754.347',2156467,'Aint No Cat Like A Coo Cat','PuffDaddy','Cats inc.',2002);
INSERT INTO BOOK VALUES('R34Y.44F',7982353,'Catnip: Your Kittens and You','Mothers Against Catnip','DARE',2006);
INSERT INTO BOOK VALUES('AD98.6DH',2456743,'How 2Pac Got Shot','Lil Homie #3','Hustle House',2000);
INSERT INTO BOOK VALUES('E3K8.E8D',3563226,'Space','Chris Hatfeild','Hubble House',2013);
INSERT INTO BOOK VALUES('2KSH.37D',6785645,'Interstellar Overdrive','Roger Waters','Floyd Plublishing',1992);
INSERT INTO BOOK VALUES('GFG7.CC7',9729429,'Koding for Kids: Its Hip and Kool','Dr. Algo Rithm','Modern Mechanics Magazine',2014);
INSERT INTO BOOK VALUES('FNAE.OO7',5483626,'Dealing With Your Daughters Boyfriends','Tuco','Hells Angels Publishing',1962);


INSERT INTO BOOKCOPY VALUES('GFG7.CC7', 2, 'in');
INSERT INTO BOOKCOPY VALUES('R34Y.44F', 1, 'in');
INSERT INTO BOOKCOPY VALUES('E3K8.E8D', 1, 'in');
INSERT INTO BOOKCOPY VALUES('2KSH.37D', 1, 'in');
INSERT INTO BOOKCOPY VALUES('FNAE.007', 2, 'in');
INSERT INTO BOOKCOPY VALUES('FNAE.007', 1, 'on-hold');
INSERT INTO BOOKCOPY VALUES('A323.347', 1, 'out');
INSERT INTO BOOKCOPY VALUES('G754.347', 2, 'in');
INSERT INTO BOOKCOPY VALUES('AD98.6DH', 1, 'in');


insert into Borrower values
(bid_counter.nextVal, '1234Cats', 'OMGSUPERCAT', '123 Cat st.',
 4794322, 'theGREATKITTY@gmail.com',1,'31/may/98', 'librarian');

INSERT INTO BORROWING VALUES(123, 2 , 'GFG7.CC7', 2, '12/apr/13', NULL);
INSERT INTO BORROWING VALUES(124, 2 , 'A323.347', 1, '12/apr/13', NULL);
INSERT INTO BORROWING VALUES(125, 2 , 'FNAE.OO7', 1, '12/apr/13', NULL);


