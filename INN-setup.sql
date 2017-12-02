/*
	By: Jordan Castillo
	Date: 10 / 2 / 17
	Email: jtcastil@calpoly.edu
	
	INN database setup script!
*/
CREATE TABLE rooms(
roomID VARCHAR(8),
roomName VARCHAR(30),
bedNum TINYINT,
bedType VARCHAR(10),
maxOccupancy SMALLINT,
basePrice SMALLINT,
decorStyle VARCHAR(30),
PRIMARY KEY(roomID)
);
CREATE TABLE reservations(
reservationCode INTEGER,
roomID VARCHAR(8),
checkInDate DATE,
checkOutDate DATE,
priceRate FLOAT,
lastName VARCHAR(20),
firstName VARCHAR(20),
numAdults TINYINT,
numKids TINYINT,
PRIMARY KEY(reservationCode),
FOREIGN KEY(roomID) REFERENCES rooms(roomID)
);
