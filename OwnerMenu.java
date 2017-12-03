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
					displayMenu();
					break;
				case 2:
					reviewRooms();
					displayMenu();
					break;
				case 4:
					return MC.SUBSYSTEM;
				default:
					System.out.println("Invalid Menu Selection");
			}
		}
	}
	
	public void reviewRooms(){
		System.out.println("1: Review rooms by Date + Room");
		System.out.println("2: Review rooms by Date only");
		int num = getMenuSelection();
		while(true)
		{
			if(num == 1)
				return;
			else if(num == 2)
			{
				reviewRoomsDate();
				return;
			}
			else	
				System.out.println("Only 1 and 2 are acceptable inputs.");
		}
	}
	
	public void reviewRoomsDate(){
		List<Tuple> table;
		System.out.println("Enter the start date");
		userDate startDate = new userDate(reader);
		System.out.println("Enter the end date");
		userDate endDate = new userDate(reader);
		try{
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(MC.reviewRoomsDate + startDate.year + "-" + startDate.month + "-" + startDate.day + "' AND '" +
												endDate.year + "-" + endDate.month + "-" + endDate.day + "';");
			table = createArray(res);
			printReviewRoomsDate(table);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void printReviewRoomsDate(List<Tuple> table){
		System.out.println("Reservation Code, CheckInDate, Room Name");
		Tuple tuple;
		int input;
		for(int i = 0; i < table.size(); i++)
		{
			tuple = table.get(i);
			System.out.println(tuple.reservationCode + ",	" + tuple.checkInDate + ",	" + tuple.roomName);
		}
		System.out.println("Enter Reservation Code to see more information about a specific reservation.");
		input = reader.nextInt();
		System.out.println(input);
		
		for(int i = 0; i < table.size(); i++)
		{
			//System.out.println("ResCode from DB: '" + table.get(i).reservationCode + "'");
			if(table.get(i).reservationCode == input)
				printTupleData(table.get(i));
		}
		System.exit(0);
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
	
	//needs to be implemented
	public void overviewDouble(){
		
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
			specificOverviewSingle(table);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void specificOverviewSingle(List<Tuple> table){
		System.out.println("Enter occupied roomID to see details on reservation, or 'Q' to quit.");
		String input = reader.nextLine();
		input = reader.nextLine();
		boolean check = false;
		if(input.equals('Q') || input.equals('q'))
			return;
		for(int i = 0; i < table.size(); i++)
			if(table.get(i).found && table.get(i).roomID.equals(input))
			{
				printTupleData(table.get(i));	//call function to print object data
				return;
			}
		System.out.println("The selected room is Unoccupied.");
		System.out.println("Enter any key to return to the Owner menu.");
		reader.nextLine();
	}
	
	public void printTupleData(Tuple tuple){
		System.out.println("Reservation Code: " + tuple.reservationCode);
		System.out.println("RoomID: " + tuple.roomID);
		System.out.println("CheckIn Date: " + tuple.checkInDate);
		System.out.println("CheckOut Date: " + tuple.checkOutDate);
		System.out.println("Price Rate: " + tuple.priceRate);
		System.out.println("Name: " + tuple.firstName + tuple.lastName);
		System.out.println("Number of Children: " + tuple.numKids);
		System.out.println("Number of Adults: " + tuple.numAdults);
		System.out.println("Room Name: " + tuple.roomName);
		System.out.println("Number of Beds: " + tuple.bedNum);
		System.out.println("Type of Bed: " + tuple.bedType);
		System.out.println("Maximum Occupancy: " + tuple.maxOccupancy);
		System.out.println("Base Price: " + tuple.basePrice);
		System.out.println("Style of Decor: " + tuple.decorStyle);
		System.out.println("Enter any key to return to the Owner menu.");
		reader.nextLine();
	}
	public void printOverviewSingle(List<Tuple> table){
		String[] rooms = {"AOB", "CAS", "FNA", "HBB", "IBD", "IBS", "MWC", "RND", "RTE", "TAA"};
		boolean check = false;
		for(String room : rooms)
		{
			System.out.print(room + " : ");
			for(int i = 0; i < table.size(); i++)
			{
				//System.out.println("'" + room + "' <---hardcoded room, roomID---> '" + table.get(i).roomID + "'");
				if(table.get(i).roomID.equals(room))
				{
					check = true;
					table.get(i).found = true;
				}
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
