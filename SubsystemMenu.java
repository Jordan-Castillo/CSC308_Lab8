import java.sql.*;

public class SubsystemMenu extends Menu{
	
	public SubsystemMenu(Connection conn) {
		super(new String[] {"ADMIN", "GUEST", "OWNER", "QUIT"}, conn);
	}
	
	public void displayMenu() {
		printOptions();
	}
	
	public int inputSwitch() {
		while(true) {
			switch(getMenuSelection()) {
				case 0:
					return MC.ADMIN;
				case 1:
					return MC.GUEST;
				case 2:
					return MC.OWNER;
				case 3:
					return MC.QUIT;
				default:
					System.out.println("Invalid Menu Selection");
			}//close switch
		}//close while
	}//close inputSwitch
}
