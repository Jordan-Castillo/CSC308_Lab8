import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.sql.*;


public class Menu {
	
	private ArrayList<String> options;
	private static Scanner reader = new Scanner(System.in);
	private Connection conn;
	
	public Menu(String[] opts, Connection conn) {
		options = new ArrayList<String>(Arrays.asList(opts));
		this.conn = conn;
	}
	
	public static void clearScreen(){
		System.out.print("\033[H\033[2J");  
	    System.out.flush();
	}
	
	public void printOptions() {
		int i = 0;
		
		clearScreen();
		for (String opt : options){
			System.out.println(i + ": " + opt);
			i++;
		}
	}
	
	public void displayMenu() {
		//to be overwritten by subclasses
	}
	
	public int getMenuSelection() {
		int ret;
		
		System.out.print("Enter Choice #: ");
		ret = reader.nextInt();
		return ret;
	}
	
	public int inputSwitch() {
		//to be overwritten by subclasses
		return 0;
	}
}

