import java.sql.*;

public class GuestMenu extends Menu{
	
	public GuestMenu(Connection conn){
		super(new String[] {"Rooms and Rates", "Reservations", "Return to Main"}, conn);
	}
	
	public void displayMenu() {
		printOptions();
	}
	
	public int inputSwitch() {
		while(true) {
			switch(getMenuSelection()) {
				case 0:
					roomRates();
					break;
				case 1:
					reservation();
					break;
				case 2:
					return MC.SUBSYSTEM;
				default:
					System.out.println("Invalid Menu Selection");
			}
		}
	}
	
	public void roomRates() { //not yet tested, connection not working
		int menuSelect;
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(MC.guestRoomInfo);
			MC.displayTable(res);//not tested, couldn't get connection to work the query is written though
			
		} catch (SQLException e) {
			System.out.print("SQLException");
			System.exit(1);
		}
		
		System.out.println("0: Make Reservation\n1: Return to Guest Menu\n");
		while(true) {
			menuSelect = getMenuSelection();
			if(menuSelect == 0) {
				reservation();
			}else if(menuSelect != 1) {
				System.out.println("Invalid menu selection, try again");
			}
		}
	}//close roomRates
	
	public void displayOpenRooms(MyDate checkIn, MyDate checkOut) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(MC.openRoomsDate(checkIn, checkOut));
			MC.displayTable(res);
		}catch (SQLException e) {
			System.out.print("SQLException");
			System.exit(1);
		}
	}
	
	public void reservation() {
		MyDate checkIn;
		MyDate checkOut;
		double rateMult;
		String roomCode;
		String fName;
		String lName;
		int adults;
		int children;
		int rDis;
		
		System.out.print("Enter desired check-in date: ");
		checkIn = new MyDate(reader);
		System.out.print("Enter desired check-out date: ");
		checkOut = new MyDate(reader);
		rateMult = getRateMult(checkIn, checkOut);
		
		displayOpenRooms(checkIn, checkOut);
		
		System.out.print("Enter an available room code: ");
		roomCode = reader.nextLine();
		System.out.print("Enter your first name: ");
		fName = reader.nextLine();
		System.out.print("Enter number of adults: ");
		adults = reader.nextInt();
		System.out.print("Enter number of children: ");
		children = reader.nextInt();
		while(true) {
			System.out.println("Enter any rate discounts:\n0: None\n1:AARP\n2:10%");
			rDis = getMenuSelection();
			if(rDis == 0) {
				break;
			}else if(rDis == 1) {
				rateMult = rateMult * 0.85;
				break;
			}else if(rDis == 2) {
				rateMult = rateMult * 0.9;
				break;
			}else {
				System.out.println("Invalid menu selection, try again.");
			}
		}//close while
		
		while(true) {
			System.out.println("Enter final confirmation:\n0:Place Reservation\n1:Cancel");
			rDis = getMenuSelection();
			if(rDis == 0) {
				//PLACE RESERVATION METHOD HERE, INFO GATHERED
				break;
			}else if(rDis == 1) {
				break;
			}else {
				System.out.println("Invalid menu selection, try again.");
			}
		}
	}
	
	public double getRateMult(MyDate checkIn, MyDate checkOut) {
		if(checkIn.obDate.getYear() < checkOut.obDate.getYear() ||
				(checkIn.obDate.getMonthValue() == 1 && checkIn.obDate.getDayOfMonth() == 1)) {
			return 1.25;
		}else if(MyDate.SpansDate(10, 30, checkIn, checkOut) || MyDate.SpansDate(7, 4, checkIn, checkOut) ||
				MyDate.SpansDate(9, 6, checkIn, checkOut)) {
			return 1.25;
		}else{
			return 1.0;
		}
	}

}
