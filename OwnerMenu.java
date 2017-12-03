import java.sql.*;
import java.util.*;

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
					break;
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
		userDate date = new userDate(reader);
		Statement stmt;
		ResultSet res;
		try{
			stmt = conn.createStatement();
			res = stmt.executeQuery(MC.overviewSingleFront + date.year + "-" + date.month + "-" + date.day + MC.overviewSingleBack);
			List<Tuple> table = createArray(res);
			printOverviewSingle(table);
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void printOverviewSingle(List<Tuple> table){
		String[] rooms = {"AOB", "CAS", "FNA", "HBB", "IBD", "IBS", "MWC", "RND", "RTE", "TAA"};
		boolean check = false;
		for(String room : rooms)
		{
			System.out.print(room + " : ");
			for(int i = 0; i < table.size(); i++)
			{
				System.out.println("'" + room + "' <---hardcoded room, roomID---> '" + table.get(i).roomID + "'");
				if(table.get(i).roomID == room)
					check = true;
			}
			if(check)
				System.out.print("Occupied" + "\n");
			else
				System.out.print("Empty" + "\n");
		}
	}
	
	public List<Tuple> createArray(ResultSet res){
		try{
			List<Tuple> table = new ArrayList<Tuple>();
			while(res.next()){
				table.add(new Tuple(res));
			}
			return table;
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.exit(-1);
		}
		return null;
	}
	
	
}
