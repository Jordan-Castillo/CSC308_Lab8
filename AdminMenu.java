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
	
	public int inputSwitch() {
		while(true) {
			switch(getMenuSelection()) {
				case 0:
					displayTable("rooms");
					break;
				case 1:
					displayTable("reservations");
					break;
				case 5:
					return MC.SUBSYSTEM;
				default:
					System.out.println("Invalid Menu Selection");
			}
		}
	}
	
	public void displayTable(String tableName)
	{
		try{
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM " + tableName + ";");
			while(res.next())
			{
				System.out.println(res.getString(1));
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void displayStatus(){
		boolean rms = false, rvs = false;
		int rmsCount = 0, rvsCount = 0;
		String status = "no database";
		Statement stmt;
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
	}

}
