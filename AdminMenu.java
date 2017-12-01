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
	
	public void displayMenuNoClear() {
		printOptionsNoClear();
		displayStatus();
	}
	
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
				case 5:
					return MC.SUBSYSTEM;
				default:
					System.out.println("Invalid Menu Selection");
			}
		}
	}

	public void dropTables(){
		clearScreen();
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

	public void clearTables()
	{
		clearScreen();
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
	
	public void displayTable(String tableName)
	{
		clearScreen();
		if(tupleCount() < 0)
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
		int returnInt = 0;
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
		System.out.println("Database Status: " + status + "\n" +
							"Reservations: " + rvsCount + "\n" + "Rooms: " + rmsCount);
		
		/*
		try{
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet res = meta.getTables(null, null, "rooms", null);
			if(res.next())//determine if rooms table exists
			{	rms = true;
				stmt = conn.createStatement();
				res = stmt.executeQuery("Select COUNT(*) FROM rooms;");
				if(res.next())
					rmsCount = res.getInt(1);
				stmt.close();
			}
			res = meta.getTables(null, null, "reservations", null);
			if(res.next())//determine if reservations table exists
			{	rvs =  true;
				status = "empty";
				stmt = conn.createStatement();
				res = stmt.executeQuery("Select COUNT(*) FROM reservations;");
				if(res.next())
					rvsCount = res.getInt(1);
				stmt.close();
			}
			if(rmsCount > 0 && rvsCount > 0)
				status = "full";
			res.close();
			System.out.println("Database Status: " + status + "\n" +
							"Reservations: " + rvsCount + "\n" + "Rooms: " + rmsCount);
		}//END TRY BLOCK
		catch(Exception ex){
			System.out.println("Failure during dbStatus() call.");
			System.exit(-1);
		}
		*/
	}

}
