import java.sql.*;

public class Tuple{
	int reservationCode; //1
	String roomID;		//2
	String checkInDate; //3
	String checkOutDate; //4 
	float priceRate; 	//5
	String lastName;	//6
	String firstName;	//7
	int numAdults;	//8
	int numKids;	//9
	//String roomID;	//10
	String roomName;	//11
	int bedNum;		//12
	String bedType;	//13
	int maxOccupancy;	//14
	int basePrice;	//15
	String decorStyle;	//16
	
	Tuple(ResultSet res){
		try{
			reservationCode = res.getInt(1);
			roomID = res.getString(2);
			checkInDate = res.getString(3);
			checkOutDate = res.getString(4);
			priceRate = res.getFloat(5);
			lastName = res.getString(6);
			firstName = res.getString(7);
			numAdults = res.getInt(8);
			numKids = res.getInt(9);
			roomName = res.getString(11);
			bedNum = res.getInt(12);
			bedType = res.getString(13);
			maxOccupancy = res.getInt(14);
			basePrice = res.getInt(15);
			decorStyle = res.getString(16);
		}
		catch(SQLException e){
			e.printStackTrace();
			System.exit(-1);
		}
	}
}