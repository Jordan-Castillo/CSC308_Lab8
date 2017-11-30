import java.io.*;
import java.util.*;
import java.sql.*;

public class MenuRunner {

	public static void main(String[] args) {
		Connection conn = null;
		if((conn = makeConnection()) == null)	
			System.out.println("Failure to connect.");
		checkTables(conn);
		
		Menu current = new SubsystemMenu(conn);
		int mConst = MC.SUBSYSTEM;
		
		while(mConst != MC.QUIT) {
			current.displayMenu();
			mConst = current.inputSwitch();
			
			switch(mConst) {
				case MC.QUIT:
					System.out.println("Bye!");
					System.exit(0);
				case MC.SUBSYSTEM:
					current = new SubsystemMenu(conn);
					break;
				case MC.ADMIN:
					current = new AdminMenu(conn);
					break;
				default:
					System.out.println("MENU NOT RECOGNIZED");//placeholder
			}
		}//end of While loop
		closeConnection(conn); //close the DB connection
		
	}//close main
	

/*
 *	Determine if the tables exist, 
 *	otherwise call functions to create them
 * */
	public static void checkTables(Connection conn){
		try{
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet res = meta.getTables(null, null, "rooms", null);
			if(res.next());//TABLE EXISTS, MOVE ON
			else //CREATE rooms TABLE
				createRooms(conn);
			res = meta.getTables(null, null, "reservations", null);
			if(res.next()); //TABLE EXISTS, MOVE ON
			else //CREATE reservations TABLE
				createReservations(conn);
			res.close();
		}//End Try block 
		catch(Exception ex){
			System.out.println("Failure while checking tables existence.");
		}
	}
/*
 *	Creates the reservations table in the database
 * */
	public static void createReservations(Connection conn){
		String reservations = MC.createReservations;
		try{
			Statement statement = conn.createStatement();
			statement.executeUpdate(reservations);
			statement.close();
		}
		catch(Exception ex){
			System.out.println("Create reservations table failed.");
		}
	}
/*
 *	Creates the rooms table in the database
 * */
	public static void createRooms(Connection conn){
		String rooms = MC.createRooms;
		try{
			Statement statement = conn.createStatement();
			statement.executeUpdate(rooms);
			statement.close();
		}
		catch(Exception ex){
			System.out.println("Create rooms table failed.");
		}
	}
	
	public static void closeConnection(Connection conn) {
		try{conn.close();}
		catch(Exception ex){}
	}
/*
 *	Establishes connection to database after reading settings from ServerSettings.txt
 *	Will return connection object if successful, null otherwise
 * */
	public static Connection makeConnection(){
		String filename = "ServerSettings.txt";
		String url = null;
		String loginId = null;
		String password = null;
		try{
			//load mysql connector driver
      	Class.forName ("com.mysql.jdbc.Driver").newInstance();
			//read login credentials from file
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			url = bufferedReader.readLine();
			loginId = bufferedReader.readLine();
			password = bufferedReader.readLine();
			bufferedReader.close();
			return DriverManager.getConnection(url + "?user=" + loginId + "&password=" + password);
      }//End try for loading driver
      catch (Exception ex)
      {
         ex.printStackTrace();
			return null;
      }//End catch
	}//End makeConnection
	
	

}
