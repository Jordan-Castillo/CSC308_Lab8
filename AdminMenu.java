
public class AdminMenu extends Menu{
	
	public AdminMenu(){
		super(new String[] {"View Rooms", "View Reservations", "Clear All Records",
			"Load Database", "Remove Database", "Switch Subsystem"});
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
