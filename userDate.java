import java.util.Scanner;

public class userDate {
	int year;
	int month;
	int day;
	
	public userDate(Scanner reader) {
		System.out.print("Enter Year: ");
		year = reader.nextInt();
		System.out.print("Enter Month (Number): ");
		month = reader.nextInt();
		System.out.print("Enter Day: ");
		day = reader.nextInt();
	}
}
