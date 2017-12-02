
public class MC {
	static final int QUIT = 0;
	static final int SUBSYSTEM = 1;
	static final int ADMIN = 2;
	static final int GUEST = 3;
	static final int OWNER = 4;
	
	static final String createRooms = "CREATE TABLE rooms" +
			"(roomID VARCHAR(8), roomName VARCHAR(30), bedNum TINYINT," +
			"bedType VARCHAR(10), maxOccupancy SMALLINT, basePrice SMALLINT," + 
			"decorStyle VARCHAR(30), PRIMARY KEY(roomID));";
			
	static final String createReservations = "CREATE TABLE reservations" +
			"(reservationCode INTEGER, roomID VARCHAR(8), checkInDate DATE," +
			"checkOutDate DATE, priceRate FLOAT, lastName VARCHAR(20)," + 
			"firstName VARCHAR(20), numAdults TINYINT, numKids TINYINT," +
			"PRIMARY KEY(reservationCode)," +
			"FOREIGN KEY(roomID) REFERENCES rooms(roomID));";
			
	static final String overviewSingleFront = "SELECT *" +
			"FROM reservations rv" + 
			"JOIN rooms rm" + 
			"ON rv.roomID = rm.roomID" + 
			"WHERE '2010-";
	static final String overviewSingleBack = "' BETWEEN rv.checkInDate AND rv.checkOutDate";
	
}
