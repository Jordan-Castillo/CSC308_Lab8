import java.sql.*;

public class AdminMenu extends Menu{
	
	public AdminMenu(Connection conn){
		super(new String[] {"View Rooms", "View Reservations", "Clear All Records",
			"Load Database", "Remove Database", "Switch Subsystem"}, conn);
	}
	
	public void displayMenu() {
		//current status display
		printOptions();
	}
	
	public int inputSwitch() {
		while(true) {
			switch(getMenuSelection()) {
				case 5:
					return MC.SUBSYSTEM;
				default:
					System.out.println("Invalid Menu Selection");
			}
		}
	}

}
