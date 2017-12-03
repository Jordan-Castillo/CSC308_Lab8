import java.util.Scanner;
import java.time.LocalDate;

public class MyDate {
	LocalDate obDate;
	
	public MyDate(Scanner reader) {
		int year;
		int month;
		int day;
		System.out.print("Enter Year: ");
		year = reader.nextInt();
		System.out.print("Enter Month (Number): ");
		month = reader.nextInt();
		System.out.print("Enter Day: ");
		day = reader.nextInt();
		
		obDate = LocalDate.of(year, month, day);
	}
	
	public String toSQLString() {
		return obDate.getYear() + "-" + obDate.getMonthValue() + "-" + obDate.getDayOfMonth();
	}
	
	public static boolean SpansDate(int month, int day, MyDate checkIn, MyDate checkOut) {
		return ((checkIn.obDate.getMonthValue() < month || (checkIn.obDate.getMonthValue() == month && checkIn.obDate.getDayOfMonth() <= day)) &&
		(checkOut.obDate.getMonthValue() > month || (checkOut.obDate.getMonthValue() == month && checkOut.obDate.getDayOfMonth() >= day)));
	}
}
