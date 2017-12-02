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
				case 2:
					return MC.SUBSYSTEM;
				default:
					System.out.println("Invalid Menu Selection");
			}
		}
	}

}
