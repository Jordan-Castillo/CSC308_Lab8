import java.sql.*;

public class OwnerMenu extends Menu{

	public OwnerMenu(Connection conn){
		super(new String[] {"Occupancy Overview", "View Revenue", "Review Rooms",
				"Review Reservations", "Return to Main"}, conn);
	}
	
	public void displayMenu() {
		printOptions();
	}
	
	/*
		inputSwitch()
			-
			-
	*/
	public int inputSwitch() {
		while(true) {
			switch(getMenuSelection()) {
				case 0:
					occupancyOverview();
				case 4:
					return MC.SUBSYSTEM;
				default:
					System.out.println("Invalid Menu Selection");
			}
		}
	}
	
	public void occupancyOverview(){
		clearScreen();
		System.out.println("Are you inquiring about 1 date or 2? Input 1 or 2");
		int select;
		if((select = getMenuSelection()) == 1)
			overviewSingle();
		else
			;
		
	}
	public void overviewSingle(){
		clearScreen();
		int month;
		int day;
		Statement stmt;
		ResultSet res;
		System.out.println("Enter the date: Month first, then day. Both as digits.");
		month = reader.nextInt();
		day = reader.nextInt();
		try{
			stmt = conn.createStatement();
			res = stmt.executeQuery(MC.overviewSingleFront + month + "-" + day + MC.overviewSingleBack);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void createArray(ResultSet res){
		
	}
	
	
}
