import java.sql.*;

public class OwnerMenu extends Menu{

	public OwnerMenu(Connection conn){
		super(new String[] {"Occupancy Overview", "View Revenue", "Review Rooms",
				"Review Reservations", "Return to Main"}, conn);
	}
	
	public void displayMenu() {
		printOptions();
	}
	
	public int inputSwitch() {
		while(true) {
			switch(getMenuSelection()) {
				case 4:
					return MC.SUBSYSTEM;
				default:
					System.out.println("Invalid Menu Selection");
			}
		}
	}
}
