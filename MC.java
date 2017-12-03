import java.time.*;
import java.sql.*;

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
			
	static final String overviewSingleFront = "SELECT * " +
			"FROM reservations rv " + 
			"JOIN rooms rm " + 
			"ON rv.roomID = rm.roomID " + 
			"WHERE '";
	static final String overviewSingleBack = "' BETWEEN rv.checkInDate AND rv.checkOutDate;";
	static final String reviewRoomsDate = "SELECT * " + 
				"FROM reservations rv " + 
				"JOIN rooms rm " + 
				"ON rv.roomID = rm.roomID " +
				"WHERE rv.checkInDate BETWEEN '";
	static final String reviewRoomsDateRoom = "SELECT * " + 
				"FROM reservations rv " + 
				"JOIN rooms rm " + 
				"ON rv.roomID = rm.roomID " +
				"WHERE rm.roomID = ";
	static final String dateRoomEnd = " AND rv.checkInDate BETWEEN '";
	//Stuff I added
	static final String guestRoomInfo =
			"SELECT RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor, nights.nights, (nights.nights / 365)" + 
			"FROM rooms, reservations," + 
			"	(SELECT SUM( IF((YEAR(res1.CheckIn) < 2010 AND YEAR(res1.Checkout) = 2010)," + 
			"			DATEDIFF(res1.Checkout, '2010-01-01')," + 
			"		IF((YEAR(res1.CheckIn) = 2010 AND YEAR(res1.Checkout) = 2010)," + 
			"			DATEDIFF(res1.Checkout, res1.CheckIn)," + 
			"		IF((YEAR(res1.CheckIn) = 2010 AND YEAR(res1.Checkout) > 2010)," + 
			"			DATEDIFF('2010-12-31', res1.CheckIn)," + 
			"            365)))) as nights," + 
			"		r1.RoomCode as roc" + 
			"	FROM rooms as r1, reservations as res1" + 
			"    WHERE r1.RoomCode = res1.Room AND" + 
			"		YEAR(res1.CheckIn) <= 2010 AND" + 
			"		YEAR(res1.Checkout) >= 2010" + 
			"	GROUP BY r1.RoomCode" + 
			"    ) as nights" +
			"WHERE rooms.RoomCode = reservations.Room AND" + 
			"	rooms.RoomCode = nights.roc AND" + 
			"	YEAR(CheckIn) <= 2010 AND" + 
			"    YEAR(Checkout) >= 2010" + 
			"GROUP BY RoomCode;";
	
	static String openRoomsDate(MyDate checkIn, MyDate checkOut) {
		return "SELECT rooms.RoomName" + 
				"FROM rooms, reservations" + 
				"WHERE rooms.RoomCode = reservations.Room AND" + 
				"	reservations.Checkout < " + checkIn.toSQLString() + 
				"    reservations.CheckIn > " + checkOut.toSQLString() + 
				"GROUP BY rooms.RoomCode;\r\n" + 
				"HAVING COUNT(*) = 1;";
	}
	
	static void displayTable(ResultSet res) { //couldn't test without DB connection
		try {
			ResultSetMetaData rsmd = res.getMetaData();
			int numColumns = rsmd.getColumnCount();
			for(int i = 1; i <= numColumns; i++)
				if(i < numColumns)
					System.out.print(rsmd.getColumnName(i) + ", ");
				else
					System.out.print(rsmd.getColumnName(i) + "\n");
			while(res.next())
			{
				for(int i = 1; i <= numColumns; i++)
					if(i < numColumns)
						System.out.print(res.getString(i) + ", ");
					else
						System.out.print(res.getString(i) + "\n");
			}
		}catch (SQLException e) {
			System.out.print("Error SQLException");
			System.exit(1);
		}
	}//close DisplayTable
	
}
