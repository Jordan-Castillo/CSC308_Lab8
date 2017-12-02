import java.sql.*;

public class AdminMenu extends Menu{
	
	public AdminMenu(Connection conn){
		super(new String[] {"View Rooms", "View Reservations", "Clear All Records",
			"Load Database", "Remove Database", "Switch Subsystem"}, conn);
	}

	public void displayMenu() {
		//current status display
		printOptions();
		displayStatus();
	}
	
	/*
		Identical to displayMenu() except it does not clear the screen.
		Used following a query, so that we can display the query, and still show
			the usable options directly below. 
	*/
	public void displayMenuNoClear() {
		printOptionsNoClear();
		displayStatus();
	}
	/*
		Switch based of user submitted digit. Options listed below.
		0 - Displays Tuples of "rooms" table	
				First prints column names
				Next prints all tuples of that table
		1 - Displays Tuples of "rooms table", same functionality as 0
		2 - Clear all tuples from the "rooms" and "reservations" tables.
		3 - 
		4 - Drop the "rooms" and "reservations" tables from the selected database
		5 - Return to the SubsystemMenu
			
	*/
	public int inputSwitch() {
		while(true) {
			switch(getMenuSelection()) {
				case 0:
					displayTable("rooms");
					displayMenuNoClear();
					break;
				case 1:
					displayTable("reservations");
					displayMenuNoClear();
					break;
				case 2:
					clearTables();
					displayMenuNoClear();
					break;
				case 4:
					dropTables();
					displayMenuNoClear();
					break;
				case 5:
					return MC.SUBSYSTEM;
				default:
					System.out.println("Invalid Menu Selection");
			}
		}
	}
	/*
		dropTables()
			-First clears the screen
			-Second tries to drop both "reservation" and "rooms"
	*/
	public void dropTables(){
		clearScreen();
		if(tupleCount("rooms") < 0)
		{
			System.out.println("There are no tables available to drop.");
			return;
		}
		try{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE reservations;");
			stmt.executeUpdate("DROP TABLE rooms;");
			stmt.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/*
	
	
	*/
	public void clearTables()
	{
		clearScreen();
		int checker;
		if((checker = tupleCount("rooms")) < 0)
		{
			System.out.println("There is no database to clear.");
			return;
		}
		else if (checker == 0)
		{
			System.out.println("There are no records to clear.");
		}
		
		try{
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("DELETE FROM reservations;");
		stmt.executeUpdate("DELETE FROM rooms;");
		stmt.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/*
	
	
	*/
	public void displayTable(String tableName)
	{
		clearScreen();
		if(tupleCount(tableName) < 0)
		{
			System.out.println("Table " + tableName + " does not exist.");
			return;
		}
		try{
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM " + tableName + ";");
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
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/*
		Returns -1 if table does not exists
		Returns n if table exists and n is the tuple count
		0 <= n 
	*/
	public int tupleCount(String tableName){
		Statement stmt;
		int returnInt = -1;
		try{
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet res = meta.getTables(null, null, tableName, null);
			if(res.next()){
				stmt = conn.createStatement();
				res = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName + ";");
				if(res.next())
					returnInt = res.getInt(1);
				stmt.close();
			}
			res.close();
			return returnInt;
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.exit(-1);
		}
		return returnInt;
	}
	/*
	
	
	*/
	public void displayStatus(){
		boolean rms = false, rvs = false;
		int rmsCount = 0, rvsCount = 0;
		String status = "no database";
		Statement stmt;
		
		rmsCount = tupleCount("rooms");
		rvsCount = tupleCount("reservations");
		if(rmsCount == 0 && rvsCount == 0)
			status = "empty";
		else if(rmsCount > 0 && rvsCount > 0)
			status = "full";
		else
		{
			rvsCount = 0;
			rmsCount = 0;
		}
		System.out.println("Database Status: " + status + "\n" +
							"Reservations: " + rvsCount + "\n" + "Rooms: " + rmsCount);

	}

}
